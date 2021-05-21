/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atsmike.models.ErrorViewModel;
import com.nbcc.atssystem.business.EmployeeServiceFactory;
import com.nbcc.atssystem.business.IEmployeeService;
import com.nbcc.atssystem.business.ITaskService;
import com.nbcc.atssystem.business.TaskServiceFactory;
import com.nbcc.atssystem.models.EmployeeFactory;
import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class EmployeeController extends CommonController {

    private static final String EMPLOYEES_VIEW = "/employees.jsp";
    private static final String EMPLOYEES_MAINT_VIEW = "/employee.jsp";
    private static final String EMPLOYEE_SUMMARY_VIEW = "/employeesummary.jsp";
    private static final String EMPLOYEE_TASKS_ADD = "/addskills.jsp";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        IEmployeeService empService = EmployeeServiceFactory.createInstance();
        ITaskService taskService = TaskServiceFactory.createInstance();

        //We want to get to a specific Employee
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");

            int id = super.getInteger(pathParts[1]);

            String create = pathParts[1];
            String addSkills = "";
            if (pathParts.length > 2) {
                addSkills = pathParts[2];
            }

            //Get the employee and put it to a variable
            IEmployee employee = empService.getEmployee(id);

            //for add skills
            List<ITask> tasks = taskService.getEmpAvailableTasks(id);

            if ("create".equals(create)) {
                request.setAttribute("isFromCreate", 1);
                request.setAttribute("employee", employee);
                request.setAttribute("tasks", empService.getEmployeeTasks(id));
                super.setView(request, EMPLOYEES_MAINT_VIEW);

            } else if ("addskills.jsp".equals(addSkills)) {
                request.setAttribute("tasks", empService.getEmployeeTasks(id));
                request.setAttribute("employee", employee);
                request.setAttribute("availableTasks", taskService.getEmpAvailableTasks(id));
                super.setView(request, EMPLOYEE_TASKS_ADD);

            } else {
                if (employee != null) {
                    request.setAttribute("employee", employee);
                    request.setAttribute("tasks", empService.getEmployeeTasks(id));
                    request.setAttribute("teams", empService.getEmployeeTeams(id));

                } else {
                    request.setAttribute("error", new ErrorViewModel(String.format("Invoice ID: %s is not found", id)));
                    super.setView(request, EMPLOYEES_MAINT_VIEW);
                }

                //Set attribute as employee or error
                super.setView(request, EMPLOYEES_MAINT_VIEW);
            }

        } else {
            //We want to show the Employee List
            request.setAttribute("employees", empService.getEmployeeList());
            super.setView(request, EMPLOYEES_VIEW);
        }

        super.getView().forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.setView(request, EMPLOYEE_SUMMARY_VIEW);

        IEmployeeService empService = EmployeeServiceFactory.createInstance();
        IEmployee emp;

        ITaskService taskService = TaskServiceFactory.createInstance();
        ITask task;

        try {
            String actionFull = super.getValue(request, "action");
            String action = "";
            int deletionId = 0;
            if (actionFull.contains("/")) {
                String[] pathParts = actionFull.split("/");
                deletionId = super.getInteger(pathParts[1]);
                action = pathParts[0];
            } else {
                action = actionFull;
            }

            int id = super.getInteger(request, "hdnEmployeeId");

            switch (action.toLowerCase()) {
                case "create":
                    emp = setEmployee(request);
                    emp = empService.createEmployee(emp);

                    request.setAttribute("employee", emp);
                    request.setAttribute("isFromCreate", 1);
                    if (!empService.isValid(emp)) {
                        request.setAttribute("errors", emp.getErrors());
                        super.setView(request, EMPLOYEES_VIEW);
                    }
                    break;
                case "search":
                    String searchString = super.getValue(request, "searchString");
                    request.setAttribute("employees", empService.getEmployeeListBySearch(searchString));
                    super.setView(request, EMPLOYEES_VIEW);
                    break;

                case "delete":
                    String isDeleted = super.getValue(request, "isDeleted");
                    emp = setEmployee(request);
                    emp.setId(id);

                    request.setAttribute("employee", emp);

                    if (isDeleted == null && deletionId == 0) {
                        request.setAttribute("isDeleted", 1);
                        request.setAttribute("idToDelete", id);
                        request.setAttribute("itemToDelete", "employee");
                        super.setView(request, EMPLOYEES_MAINT_VIEW);
                    } else {
                        if (empService.deleteEmployee(id) == 0) {
                            emp.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                             request.setAttribute("isDeleted", null);
                            request.setAttribute("errors", emp.getErrors());
                            request.setAttribute("tasks", empService.getEmployeeTasks(id));
                            super.setView(request, EMPLOYEES_MAINT_VIEW);
                        } else {
                            request.setAttribute("isSuccess", 1);
                            request.setAttribute("employees", empService.getEmployeeList());
                            super.setView(request, EMPLOYEES_VIEW);
                        }
                    }
                    break;

                case "reset":
                    request.setAttribute("employees", empService.getEmployeeList());
                    super.setView(request, EMPLOYEES_VIEW);

                case "add skill(s)":

                    emp = empService.getEmployee(id);
                    emp.setId(id);

                    String[] selectedTasksId_param = request.getParameterValues("selectedTasks");
                    List<Integer> selectedTasksId = new ArrayList();

                    if (selectedTasksId_param != null) {
                        for (String selId : selectedTasksId_param) {
                            selectedTasksId.add(Integer.parseInt(selId));
                        }
                    } else {

                        emp.addError(ErrorFactory.createInstance(15, "Task is required"));
                    }

                    request.setAttribute("employee", emp);
                    request.setAttribute("tasks", empService.getEmployeeTasks(id));
                    request.setAttribute("teams", empService.getEmployeeTeams(id));
                    request.setAttribute("isTaskAdded", 1);
                    request.setAttribute("availableTasks", taskService.getEmpAvailableTasks(id));
                    request.setAttribute("tasks", empService.getEmployeeTasks(id));

                    if (empService.addEmployeeSkills(id, selectedTasksId)) {
                        request.setAttribute("isSuccess", 1);
                        request.setAttribute("tasks", empService.getEmployeeTasks(id));
                        super.setView(request, EMPLOYEES_MAINT_VIEW);
                    }

                    if (!empService.isValid(emp)) {
                        request.setAttribute("employee", emp);
                        request.setAttribute("errors", emp.getErrors());
                        super.setView(request, EMPLOYEE_TASKS_ADD);
                    }
                    break;

                case "remove skill":
                    emp = empService.getEmployee(id);
                    boolean isFromRemove = true;
                    request.setAttribute("deleteConfirm", isFromRemove);
                    request.setAttribute("employee", emp);

                    request.setAttribute("tasks", empService.getEmployeeTasks(id));
                    request.setAttribute("idToDelete", deletionId);
                    request.setAttribute("itemToDelete", "skill");
                    request.setAttribute("teams", empService.getEmployeeTeams(id));
                    request.setAttribute("isDeleted", 1);

//                    if (!empService.isValid(emp)) {
//                        request.setAttribute("currentTasks", empService.getEmployeeTasks(id));
//                        request.setAttribute("errors", emp.getErrors());
//                        super.setView(request, EMPLOYEES_MAINT_VIEW);
//                    }
                    super.setView(request, EMPLOYEES_MAINT_VIEW);
                    break;

                case "delete skill":
                    emp = empService.getEmployee(id);
                    request.setAttribute("tasks", empService.getEmployeeTasks(id));

                    if (empService.checkJobTaskFuture(id) > 0) {
                        emp.addError(ErrorFactory.createInstance(6, "Cannot delete this task, it is assigned to a job in the future."));
                        request.setAttribute("employee", emp);
                        request.setAttribute("currentTasks", empService.getEmployeeTasks(id));
                        super.setView(request, EMPLOYEES_MAINT_VIEW);
                    } else {

                        if (empService.removeEmployeeSkills(id, deletionId)) {
                            request.setAttribute("tasks", empService.getEmployeeTasks(id));
                            request.setAttribute("employee", emp);
                            request.setAttribute("currentTasks", empService.getEmployeeTasks(id));
                            super.setView(request, EMPLOYEES_MAINT_VIEW);
                        }

                        if (!empService.isValid(emp)) {
                            request.setAttribute("currentTasks", empService.getEmployeeTasks(id));
                            request.setAttribute("errors", emp.getErrors());
                            request.setAttribute("employee", emp);
                            super.setView(request, EMPLOYEES_MAINT_VIEW);
                        }

                    }

                    break;

                case "save":
                    emp = setEmployee(request);
                    emp.setId(id);
                    if (empService.saveEmployee(emp) == false) {
                        emp.addError(ErrorFactory.createInstance(12, "Save failed. No row updated."));
                    }

                    emp = empService.getEmployee(id);
                    request.setAttribute("tasks", empService.getEmployeeTasks(id));
                    request.setAttribute("savedEmp", 1);
                    request.setAttribute("employee", emp);
                    if (!empService.isValid(emp)) {
                        request.setAttribute("errors", emp.getErrors());
                        super.setView(request, EMPLOYEES_MAINT_VIEW);
                    }

                    break;

            }

        } catch (Exception ex) {

        }
        super.getView().forward(request, response);
    }

    private IEmployee setEmployee(HttpServletRequest request) {

        String firstName = "";
        String lastName = "";
        String sin = "";
        double hourlyRate = 0.0;

        firstName = super.getValue(request, "firstName");
        lastName = super.getValue(request, "lastName");
        sin = super.getValue(request, "sin");
        hourlyRate = super.getDouble(request, "hourlyRate");

        IEmployee employee = EmployeeFactory.createInstance(firstName, lastName, sin, hourlyRate);
        return employee;
    }

}
