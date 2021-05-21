/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.ICostVM;
import com.nbcc.atssystem.models.ITeam;

import java.util.List;

/**
 *
 * @author DELL
 */
public interface IHomeRepository {

    public List<ICostVM> getAllMonthlyCostByYear(int id);

    public List<ICostVM> getAllYearlyCost();

    public int getNumberJobsToday();

    public ITeam getCurrentTeamOnCall();

    public Double getUpToDateMonthlyRevenue();

    public Double getUpToDateMonthlyCost();

    public Double getUpToDateYearlyRevenue();

    public Double getUpToDateYearlyCost();
}
