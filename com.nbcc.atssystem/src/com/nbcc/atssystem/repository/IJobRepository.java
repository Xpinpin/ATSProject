/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.IJob;
import com.nbcc.atssystem.models.IJobComment;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Susannie Tiempo
 */
public interface IJobRepository {

    int insertJob(IJob job);

    List<IJob> retrieveJobs(String start);

    IJob retrieveJob(int job);

    int deleteJob(int id);

    int insertJobComment(IJobComment comment);

    int DeleteComment(int id);

    List<IJobComment> retrieveJobComments(int id);

    IJobComment retrieveComment(int id);
    
    int updateJobComment(IJobComment jobComment);
}
