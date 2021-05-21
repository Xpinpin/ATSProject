/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.repository.ITaskRepository;
import com.nbcc.atssystem.repository.TaskRepositoryFactory;
import java.util.List;

/**
 *
 * @author Gui Ferrera - merged
 */
public class TaskService implements ITaskService {

    private final ITaskRepository repo;

    public TaskService() {
        repo = TaskRepositoryFactory.createInstance();
    }

    @Override
    public ITask createTask(ITask task) {
        if (isValid(task)) {
            int id = repo.insertTask(task);

            if (id > 0) {
                task.setId(id);
            } else {
                task.addError(ErrorFactory.createInstance(4, "Task is not valid for creation."));
            }

        } else {
            task.addError(ErrorFactory.createInstance(99, "Task creation failed due to the validation errors."));
        }

        return task;
    }

    @Override
    public boolean saveTask(ITask task) {
        int returnedRow = 0;
        if (isValid(task)) {
             returnedRow = repo.updateTask(task);
        } else {
            task.addError(ErrorFactory.createInstance(99, "Task update failed due to the validation errors."));
        }

        return returnedRow > 0;
    }

    /**
     * Get the task based on the ID in the service layer
     *
     * @param id
     * @return Task
     */
    public ITask getTask(int id) {
        ITask task = repo.retrieveTask(id);

        return task;
    }

    /**
     * Get all of the tasks in the service layer
     *
     * @return List of Tasks
     */
    public List<ITask> getTaskList() {
        List<ITask> tasks = repo.retrieveTask();

        return tasks;
    }

    @Override
    public boolean isValid(ITask task) {
        return task.getErrors().isEmpty();
    }

    @Override
    public int deleteTask(int id) {
        //ADD CHECK FOR HAVING TASK OR JOB WITH TASK?
        if (id <= 0) {
            return 0;
        } else {

            return repo.deleteTask(id);
        }
    }

    @Override
    public int taskEmployeeCount(int id) {
        return repo.employeeWithTask(id);
    }

    @Override
    public int jobTaskCount(int id) {
        return repo.jobWithTask(id);
    }

    @Override
    public List<ITask> getEmpAvailableTasks(int id) {
        return repo.retrieveEmployeeAvailableSkills(id);
    }

}
