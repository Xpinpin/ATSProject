/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.EmployeeFactory;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.models.TeamFactory;
import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Nguyen Pham
 */
public class TeamRepository extends BaseRepository implements ITeamRepository {

    private final String SPROC_CREATE_TEAM = "CALL CreateTeam(?,?,?,?,?)";
    private final String SPROC_CHOOSE_EMPLOYEES = "CALL ShowEmployeesNotAssignTeam()";
    private final String SPROC_SHOW_TEAMS_TASKS = "CALL ShowTeamsWithTasks(?)";
    private final String SPROC_SHOW_TEAMS = "CALL ShowAllTeams()";
    private final String SPROC_SHOW_ONCALL_TEAMS = "CALL ShowOnCallTeams()";
    private final String SPROC_SHOW_TEAM = "CALL ShowTeam(?)";
    private final String SPROC_SHOW_TEAMMEMBERS = "CALL ShowTeamMembers(?)";
    private final String SPROC_CHECK_TEAM_AVAILABILITY = "CALL CheckTeamAvailability(?,?,?)";
    private final String SPROC_DELETE_TEAM = "CALL DeleteTeam(?)";
    private final String SPROC_PLACE_TEAM_ON_CALL = "CALL PlaceTeamOnCall(?)";
    private IDAL dataAccess;

    public TeamRepository() {
        dataAccess = DALFactory.createInstance();
    }

    @Override
    public int insertTeam(ITeam team) {
        int returnId = 0;
        List<Object> returnValues = new ArrayList<Object>();

        List<IParameter> params = ParameterFactory.createListInstance();

        int teamId = 0;

        for (Integer member : team.getMembers()) {

            params.clear();
            //INPUT PARAMS FOR CREATE TEAM
            params.add(ParameterFactory.createInstance(team.getTeamName()));
            params.add(ParameterFactory.createInstance(team.isIsOnCall()));
            params.add(ParameterFactory.createInstance(member));
            params.add(ParameterFactory.createInstance(teamId));

            //OUTPUT PARAM FOR CREATE TEAM
            params.add(ParameterFactory.createInstance(returnId, IParameter.Direction.OUT, java.sql.Types.INTEGER));
            returnValues = dataAccess.executeNonQuery(SPROC_CREATE_TEAM, params);

            teamId = Integer.parseInt(returnValues.get(0).toString());

        }

        try {
            if (returnValues != null) {
                returnId = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnId;

    }

    //retrieve all teams
    public List<ITeam> retrieveAllTeams() {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_CHOOSE_EMPLOYEES, null);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTeams;
    }

    @Override
    public List<ITeam> retrieveAllOnCallTeams() {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_ONCALL_TEAMS, null);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTeams;
    }

    @Override
    public ITeam retrieveTeam(int id) {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_TEAM, params);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTeams.get(0);
    }

    public Boolean checkIsTeamAvailable(int teamId, Date start, Date end) {
        int returnValue = 0;
        LocalDateTime start_ldt = LocalDateTime.ofInstant(start.toInstant(),
                ZoneId.systemDefault());
        LocalDateTime end_ldt = LocalDateTime.ofInstant(end.toInstant(),
                ZoneId.systemDefault());

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(teamId));
            params.add(ParameterFactory.createInstance(ChangeFormatDate(start_ldt.toString())));
            params.add(ParameterFactory.createInstance(ChangeFormatDate(end_ldt.toString())));

            Object returnValues = dataAccess.executeScalar(SPROC_CHECK_TEAM_AVAILABILITY, params);
            returnValue = Integer.parseInt(returnValues.toString());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return returnValue > 0;
    }

    //retrieve all teams
    @Override
    public List<ITeam> retrieveAvailableTeams(List<Integer> tasks) {
        List<ITeam> retrievedTeamsAll = TeamFactory.createListInstance();
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        int returnId = 0;
        List<Object> returnValues = new ArrayList<Object>();

        try {

            List<IParameter> params = ParameterFactory.createListInstance();

            for (Integer task : tasks) {

                params.clear();
                params.add(ParameterFactory.createInstance(task));

                List<ITeam> initialTeams = TeamFactory.createListInstance();

                CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_TEAMS_TASKS, params);
                initialTeams = toListOfTeamsNameIdOnly(cs);

                for (ITeam t : initialTeams) {
                    retrievedTeamsAll.add(t);
                }
            }

            for (ITeam t : retrievedTeamsAll) {

                if (retrievedTeamsAll.stream().filter(x -> x.getId() == t.getId()).count() == tasks.size()) {
                    if (!retrievedTeams.stream().anyMatch(x -> t.getId() == x.getId())) {
                        retrievedTeams.add(t);
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTeams;
    }

    public List<IEmployee> retrieveEmployeesNotAssigned() {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();

        try {

            CachedRowSet cs = dataAccess.executeFill(SPROC_CHOOSE_EMPLOYEES, null);
            retrievedEmployees = toListOfEmployees(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedEmployees;
    }

    @Override
    public List<IEmployee> retrieveTeamMembers(Integer id) {
        List<IEmployee> retrievedEmployees = EmployeeFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));
            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_TEAMMEMBERS, params);
            retrievedEmployees = toListOfEmployees(cs);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedEmployees;
    }

    /**
     * Deletes a team
     *
     * @param id
     * @return deleted team
     */
    @Override
    public int deleteTeam(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(id));
        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_TEAM, params);
        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt((returnValues.get(0).toString()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

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

    private List<ITeam> toListOfTeams(CachedRowSet cs) throws SQLException {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        ITeam team;

        while (cs.next()) {
            team = TeamFactory.createInstance();
            team.setId(super.getInt("TeamId", cs));
            team.setTeamName(cs.getString("Name"));
            team.setIsOnCall(cs.getBoolean("IsOnCall"));
            team.setIsDeleted(cs.getBoolean("IsDeleted"));
            team.setCreatedAt(super.getDate("CreatedAt", cs));
            team.setUpdatedAt(super.getDate("UpdatedAt", cs));
            team.setDeletedAt(super.getDate("DeletedAt", cs));

            retrievedTeams.add(team);
        }

        return retrievedTeams;
    }

    //retrieve all teams
    @Override
    public List<ITeam> getAllTeams() {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_TEAMS, null);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return retrievedTeams;

    }

    private List<ITeam> toListOfTeamsNameIdOnly(CachedRowSet cs) throws SQLException {
        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        ITeam team;

        while (cs.next()) {
            team = TeamFactory.createInstance();
            team.setId(super.getInt("TeamId", cs));
            team.setTeamName(cs.getString("Name"));
            retrievedTeams.add(team);
        }

        return retrievedTeams;
    }

    private String ChangeFormatDate(String date) {
        String[] dateTime_param = date.split("T");
        String date_param = dateTime_param[0];
        String time_param = dateTime_param[1];

        return date_param + " " + time_param + ":00";

    }

    @Override
    public int placeTeamOnCall(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(id));
        returnValues = dataAccess.executeNonQuery(SPROC_PLACE_TEAM_ON_CALL, params);
        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt((returnValues.get(0).toString()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }
}
