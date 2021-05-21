/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

/**
 *
 * @author Susannie Tiempo
 */
public class JobServiceFactory {
    public static IJobService createInstance(){
        return new JobService();
    }
}
