/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.repository.ITeamRepository;
import com.nbcc.atssystem.repository.TeamRepositoryFactory;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public class TeamService implements ITeamService {

    private final ITeamRepository repo;

    public TeamService() {
        repo = TeamRepositoryFactory.createInstance();
    }

    @Override
    public ITeam createTeam(ITeam team) {
        int maxTeamSize = 2;

        if (team.getMembers().size() != maxTeamSize) {
            team.addError(ErrorFactory.createInstance(11, "There must be " + maxTeamSize + " members in a team."));

        }
        if (isValid(team)) {
            int id = repo.insertTeam(team);

            if (id > 0) {
                team.setId(id);
            } else {
                team.addError(ErrorFactory.createInstance(4, "Team is not valid for creation."));
            }

        } else {
            team.addError(ErrorFactory.createInstance(99, "Team creation failed due to the validation errors."));
        }

        return team;
    }

    @Override
    public boolean isValid(ITeam team) {

        return team.getErrors().isEmpty();
    }

    @Override
    public List<ITeam> getAvailableTeams(List<Integer> tasks) {
        List<ITeam> teams = repo.retrieveAvailableTeams(tasks);

        return teams;
    }

    @Override
    public List<ITeam> getAllOnCallTeams() {
        List<ITeam> teams = repo.retrieveAllOnCallTeams();

        return teams;
    }

    @Override
    public List<IEmployee> getEmployeeListNotAssigned() {
        List<IEmployee> employees = repo.retrieveEmployeesNotAssigned();
        return employees;
    }

    @Override
    public List<IEmployee> getTeamMembers(Integer id) {
        List<IEmployee> teamMembers = repo.retrieveTeamMembers(id);
        return teamMembers;
    }

    @Override
    public ITeam getTeam(Integer id) {
        ITeam team = repo.retrieveTeam(id);
        return team;
    }

    @Override
    public boolean checkTeamAvailability(int teamId, Date start, Date end) {

        return repo.checkIsTeamAvailable(teamId, start, end); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ITeam> getAllTeams() {

        return repo.getAllTeams();
    }

    @Override
    public int deleteTeam(int id) {

        if (id <= 0) {
            return 0;
        } else {

            return repo.deleteTeam(id);
        }
    }

    @Override
    public int putTeamOnCall(int id) {
        if (id <= 0) {
            return 0;
        } else {
            return repo.placeTeamOnCall(id);
        }
    }

}
