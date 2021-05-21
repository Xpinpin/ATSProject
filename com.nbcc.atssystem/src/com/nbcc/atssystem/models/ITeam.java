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
public interface ITeam extends IBase {

    public int getId();

    public void setId(int id);

    public String getTeamName();

    public void setTeamName(String teamName);

    public boolean isIsOnCall();

    public void setIsOnCall(boolean isOnCall);

    public boolean isIsDeleted();

    public void setIsDeleted(boolean isDeleted);

    public Date getCreatedAt();

    public void setCreatedAt(Date createdAt);

    public Date getUpdatedAt();

    public void setUpdatedAt(Date updatedAt);

    public Date getDeletedAt();

    public void setDeletedAt(Date deletedAt);

    public List<Integer> getMembers();

    public void setMembers(List<Integer> members);
}
