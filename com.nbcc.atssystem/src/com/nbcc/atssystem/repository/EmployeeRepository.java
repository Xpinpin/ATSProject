 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.EmployeeFactory;
import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.models.TaskFactory;
import com.nbcc.atssystem.models.TeamFactory;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Nguyen Pham & Guilherme Ferreira
 */
public class EmployeeRepository extends BaseRepository implements IEmployeeRepository {

    private final String SPROC_INSERT_EMPLOYEE = "CALL InsertEmployee(?,?,?,?,?)";
    private final String SPROC_SELECT_EMPLOYEES = "CALL ShowEmployees()";
    private final String SPROC_SELECT_EMPLOYEE = "CALL ShowEmployee(?)";
    private final String SPROC_SELECT_EMPLOYEES_TASKS = "CALL ShowEmployeeTasks(?)";
    private final String SPROC_SELECT_EMPLOYEES_TEAMS = "CALL ShowEmployeeTeam(?)";
    private final String SPROC_SELECT_EMPLOYEES_SEARCH = "CALL  ShowEmployeesBySearch(?)";
    private final String SPROC_DELETE_EMPLOYEE = "CALL DeleteEmployee(?)";
    private final String SPROC_ADD_EMPLOYEE_SKILLS = "CALL AddEmployeeSkills(?,?)";
    private final String SPROC_DELETE_EMPLOYEE_SKILLS = "CALL DeleteEmployeeSkills(?,?)";
    private final String SPROC_CHECK_JOB_TASKS_FUTURE = "CALL CheckJobTaskFuture(?)";
    private final String SPROC_UPDATE_EMPLOYEE = "CALL UpdateEmployee(?,?,?,?,?)";
    

    private IDAL dataAccess;

    public EmployeeRepository() {
        dataAccess = DALFactory.createInstance();
    }

