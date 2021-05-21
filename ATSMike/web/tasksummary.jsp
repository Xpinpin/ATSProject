<%-- 
    Document   : tasksummary
    Created on : 20-Mar-2021, 3:46:43 PM
    Author     : gui_a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Task Create Summary</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">

                    <c:choose>
                        <c:when test="${ savedTask != null }">
                            <h2>Task Updated - Successful</h2>
                            <h5 class="text-success text-center mb-5 mt-5"> The task record was successfully updated. <h5>
                        </c:when>
                        <c:otherwise>
                            <h2>New Task Information - Summary</h2>
                        </c:otherwise>
                    </c:choose>  
                            <div class="row justify-content-lg-center">
                                <div class="col-lg-8">
                                    <div class="table-responsive-lg">
                                        <table class="table table-striped table-hover">
                                            <tbody>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Task Id:</td>
                                                    <td>${ task.id }</td>
                                                </tr>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Task Name:</td>
                                                    <td>${ task.name }</td>
                                                </tr>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Description:</td>
                                                    <td>${ task.description }</td>
                                                </tr>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Duration (mins):</td>
                                                    <td>${ task.duration}</td>
                                                </tr>
                                                <c:if test="${ task.updatedAt != null }">
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Updated Date:</td>
                                                        <td><fmt:formatDate value="${task.updatedAt}" dateStyle="long"/> </td>
                                                    </tr>
                                                </c:if>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="mt-5">
                                <a  href="${pageContext.request.contextPath}/tasks" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">
                                        View All Tasks
                                </span>
                            </div>

                            </div>
                            </div>
                            </main>        
                            </body>
                            <%@include file="WEB-INF/jspf/footer.jspf" %>
                            </html>
