/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Chris.Cusack
 */
public abstract class CommonController extends HttpServlet {

    private RequestDispatcher view;

    protected RequestDispatcher getView() {
        return this.view;
    }

    protected void setView(HttpServletRequest request, String view) {
        this.view = request.getRequestDispatcher(view);
    }

    protected boolean isNumeric(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected int getInteger(String value) {
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            return 0;
        }
    }

    protected int getInteger(HttpServletRequest request, String key) {
        try {
            return Integer.parseInt(request.getParameter(key));
        } catch (Exception e) {
            return 0;
        }
    }

    protected  List<Integer>  getIntegerList(HttpServletRequest request, String key) {
        try {

            String[] listId_param = request.getParameterValues(key);
            List<Integer> listId = new ArrayList();
            for (String id : listId_param) {
                listId.add(Integer.parseInt(id));
            }
            return listId;
        } catch (Exception e) {
            List<Integer> emptyList = new ArrayList();
            return  emptyList;
        }
    }

    protected double getDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (Exception e) {
            return 0.0;
        }
    }

    protected double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.parseDouble(request.getParameter(key));
        } catch (Exception e) {
            return 0.0;
        }
    }

    protected String getValue(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }

    protected Date getDate(HttpServletRequest request, String key) {
        try {
            Date date = new Date();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

            date = df.parse(request.getParameter(key));

            return date;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected String getFormattedDate(String dateString) {

        try {
            String[] dateParts = dateString.split("T");
            String formattedDate = dateParts[0] + " " + dateParts[1];

            return formattedDate;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    protected String getStringDate(String date) {

        try {

            String[] dateParts = date.split(" ");
            String formattedDate = dateParts[0] + "T" + dateParts[1];

            return formattedDate;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
