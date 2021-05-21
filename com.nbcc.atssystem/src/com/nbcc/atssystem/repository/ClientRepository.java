/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.ClientFactory;
import com.nbcc.atssystem.models.IClient;
import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Susannie Tiempo
 */
public class ClientRepository extends BaseRepository implements IClientRepository {

    private final String SPROC_INSERT_CLIENT = "CALL InsertClient(?,?,?,?,?)";
    private final String SPROC_SELECT_CLIENT = "CALL ShowClient(?)";
    private final String SPROC_SELECT_CLIENTS = "CALL ShowClients(?)";
    private final String SPROC_SELECT_ALL_CLIENTS = "CALL ShowAllClients()";
    private final String SPROC_DELETE_CLIENT = "CALL DeleteClient(?)";
    private final String SPROC_UPDATE_CLIENT = "CALL InsertClient(?,?,?,?,?)";
    
    private IDAL dataAccess;

    public ClientRepository() {
        dataAccess = DALFactory.createInstance();
    }

    /**
     * Add a new client to the database
     *
     * @param client
     * @return the client Id just inserted
     */
    @Override
    public int insertClient(IClient client) {
        int returnId = 0;
        List<Object> returnValues;

        List<IParameter> parms = ParameterFactory.createListInstance();

        //INPUT PARMS
        parms.add(ParameterFactory.createInstance(client.getName()));
        parms.add(ParameterFactory.createInstance(client.getAddress()));
        parms.add(ParameterFactory.createInstance(client.getEmailAddress()));
        parms.add(ParameterFactory.createInstance(client.getPhoneNumber()));

        //OUTPUT PARMS
        parms.add(ParameterFactory.createInstance(returnId, IParameter.Direction.OUT, java.sql.Types.INTEGER));

        returnValues = dataAccess.executeNonQuery(SPROC_INSERT_CLIENT, parms);

        try {
            if (returnValues != null) {
                returnId = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnId;
    }

    /**
     * Updates a client.
     *
     * @param client
     * @return the count of the affected rows.
     */
    @Override
    public int updateClient(IClient client) {
        int affectedRows = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(client.getId()));
        params.add(ParameterFactory.createInstance(client.getName()));
        params.add(ParameterFactory.createInstance(client.getAddress()));
        params.add(ParameterFactory.createInstance(client.getEmailAddress()));
        params.add(ParameterFactory.createInstance(client.getPhoneNumber()));
        returnValues = dataAccess.executeNonQuery(SPROC_UPDATE_CLIENT, params);
        try {
            if (returnValues != null) {
                affectedRows = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return affectedRows;
    }

    /**
     * Get the client list based on search criteria
     *
     * @return List of the clients that meets the search string criteria for
     * client name
     */
    @Override
    public List<IClient> retrieveClients(String searchString) {
        List<IClient> retrievedClients = ClientFactory.createListInstance();

        try {
            //INPUT PARMS
            List<IParameter> parms = ParameterFactory.createListInstance();
            parms.add(ParameterFactory.createInstance(searchString));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_CLIENTS, parms);
            retrievedClients = toListOfClients(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedClients;
    }

    /**
     * Get the client list
     *
     * @return List of the clients
     */
    @Override
    public List<IClient> retrieveAllClients() {
        List<IClient> retrievedClients = ClientFactory.createListInstance();

        try {
            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_ALL_CLIENTS, null);
            retrievedClients = toListOfClients(cs);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedClients;
    }

    /**
     * Get the specific client by the ID
     *
     * @param id
     * @return Client
     */
    @Override
    public IClient retrieveClient(int id) {
        IClient retrievedClient = ClientFactory.createInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SELECT_CLIENT, params);
            retrievedClient = toListOfClients(cs).get(0);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return retrievedClient;
    }

    /**
     * Deletes a client
     *
     * @param id
     * @return deleted client
     */
    @Override
    public int deleteClient(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(id));
        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_CLIENT, params);
        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt((returnValues.get(0).toString()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected;
    }

    /**
     * Create a list of clients by passing in the data from database
     *
     * @param cs
     * @return list of clients
     * @throws SQLException
     */
    private List<IClient> toListOfClients(CachedRowSet cs) throws SQLException {
        List<IClient> retrievedClients = ClientFactory.createListInstance();
        IClient client;

        while (cs.next()) {
            client = ClientFactory.createInstance();
            client.setId(super.getInt("ClientId", cs));
            client.setName(cs.getString("Name"));
            client.setAddress(cs.getString("Address"));
            client.setEmailAddress(cs.getString("EmailAddress"));
            client.setPhoneNumber(cs.getString("PhoneNumber"));
            client.setCreatedAt(super.getDate("CreatedAt", cs));
            client.setUpdatedAt(super.getDate("UpdatedAt", cs));
            client.setDeletedAt(super.getDate("DeletedAt", cs));
            client.setIsDeleted(cs.getBoolean(8));

            retrievedClients.add(client);
        }

        return retrievedClients;
    }

}
