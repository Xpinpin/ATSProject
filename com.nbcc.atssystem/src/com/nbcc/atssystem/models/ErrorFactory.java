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
public class ErrorFactory {
    //complete the factory for constructors
    
    //createListInstance
    public static IError createInstance(){
        return new Error();
    }
    
    public static IError createInstance(int code, String description){
        return new Error(code, description);
    }
    
    
    public static ArrayList<IError> createListInstance(){
        return new ArrayList<IError>();
    }
}
