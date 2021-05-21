/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;

/**
 *
 * @author Nguyen Pham
 */
public interface IEmployee extends IBase {
     public int getId();
    
    public void setId(int id);
    
    public String getFirstName();
    
    public void setFirstName(String firstName);
    
    public String getLastName();
    
    public void setLastName(String lastName);
    
    public String getSIN();
    
    public void setSIN(String SIN);
    
    public double getHourlyRate();
    
    public void setHourlyRate(double hourlyRate);
    
    public boolean getIsDeleted();
    
    public void setIsDeleted(boolean isDeleted);
    
    public Date getCreatedAt();
    
    public void setCreatedAt(Date createdAt);
    
    public Date getUpdatedAt();
    
    public void setUpdatedAt(Date updatedAt);
    
    public Date getDeletedAt();
    
    public void setDeletedAt(Date deletedAt);
    
}
