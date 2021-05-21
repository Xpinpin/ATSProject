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
 * @author Nguyen Pham
 */
public class Team extends Base implements ITeam {

    private int id;
    private String teamName;
    private boolean isOnCall;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private List<Integer> members;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Team() {

    }

    public Team(String teamName, boolean isOnCall) {

        this.teamName = teamName;
        this.isOnCall = isOnCall;
    }

    public Team(String teamName, boolean isOnCall, List<Integer> members) {

       setTeamName(teamName);
       setIsOnCall(isOnCall);
       setMembers(members);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Properties">
    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTeamName() {
        return teamName;
    }

    @Override
    public void setTeamName(String teamName) {
        if (teamName == null || teamName.equals("")) {
            addError(ErrorFactory.createInstance(10, "Team name is required."));
        } else {
            this.teamName = teamName;

        }

    }

    @Override
    public boolean isIsOnCall() {
        return isOnCall;
    }

    @Override
    public void setIsOnCall(boolean isOnCall) {
        
        this.isOnCall = isOnCall;
    }

    @Override
    public boolean isIsDeleted() {
        return isDeleted;
    }

    @Override
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public Date getDeletedAt() {
        return deletedAt;
    }

    @Override
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public List<Integer> getMembers() {
        return members;
    }

    @Override
    public void setMembers(List<Integer> members) {
        this.members = members;
    }

//</editor-fold>
}
