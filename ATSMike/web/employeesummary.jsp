<%-- 
    Document   : employeesummary
    Created on : Mar 19, 2021, 1:54:09 PM
    Author     : Nguyen Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Employee Create Summary</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:choose>
                       
                        <c:when test="${ savedEmp != null }">
                            <h2>Employee Updated - Successful</h2>
                            <h5 class="text-success text-center mb-5 mt-5"> The employee record was successfully updated. <h5>
                                </c:when>

                                <c:when test="${ isTaskAdded != null }">
                                    <h2>Task Added - Successful</h2>
                                    <h5 class="text-success text-center mb-5 mt-5"> The task record was successfully added to the database. <h5>
                                        </c:when>          
                                        <c:otherwise>
                                            <h2>New Employee Information - Summary</h2>
                                        </c:otherwise>
                                    </c:choose>  
                                    <table class="table table-striped table-hover">
                                        <tbody>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Employee Id:</td>
                                                <td>${ employee.id }</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Employee Name:</td>
                                                <td>${ employee.firstName } ${ employee.lastName}</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">SIN:</td>
                                                <td>${ employee.SIN}</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold" >Hourly Rate:</td>
                                                <td>
                                                    <fmt:formatNumber value="${employee.hourlyRate}" type="currency" currencySymbol="$"/>
                                                </td>
                                            </tr>
                                            <c:if test="${employee.updatedAt != null }">
                                                <tr> 
                                                    <td style="text-align: right;" class="font-weight-bold">Updated Date:</td>
                                                    <td><fmt:formatDate value="${employee.updatedAt}" dateStyle="long"/> </td>
                                                </tr>
                                            </c:if>

                                    </table>
                                                
                                    <c:set var="taskCount" value="${ tasks.size()}" />
                                    <c:if test="${ taskCount > 0}">
                                        <div class="row justify-content-lg-center">
                                            <div class="col-lg-8">
                                                <div class="table-responsive-lg">
                                                    <table class="table table-striped table-hover">
                                                        <tr class="bg-secondary text-light text-center">
                                                            <th >
                                                                Task Name
                                                            </th>
                                                        </tr>
                                                        <c:forEach items="${tasks}"  var="currentTask">
                                                            <tr class="text-center">
                                                            <td>${ currentTask.name }</td>
                                                            </tr>
                                                        </c:forEach>

                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </c:if>

                                    <div class="mt-5">
                                        <a  href="${pageContext.request.contextPath}/employees" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                            <c:if test="${employee == null && isDeleted == null }">
                                                Cancel
                                            </c:if>
                                            <c:if test="${employee != null || isDeleted != null }">
                                                View All Employees
                                            </c:if>
                                        </span>
                                    </div>
                                    </div>

                                    </div>
                                    </main>        
                                    </body>
                                    <%@include file="WEB-INF/jspf/footer.jspf" %>
                                    </html>
