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
 * @author Nguyen Pham
 */
public class Employee extends Base implements IEmployee {

    private int id;
    private String firstName;
    private String lastName;
    private String SIN;
    private double hourlyRate;
    private boolean isDeleted;
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    //<editor-fold defaultstate="collapsed" desc="Constructors">
    public Employee() {

    }

    public Employee(String firstName, String lastName, String SIN, double hourlyRate) {

        setFirstName(firstName);
        setLastName(lastName);
        setSIN(SIN);
        setHourlyRate(hourlyRate);
    }

    public Employee(int id, String firstName, String lastName, String SIN, double hourlyRate, boolean isDeleted, Date createdAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SIN = SIN;
        this.hourlyRate = hourlyRate;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
    }

    public Employee(int id, String firstName, String lastName, String SIN, double hourlyRate, boolean isDeleted, Date createdAt, Date updatedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SIN = SIN;
        this.hourlyRate = hourlyRate;
        this.isDeleted = isDeleted;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Employee(int id, String firstName, String lastName, String SIN, double hourlyRate, boolean isDeleted, Date createdAt, Date updatedAt, Date deletedAt) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.SIN = SIN;
        this.hourlyRate = hourlyRate;
        this.isDeleted = isDeleted;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {

        if (firstName == null || firstName.equals("")) {
            addError(ErrorFactory.createInstance(1, "First Name is required."));
        } else if (firstName.length() > 30) {
            addError(ErrorFactory.createInstance(2, "First Name maximum length is 30 characters."));
        } else {
            this.firstName = firstName;

        }

    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.equals("")) {
            addError(ErrorFactory.createInstance(3, "Last Name is required."));
        } else if (lastName.length() > 30) {
            addError(ErrorFactory.createInstance(4, "Last Name maximum length is 30 characters."));
        } else {
            this.lastName = lastName;

        }
    }

    public String getSIN() {
        return SIN;
    }

    public void setSIN(String SIN) {

        if (SIN == null || SIN.equals("")) {
            addError(ErrorFactory.createInstance(5, "SIN is required."));
        } else if (!Pattern.compile("\\b(?:\\d{3}\\d{3}\\d{3})\\b|\\b(?:\\d{3} \\d{3} \\d{3})\\b", Pattern.CASE_INSENSITIVE).matcher(SIN).find()) {
            addError(ErrorFactory.createInstance(6, "SIN is not in a correct format."));
        } else {
            this.SIN = SIN;

        }

    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        if (hourlyRate == 0.0) {
            addError(ErrorFactory.createInstance(7, "Hourly Rate is required."));
        } else {
            this.hourlyRate = hourlyRate;

        }
    }

    public boolean getIsDeleted() {
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
