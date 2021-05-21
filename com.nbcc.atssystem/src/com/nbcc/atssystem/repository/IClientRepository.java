/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.IClient;
import java.util.List;

/**
 *
 * @author Susannie Tiemppo
 */
public interface IClientRepository {
    int insertClient(IClient client);
    List<IClient> retrieveClients(String searchString);
    List<IClient> retrieveAllClients();
    IClient retrieveClient(int id);
    int deleteClient(int id);
    int updateClient(IClient client);
}
