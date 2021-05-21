/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nbcc.atsmike.controllers;

import com.nbcc.atsmike.models.ErrorViewModel;
import com.nbcc.atssystem.business.ClientServiceFactory;
import com.nbcc.atssystem.business.EmployeeServiceFactory;
import com.nbcc.atssystem.business.IClientService;
import com.nbcc.atssystem.business.IEmployeeService;
import com.nbcc.atssystem.business.IJobService;
import com.nbcc.atssystem.business.ITaskService;
import com.nbcc.atssystem.business.ITeamService;
import com.nbcc.atssystem.business.JobServiceFactory;
import com.nbcc.atssystem.business.TaskServiceFactory;
import com.nbcc.atssystem.business.TeamServiceFactory;
import com.nbcc.atssystem.models.ErrorFactory;
import com.nbcc.atssystem.models.IEmployee;
import com.nbcc.atssystem.models.IJob;
import com.nbcc.atssystem.models.IJobComment;
import com.nbcc.atssystem.models.ITask;
import com.nbcc.atssystem.models.JobCommentFactory;
import com.nbcc.atssystem.models.JobFactory;
import com.nbcc.atssystem.models.JobVM;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @Author: Susannie Tiempo, Nguyen Pham, Gui Ferriera
 * @Date: April 16, 2021
 * @Description: ATS Project 2021
 */
public class JobController extends CommonController {

    private static final String JOBS_VIEW = "/jobs.jsp";
    private static final String JOB_MAINT_VIEW = "/job.jsp";
    private static final String JOB_SUMMARY_VIEW = "/jobsummary.jsp";
    private static final String COMMENT_ADD = "/addcomments.jsp";

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

        IJobService jobService = JobServiceFactory.createInstance();
        IClientService clientService = ClientServiceFactory.createInstance();
        ITaskService taskService = TaskServiceFactory.createInstance();
        ITeamService teamService = TeamServiceFactory.createInstance();

