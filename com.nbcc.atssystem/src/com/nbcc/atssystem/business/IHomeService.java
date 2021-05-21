/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ICostVM;
import com.nbcc.atssystem.models.ITeam;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface IHomeService {

    public List<ICostVM> getMonthlyCost(int year);

    public List<ICostVM> getYearlyCost();

    public int getJobsNumToday();

    public ITeam getCurrentOnCallTeam();

    public Double getCurrentMonthRevenue();

    public Double getCurrentMonthCost();

    public Double getCurrentYearRevenue();

    public Double getCurrentYearCost();
}
