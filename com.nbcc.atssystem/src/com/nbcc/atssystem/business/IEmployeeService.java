/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public interface IEmployeeService {

    IEmployee createEmployee(IEmployee emp);

    IEmployee getEmployee(int id);

    List<IEmployee> getEmployeeList();

    List<IEmployee> getEmployeeListBySearch(String searchString);

    boolean isValid(IEmployee emp);

    List<ITask> getEmployeeTasks(int id);

    List<ITeam> getEmployeeTeams(int id);

    int deleteEmployee(int id);

    boolean addEmployeeSkills(int empId, List<Integer> taskIds);

    boolean removeEmployeeSkills(int empId, int taskId);

    int checkJobTaskFuture(int id);

    boolean saveEmployee(IEmployee emp);

}
