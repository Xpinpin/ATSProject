/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;

/**
 *
 * @author Susannie Tiempo
 * @description The Client Model Interface. Defines properties of the Client.
 * Validations for the properties will be implemented in the class that
 * implements this interface
 */
public interface IClient extends IBase {

    public int getId();

    public void setId(int id);

    public String getName();

    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);

    public String getEmailAddress();

    public void setEmailAddress(String emailAddress);

    public String getPhoneNumber();

    public void setPhoneNumber(String phoneNumber);

    public Date getCreatedAt();

    public void setCreatedAt(Date createdAt);

    public Date getUpdatedAt();

    public void setUpdatedAt(Date updatedAt);

    public Date getDeletedAt();

    public void setDeletedAt(Date deletedAt);

    public boolean isIsDeleted();

    public void setIsDeleted(boolean isDeleted);

}
