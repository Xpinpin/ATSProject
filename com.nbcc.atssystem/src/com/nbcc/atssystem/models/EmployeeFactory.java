/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Nguyen Pham
 */
public class EmployeeFactory {
    public static IEmployee createInstance(){
        return new Employee();
    }
    
    //instance to use for creation (without updatedAt and deletedAt)
    public static IEmployee createInstance(String firstName, String lastName, String SIN, double hourlyRate){
        return new Employee(firstName, lastName, SIN, hourlyRate);
    }

    
    
    public static IEmployee createInstance(int id, String firstName, String lastName, String SIN, double hourlyRate, boolean isDeleted, Date createdAt){
        return new Employee(id, firstName, lastName, SIN, hourlyRate, isDeleted, createdAt);
    }
    
    public static ArrayList<IEmployee> createListInstance(){
        return new ArrayList<IEmployee>();
    }
}
