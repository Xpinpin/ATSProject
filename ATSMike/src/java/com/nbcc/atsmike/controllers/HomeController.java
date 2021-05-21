/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atssystem.business.HomeServiceFactory;
import com.nbcc.atssystem.business.IHomeService;
import com.nbcc.atssystem.business.ITeamService;
import com.nbcc.atssystem.business.TeamServiceFactory;
import com.nbcc.atssystem.models.CostVMFactory;
import com.nbcc.atssystem.models.ICostVM;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.ITeam;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Year;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class HomeController extends CommonController {

    private static final String HOME_VIEW = "/index.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        super.setView(request, HOME_VIEW);

        IHomeService service = HomeServiceFactory.createInstance();
        ITeamService teamService = TeamServiceFactory.createInstance();

        int year = super.getInteger(request, "hdnYear");

        request.setAttribute("numberOfJobs", service.getJobsNumToday());
        ITeam currentTeam = service.getCurrentOnCallTeam();
        if (currentTeam == null) {
            request.setAttribute("onCallTeam", null);
        } else {
            request.setAttribute("onCallTeam", currentTeam);

        }
        if (currentTeam == null) {
            request.setAttribute("members", null);
        } else {
            List<IEmployee> members = teamService.getTeamMembers(service.getCurrentOnCallTeam().getId());

            request.setAttribute("members", members);

        }

        request.setAttribute("monthlyRevenueToDate", service.getCurrentMonthRevenue());
        request.setAttribute("monthlyCostToDate", service.getCurrentMonthCost());
        request.setAttribute("yearlyRevenueToDate", service.getCurrentYearRevenue());
        request.setAttribute("yearlyCostToDate", service.getCurrentYearCost());

        List<ICostVM> yearlyCosts = service.getYearlyCost();
        request.setAttribute("yearlyCosts", yearlyCosts);
        int currentYear = Year.now().getValue();
        int lastYear = currentYear - 1;
        int shownYear = Year.now().getValue();
        request.setAttribute("year", currentYear);
        request.setAttribute("lastYear", lastYear);
        request.setAttribute("shownYear", shownYear);

        List<ICostVM> monthlyCosts = service.getMonthlyCost(currentYear);
        request.setAttribute("monthlyCosts", monthlyCosts);
        super.getView().forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.setView(request, HOME_VIEW);

        int year = super.getInteger(request, "action");
        IHomeService service = HomeServiceFactory.createInstance();
        ITeamService teamService = TeamServiceFactory.createInstance();

        request.setAttribute("numberOfJobs", service.getJobsNumToday());
        request.setAttribute("onCallTeam", service.getCurrentOnCallTeam());
        request.setAttribute("members", teamService.getTeamMembers(service.getCurrentOnCallTeam().getId()));
        request.setAttribute("monthlyRevenueToDate", service.getCurrentMonthRevenue());
        request.setAttribute("monthlyCostToDate", service.getCurrentMonthCost());
        request.setAttribute("yearlyRevenueToDate", service.getCurrentYearRevenue());
        request.setAttribute("yearlyCostToDate", service.getCurrentYearCost());
        int currentYear = Year.now().getValue();
        int lastYear = currentYear - 1;
        int shownYear = 0;
        if (year == currentYear) {
            shownYear = Year.now().getValue();
        } else {
            shownYear = (Year.now().getValue()) - 1;

        }
        request.setAttribute("year", currentYear);
        request.setAttribute("shownYear", shownYear);

        request.setAttribute("lastYear", lastYear);
        List<ICostVM> yearlyCosts = service.getYearlyCost();

        request.setAttribute("yearlyCosts", yearlyCosts);
        List<ICostVM> monthlyCosts = CostVMFactory.createListInstance();

        monthlyCosts = service.getMonthlyCost(year);

        request.setAttribute("monthlyCosts", monthlyCosts);
        super.getView().forward(request, response);
    }

}
