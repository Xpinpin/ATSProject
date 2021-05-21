    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.Date;
import java.util.stream.Stream;

/**
 *
 * @author Gui
 * @description The Invoice Model Interface. Defines properties of the Invoice. Validations for the properties will
 * be implemented in the class that implements this interface
 */
public interface ITask extends IBase {


    int getId();

    void setId(int id);

    String getName();

    void setName(String name);
    
    String getDescription();

    void setDescription(String description);
    
    int getDuration();

    void setDuration(int duration);

    Date getCreatedAt();

    void setCreatedAt(Date createdAt);
    
    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);
}
