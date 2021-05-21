/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

/**
 *
 * @author DELL
 */
public class CostVM extends Base implements ICostVM{
    
    private int Month;
    private int Year;
    private Double Cost;
    private Double Billable;
    private Double Revenue;

    public CostVM(){
        
    }
    
    public CostVM(int Month, int Year, Double Cost, Double Billable, Double Revenue) {
        this.Month = Month;
        this.Year = Year;
        this.Cost = Cost;
        this.Billable = Billable;
        this.Revenue = Revenue;
    }

    public CostVM(int Year, Double Cost, Double Billable, Double Revenue) {
        this.Year = Year;
        this.Cost = Cost;
        this.Billable = Billable;
        this.Revenue = Revenue;
    }

    
    
    public int getMonth() {
        return Month;
    }

    public void setMonth(int Month) {
        this.Month = Month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int Year) {
        this.Year = Year;
    }

    public Double getCost() {
        return Cost;
    }

    public void setCost(Double Cost) {
        this.Cost = Cost;
    }

    public Double getBillable() {
        return Billable;
    }

    public void setBillable(Double Billable) {
        this.Billable = Billable;
    }

    public Double getRevenue() {
        return Revenue;
    }

    public void setRevenue(Double Revenue) {
        this.Revenue = Revenue;
    }
    
    
    
    
}
