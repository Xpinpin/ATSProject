/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atssystem.repository;

import com.nbcc.atssystem.models.IJob;
import com.nbcc.atssystem.models.IJobComment;
import com.nbcc.atssystem.models.JobCommentFactory;
import com.nbcc.atssystem.models.JobFactory;
import com.nbcc.dataaccess.DALFactory;
import com.nbcc.dataaccess.IDAL;
import com.nbcc.dataaccess.IParameter;
import com.nbcc.dataaccess.ParameterFactory;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import javax.sql.rowset.CachedRowSet;

/**
 *
 * @author Susannie Tiempo
 */
public class JobRepository extends BaseRepository implements IJobRepository {

    private final String SPROC_CREATE_JOB = "CALL InsertJob(?,?,?,?,?,?,?,?)";
    private final String SPROC_SHOW_JOBS = "CALL SearchJobByDate(?)";
    private final String SPROC_SHOW_JOB = "CALL ShowJob(?)";
    private final String SPROC_SHOW_JOB_TASKS = "CALL ShowTasksJob(?)";
    private final String SPROC_CREATE_JOB_TASK = "CALL InsertJobTask(?,?,?,?,?)";
    private final String SPROC_DELETE_JOB = "CALL DeleteJob(?)";
    private final String SPROC_CREATE_COMMENT = "CALL InsertJobComment(?,?,?)";
    private final String SPROC_DELETE_COMMENT = "CALL DeleteJobComment(?)";
    private final String SPROC_SHOW_JOB_COMMENT = "CALL ShowJobComments(?)";
    private final String SPROC_SHOW_COMMENT = "CALL ShowComment(?)";
    private final String SPROC_UPDATE_JOB_COMMENT = "CALL UpdateJobComment(?,?,?)";

    private IDAL dataAccess;

    public JobRepository() {
        dataAccess = DALFactory.createInstance();
    }

