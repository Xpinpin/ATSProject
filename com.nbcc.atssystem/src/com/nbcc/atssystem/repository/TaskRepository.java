/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import com.nbcc.atssystem.models.TaskFactory;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.CachedRowSet;
import com.nbcc.atssystem.models.ITask;

/**
 *
 * @author Guilherme Ferreira - merged
 */
public class TaskRepository extends BaseRepository implements ITaskRepository {

    private final String SPROC_SELECT_TASKS = "CALL ListTasks(null)";
    private final String SPROC_SELECT_TASK = "CALL ShowTask(?)";
    private final String SPROC_INSERT_TASK = "CAll InsertTask(?,?,?,?)";
    private final String SPROC_DELETE_TASK = "CALL DeleteTask(?)";
    private final String SPROC_EMP_WITH_TASK = "CALL EmployeeWithTask(?)";
    private final String SPROC_JOB_WITH_TASK = "CALL JobWithTask(?)";
    private final String SPROC_EMP_AVAILABLE_SKILL = "CALL EmployeeAvailableSkills(?)";
    private final String SPROC_UPDATE_TASK = "CALL UpdateTask(?,?,?,?)";

    private IDAL dataAccess;

    public TaskRepository() {
        dataAccess = DALFactory.createInstance();
    }

    private List<ITask> toListOfTasks(CachedRowSet cs) throws SQLException {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();
        ITask tas;

        while (cs.next()) {
            tas = TaskFactory.createInstance();
            tas.setId(super.getInt("TaskId", cs));
            tas.setName(cs.getString("Name"));
            tas.setDescription(cs.getString("Description"));
            tas.setDuration(super.getInt("Duration", cs));
            tas.setCreatedAt(super.getDate("CreatedAt", cs));
            tas.setUpdatedAt(super.getDate("UpdatedAt", cs));
            retrievedTasks.add(tas);
        }

        return retrievedTasks;
    }

    @Override
    public int insertTask(ITask task) {
        int returnId = 0;
        List<Object> returnValues;

        List<IParameter> parms = ParameterFactory.createListInstance();

        //INPUT PARMS
        parms.add(ParameterFactory.createInstance(task.getName()));
        parms.add(ParameterFactory.createInstance(task.getDescription()));
        parms.add(ParameterFactory.createInstance(task.getDuration()));

        //OUTPUT PARMS
        parms.add(ParameterFactory.createInstance(returnId, IParameter.Direction.OUT, java.sql.Types.INTEGER));

        returnValues = dataAccess.executeNonQuery(SPROC_INSERT_TASK, parms);

        try {
            if (returnValues != null) {
                returnId = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnId;
    }

    /**
     * Check if a employee has a task.
     *
     * @param id
     * @return the count of employees that have a task
     */
    @Override
    public int employeeWithTask(int id) {
        List<IParameter> parms = ParameterFactory.createListInstance();
        parms.add(ParameterFactory.createInstance(id));
        Object returnValue = dataAccess.executeScalar(SPROC_EMP_WITH_TASK, parms);
        return Integer.parseInt(returnValue.toString());
    }

    /**
     * Check if a job has a task.
     *
     * @param id
     * @return the count of jobs that have a task
     */
    @Override
    public int jobWithTask(int id) {
        List<IParameter> parms = ParameterFactory.createListInstance();
        parms.add(ParameterFactory.createInstance(id));
        Object returnValue = dataAccess.executeScalar(SPROC_JOB_WITH_TASK, parms);
        return Integer.parseInt(returnValue.toString());
    }

    @Override
    public int deleteTask(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(id));
        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_TASK, params);
        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt((returnValues.get(0).toString()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Get the tasks list
     *
     * @return All of the tasks
     */
    @Override
    public List<ITask> retrieveTask() {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_TASKS, null);
            retrievedTasks = toListOfTasks(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTasks;
    }

    /**
     * Get the specific task by the ID
     *
     * @param id
     * @return Task
     */
    @Override
    public ITask retrieveTask(int id) {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_TASK, params);
            retrievedTasks = toListOfTasks(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedTasks.get(0);
    }

    //available task emp part--ADD EMPLOYEE SKILLS
    public List<ITask> retrieveEmployeeAvailableSkills(int id) {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_EMP_AVAILABLE_SKILL, params);

            retrievedTasks = toListOfTasks(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTasks;
    }

    /**
     * Updates a task.
     *
     * @param task
     * @return the count of the affected rows.
     */
    @Override
    public int updateTask(ITask task) {
        int affectedRows = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(task.getId()));
        params.add(ParameterFactory.createInstance(task.getName()));
        params.add(ParameterFactory.createInstance(task.getDescription()));
        params.add(ParameterFactory.createInstance(task.getDuration()));
        returnValues = dataAccess.executeNonQuery(SPROC_UPDATE_TASK, params);
        try {
            if (returnValues != null) {
                affectedRows = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return affectedRows;
    }

}
