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
        <title>ATS Application - Client Create</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <div class="form-wrapper">
            <div class="heading">
                <c:choose>
                    <c:when test="${ savedClient != null }">
                        <h2>Client Updated - Successful</h2>
                        <h5 class="text-success text-center mb-5 mt-5"> The client record was successfully updated. <h5>
                    </c:when>
                    <c:otherwise>
                        <h2>New Client Information - Summary</h2>
                    </c:otherwise>
                </c:choose>  
                        <div class="row justify-content-lg-center">
                            <div class="col-lg-8">
                                <div class="table-responsive-lg">
                                    <table class="table table-striped table-hover">
                                        <tbody>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold"> Client Id:</td>
                                                <td>${ client.id }</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Client Name:</td>
                                                <td>${ client.name }</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Address:</td>
                                                <td>${ client.address }</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Email Address:</td>
                                                <td>${ client.emailAddress }</td>
                                            </tr>
                                            <tr>
                                                <td style="text-align: right;" class="font-weight-bold">Phone Number:</td>
                                                <td>${ client.phoneNumber }</td>
                                            </tr>
                                            <c:if test="${client.updatedAt != null }">
                                                <tr> 
                                                    <td style="text-align: right;" class="font-weight-bold">Updated Date:</td>
                                                    <td><fmt:formatDate value="${client.updatedAt}" dateStyle="long"/> </td>
                                                </tr>
                                            </c:if>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="mt-5">
                            <a  href="${pageContext.request.contextPath}/clients" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                <c:if test="${client == null }">
                                    Cancel
                                </c:if>
                                <c:if test="${client != null }">
                                    View All Clients
                                </c:if>
                            </span>
                        </div>
                    </div>
                </div>
            </div>
        </main>        
    </body>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
