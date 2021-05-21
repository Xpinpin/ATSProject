/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public class TeamFactory {

    public static ITeam createInstance() {
        return new Team();
    }

    public static ITeam createInstance(String teamName, boolean isOnCall) {
        return new Team(teamName, isOnCall);
    }

    public static ITeam createInstance(String teamName, boolean isOnCall, List<Integer> members) {
        return new Team(teamName, isOnCall, members);
    }

    public static ArrayList<ITeam> createListInstance() {
        return new ArrayList<ITeam>();
    }
}
