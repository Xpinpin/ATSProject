/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IClient;
import com.nbcc.atssystem.repository.ClientRepositoryFactory;
import com.nbcc.atssystem.repository.IClientRepository;
import java.util.List;

/**
 *
 * @author Susannie Tiempo
 */
public class ClientService implements IClientService {

    private final IClientRepository repo;

    public ClientService() {
        repo = ClientRepositoryFactory.createInstance();
    }

    /**
     * Create the client in the service layer
     *
     * @param client
     * @return Client
     */
    @Override
    public IClient createClient(IClient client) {
        if (isValid(client)) {
            int id = repo.insertClient(client);

            if (id > 0) {
                client.setId(id);
            } else {
                client.addError(ErrorFactory.createInstance(4, "Client is not valid for creation."));
            }

        } else {
            client.addError(ErrorFactory.createInstance(99, "Client creation failed due to the validation errors."));
        }
        return client;
    }
    
    
    @Override
    public boolean saveClient(IClient client) {
        int returnedRow = 0;
        if (isValid(client)) {
             returnedRow = repo.updateClient(client);
        } else {
            client.addError(ErrorFactory.createInstance(99, "Client update failed due to the validation errors."));
        }

        return returnedRow > 0;
    }

    /**
     * Get the client based on the ID in the service layer
     *
     * @param id
     * @return Client
     */
    @Override
    public IClient getClient(int id) {
        IClient client = repo.retrieveClient(id);

        return client;
    }

    /**
     * Get all of the client in the service layer
     *
     * @param searchString
     * @return List of Clients
     */
    @Override
    public List<IClient> getClientList(String searchString) {
        List<IClient> clients = repo.retrieveClients(searchString);

        return clients;
    }

    /**
     * Get all of the client in the service layer
     *
     * @param searchString
     * @return List of Clients
     */
    @Override
    public List<IClient> getAllClientList() {
        List<IClient> clients = repo.retrieveAllClients();

        return clients;
    }

    @Override
    public int deleteClient(int id) {
        
        if (id <= 0) {
            return 0;
        } else {

            return repo.deleteClient(id);
        }
    }

    /**
     * Make sure that the Client passing in is valid for manipulation
     *
     * @param client
     * @return boolean
     */
    @Override
    public boolean isValid(IClient client) {
        return client.getErrors().isEmpty();
    }
}
