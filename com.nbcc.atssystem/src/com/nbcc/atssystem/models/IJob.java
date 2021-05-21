/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;
import java.util.List;

/**
 * @author Susannie Tiempo and Nguyen Pham
 */
public interface IJob extends IBase {

    public int getId();

    public void setId(int id);

    public int getClientId();

    public void setClientId(int id);

    public int getTeamId();

    public void setTeamId(int id);

    public String getDescription();

    public void setDescription(String description);

    public Date getStart();

    public void setStart(Date createdAt);

    public Date getEnd();

    public void setEnd(Date updatedAt);

    public boolean isIsOnSite();

    public void setIsOnSite(boolean isOnSet);

    public List<Integer> getTasks();

    public void setTasks(List<Integer> members);

    public double getCost();

    public void setCost(double cost);

    public double getRevenue();

    public void setRevenue(double revenue);

    public boolean isIsEmergencyBooking();

    public void setIsEmergencyBooking(boolean isEmergencyBooking);

    public List<IJobComment> getComments();

    public void setComments(List<IJobComment> comments);
    }