    @Override
    public int insertJob(IJob job) {
        int returnIdJob = 0;
        int returnIdJobTasks = 0;
        List<Object> returnValues = new ArrayList<Object>();

        List<IParameter> params = ParameterFactory.createListInstance();

        LocalDateTime start_ldt = LocalDateTime.ofInstant(job.getStart().toInstant(),
                ZoneId.systemDefault());
        LocalDateTime end_ldt = LocalDateTime.ofInstant(job.getEnd().toInstant(),
                ZoneId.systemDefault());
        try {
            params.add(ParameterFactory.createInstance(job.getTeamId()));
            params.add(ParameterFactory.createInstance(job.getClientId()));
            params.add(ParameterFactory.createInstance(job.getDescription()));
            params.add(ParameterFactory.createInstance(ChangeFormatDate(start_ldt.toString())));
            params.add(ParameterFactory.createInstance(ChangeFormatDate(end_ldt.toString())));
            params.add(ParameterFactory.createInstance(job.isIsOnSite() == true ? 1 : 0));
            params.add(ParameterFactory.createInstance(job.isIsEmergencyBooking() == true ? 1 : 0));

            params.add(ParameterFactory.createInstance(returnIdJob, IParameter.Direction.OUT, java.sql.Types.INTEGER));
            returnValues = dataAccess.executeNonQuery(SPROC_CREATE_JOB, params);
            if (returnValues != null) {
                returnIdJob = Integer.parseInt(returnValues.get(0).toString());

            }

            if (returnIdJob > 0) {

                for (Integer taskId : job.getTasks()) {
                    params.clear();
                    params.add(ParameterFactory.createInstance(taskId));
                    params.add(ParameterFactory.createInstance(returnIdJob));
                    params.add(ParameterFactory.createInstance(job.getCost()));
                    params.add(ParameterFactory.createInstance(job.getRevenue()));
                    params.add(ParameterFactory.createInstance(returnIdJobTasks, IParameter.Direction.OUT, java.sql.Types.INTEGER));

                    returnValues = dataAccess.executeNonQuery(SPROC_CREATE_JOB_TASK, params);
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnIdJob;

    }

    @Override
    //retrieve all teams
    public List<IJob> retrieveJobs(String start) {

        List<IJob> retrievedJobs = JobFactory.createListInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(start));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_JOBS, params);
            if (cs != null) {
                retrievedJobs = toListOfJobs(cs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return retrievedJobs;
    }

    @Override
    public IJob retrieveJob(int id) {

        List<IJob> retrievedJobs = JobFactory.createListInstance();
        IJob job = JobFactory.createInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_JOB, params);
            if (cs != null) {
                retrievedJobs = toListOfJobs(cs);
            }
            job = retrievedJobs.get(0);
            CachedRowSet cs2 = dataAccess.executeFill(SPROC_SHOW_JOB_TASKS, params);
            AddTasksToJobs(cs2, job);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }

        return job;
    }

    @Override
    //retrieve all teams
    public List<IJobComment> retrieveJobComments(int id) {

        List<IJobComment> retrievedJobComments = JobCommentFactory.createListOfInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_JOB_COMMENT, params);
            if (cs != null) {
                retrievedJobComments = toListComments(cs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return retrievedJobComments;
    }

    @Override
    //retrieve all teams
    public IJobComment retrieveComment(int id) {

        List<IJobComment> retrievedJobComments = JobCommentFactory.createListOfInstance();

        try {
            List<IParameter> params = ParameterFactory.createListInstance();
            params.add(ParameterFactory.createInstance(id));

            CachedRowSet cs = dataAccess.executeFill(SPROC_SHOW_COMMENT, params);
            if (cs != null) {
                retrievedJobComments = toListComments(cs);
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());

        }
        return retrievedJobComments.get(0);
    }

    /**
     * Updates a job comment.
     *
     * @param jobComment
     * @return the count of the affected rows.
     */
    @Override
    public int updateJobComment(IJobComment jobComment) {
        int affectedRows = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();
        params.add(ParameterFactory.createInstance(jobComment.getCommentId()));
        params.add(ParameterFactory.createInstance(jobComment.getJobId()));
        params.add(ParameterFactory.createInstance(jobComment.getComment()));
        returnValues = dataAccess.executeNonQuery(SPROC_UPDATE_JOB_COMMENT, params);
        try {
            if (returnValues != null) {
                affectedRows = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
        return affectedRows;
    }

    private List<IJob> toListOfJobs(CachedRowSet cs) throws SQLException {
        List<IJob> retrievedJobs = JobFactory.createListInstance();
        IJob job;

        while (cs.next()) {
            job = JobFactory.createInstance();
            job.setId(super.getInt("JobId", cs));
            job.setTeamId(super.getInt("TeamId", cs));
            job.setClientId(super.getInt("ClientId", cs));
            job.setDescription(cs.getString("Description"));
            job.setIsOnSite(cs.getBoolean("OnSite"));
            job.setStart(super.getDate("Start", cs));
            job.setEnd(super.getDate("End", cs));

            retrievedJobs.add(job);
        }

        return retrievedJobs;
    }

    private List<IJobComment> toListComments(CachedRowSet cs) throws SQLException {
        List<IJobComment> retrievedComments = JobCommentFactory.createListOfInstance();
        IJobComment comment;

        while (cs.next()) {
            comment = JobCommentFactory.createInstance(cs.getString("Comment"));
            comment.setCommentId(super.getInt("CommentId", cs));
            comment.setCreatedAt(super.getDate("CreatedAt", cs));

            retrievedComments.add(comment);
        }

        return retrievedComments;
    }

    private void AddTasksToJobs(CachedRowSet cs, IJob job) throws SQLException {

        List<Integer> tasks = new ArrayList();

        while (cs.next()) {
            tasks.add(super.getInt("TaskId", cs));
            job.setRevenue(super.getDouble("OperatingRevenue", cs));
            job.setCost(super.getDouble("OperatingCost", cs));
        }

        job.setTasks(tasks);

    }

    private String ChangeFormatDate(String date) {
        String[] dateTime_param = date.split("T");
        String date_param = dateTime_param[0];
        String time_param = dateTime_param[1];

        return date_param + " " + time_param + ":00";

    }

    @Override
    public int deleteJob(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));

        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_JOB, params);

        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rowsAffected;
    }

    public int insertJobComment(IJobComment comment) {
        int returnId = 0;
        List<Object> returnValues;

        List<IParameter> parms = ParameterFactory.createListInstance();

        //INPUT PARMS
        parms.add(ParameterFactory.createInstance(comment.getJobId()));
        parms.add(ParameterFactory.createInstance(comment.getComment()));

        //OUTPUT PARMS
        parms.add(ParameterFactory.createInstance(returnId, IParameter.Direction.OUT, java.sql.Types.INTEGER));

        returnValues = dataAccess.executeNonQuery(SPROC_CREATE_COMMENT, parms);

        try {
            if (returnValues != null) {
                returnId = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return returnId;
    }

    public int DeleteComment(int id) {
        int rowsAffected = 0;
        List<Object> returnValues;
        List<IParameter> params = ParameterFactory.createListInstance();

        params.add(ParameterFactory.createInstance(id));

        returnValues = dataAccess.executeNonQuery(SPROC_DELETE_COMMENT, params);

        try {
            if (returnValues != null) {
                rowsAffected = Integer.parseInt(returnValues.get(0).toString());
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return rowsAffected;
    }

}
