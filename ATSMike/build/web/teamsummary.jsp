<%-- 
    Document   : tasksummary
    Created on : 20-Mar-2021, 3:46:43 PM
    Author     : Gui Ferrera - Merged
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Team Create Summary</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:choose>
                        <c:when test="${ placeOnCallSuccess != null }">
                            <h2>Team Updated - Successful</h2>
                            <h5 class="text-success text-center mb-5 mt-5"> The team record was successfully updated. <h5>
                                </c:when>
                                <c:otherwise>
                                    <h2>New Team Information - Summary</h2>
                                </c:otherwise>
                            </c:choose>
                            <div class="row justify-content-lg-center">
                                <div class="col-lg-8">
                                    <div class="table-responsive-lg">
                                        <table class="table table-striped table-hover">
                                            <tbody>
                                                <tr >
                                                    <td style="text-align: right;" class="font-weight-bold">Team Id:</td>
                                                    <td>${ team.id }</td>
                                                </tr>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Team Name:</td>
                                                    <td>${ team.teamName }</td>
                                                </tr>
                                                <tr>
                                                    <td style="text-align: right;" class="font-weight-bold">Is On Call:</td>
                                                    <td>${ team.isOnCall}</td>
                                                </tr>
                                                <c:if test="${team.updatedAt != null }">
                                                    <tr>
                                                        <td style="text-align: right;" class="font-weight-bold">Updated Date:</td>
                                                        <td><fmt:formatDate value="${team.updatedAt}" dateStyle="long"/> </td>
                                                    </tr>

                                                </c:if>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="mt-5">
                                <a  href="${pageContext.request.contextPath}/teams" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                    <c:if test="${team == null }">
                                        Cancel
                                    </c:if>
                                    <c:if test="${team != null }">
                                        View All Teams
                                    </c:if>
                                </span>
                            </div>
                            </div>
                            </div>
                            </main>        
                            </body>
                            <%@include file="WEB-INF/jspf/footer.jspf" %>
                            </html>