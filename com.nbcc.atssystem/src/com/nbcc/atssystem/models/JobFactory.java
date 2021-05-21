/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Susannie Tiempo
 */
public class JobFactory {

    public static IJob createInstance() {
        return new Job();
    }

    public static IJob createInstance(int clientId, int teamId, String description, boolean isOnSite, Date createdAt, Date updatedAt, List<Integer> tasks) {
        return new Job(clientId, teamId, description, isOnSite, createdAt, updatedAt, tasks);
    }


    public static IJob createInstance(int clientId, int teamId, String description, boolean isOnSite, boolean isEmergencyBooking, Date start, Date end, List<Integer> tasks, double cost, double revenue) {
        return new Job(clientId, teamId, description, isOnSite, isEmergencyBooking, start, end, tasks, cost, revenue);
    }

    public static ArrayList<IJob> createListInstance() {
        return new ArrayList<IJob>();
    }
}
