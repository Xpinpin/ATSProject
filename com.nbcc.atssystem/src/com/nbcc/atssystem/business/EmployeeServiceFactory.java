/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

/**
 *
 * @author Nguyen Pham
 */
public class EmployeeServiceFactory {
    public static IEmployeeService createInstance(){
        return new EmployeeService();
    }
}
