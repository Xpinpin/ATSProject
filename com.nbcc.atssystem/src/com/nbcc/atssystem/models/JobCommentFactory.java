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
 * @author DELL
 */
public class JobCommentFactory {

    public static IJobComment createInstance(String comment, int jobId) {
        return new JobComment(comment, jobId);
    }

    public static IJobComment createInstance(String comment, Date createdAt, int jobId) {
        return new JobComment(comment, createdAt, jobId);
    }

    public static IJobComment createInstance() {
        return new JobComment();
    }

    public static IJobComment createInstance(String comment) {
        return new JobComment(comment);
    }

    public static ArrayList<IJobComment> createListOfInstance() {
        return new ArrayList<IJobComment>();
    }
}
