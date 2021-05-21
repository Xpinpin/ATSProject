/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.IClient;
import java.util.List;

/**
 *
 * @author Susannie Tiempo
 */
public interface IClientService {

    IClient createClient(IClient client);

    IClient getClient(int id);

    List<IClient> getClientList(String searchString);

    List<IClient> getAllClientList();

    boolean isValid(IClient client);

    int deleteClient(int id);

    boolean saveClient(IClient client);
}
