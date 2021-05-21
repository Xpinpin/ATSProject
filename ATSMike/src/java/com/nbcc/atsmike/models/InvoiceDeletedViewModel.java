/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.models;

import java.io.Serializable;

/**
 *
 * @author Chris.Cusack
 */
public class InvoiceDeletedViewModel implements Serializable {
    private int id;
    private int rowsDeleted;
    
    public InvoiceDeletedViewModel(){}

    public InvoiceDeletedViewModel(int id, int rowsDeleted) {
        this.id = id;
        this.rowsDeleted = rowsDeleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRowsDeleted() {
        return rowsDeleted;
    }

    public void setRowsDeleted(int rowsDeleted) {
        this.rowsDeleted = rowsDeleted;
    }
}
