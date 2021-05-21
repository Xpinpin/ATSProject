/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.repository.EmployeeRepositoryFactory;
import com.nbcc.atssystem.repository.IEmployeeRepository;
import java.util.List;

/**
 *
 * @author Nguyen Pham & Guilherme Ferreira
 */
public class EmployeeService implements IEmployeeService {

    private final IEmployeeRepository repo;

    public EmployeeService() {
        repo = EmployeeRepositoryFactory.createInstance();
    }

    /**
     * Updates the employee in the service layer
     *
     * @param emp
     * @return Employee
     */
    @Override
    public IEmployee createEmployee(IEmployee emp) {
        if (isValid(emp)) {
            int id = repo.insertEmployee(emp);

            if (id > 0) {
                emp.setId(id);
            } else {
                emp.addError(ErrorFactory.createInstance(4, "Employee is not valid for creation."));
            }

        } else {
            emp.addError(ErrorFactory.createInstance(99, "Employee creation failed due to the validation errors."));
        }
        return emp;
    }

    /**
     * Create the employee in the service layer
     *
     * @param emp
     * @return Employee
     */
    @Override
    public boolean saveEmployee(IEmployee emp) {
        int returnedRow = 0;
        if (isValid(emp)) {
            returnedRow = repo.updateEmployee(emp);
        } else {
            emp.addError(ErrorFactory.createInstance(99, "Employee update failed due to the validation errors."));
        }

        return returnedRow > 0;
    }

    /**
     * Get the employee based on the ID in the service layer
     *
     * @param id
     * @return Employee
     */
    @Override
    public IEmployee getEmployee(int id) {
        IEmployee emp = repo.retrieveEmployee(id);

        return emp;
    }

    /**
     * Get all of the employees in the service layer
     *
     * @return List of Employee
     */
    @Override
    public List<IEmployee> getEmployeeList() {
        List<IEmployee> employees = repo.retrieveEmployees();

        return employees;
    }

    /**
     * Make sure that the Employee passing in is valid for manipulation
     *
     * @param employee
     * @return boolean
     */
    @Override
    public boolean isValid(IEmployee employee) {
        return employee.getErrors().isEmpty();
    }

    @Override
    public List<ITask> getEmployeeTasks(int id) {
        List<ITask> tasks = repo.retrieveEmployeeTasks(id);

        return tasks;
    }

    @Override
    public List<ITeam> getEmployeeTeams(int id) {
        List<ITeam> teams = repo.retrieveEmployeeTeam(id);

        return teams;
    }

    @Override
    public List<IEmployee> getEmployeeListBySearch(String searchString) {
        List<IEmployee> employees = repo.retrieveEmployeesBySearch(searchString);

        return employees;
    }

    @Override
    public int deleteEmployee(int id) {
        if (id <= 0) {
            return 0;
        } else {
            return repo.deleteEmployee(id);
        }

    }

    @Override
    public boolean addEmployeeSkills(int empId, List<Integer> taskIds) {
        boolean isAdded = repo.insertEmployeeSkills(empId, taskIds);

        return isAdded;
    }

    @Override
    public boolean removeEmployeeSkills(int empId, int taskId) {
        boolean isDeleted = true;

        repo.deleteEmployeeSkills(empId, taskId);

        return isDeleted;
    }

    public int checkJobTaskFuture(int id) {
        int jt = repo.checkJobTaskFuture(id);

        return jt;
    }
}
