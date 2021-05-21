/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.List;

/**
 *
 * @author shin
 */
public class JobVM {

    private IJob job = JobFactory.createInstance();
    private String clientName;
    private String teamName;
    private String start;
    private String end;
    private List<ITask> tasks =  TaskFactory.createListInstance();
    private List<IJob> jobs = JobFactory.createListInstance();

    public List<IJob> getJobs() {
        return jobs;
    }

    public void setJobs(List<IJob> jobs) {
        this.jobs = jobs;
    }

    
    public List<ITask> getTasks() {
        return tasks;
    }

    public void setTasks(List<ITask> tasks) {
        this.tasks = tasks;
    }

    public JobVM() {
    }

    public IJob getJob() {
        return job;
    }

    public void setJob(IJob job) {
        this.job = job;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

}
