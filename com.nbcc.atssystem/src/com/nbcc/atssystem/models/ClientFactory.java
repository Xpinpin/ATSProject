/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Susannie Tiempo
 */
public class ClientFactory {

    public static IClient createInstance() {
        return new Client();
    }

    public static IClient createInstance( String name, String address, String emailAddress, String phoneNumber) {
        return new Client(name, address, emailAddress, phoneNumber);
    }

    public static IClient createInstance(int id, String name, String address, Date createdAt, Date updatedAt, Date deletedAt) {
        return new Client(id, name, address, createdAt, updatedAt, deletedAt);
    }

    public static ArrayList<IClient> createListInstance() {
        return new ArrayList<IClient>();
    }
}
