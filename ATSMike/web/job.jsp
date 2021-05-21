<%-- 
    Document   : Job View
    Created on : Mar 30, 2021
    Author     : Susannie Tiempo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Job Detail</title>
    </head>
    <body>

        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:choose>
                        <c:when test="${isFromCreate != null || job.errors.size() > 0}">
                            <h2>  Create Job </h2>
                        </c:when>
                        <c:otherwise>
                            <h2>Job Detail</h2>  
                        </c:otherwise>
                    </c:choose>
                    <section>
                        <c:set var="errors" value="${ error }" />
                        <c:if test="${ errors == null }">
                            <form method="POST" action="create">
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-8">
                                        <div class="table-responsive-lg">
                                            <c:if test="${isFromCreate != null}">
                                                <div class="text-danger font-small"> *All fields are required. </div>
                                            </c:if>
                                            <table class="table table-striped table-hover">
                                                <c:if test="${vm != null && vm.job.id != 0 }">
                                                    <tr>
                                                        <td class="font-weight-bold"><label>Job Id:</label></td>
                                                        <td>
                                                            ${ vm.job.id }
                                                            <input type="hidden" value="${ vm.job.id }" name="hdnJobId" />      
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>                    
                                                    <td class="font-weight-bold"> Client: </td>
                                                    <td>
                                                        <select class="form-control" name="clients" >
                                                            <c:if test="${vm.clientName != null}">
                                                                <option value='${vm.job.clientId}'>${vm.clientName} </option>
                                                            </c:if>
                                                            <c:if test="${clients.size() > 1 }">
                                                                <option value='0'>Please select a client </option>
                                                            </c:if>
                                                            <c:forEach items="${clients}"  var="client">
                                                                <option value='${client.id}'>${client.name} </option>
                                                            </c:forEach>
                                                        </select>
                                                    </td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Job Start Date and Time:</td>
                                                    <td>
                                                        <c:if test="${vm.job.id == 0 || vm.job == null}">
                                                            <input class="form-control" type="datetime-local" name="start" value='${vm.start}'/>
                                                        </c:if>
                                                        <c:if test="${vm.job.id > 0}">
                                                            <input class="form-control" type="text" name="start" value='${vm.job.start}'/>
                                                        </c:if>
                                                    </td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Job Description:</td>
                                                    <td>
                                                        <textarea rows="4" cols="50" class="form-control" type="datetime-local" name="description" value='${vm.job.description}'/>${vm.job.description}
                                                        </textarea>                           
                                                    </td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Is OnSite?:</td>
                                                    <td><input class="mr-2" type="radio" name="isOnSite" value='1' ${vm.job.isOnSite == true ? "checked" : ""}/> <span class="mr-5"> True</span> 
                                                        <input class="mr-2"  type="radio" name="isOnSite" value='0' ${vm.job.isOnSite == false ? "checked" : ""}/>False 
                                                    </td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Is Emergency Booking?:</td>
                                                    <td><input class="mr-2" type="radio" name="isEmergency" value='1' ${vm.job.isEmergencyBooking == true ? "checked" : ""}/> <span class="mr-5"> True</span> 
                                                        <input class="mr-2"  type="radio" name="isEmergency" value='0' ${vm.job.isEmergencyBooking == false ? "checked" : ""}/>False 
                                                    </td>
                                                </tr>
                                                <tr>
                                                <input type="hidden" value="${hdtasks}" name="hdnSelectedTasks" />   

                                                <td class="font-weight-bold">Tasks for the Job:</td>

                                                <td>
                                                    <select  class="form-control" name="tasks" multiple>
                                                        <c:if test="${vm.tasks.size() != 0}">
                                                            <c:forEach items="${vm.tasks}"  var="task">
                                                                <option name="" value='${task.id}'>${task.name}</option>
                                                            </c:forEach>
                                                        </c:if>
                                                        <c:if test="${tasks.size() != 0}">
                                                            <c:forEach items="${tasks}"  var="task">
                                                                <option name="" value='${task.id}'>${task.name}</option>
                                                            </c:forEach>
                                                        </c:if>       

                                                    </select>
                                                </td>
                                                </tr>

                                                <c:if test="${vm.teamName != null}">
                                                    <tr>      
                                                        <td class="font-weight-bold">Assigned Team</td>
                                                        <td>
                                                            <select class="form-control" name="teams" >
                                                                <option value='${vm.job.teamId}'>${vm.teamName} </option>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                                <c:if test="${teams != null && teams.size() > 0}">
                                                    <tr>      
                                                        <td class="font-weight-bold">Please select a team for the job</td>
                                                        <td>
                                                            <select class="form-control" name="teams" >
                                                                <c:if test="${teams.size() > 0 }">
                                                                    <option value='0'>Please select a team </option>
                                                                </c:if>
                                                                <c:forEach items="${teams}"  var="team">
                                                                    <option value='${team.id}'>${team.teamName} </option>
                                                                </c:forEach>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                                <c:if test="${teams != null && teams.size() == 0}">
                                                    <tr>      
                                                        <td class="font-weight-bold">Please select a team for the job</td>
                                                        <td>
                                                            <p class="text-danger"> No team is has matching skillset. </p>
                                                        </td>
                                                    </tr>
                                                </c:if>

                                                <c:if test="${job != null && job.id != 0 }">
                                                    <tr>                    
                                                        <td class="font-weight-bold">Job End Date and Time: </td>
                                                        <td><label class="form-control" type="text" name="end" value='${ vm.job.end }'/>${ vm.job.end }</td>
                                                    </tr>
                                                    <tr>                    
                                                        <td class="font-weight-bold">Billable Cost:</td>
                                                        <td><fmt:formatNumber value="${vm.job.cost}" type="currency" currencySymbol="$"/></td>
                                                    </tr>
                                                    <tr>                    
                                                        <td class="font-weight-bold">Billable Revenue:</td>
                                                        <td><fmt:formatNumber value="${vm.job.revenue}" type="currency" currencySymbol="$"/></td>
                                                    </tr>
                                                </c:if>
                                            </table>

                                            <c:if test="${vm.job != null && vm.job.id != 0 }">
                                                <h2 style="text-align:center"> Comments
                                                </h2>

                                                <section>
                                                    <c:set var="commentCount" value="${ job.comments.size()}" />
                                                    <c:choose>
                                                        <c:when test="${ commentCount > 0}">
                                                            <div class="row justify-content-lg-center">
                                                                <div class="table-responsive-lg">
                                                                    <table class="table table-striped table-hover">
                                                                        <tr class="bg-dark text-light text-center">
                                                                            <th>
                                                                                Comment
                                                                            </th>
                                                                            <th>
                                                                                Created Date
                                                                            </th>
                                                                            <th>
                                                                                Updated Date 
                                                                            </th>
                                                                            <th style="width: 170px;">
                                                                                Action
                                                                            </th>

                                                                        </tr>
                                                                        <c:forEach items="${job.comments}"  var="currComment">
                                                                            <tr class="text-center" name="commentSelected" value="${ currComment.commentId }">
                                                                            <input type="hidden" value="${ currComment.commentId }" name="commentDeletion${currComment.commentId}" />    
                                                                            <td>${ currComment.comment }</td>
                                                                            <td>${ currComment.createdAt }</td>
                                                                            <td>${ currComment.updatedAt }</td>
                                                                            <td>
                                                                                <button  class="btn btn-link btn-link btn-sm" type="submit" value="removecomment/${currComment.commentId}" name="action" >Remove</button>|
                                                                                <button   class="btn btn-link btn-link btn-sm" type="submit" value="editcomment/${currComment.commentId}" name="action" >Edit</button>
                                                                            </td>

                                                                            </tr>
                                                                        </c:forEach>

                                                                        <c:if test="${ isDeleted != null }">
                                                                            <script type="text/javascript">
                                                                                $(window).on('load', function () {
                                                                                    $('#deleteModal').modal('show');
                                                                                });
                                                                            </script>

                                                                            <div class="modal fade" tabindex="-1" role="dialog" id="deleteModal">
                                                                                <div class="modal-dialog" role="document">
                                                                                    <div class="modal-content">
                                                                                        <div class="modal-header">
                                                                                            <i class="fa fa-exclamation-circle fa-2x text-danger" aria-hidden="true"></i> <h5 class="modal-title ml-2">  Deletion Confirmation</h5>
                                                                                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                                                                                <span aria-hidden="true">&times;</span>
                                                                                            </button>
                                                                                        </div>
                                                                                        <div class="modal-body">
                                                                                            <p class="text-danger" >Are you sure you want to delete comment for this job?</p>
                                                                                        </div>
                                                                                        <div class="modal-footer">
                                                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                                            <button  class="btn btn-outline-primary" type="submit" value="delete comment/${idToDelete}" name="action" >Delete Comment</button>
                                                                                        </div>
                                                                                    </div>
                                                                                </div>
                                                                            </div>
                                                                        </c:if>

                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </c:when>
                                                        <c:when test="${ commentCount == 0}">
                                                            <h4 style="text-align:center">No Comment Added</h4>
                                                        </c:when>
                                                    </c:choose>
                                                </section>
                                            </c:if>

                                            <div class="mt-5"></div>

                                            <div class="row justify-content-lg-center mt-4">
                                                <c:choose>
                                                    <c:when test="${job != null && job.id != 0}">
                                                        <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Save" name="action" />    
                                                        <input class="btn btn-outline-primary  col-sm-2 mr-3" type="submit" value="Delete" name="action" />
                                                        <a href="${pageContext.request.contextPath}/job/${job.id}/addcomments.jsp" class="btn btn-md btn-dark  ">Add Comments</a>  
                                                    </c:when>
                                                    <c:when test="${teams != null}">
                                                        <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Create" name="action" />
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button class="btn btn-md btn-primary   mr-3" type="submit" value="Add a team" name="action" >Add a team </button>   
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>

                            <div class="mt-5"></div>
                        </c:if>

                    </section>

                    <div class="mt-5">
                        <a  href="${pageContext.request.contextPath}/jobs" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">
                            <c:choose>
                                <c:when test="${ job == null || job.id == 0}">
                                    Cancel
                                </c:when>
                                <c:otherwise>
                                    View All Jobs 
                                </c:otherwise>
                            </c:choose>
                        </span>
                    </div>
                </div>
                <div>
                    </main>

                    <c:if test="${job != null && job.errors.size() > 0}">
                        <script type="text/javascript">
                            $(window).on('load', function () {
                                $('#myModal').modal('show');
                            });
                        </script>
                        <div class="modal fade" tabindex="-1" role="dialog" id="myModal">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header text-center">
                                        <h5 class="modal-title font-weight-bold text-danger"> <i class="fa fa-exclamation-circle ml-2" aria-hidden="true"></i>Errors</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body mr-3">
                                        <c:choose>
                                            <c:when test="${ vm.job.errors.size() > 0 }">
                                                <ul>
                                                    <c:forEach items="${ vm.job.errors }" var="err">
                                                        <li class="ml-4 font-small"> <span class="mr-2  font-weight-bold">Error Code: </span> ${ err.code }  <span class="ml-2  font-weight-bold"> Desc: </span> ${err.description }</li>
                                                        </c:forEach>
                                                </ul>
                                            </c:when>
                                            <c:when test="${ error != null }">
                                                <ul>
                                                    <c:forEach items="${ error.errors }" var="err">
                                                        <li>${ err }</li>
                                                        </c:forEach>
                                                </ul>

                                            </c:when>
                                        </c:choose>
                                        <p class="text-info"> Please try again with valid entry.</p>
                                    </div>
                                    <div class="modal-footer">                        
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${ isSuccess != null }">
                        <script type="text/javascript">
                            $(window).on('load', function () {
                                $('#deleteModal').modal('show');
                            });
                        </script>

                        <div class="modal fade" tabindex="-1" role="dialog" id="deleteModal">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <i class="fa fa-check-circle fa-2x text-success" aria-hidden="true"></i> <h5 class="modal-title ml-2">  Success Confirmation</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body">
                                        <p class="text-success" >Comment was ${commentActivity} successfully to the job.</p>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:if>

                    </body>
                    <%@include file="WEB-INF/jspf/footer.jspf" %>
                    </html>