        //We want to get to a specific job
        if (pathInfo != null) {
            String[] pathParts = pathInfo.split("/");

            String part = pathParts[1];

            String addComments = "";

            int id = super.getInteger(pathParts[1]);
            IJob job = jobService.getJob(id);

            if (pathParts.length > 2) {
                addComments = pathParts[2];
            }

            if ("create".equals(part)) {

                request.setAttribute("isFromCreate", 1);
                request.setAttribute("clients", clientService.getAllClientList());
                request.setAttribute("tasks", taskService.getTaskList());
                super.setView(request, JOB_MAINT_VIEW);

            } else if ("addcomments.jsp".equals(addComments)) {
                request.setAttribute("isFromCreate", 1);
                request.setAttribute("job", job);
                super.setView(request, COMMENT_ADD);
            } else {
                JobVM vm = new JobVM();

                job.setComments(jobService.getJobComments(id));
                vm.setJob(job);
                vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                vm.setClientName(clientService.getClient(job.getClientId()).getName());

                List<ITask> selTasks = new ArrayList();
                if (job.getTasks() != null) {
                    for (Integer task : job.getTasks()) {
                        selTasks.add(taskService.getTask(task));
                    }
                }
                vm.setTasks(selTasks);

//                Date today = new Date();
//                Date start = job.getStart();
//                LocalDateTime today_ldt = LocalDateTime.ofInstant(today.toInstant(),
//                        ZoneId.systemDefault());
//                LocalDateTime start_ldt = LocalDateTime.ofInstant(start.toInstant(),
//                        ZoneId.systemDefault());
//
//                vm.setStart(LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault()).toString());
//                vm.setEnd(LocalDateTime.ofInstant(job.getEnd().toInstant(), ZoneId.systemDefault()).toString());
                if (job != null) {
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);

                } else {
                    request.setAttribute("error", new ErrorViewModel(String.format("Job ID: %s is not found", id)));

                }
                super.setView(request, JOB_MAINT_VIEW);
            }

        } else {
            Date today = new Date();
            LocalDateTime start_ldt = LocalDateTime.ofInstant(today.toInstant(),
                    ZoneId.systemDefault());

            String today_param = start_ldt.toString().split("T")[0];
            request.setAttribute("searchString", today_param);
            String searchString = super.getValue(request, "searchString");
            //String today_param = start_ldt.toString().split("T")[0];
            List<JobVM> vms = new ArrayList();
            List<IJob> jobs = jobService.getJobs(today_param);
            JobVM vm = new JobVM();
            for (IJob j : jobs) {
                vm.setJob(j);
                vm.setTeamName(teamService.getTeam(j.getTeamId()).getTeamName());
                vm.setClientName(clientService.getClient(j.getClientId()).getName());
                vms.add(vm);
            }

            request.setAttribute("searchString", searchString);
            request.setAttribute("vms", vms);
            super.setView(request, JOBS_VIEW);

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

        //super.setView(request, JOB_SUMMARY_VIEW);
        IJobService jobService = JobServiceFactory.createInstance();
        IClientService clientService = ClientServiceFactory.createInstance();
        ITaskService taskService = TaskServiceFactory.createInstance();
        ITeamService teamService = TeamServiceFactory.createInstance();
        IJob job = JobFactory.createInstance();
        JobVM vm;

        int id = super.getInteger(request, "hdnJobId");

        try {

            //get the filepath for comment removal confirmation
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
                case "add a team":

                    vm = new JobVM();

                    int clientId = super.getInteger(request, "clients");
                    int isOnSite = super.getInteger(request, "isOnSite");
                    int isEmergency = super.getInteger(request, "isEmergency");

                    String[] listId_param = request.getParameterValues("tasks");
                    List<Integer> taskIds = new ArrayList();
                    List<ITask> selectedTasks = new ArrayList();

                    if (listId_param != null) {

                        for (String taskId : listId_param) {
                            taskIds.add(Integer.parseInt(taskId));
                        }
                        //List<Integer> taskIds = super.getIntegerList(request, "tasks");

                        for (Integer task : taskIds) {
                            selectedTasks.add(taskService.getTask(task));
                        }
                    }

                    job.setClientId(clientId);
                    job.setIsOnSite(isOnSite != 0);
                    job.setIsEmergencyBooking(isEmergency != 0);
                    job.setTasks(taskIds);
                    job.setDescription(super.getValue(request, "description").trim());

                    String start_param = "";
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    if (!request.getParameterValues("start")[0].isEmpty()) {
                        start_param = super.getFormattedDate(request.getParameterValues("start")[0]);
                        job.setStart(formatter.parse(start_param));
                    } else {
                        job.setStart(null);
                    }

                    vm.setStart(super.getValue(request, "start"));
                    vm.setClientName(clientService.getClient(clientId).getName());
                    vm.setTasks(selectedTasks);

                    vm.setJob(job); // int minDuration = (selectedTasks.size() % 2) == 0 ? totalDuration / selectedTasks.size() : totalDuration / selectedTasks.size();
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);

                    if (!jobService.isValid(job)) {
                        request.setAttribute("errors", job.getErrors());
                        if (clientId == 0) {
                            request.setAttribute("clients", clientService.getAllClientList());
                        }
                        if (taskIds.size() == 0) {
                            request.setAttribute("tasks", taskService.getTaskList());
                        }
                        super.setView(request, JOB_MAINT_VIEW);
                    } else {
                        if (job.isIsEmergencyBooking()) {
                            request.setAttribute("teams", teamService.getAllOnCallTeams());
                        } else {
                            request.setAttribute("teams", teamService.getAvailableTeams(taskIds));
                        }

                        super.setView(request, JOB_MAINT_VIEW);

                    }

                    break;

                case "create":

                    job = setJob(request);

                    if (!teamService.checkTeamAvailability(job.getTeamId(), job.getStart(), job.getEnd())) {
                        job.addError(ErrorFactory.createInstance(1, "Team  is not available to do the job at the selected period."));
                    } else {
                        job = jobService.createJob(job);
                    }

                    vm = new JobVM();
                    vm.setClientName(clientService.getClient(job.getClientId()).getName());
                    vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                    vm.setStart(LocalDateTime.ofInstant(job.getStart().toInstant(), ZoneId.systemDefault()).toString());
                    vm.setEnd(LocalDateTime.ofInstant(job.getEnd().toInstant(), ZoneId.systemDefault()).toString());
                    vm.setJob(job);

                    List<ITask> selTasks = new ArrayList();
                    job.getTasks().forEach(task -> selTasks.add(taskService.getTask(task)));
                    vm.setTasks(selTasks);
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);
                    request.setAttribute("isFromCreate", 1);

                    if (!jobService.isValid(job)) {
                        if (job.getTeamId() == 0) {
                            request.setAttribute("teams", teamService.getAvailableTeams(job.getTasks()));
                        }
                        request.setAttribute("errors", job.getErrors());
                        super.setView(request, JOB_MAINT_VIEW);
                    } else {
                        super.setView(request, JOB_SUMMARY_VIEW);
                    }

                    break;

                case "search":
                    String searchString = super.getValue(request, "searchString");
                    //String today_param = start_ldt.toString().split("T")[0];
                    List<JobVM> vms = new ArrayList();
                    List<IJob> jobs = jobService.getJobs(searchString);

                    for (IJob j : jobs) {
                        vm = new JobVM();
                        vm.setJob(j);
                        vm.setTeamName(teamService.getTeam(j.getTeamId()).getTeamName());
                        vm.setClientName(clientService.getClient(j.getClientId()).getName());
                        vms.add(vm);
                    }

                    request.setAttribute("searchString", searchString);
                    request.setAttribute("vms", vms);
                    super.setView(request, JOBS_VIEW);
                    break;

                case "delete":

                    job = jobService.getJob(id);
                    job.setId(id);
                    vm = new JobVM();
                    vm.setJob(job);
                    vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                    vm.setClientName(clientService.getClient(job.getClientId()).getName());
                    selTasks = new ArrayList();
                    if (job.getTasks() != null) {
                        for (Integer task : job.getTasks()) {
                            selTasks.add(taskService.getTask(task));
                        }
                    }
                    vm.setTasks(selTasks);

                    vm.setJob(job); // int minDuration = (selectedTasks.size() % 2) == 0 ? totalDuration / selectedTasks.size() : totalDuration / selectedTasks.size();
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);

                    if (jobService.deleteJob(id) == 0) {
                        job.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                        request.setAttribute("isDeleted", 1);
                        request.setAttribute("errors", job.getErrors());
                        super.setView(request, JOB_MAINT_VIEW);
                    } else {
                        request.setAttribute("isDeleted", 1);
                        super.setView(request, JOB_SUMMARY_VIEW);
                    }
                    break;

                case "add comment":

                    IJobComment comment = JobCommentFactory.createInstance();
                    comment = setComment(request);
                    comment.setJobId(id);
                    comment = jobService.createComment(comment);
                    job = jobService.getJob(id);
                    if (comment.getErrors().size() > 0) {
                        request.setAttribute("comment", comment);
                        request.setAttribute("job", job);
                        request.setAttribute("errors", comment.getErrors());
                        super.setView(request, COMMENT_ADD);
                    } else {

                        job = jobService.getJob(id);

                        job.setId(id);
                        jobService.deleteComment(deletionId);
                        job.setComments(jobService.getJobComments(id));

                        vm = new JobVM();
                        vm.setJob(job);
                        vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                        vm.setClientName(clientService.getClient(job.getClientId()).getName());

                        selTasks = new ArrayList();
                        if (job.getTasks() != null) {
                            for (Integer task : job.getTasks()) {
                                selTasks.add(taskService.getTask(task));
                            }
                        }
                        vm.setTasks(selTasks);
                        request.setAttribute("isSuccess", 1);
                        request.setAttribute("commentActivity", "added");
                        request.setAttribute("vm", vm);
                        request.setAttribute("job", job);
                        super.setView(request, JOB_MAINT_VIEW);

                    }

                    break;

                case "removecomment":

                    //TO DO : Refactor this part
                    job = jobService.getJob(id);
                    job.setId(id);
                    job.setComments(jobService.getJobComments(id));

                    vm = new JobVM();
                    vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                    vm.setClientName(clientService.getClient(job.getClientId()).getName());
                    selTasks = new ArrayList();
                    if (job.getTasks() != null) {
                        for (Integer task : job.getTasks()) {
                            selTasks.add(taskService.getTask(task));
                        }
                    }
                    vm.setTasks(selTasks);

                    vm.setJob(job);

                    request.setAttribute("idToDelete", deletionId);
                    request.setAttribute("isDeleted", 1);
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);
                    super.setView(request, JOB_MAINT_VIEW);
                    break;

                case "delete comment":

                    job = jobService.getJob(id);

                    job.setId(id);
                    jobService.deleteComment(deletionId);
                    job.setComments(jobService.getJobComments(id));

                    vm = new JobVM();
                    vm.setJob(job);
                    vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                    vm.setClientName(clientService.getClient(job.getClientId()).getName());

                    selTasks = new ArrayList();
                    if (job.getTasks() != null) {
                        for (Integer task : job.getTasks()) {
                            selTasks.add(taskService.getTask(task));
                        }
                    }
                    vm.setTasks(selTasks);

                    request.setAttribute("isSuccess", 1);
                    request.setAttribute("commentActivity", "deleted");
                    request.setAttribute("vm", vm);
                    request.setAttribute("job", job);
                    super.setView(request, JOB_MAINT_VIEW);

                    break;

                case "editcomment":

                    String isFromEditComment = super.getValue(request, "isFromEditComment");
                    int comId = deletionId;

                    request.setAttribute("job", jobService.getJob(id));

                    if (isFromEditComment == null && comId != 0) {
                        request.setAttribute("comment", jobService.getComment(comId));
                        request.setAttribute("isFromEditComment", 1);
                        super.setView(request, COMMENT_ADD);
                    } else {
                        comment = jobService.getComment(super.getInteger(request, "hdnCommentId"));
                        comment.setComment(super.getValue(request, "comment"));
                        comment.setJobId(id);

                        if (jobService.saveComment(comment) == false) {
                            comment.addError(ErrorFactory.createInstance(15, "No records are deleted"));
                            request.setAttribute("isFromEditComment", 1);
                            super.setView(request, COMMENT_ADD);
                        } else {
                            job = jobService.getJob(id);
                            vm = new JobVM();
                            vm.setJob(job);
                            vm.setTeamName(teamService.getTeam(job.getTeamId()).getTeamName());
                            vm.setClientName(clientService.getClient(job.getClientId()).getName());
                            job.setComments(jobService.getJobComments(id));

                            selTasks = new ArrayList();
                            if (job.getTasks() != null) {
                                for (Integer task : job.getTasks()) {
                                    selTasks.add(taskService.getTask(task));
                                }
                            }
                            vm.setTasks(selTasks);
                            request.setAttribute("isSuccess", 1);
                            request.setAttribute("commentActivity", "updated");
                            request.setAttribute("vm", vm);
                            request.setAttribute("job", job);
                            super.setView(request, JOB_MAINT_VIEW);
                        }

                    }

                    break;
            }

        } catch (Exception ex) {

        }
        super.getView().forward(request, response);
    }

    private IJob setJob(HttpServletRequest request) throws ParseException {

        //declare needed variables
        int teamId = 0;
        int clientId = 0;

        String description = "";
        ITeamService teamService = TeamServiceFactory.createInstance();
        IEmployeeService empService = EmployeeServiceFactory.createInstance();
        ITaskService taskService = TaskServiceFactory.createInstance();
        //get values from request
        String start_param = super.getFormattedDate(request.getParameterValues("start")[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        teamId = super.getInteger(request, "teams");
        clientId = super.getInteger(request, "clients");
        description = super.getValue(request, "description");
        List<Integer> taskIds = super.getIntegerList(request, "tasks");
        boolean isOnSite = super.getInteger(request, "isOnSite") != 0;
        boolean isEmergency = super.getInteger(request, "isEmergency") != 0;
        //assigned the task to employee
        List<IEmployee> teamMembers = teamService.getTeamMembers(teamId);
        List<ITask> empOneTasks = empService.getEmployeeTasks(teamMembers.get(0).getId());
        List<ITask> empTwoTasks = empService.getEmployeeTasks(teamMembers.get(1).getId());
        List<ITask> empOneAssignedTasks = new ArrayList();
        List<ITask> empTwoAssignedTasks = new ArrayList();

        //get tasks that are matched for both employees
        List<Integer> commonTasks = taskIds.stream().filter(x -> empOneTasks.stream().anyMatch(c -> c.getId() == x) && empTwoTasks.stream().anyMatch(a -> a.getId() == x)).collect(Collectors.toList());

        //make sure that common tasks are evenly distributed between employees
        if (commonTasks != null) {

            if ((commonTasks.size() % 2) == 0) {
                int indexSize = commonTasks.size() / 2;
                for (int i = 0; i < indexSize; i++) {
                    empOneAssignedTasks.add(taskService.getTask(commonTasks.get(i)));
                    empTwoAssignedTasks.add(taskService.getTask(commonTasks.get(i + indexSize)));
                }
            } else {

                if (commonTasks.size() > 1) {
                    int indexSize = (commonTasks.size() - 1) / 2;
                    for (int i = 0; i < indexSize; i++) {
                        empOneAssignedTasks.add(taskService.getTask(commonTasks.get(i)));
                        empTwoAssignedTasks.add(taskService.getTask(commonTasks.get(i + indexSize)));
                    }
                    empOneAssignedTasks.add(taskService.getTask(commonTasks.get(commonTasks.size() - 1)));
                } else if (commonTasks.size() == 1) {
                    empOneAssignedTasks.add(taskService.getTask(commonTasks.get(0)));
                }

            }

            empOneTasks.removeAll(empOneTasks.stream().filter(x -> commonTasks.stream().anyMatch(c -> c == x.getId())).collect(Collectors.toList()));
            empTwoTasks.removeAll(empTwoTasks.stream().filter(x -> commonTasks.stream().anyMatch(c -> c == x.getId())).collect(Collectors.toList()));

        }

        for (Integer t : taskIds) {
            for (ITask task : empOneTasks) {
                if (task.getId() == t) {
                    empOneAssignedTasks.add(task);
                }
            }
        }

        for (Integer t : taskIds) {
            for (ITask task : empTwoTasks) {
                if (task.getId() == t) {
                    empTwoAssignedTasks.add(task);
                }
            }
        }
        //Add the distict task for employee
        //step1: remove the common tasks in the employee tasks to get the distinct task
        //Step2: add the distinct task to the assigned task

        //Get total work duration
        int empOneDuration = empOneAssignedTasks.stream()
                .map(item -> item.getDuration())
                .reduce(0, (a, b) -> a + b);

        int empTwoDuration = empTwoAssignedTasks.stream()
                .map(item -> item.getDuration())
                .reduce(0, (a, b) -> a + b);

        //the final duration is duration of the employee whose tasks will take the longest
        int finalJobDuration = empOneDuration > empTwoDuration ? empOneDuration : empTwoDuration;

        //calculate start and end time
        Calendar cal = Calendar.getInstance();
        cal.setTime(formatter.parse(start_param));
        Date start;
        Date end;
        //check if onsite or offsite
        if (isOnSite) {
            //add 30 mins before
            cal.add(Calendar.MINUTE, 30);
            start = cal.getTime();
            //add total job duration + 30
            cal.add(Calendar.MINUTE, finalJobDuration + 30);
            end = cal.getTime();
        } else {
            start = cal.getTime();
            cal.add(Calendar.MINUTE, finalJobDuration);
            end = cal.getTime();
        }

        if (isOnSite) {
            finalJobDuration += 60;
        }

        //calculate cost
        double totalEmpRate = teamMembers.stream()
                .mapToDouble(x -> x.getHourlyRate())
                .sum();
        double totalCost = totalEmpRate * (double) finalJobDuration / 60.00;

        //calculate revenue
        double revenue = totalCost * 3;

        // String start_String = super.getStringDate(formatter.format(start));
        IJob job = JobFactory.createInstance(clientId, teamId, description.trim(), isOnSite, isEmergency, start, end, taskIds, totalCost, revenue);

        return job;
    }

    private int CountOfMatchTasksToEmp(List<ITask> empTasks, List<Integer> taskIds) {
        int countOfMatchedSkillsEmp = 0;
        for (ITask task : empTasks) {
            countOfMatchedSkillsEmp += taskIds.stream().filter(x -> x == task.getId()).count();
        }
        return countOfMatchedSkillsEmp;
    }

    private IJobComment setComment(HttpServletRequest request) {
        String comment = "";

        comment = super.getValue(request, "comment");

        IJobComment jobComment = JobCommentFactory.createInstance(comment);

        return jobComment;

    }

}
