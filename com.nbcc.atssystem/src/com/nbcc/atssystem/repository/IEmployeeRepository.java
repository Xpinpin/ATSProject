/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author Nguyen Pham and Gui Ferreira
 */
public interface IEmployeeRepository {

    int insertEmployee(IEmployee employee);

    List<IEmployee> retrieveEmployees();

    IEmployee retrieveEmployee(int id);

    List<ITask> retrieveEmployeeTasks(int id);

    List<ITeam> retrieveEmployeeTeam(int id);

    List<IEmployee> retrieveEmployeesBySearch(String searchString);

    int deleteEmployee(int id);

    boolean insertEmployeeSkills(int empId, List<Integer> taskIds);

    boolean deleteEmployeeSkills(int empId, int taskId);

    int checkJobTaskFuture(int id);

    int updateEmployee(IEmployee employee);

}
