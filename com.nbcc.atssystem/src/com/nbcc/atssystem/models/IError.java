/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

/**
 *
 * @author Chris.Cusack
 */
public interface IError {
    public int getCode();
    public void setCode(int code);
    public String getDescription();
    public void setDescription(String description);
}
