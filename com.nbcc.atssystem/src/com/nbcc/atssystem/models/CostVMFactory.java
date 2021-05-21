/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class CostVMFactory {
      public static ICostVM createInstance() {
        return new CostVM();
    }

    public static ICostVM createInstance(int Month, int Year, Double Cost, Double Billable, Double Revenue) {
        return new CostVM(Month, Year, Cost, Billable, Revenue);
    }
    
    public static ICostVM createInstance(int Year, Double Cost, Double Billable, Double Revenue) {
        return new CostVM( Year, Cost, Billable, Revenue);
    }
    
    
    public static ArrayList<ICostVM> createListInstance() {
        return new ArrayList<ICostVM>();
    }
}
