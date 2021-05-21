/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atsmike.models.ErrorViewModel;
import com.nbcc.atssystem.business.ClientServiceFactory;
import com.nbcc.atssystem.business.IClientService;
import com.nbcc.atssystem.models.ClientFactory;
import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IClient;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class ClientController extends CommonController {

    private static final String CLIENTS_VIEW = "/clients.jsp";
    private static final String CLIENTS_MAINT_VIEW = "/client.jsp";
    private static final String CLIENTS_SUMMARY_VIEW = "/clientsummary.jsp";

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String pathInfo = request.getPathInfo();

        IClientService clientService = ClientServiceFactory.createInstance();

        //We want to get to a specific Client
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");

            int id = super.getInteger(pathParts[1]);

            //Get the client and put it to a variable
            IClient client = clientService.getClient(id);

            if (client != null) {
                request.setAttribute("client", client);
            } else {
                request.setAttribute("error", new ErrorViewModel(String.format("Client ID: %s is not found", id)));

            }

            //Set attribute as client or error
            super.setView(request, CLIENTS_MAINT_VIEW);
        } else {
            //We want to show the Client List
            request.setAttribute("clients", clientService.getAllClientList());
            super.setView(request, CLIENTS_VIEW);
        }

        super.getView().forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        super.setView(request, CLIENTS_SUMMARY_VIEW);

        IClientService clientService = ClientServiceFactory.createInstance();
        IClient client;
        try {
            int id = super.getInteger(request, "hdnClientId");

            String actionFull = super.getValue(request, "action");
            String action = "";
            int deletionId = 0;
            if (actionFull.contains("/")) {
                String[] pathParts = actionFull.split("/");
                deletionId = super.getInteger(pathParts[1]);
                action = pathParts[0];
            } else {
                action = actionFull;
            }

            switch (action.toLowerCase()) {
                case "create":
                    client = setClient(request);
                    client = clientService.createClient(client);

                    request.setAttribute("client", client);

                    if (!clientService.isValid(client)) {
                        request.setAttribute("errors", client.getErrors());
                        super.setView(request, CLIENTS_MAINT_VIEW);
                    }
                    break;

                case "search":
                    String searchString = super.getValue(request, "searchString");
                    request.setAttribute("clients", clientService.getClientList(searchString));
                    super.setView(request, CLIENTS_VIEW);
                    break;

               case "save":
                    client = setClient(request);
                    client.setId(id);
                    if (clientService.saveClient(client) == false) {
                        client.addError(ErrorFactory.createInstance(15, "Save failed. No row updated."));
                    }
                    
                    client = clientService.getClient(id);
                    request.setAttribute("savedClient", 1);
                    request.setAttribute("client", client);
                    if (!clientService.isValid(client)) {
                        request.setAttribute("errors", client.getErrors());
                        super.setView(request, CLIENTS_MAINT_VIEW);
                    }
                    break;

                case "delete":

                    String isDeleted = super.getValue(request, "isDeleted");
                    client = setClient(request);
                    client.setId(id);

                    request.setAttribute("client", client);

                    if (isDeleted == null && deletionId == 0) {
                        request.setAttribute("isDeleted", 1);
                        request.setAttribute("idToDelete", id);
                        super.setView(request, CLIENTS_MAINT_VIEW);
                    } else {
                        if (clientService.deleteClient(deletionId) == 0) {
                            client.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                            request.setAttribute("isDeleted", null);
                            request.setAttribute("errors", client.getErrors());
                            super.setView(request, CLIENTS_MAINT_VIEW);
                        } else {
                            request.setAttribute("isSuccess", 1);
                            request.setAttribute("clients", clientService.getAllClientList());
                            super.setView(request, CLIENTS_VIEW);
                        }

                    }

                    break;

                case "reset":
                    request.setAttribute("clients", clientService.getAllClientList());
                    super.setView(request, CLIENTS_VIEW);

            }

        } catch (Exception ex) {

        }
        super.getView().forward(request, response);
    }

    private IClient setClient(HttpServletRequest request) {

        String name = "";
        String address = "";
        String emailAddress = "";
        String phoneNumber = "";

        name = super.getValue(request, "clientname");
        address = super.getValue(request, "address");
        emailAddress = super.getValue(request, "emailAddress");
        phoneNumber = super.getValue(request, "phoneNumber");

        IClient client = ClientFactory.createInstance(name, address, emailAddress, phoneNumber);

        return client;
    }

}
