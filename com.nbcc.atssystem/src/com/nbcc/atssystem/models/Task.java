/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Gui
 */
public class Task extends Base implements ITask {

    private int id;
    private String name;
    private String description;
    private int duration;
    private Date createdAt;
    private Date updatedAt;
    private double opCost;
    private double opRevenue;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Task() {
    }

    public Task(String name, String description, int duration) {

        setName(name);
        setDescription(description);
        setDuration(duration);

    }

    public Task(int taskId, String name, String description, int duration, Date createdAt, Date updatedAt) {
        this.id = taskId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Task(int taskId, String name, String description, int duration, Date createdAt) {
        this.id = taskId;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.createdAt = createdAt;
    }

//</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Properties">
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.equals("")) {
            addError(ErrorFactory.createInstance(1, "Task Name is required."));
        } else {
            this.name = name;
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {

        if (description == null || description.equals("")) {
            addError(ErrorFactory.createInstance(1, "Task Description is required."));
        } else {
            this.description = description;
        }

    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        if (duration == 0) {
            addError(ErrorFactory.createInstance(3, "Task Duration is required."));
        }
        if (duration < 30) {
            addError(ErrorFactory.createInstance(4, "Task Duration cannot be less than 30 minutes."));
        }
        if (duration % 15 != 0) {
            addError(ErrorFactory.createInstance(5, "Task Duration has to be in 15 minutes intervals."));
        } else {
            this.duration = duration;
        }
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getOpCost() {
        return opCost;
    }

    public void setOpCost(List<Employee> employees) {
        this.opCost = calculateOpCost(employees);
    }

    public void setOpRevenue(double opRevenue) {
        this.opRevenue = opRevenue;
    }

    private double calculateOpCost(List<Employee> employees) {
        double totalCost = 0;
        for (Employee emp : employees) {
            totalCost += emp.getHourlyRate() * (double) ((duration + 30) / 60);
        }

        return totalCost;
    }

 

//</editor-fold>
}
