/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ITask;
import java.util.List;

/**
 *
 * @author gui_a
 */
public interface ITaskService {

    ITask createTask(ITask task);

    ITask getTask(int id);

    List<ITask> getTaskList();

    boolean isValid(ITask task);

    int deleteTask(int id);

    int taskEmployeeCount(int id);

    int jobTaskCount(int id);

    List<ITask> getEmpAvailableTasks(int id);
    
    boolean saveTask(ITask task);

}