    /**
     * Add a new employee to the database
     *
     * @param employee
     * @return the employee Id just inserted
     */
    @Override
    public int insertEmployee(IEmployee employee) {
        int returnId = 0;
        List<Object> returnValues;

        List<IParameter> parms = ParameterFactory.createListInstance();

        //INPUT PARMS
        parms.add(ParameterFactory.createInstance(employee.getFirstName()));
        parms.add(ParameterFactory.createInstance(employee.getLastName()));
        parms.add(ParameterFactory.createInstance(employee.getSIN()));
        parms.add(ParameterFactory.createInstance(employee.getHourlyRate()));

        //OUTPUT PARMS
        parms.add(ParameterFactory.createInstance(returnId, IParameter.Direction.OUT, java.sql.Types.INTEGER));

        returnValues = dataAccess.executeNonQuery(SPROC_INSERT_EMPLOYEE, parms);

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
     * Updates a employee.
     *
     * @param employee
     * @return the count of the affected rows.
     */
    @Override
    public int updateEmployee(IEmployee employee) {
        int affectedRows = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(employee.getId()));
        params.add(ParameterFactory.createInstance(employee.getFirstName()));
        params.add(ParameterFactory.createInstance(employee.getLastName()));
        params.add(ParameterFactory.createInstance(employee.getSIN()));
        params.add(ParameterFactory.createInstance(employee.getHourlyRate()));
        returnValues = dataAccess.executeNonQuery(SPROC_UPDATE_EMPLOYEE, params);
        try {
            if (returnValues != null) {
                affectedRows  = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return affectedRows;
    }
    
    /**
     * Get the employees list
     *
     * @return All of the employees
     */
    @Override
    public List<IEmployee> retrieveEmployees() {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_EMPLOYEES, null);
            retrievedEmployees = toListOfEmployees(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedEmployees;
    }

    /**
     * Get the specific employee by the ID
     *
     * @param id
     * @return Employee
     */
    @Override
    public IEmployee retrieveEmployee(int id) {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_EMPLOYEE, params);
            retrievedEmployees = toListOfEmployees(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedEmployees.get(0);
    }

    public List<ITask> retrieveEmployeeTasks(int id) {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();

        try {

            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_EMPLOYEES_TASKS, params);
            retrievedTasks = toListOfTasks(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedTasks;
    }
    
    
 
    /**
     * Create a list of employee by passing in the data from database
     *
     * @param cs
     * @return List of Employee
     * @throws SQLException
     */
    private List<IEmployee> toListOfEmployees(CachedRowSet cs) throws SQLException {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();
        IEmployee emp;

        while (cs.next()) {
            emp = EmployeeFactory.createInstance();
            emp.setId(super.getInt("EmployeeId", cs));
            emp.setFirstName(cs.getString("FirstName"));
            emp.setLastName(cs.getString("LastName"));
            emp.setSIN(cs.getString("SIN"));
            emp.setHourlyRate(super.getDouble("HourlyRate", cs));
            emp.setIsDeleted(cs.getBoolean("IsDeleted"));
            emp.setCreatedAt(super.getDate("CreatedAt", cs));
            emp.setUpdatedAt(super.getDate("UpdatedAt", cs));
            emp.setDeletedAt(super.getDate("DeletedAt", cs));

            retrievedEmployees.add(emp);
        }

        return retrievedEmployees;
    }

    private List<ITask> toListOfTasks(CachedRowSet cs) throws SQLException {
        List<ITask> retrievedTasks = TaskFactory.createListInstance();
        ITask task;

        while (cs.next()) {
            task = TaskFactory.createInstance();
            task.setId(super.getInt("TaskId", cs));
            task.setName(cs.getString("Name"));
            task.setDuration(super.getInt("Duration", cs));

            retrievedTasks.add(task);
        }

        return retrievedTasks;
    }

    private List<ITeam> toListOfTeams(CachedRowSet cs) throws SQLException {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        ITeam team;

        while (cs.next()) {
            team = TeamFactory.createInstance();
            team.setTeamName(cs.getString("Name"));

            retrievedTeams.add(team);
        }

        return retrievedTeams;
    }

    @Override
    public List<ITeam> retrieveEmployeeTeam(int id) {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_EMPLOYEES_TEAMS, params);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedTeams;

    }

    @Override
    public List<IEmployee> retrieveEmployeesBySearch(String searchString) {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();

        try {
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(searchString));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_EMPLOYEES_SEARCH, parms);
            retrievedEmployees = toListOfEmployees(cs);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedEmployees;

    }

    @Override
    public int deleteEmployee(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));

        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_EMPLOYEE, params);

        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rowsAffected;
    }

    //add emp skills
    @Override
    public boolean insertEmployeeSkills(int empId, List<Integer> taskIds) {

        List<Object> returnValues = new ArrayList<Object>();

        List<IParameter> params = ParameterFactory.createListInstance();

        for (Integer empTask : taskIds) {
            params.clear();
            params.add(ParameterFactory.createInstance(empId));
            params.add(ParameterFactory.createInstance(empTask));

            returnValues = dataAccess.executeNonQuery(SPROC_ADD_EMPLOYEE_SKILLS, params);
        }

        try {
            if (returnValues != null && returnValues.size() > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    //remove emp skills
    @Override
    public boolean deleteEmployeeSkills(int empId, int taskId) {
        boolean successful = false;
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        //Add parameters in  order they appear in your spcs
        params.add(ParameterFactory.createInstance(empId));
        params.add(ParameterFactory.createInstance(taskId));
        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_EMPLOYEE_SKILLS, params);
        try {
            if (returnValues != null && returnValues.size() > 0) {
                return true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return successful;
    }

    public int checkJobTaskFuture(int id) {
        int countTasks = 0;

        try {
            List<IParameter> params = ParameterFactory.createListInstance();

            params.add(ParameterFactory.createInstance(id));
            Object o = dataAccess.executeScalar(SPROC_CHECK_JOB_TASKS_FUTURE, params);

            countTasks = Integer.parseInt(o.toString());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return countTasks;
    }

}
