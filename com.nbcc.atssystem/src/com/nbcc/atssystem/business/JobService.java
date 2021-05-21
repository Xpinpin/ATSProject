/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.business;

import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IJob;
import com.nbcc.atssystem.models.IJobComment;
import com.nbcc.atssystem.repository.IJobRepository;
import com.nbcc.atssystem.repository.JobRepositoryFactory;
import java.util.List;

/**
 *
 * @author Susannie Tiempo
 */
public class JobService implements IJobService {

    private final IJobRepository repo;

    public JobService() {
        repo = JobRepositoryFactory.createInstance();
    }

    /**
     * Create the job in the service layer
     *
     * @param job
     * @return Job
     */
    @Override
    public IJob createJob(IJob job) {
        if (isValid(job)) {
            int id = repo.insertJob(job);

            if (id > 0) {
                job.setId(id);
            } else {
                job.addError(ErrorFactory.createInstance(4, "Job is not valid for creation."));
            }

        } else {
            job.addError(ErrorFactory.createInstance(99, "Client creation failed due to the validation errors."));
        }
        return job;
    }

    /**
     * Get the client based on the ID in the service layer
     *
     * @param id
     * @return Client
     */
    @Override
    public IJob getJob(int id) {
        IJob job = repo.retrieveJob(id);

        return job;
    }

    /**
     * Get all of the job in the service layer
     *
     * @param start
     * @return List of Jobs for a particular date
     */
    @Override
    public List<IJob> getJobs(String start) {
        List<IJob> jobs = repo.retrieveJobs(start);

        return jobs;
    }

    public IJobComment createComment(IJobComment comment) {
        int id = repo.insertJobComment(comment);

        if (id > 0) {
            comment.setCommentId(id);
        } else {
            comment.addError(ErrorFactory.createInstance(20, "Job Comment is not valid for creation."));
        }

        return comment;
    }

    public int deleteComment(int id) {
        if (id <= 0) {
            return 0;
        } else {
            return repo.DeleteComment(id);
        }
    }

    /**
     * Make sure that the Client passing in is valid for manipulation
     *
     * @param job
     * @return boolean
     */
    @Override
    public boolean isValid(IJob job) {
        return job.getErrors().isEmpty();
    }

    @Override
    public int deleteJob(int id) {
        if (id <= 0) {
            return 0;
        } else {
            return repo.deleteJob(id);
        }

    }

    /**
     * Get all of the job comments in the service layer
     *
     * @param id
     * @return List of comments for a particular job
     */
    @Override
    public List<IJobComment> getJobComments(int id) {
        List<IJobComment> comments = repo.retrieveJobComments(id);

        return comments;
    }

    @Override
    public IJobComment getComment(int id) {
        IJobComment comment = repo.retrieveComment(id);

        return comment;
    }

    @Override
    public boolean saveComment(IJobComment comment) {
        int returnedRow = 0;
        returnedRow = repo.updateJobComment(comment);
        
        if (returnedRow == 0) {
            comment.addError(ErrorFactory.createInstance(21, "Job comment is not valid for update."));
        }

        return returnedRow > 0;
    }

}
