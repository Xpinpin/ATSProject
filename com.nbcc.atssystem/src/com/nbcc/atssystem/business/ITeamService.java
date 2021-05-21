/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITeam;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public interface ITeamService {

    ITeam createTeam(ITeam team);

    List<IEmployee> getEmployeeListNotAssigned();

    List<ITeam> getAvailableTeams(List<Integer> tasks);

    List<IEmployee> getTeamMembers(Integer id);

    boolean isValid(ITeam team);

    boolean checkTeamAvailability(int teamId, Date start, Date end);

    ITeam getTeam(Integer id);

    List<ITeam> getAllOnCallTeams();

    List<ITeam> getAllTeams();

    int deleteTeam(int id);
    
    int putTeamOnCall(int id);

}
