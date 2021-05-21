/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author Susannie Tiempo
 */
public class Client extends Base implements IClient {

    private int id;
    private String name;
    private String address;
    private String emailAddress;
    private String phoneNumber;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;
    private boolean isDeleted;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Client() {

    }

    public Client(String name, String address, String emailAddress, String phoneNumber) {

        setName(name);
        setAddress(address);
        setEmailAddress(emailAddress);
        setPhoneNumber(phoneNumber);

    }

    public Client(int id, String name, String address, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
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
            addError(ErrorFactory.createInstance(1, "Name is required."));
        } else if (name.length() > 30) {
            addError(ErrorFactory.createInstance(2, "Name maximum length is 50 characters."));
        } else {
            this.name = name;

        }
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {

        if (address == null || address.equals("")) {
            addError(ErrorFactory.createInstance(1, "Address is required."));
        } else if (address.length() > 150) {
            addError(ErrorFactory.createInstance(2, "Address maximum length is 150 characters."));
        } else {
            this.address = address;
        }

    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        if (emailAddress == null || emailAddress.equals("")) {
            addError(ErrorFactory.createInstance(1, "EmailAddress is required."));
        } else if (!Pattern.compile(regex, Pattern.CASE_INSENSITIVE).matcher(emailAddress).find()) {
            addError(ErrorFactory.createInstance(2, "The Email Address is invalid."));
        } else if (emailAddress.length() > 150) {
            addError(ErrorFactory.createInstance(3, "Email address maximum length is 150 characters."));
        } else {
            this.emailAddress = emailAddress;

        }
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNum) {
        String pattern = "^(\\d{3}[- .]?){2}\\d{4}$";

        if (phoneNum == null || phoneNum.equals("")) {
            addError(ErrorFactory.createInstance(5, "Phone number is required."));
        } else if (!Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(phoneNum).find()) {
            addError(ErrorFactory.createInstance(6, "The phone numer is in valid format.Valid format are: NNNNNNNNNN; NNN NNN NNNN; NNN.NNN.NNNN; and NNN-NNN-NNNN"));
        } else if (phoneNum.length() > 20) {
            addError(ErrorFactory.createInstance(3, "Pone number maximum length is 20 characters."));
        } else {
            this.phoneNumber = phoneNum;
        }

    }

    public boolean isIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

//</editor-fold>
    
    
}
