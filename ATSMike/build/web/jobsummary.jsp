<%-- 
     Document   : Client View Summary - Post
    Created on : Mar 20, 2021
    Author     : Susannie Tiempo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Job Create</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <div class="form-wrapper">
            <div class="heading">
                <c:choose>
                    <c:when test="${ isDeleted != null }">
                        <h2>Job Deletion - Successful</h2>
                        <h5 class="text-success text-center mb-5 mt-5"> The job record was successfully deleted from the database. <h5>
                                <h4 class="text-center">Job deleted Id: <span class="font-weight-bold">${vm.job.id}</span></h4>
                                </c:when>

                            <c:otherwise>
                                <h2>New Job Information - Summary</h2>
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-8">
                                        <div class="table-responsive-lg">
                                            <table class="table table-striped table-hover">
                                                <tbody>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Job Id:</td>
                                                        <td>${ vm.job.id }</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Client Name:</td>
                                                        <td>${ vm.clientName }</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold" >Team Assigned:</td>
                                                        <td>${ vm.teamName }</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Start:</td>
                                                        <td>${ vm.job.start }</td>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">End:</td>
                                                        <td>${ vm.job.end }</td>
                                                    </tr>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Billable Cost</td>
                                                        <td><fmt:formatNumber value="${vm.job.cost}" type="currency" currencySymbol="$"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Billable Revenue</td>
                                                        <td><fmt:formatNumber value="${vm.job.revenue}" type="currency" currencySymbol="$"/></td>
                                                    </tr>
                                                    <tr >
                                                        <td style="text-align: right;" class="font-weight-bold">Included Tasks</td>
                                                        <td>
                                                            <ul>
                                                                <c:forEach items="${vm.tasks}"  var="task">
                                                                    <li class="ml-3">${ task.name }</li>
                                                                    </c:forEach>
                                                                <ul>
                                                                    </td>
                                                                    </tr>

                                                                    </table>
                                                                    </div>
                                                                    </div>
                                                                    </div>
                                                                    <div class="mt-5">
                                                                        <a  href="${pageContext.request.contextPath}/jobs" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                                                            <c:if test="${vm == null }">
                                                                                Cancel
                                                                            </c:if>
                                                                            <c:if test="${vm != null }">
                                                                                View Job List
                                                                            </c:if>
                                                                        </span>
                                                                    </div>
                                                                </c:otherwise>
                                                          </c:choose>
                                                            </div>
                                                            </div>

                                                            </main>        
                                                            </body>
                                                            <%@include file="WEB-INF/jspf/footer.jspf" %>
                                                            </html>
