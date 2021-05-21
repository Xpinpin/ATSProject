/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.IJob;
import com.nbcc.atssystem.models.IJobComment;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Nguyen Pham
 */
public interface IJobService {

    IJob createJob(IJob job);

    List<IJob> getJobs(String start);

    IJob getJob(int id);

    boolean isValid(IJob job);

    int deleteJob(int id);

    IJobComment createComment(IJobComment comment);

    int deleteComment(int id);

    List<IJobComment> getJobComments(int id);

    IJobComment getComment(int id);

   boolean saveComment(IJobComment comment);
}
