/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITeam;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public interface ITeamRepository {

    int insertTeam(ITeam team);

    List<IEmployee> retrieveEmployeesNotAssigned();

    List<ITeam> retrieveAvailableTeams(List<Integer> tasks);

    List<IEmployee> retrieveTeamMembers(Integer id);

    Boolean checkIsTeamAvailable(int teamId, Date start, Date end);

    ITeam retrieveTeam(int id);

    List<ITeam> retrieveAllOnCallTeams();

    List<ITeam> getAllTeams();

    int deleteTeam(int id);

    int placeTeamOnCall(int id);
}
