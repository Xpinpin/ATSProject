/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atssystem.business.ITeamService;
import com.nbcc.atssystem.business.TeamServiceFactory;
import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.models.TeamFactory;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class TeamController extends CommonController {

    private static final String TEAMS_VIEW = "/teams.jsp";
    private static final String TEAMS_MAINT_VIEW = "/team.jsp";
    private static final String TEAM_SUMMARY_VIEW = "/teamsummary.jsp";

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        ITeamService service = TeamServiceFactory.createInstance();

        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");
            int id = super.getInteger(pathParts[1]);
            String create = pathParts[1];

            if ("create".equals(create)) {
                request.setAttribute("employees", service.getEmployeeListNotAssigned());
                super.setView(request, TEAMS_MAINT_VIEW);
            } else {
                ITeam team = service.getTeam(id);
                request.setAttribute("team", team);
                request.setAttribute("employees", service.getTeamMembers(id));

                super.setView(request, TEAMS_MAINT_VIEW);
            }

        } else {
            request.setAttribute("teams", service.getAllTeams());
            super.setView(request, TEAMS_VIEW);
        }

        super.getView().forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.setView(request, TEAM_SUMMARY_VIEW);
        ITeamService teamService = TeamServiceFactory.createInstance();
        ITeam team;

        try {
            int id = super.getInteger(request, "hdnTeamId");

            String actionFull = super.getValue(request, "action");
            String action = "";
            int deletionId = 0;
            if (actionFull.contains("/")) {
                String[] pathParts = actionFull.split("/");
                deletionId = super.getInteger(pathParts[1]);
                action = pathParts[0];
            } else {
                action = actionFull;
            }

            switch (action.toLowerCase()) {
                case "create":
                    team = setTeam(request);
                    team = teamService.createTeam(team);
                    request.setAttribute("team", team);

                    if (!teamService.isValid(team)) {
                        request.setAttribute("employees", teamService.getEmployeeListNotAssigned());
                        request.setAttribute("errors", team.getErrors());
                        super.setView(request, TEAMS_MAINT_VIEW);
                    }
                    break;

                case "delete":

                    String isDeleted = super.getValue(request, "isDeleted");
                    team = setTeam(request);
                    team.setId(id);
                    request.setAttribute("team", team);

                    if (isDeleted == null && deletionId == 0) {
                        request.setAttribute("isDeleted", 1);
                        request.setAttribute("idToDelete", id);
                        super.setView(request, TEAMS_MAINT_VIEW);
                    } else {
                        if (teamService.deleteTeam(deletionId) == 0) {
                            team.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                            request.setAttribute("isDeleted", null);
                            request.setAttribute("employees", teamService.getTeamMembers(id));
                            request.setAttribute("errors", team.getErrors());
                            super.setView(request, TEAMS_MAINT_VIEW);
                        } else {
                            request.setAttribute("isSuccess", 1);
                            request.setAttribute("teams", teamService.getAllTeams());
                            super.setView(request, TEAMS_VIEW);
                        }
                    }

                    break;

                case "place on call":

                    team = teamService.getTeam(id);
                    request.setAttribute("employees", teamService.getTeamMembers(id));
                    if (teamService.putTeamOnCall(id) == 0) {
                        team.addError(ErrorFactory.createInstance(17, "Only one team can be made on call."));
                        request.setAttribute("team", team);
                        request.setAttribute("errors", team.getErrors());
                        super.setView(request, TEAMS_MAINT_VIEW);
                    } else {
                        request.setAttribute("placeOnCallSuccess", 1);
                        request.setAttribute("team", teamService.getTeam(id));
                    }

                    break;

            }

        } catch (Exception ex) {

        }
        super.getView().forward(request, response);
    }

    private ITeam setTeam(HttpServletRequest request) {
        String teamName = "";
        int isOnCallInt = 0;
        boolean isOnCall = false;

        teamName = super.getValue(request, "teamName");
        isOnCallInt = super.getInteger(request, "isOnCall");
        String[] members = request.getParameterValues("members");
        ArrayList<Integer> memberId = new ArrayList<Integer>();
        if (members == null) {
            memberId.add(0);
        } else {
            for (int i = 0; i < members.length; i++) {
                memberId.add(super.getInteger(members[i]));
            }
        }

        if (isOnCallInt == 0) {
            isOnCall = false;
        } else {
            isOnCall = true;
        }

        ITeam team = TeamFactory.createInstance(teamName, isOnCall, memberId);
        return team;

    }

}
