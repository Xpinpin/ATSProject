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
public interface ICostVM  extends IBase{
      public int getMonth();

    public void setMonth(int Month);

    public int getYear();

    public void setYear(int Year);

    public Double getCost();

    public void setCost(Double Cost);

    public Double getBillable();

    public void setBillable(Double Billable);

    public Double getRevenue();

    public void setRevenue(Double Revenue);
}
