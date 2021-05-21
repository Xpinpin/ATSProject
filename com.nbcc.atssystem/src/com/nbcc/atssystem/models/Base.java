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
public abstract class Base implements IBase {
    
    private ArrayList<IError> errors = new ArrayList();
    
    @Override
    public ArrayList<IError> getErrors() {
        return errors;
    }
    
    @Override
    public void addError(IError error){
        errors.add(error);
    }
}
