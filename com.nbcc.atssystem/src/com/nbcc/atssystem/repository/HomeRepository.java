/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.CostVMFactory;
import com.nbcc.atssystem.models.ICostVM;
import com.nbcc.atssystem.models.ITeam;
import com.nbcc.atssystem.models.TeamFactory;
import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author DELL
 */
public class HomeRepository extends BaseRepository implements IHomeRepository {

    private final String SPROC_CURRENT_TEAM_ON_CALL = "CALL CurrentTeamOnCall()";
    private final String SPROC_MONTHLY_COST = "CALL ShowMonthlyCostForYear(?)";
    private final String SPROC_YEARLY_COST = "CALL ShowYearlyCost()";
    private final String SPROC_JOBS_TODAY = "CALL NumberOfJobsToday()";
    private final String SPROC_MONTHLY_REVENUE_TODATE = "CALL ShowMonthlyRevenue()";
    private final String SPROC_MONTHLY_COST_TODATE = "CALL ShowMonthlyCost()";
    private final String SPROC_YEARLY_REVENUE_TODATE = "CALL ShowYearlyRevenue()";
    private final String SPROC_YEARLY_COST_TODATE = "CALL ShowYearlyCostToDate()";

    private IDAL dataAccess;

    public HomeRepository() {
        dataAccess = DALFactory.createInstance();
    }

    public Double getUpToDateMonthlyRevenue() {
        return Double.parseDouble(dataAccess.executeScalar(SPROC_MONTHLY_REVENUE_TODATE, null).toString());
    }

    public Double getUpToDateMonthlyCost() {
        return Double.parseDouble(dataAccess.executeScalar(SPROC_MONTHLY_COST_TODATE, null).toString());
    }

    public Double getUpToDateYearlyRevenue() {
        return Double.parseDouble(dataAccess.executeScalar(SPROC_YEARLY_REVENUE_TODATE, null).toString());
    }

    public Double getUpToDateYearlyCost() {
        return Double.parseDouble(dataAccess.executeScalar(SPROC_YEARLY_COST_TODATE, null).toString());
    }

    public List<ICostVM> getAllMonthlyCostByYear(int year) {

        List<ICostVM> monthlyCosts = CostVMFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(year));

            CachedRowSet cs = dataAccess.executeFill(SPROC_MONTHLY_COST, params);
            if (cs != null) {
                monthlyCosts = toListOfCostVM(cs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return monthlyCosts;

    }

    public List<ICostVM> getAllYearlyCost() {

        List<ICostVM> yearlyCosts = CostVMFactory.createListInstance();

        try {

            CachedRowSet cs = dataAccess.executeFill(SPROC_YEARLY_COST, null);
            if (cs != null) {
                List<ICostVM> retrievedCosts = CostVMFactory.createListInstance();
                ICostVM cost;

                while (cs.next()) {
                    cost = CostVMFactory.createInstance();

                    cost.setYear(super.getInt("Year", cs));
                    cost.setCost(super.getDouble("TotalCost", cs));
                    cost.setBillable(super.getDouble("TotalBillable", cs));
                    cost.setRevenue(super.getDouble("TotalRevenue", cs));

                    retrievedCosts.add(cost);

                }

                yearlyCosts = retrievedCosts;
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return yearlyCosts;

    }

    public int getNumberJobsToday() {
        int jobsNum = 0;

        jobsNum = Integer.parseInt(dataAccess.executeScalar(SPROC_JOBS_TODAY, null).toString());

        return jobsNum;
    }

    public ITeam getCurrentTeamOnCall() {

        List<ITeam> retrievedTeams = TeamFactory.createListInstance();
        try {

            CachedRowSet cs = dataAccess.executeFill(SPROC_CURRENT_TEAM_ON_CALL, null);
            retrievedTeams = toListOfTeams(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        if (retrievedTeams.size() == 0) {
            return null;
        } else {
            return retrievedTeams.get(0);

        }

    }

    private List<ICostVM> toListOfCostVM(CachedRowSet cs) throws SQLException {
        List<ICostVM> retrievedCosts = CostVMFactory.createListInstance();
        ICostVM cost;

        while (cs.next()) {
            cost = CostVMFactory.createInstance();

            cost.setMonth(super.getInt("Month", cs));
            cost.setYear(super.getInt("Year", cs));
            cost.setCost(super.getDouble("TotalCost", cs));
            cost.setBillable(super.getDouble("TotalBillable", cs));
            cost.setRevenue(super.getDouble("TotalRevenue", cs));

            retrievedCosts.add(cost);

        }

        return retrievedCosts;
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

}
