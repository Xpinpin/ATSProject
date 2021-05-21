/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

/**
 *
 * @author Nguyen Pham
 */
public class EmployeeRepositoryFactory {
    public static IEmployeeRepository createInstance(){
        return new EmployeeRepository();
    }
}
