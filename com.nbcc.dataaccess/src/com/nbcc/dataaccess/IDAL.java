/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.dataaccess;

import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Chris.Cusack
 */
public interface IDAL {
    List<Object> executeNonQuery(String statement, List<IParameter> params);
    CachedRowSet executeFill(String statement, List<IParameter> params); //DataTable in C#
    Object executeScalar(String statement,List<IParameter> params);
}
