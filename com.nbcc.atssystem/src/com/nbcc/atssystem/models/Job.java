/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Susannie Tiempo Ent Java - ATS Project April 2020
 */
public class Job extends Base implements IJob {

    private int id;
    private int clientId;
    private int teamId;
    private String description;
    private boolean isOnSite;
    private boolean isEmergencyBooking;
    private Date start;
    private Date end;
    private List<Integer> tasks;
    private double cost;
    private double revenue;
    private List<IJobComment> comments = JobCommentFactory.createListOfInstance();

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Job() {
    }

    public Job(int id, int clientId, int teamId, String description, boolean isOnSite, Date start, Date end, List<Integer> tasks) {
        setId(id);
        setClientId(clientId);
        setTeamId(teamId);
        setDescription(description);
        setIsOnSite(isOnSite);
        setStart(start);
        setEnd(end);
        setTasks(tasks);
    }

    public Job(int clientId, int teamId, String description, boolean isOnSite, Date start, Date end, List<Integer> tasks) {
        setClientId(clientId);
        setTeamId(teamId);
        setDescription(description);
        setIsOnSite(isOnSite);
        setStart(start);
        setEnd(end);
        setTasks(tasks);
    }

    public Job(int clientId, int teamId, String description, boolean isOnSite, Date start, Date end, List<Integer> tasks, double cost, double revenue) {
        setClientId(clientId);
        setTeamId(teamId);
        setDescription(description);
        setIsOnSite(isOnSite);
        setStart(start);
        setEnd(end);
        setTasks(tasks);
        setCost(cost);
        setRevenue(revenue);
    }

    public Job(int id, int clientId, int teamId, String description, boolean isOnSite, boolean isEmergencyBooking, Date start, Date end, List<Integer> tasks, double cost, double revenue) {
        setClientId(clientId);
        setTeamId(teamId);
        setDescription(description);
        setIsOnSite(isOnSite);
        setIsEmergencyBooking(isEmergencyBooking);
        setStart(start);
        setEnd(end);
        setTasks(tasks);
        setCost(cost);
        setRevenue(revenue);
    }

    public Job(int clientId, int teamId, String description, boolean isOnSite, boolean isEmergencyBooking, Date start, Date end, List<Integer> tasks, double cost, double revenue) {
        setClientId(clientId);
        setTeamId(teamId);
        setDescription(description);
        setIsOnSite(isOnSite);
        setIsEmergencyBooking(isEmergencyBooking);
        setStart(start);
        setEnd(end);
        setTasks(tasks);
        setCost(cost);
        setRevenue(revenue);
    }

//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Properties">
    public int getId() {
        return id;
    }

    public List<IJobComment> getComments() {
        return comments;
    }

    public void setComments(List<IJobComment> comments) {
        this.comments = comments;
    }


    public boolean isIsEmergencyBooking() {
        return isEmergencyBooking;
    }

    public void setIsEmergencyBooking(boolean isEmergencyBooking) {
        this.isEmergencyBooking = isEmergencyBooking;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        if (clientId == 0) {
            addError(ErrorFactory.createInstance(1, "Client  is required."));
        } else {
            this.clientId = clientId;
        }
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        if (teamId == 0) {
            addError(ErrorFactory.createInstance(2, "Team  is required."));

        } else {
            this.teamId = teamId;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == null || description.equals("")) {
            addError(ErrorFactory.createInstance(3, "Description  is required."));
        } else if (description.length() > 100) {
            addError(ErrorFactory.createInstance(4, "Descriptionmaximum length is 100 characters."));
        } else {
            this.description = description;
        }
    }

    public boolean isIsOnSite() {
        return isOnSite;
    }

    public void setIsOnSite(boolean isOnSite) {
        this.isOnSite = isOnSite;
    }

    public Date getStart() {

        return start;
    }

    public void setStart(Date start) {

        if (start == null || start.equals("")) {
            addError(ErrorFactory.createInstance(5, "Start  is required."));
            this.start = start;
            return;
        } else {

            Calendar c1 = Calendar.getInstance();
            c1.setTime(start);
            int hour = c1.get(Calendar.HOUR_OF_DAY);
            if (start.before(new Date())) {
                addError(ErrorFactory.createInstance(6, "Start cannot be in the past."));
            } else if (!this.isEmergencyBooking && ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                    || (Calendar.DAY_OF_WEEK == Calendar.SUNDAY))) {
                addError(ErrorFactory.createInstance(7, "Start date must be on business days only"));
            } else if (!this.isEmergencyBooking && (hour < 8 || hour > 17)) {
                addError(ErrorFactory.createInstance(8, "Start time is not within business hours. Business hours: 8AM-5PM M-F"));
            } else {
                this.start = start;
            }

        }

    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(start);
        int hour = c1.get(Calendar.HOUR_OF_DAY);

        if (end == null || end.equals("")) {
            addError(ErrorFactory.createInstance(9, "End  is required."));
        } else if (!this.isEmergencyBooking && ((c1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
                || (Calendar.DAY_OF_WEEK == Calendar.SUNDAY))) {
            addError(ErrorFactory.createInstance(10, "Must be on business days only"));
        } else if (!this.isEmergencyBooking && hour > 17) {
            addError(ErrorFactory.createInstance(11, "End time is after business hourss. Business hours: 8AM-5PM M-F"));
        } else {
            this.end = end;
        }
    }

    public List<Integer> getTasks() {
        return tasks;
    }

    public void setTasks(List<Integer> tasks) {
        if (tasks.size() == 0) {
            addError(ErrorFactory.createInstance(12, "At least 1 task is required."));
        } else {
            this.tasks = tasks;
        }
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

//</editor-fold>
}
