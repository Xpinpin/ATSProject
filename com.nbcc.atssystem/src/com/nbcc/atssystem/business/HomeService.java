/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ICostVM;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.repository.HomeRepositoryFactory;
import com.nbcc.atssystem.repository.IHomeRepository;
import java.util.List;

/**
 *
 * @author DELL
 */
public class HomeService implements IHomeService{
    private final IHomeRepository repo;

    public HomeService() {
        repo = HomeRepositoryFactory.createInstance();
    }
    
    public List<ICostVM> getMonthlyCost(int year) {
        return repo.getAllMonthlyCostByYear(year);
    }
    
    public List<ICostVM> getYearlyCost() {
        return repo.getAllYearlyCost();
    }
    
    public int getJobsNumToday() {
        return repo.getNumberJobsToday();
    }
    
    public ITeam getCurrentOnCallTeam() {
        return repo.getCurrentTeamOnCall();
    }
    
    public Double getCurrentMonthRevenue() {
        return repo.getUpToDateMonthlyRevenue();
    }
    public Double getCurrentMonthCost(){
        return repo.getUpToDateMonthlyCost();
    }
    public Double getCurrentYearRevenue(){
        return repo.getUpToDateYearlyRevenue();
    }
    public Double getCurrentYearCost() {
        return repo.getUpToDateYearlyCost();
    }
    
}
