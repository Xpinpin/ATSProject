/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import java.util.List;
import com.nbcc.atssystem.models.ITask;

/**
 *
 * @author Gui Ferrera
 */
public interface ITaskRepository {

    int insertTask(ITask task);

    int updateTask(ITask task);

    int deleteTask(int taskId);

    List<ITask> retrieveTask();

    ITask retrieveTask(int taskId);

    int employeeWithTask(int id);

    int jobWithTask(int id);

    List<ITask> retrieveEmployeeAvailableSkills(int id);


}
