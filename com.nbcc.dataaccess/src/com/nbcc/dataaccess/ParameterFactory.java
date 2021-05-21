/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.dataaccess;

import com.nbcc.dataaccess.IParameter.Direction;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class ParameterFactory {
    //Support creating Parameter class instance
    //We need to support constructor calls

    public static IParameter createInstance() {
        return new Parameter();
    }

    public static IParameter createInstance(Object value) {
        return new Parameter(value);
    }

    public static IParameter createInstance(Object value, Direction direction) {
        return new Parameter(value, direction);
    }

    public static IParameter createInstance(Object value, Direction direction, int sqlType) {
        return new Parameter(value, direction, sqlType);
    }

    public static ArrayList<IParameter> createListInstance(){
        return new ArrayList<IParameter>();
    }
    
}
