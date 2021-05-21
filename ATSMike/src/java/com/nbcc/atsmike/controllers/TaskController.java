/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atsmike.models.ErrorViewModel;
import com.nbcc.atssystem.business.ITaskService;
import com.nbcc.atssystem.business.TaskServiceFactory;
import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.TaskFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class TaskController extends CommonController {

    private static final String TASKS_VIEW = "/tasks.jsp";
    private static final String TASKS_MAINT_VIEW = "/task.jsp";
    private static final String TASK_SUMMARY_VIEW = "/tasksummary.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        //Service Instance
        ITaskService taskService = TaskServiceFactory.createInstance();

        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            int id = super.getInteger(pathParts[1]);

            ITask task = taskService.getTask(id);

            if (task != null) {
                request.setAttribute("task", task);
            } else {
                request.setAttribute("error", new ErrorViewModel(String.format("Task ID: %s is not found", id)));

            }

            super.setView(request, TASKS_MAINT_VIEW);
        } else {
            request.setAttribute("tasks", taskService.getTaskList());
            super.setView(request, TASKS_VIEW);
        }

        super.getView().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.setView(request, TASK_SUMMARY_VIEW);
        //Invoice service instance
        ITaskService service = TaskServiceFactory.createInstance();
        ITask task;
        try {
            int id = super.getInteger(request, "hdnTaskId");

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

            switch (action.toLowerCase()) {
                case "create":
                    task = setTask(request);
                    task = service.createTask(task);

                    request.setAttribute("task", task);

                    if (!service.isValid(task)) {
                        request.setAttribute("isOnCreate", 1);
                        request.setAttribute("errors", task.getErrors());
                        super.setView(request, TASKS_MAINT_VIEW);
                    }
                    break;
                case "save":
                    task = setTask(request);
                    task.setId(id);
                    if (service.saveTask(task) == false) {
                        task.addError(ErrorFactory.createInstance(12, "Save failed. No row updated."));
                    }

                    task = service.getTask(id);
                    request.setAttribute("savedTask", 1);
                    request.setAttribute("task", task);
                    if (!service.isValid(task)) {
                        request.setAttribute("errors", task.getErrors());
                        super.setView(request, TASKS_MAINT_VIEW);
                    }
                    break;
                case "delete":

                    String isDeleted = super.getValue(request, "isDeleted");

                    task = setTask(request);
                    task.setId(id);

                    request.setAttribute("task", task);

                    if (isDeleted == null && deletionId == 0) {
                        request.setAttribute("isDeleted", 1);
                        request.setAttribute("idToDelete", id);
                        super.setView(request, TASKS_MAINT_VIEW);

                    } else {

                        if (service.taskEmployeeCount(id) > 0 || service.jobTaskCount(id) > 0) {
                            task.addError(ErrorFactory.createInstance(13, "Task is assigned to employee(s) or is part of a job. Cannot be deleted."));
                            request.setAttribute("isDeleted", null);
                            request.setAttribute("errors", task.getErrors());
                            super.setView(request, TASKS_MAINT_VIEW);
                        } else {
                            if (service.deleteTask(id) == 0) {
                                task.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                                request.setAttribute("isDeleted", null);
                                request.setAttribute("errors", task.getErrors());
                                super.setView(request, TASKS_MAINT_VIEW);
                            } else {
                                request.setAttribute("isSuccess", 1);
                                request.setAttribute("tasks", service.getTaskList());
                                super.setView(request, TASKS_VIEW);
                            }
                        }

                    }

                    break;
            }
        } catch (Exception e) {
            super.setView(request, TASKS_MAINT_VIEW);
            request.setAttribute("error", new ErrorViewModel("An error occurred attempting to maintain tasks"));
        }

        super.getView().forward(request, response);
    }

    //Create a method to reuse for creation and saving to load properties in an task
    private ITask setTask(HttpServletRequest request) {

        String taskName = "";
        String description = "";
        int duration = 0;

        taskName = super.getValue(request, "taskName");
        description = super.getValue(request, "description");
        duration = super.getInteger(request, "duration");

        ITask task = TaskFactory.createInstance(taskName, description, duration);
        return task;
    }
}
