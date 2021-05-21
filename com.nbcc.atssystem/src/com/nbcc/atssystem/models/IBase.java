/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;

/**
 *
 * @author Chris.Cusack
 */
public interface IBase {
    ArrayList<IError> getErrors();
    void addError(IError error);
}
