<%-- 
    Document   : employees
    Created on : Mar 20, 2021, 11:01:51 AM
    Author     : Nguyen Pham
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Teams Index</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <h2>All Teams</h2>
                    <div class="mt-5"></div>

                    <section>
                        <c:set var="teamCount" value="${ teams.size()}" />
                        <c:choose>
                            <c:when test="${ teamCount > 0}">
                                <div class="table-responsive-lg">
                                    <table class="table table-striped table-hover">
                                        <thead class="thead-dark">
                                            <tr class="text-center">
                                                <th>
                                                    Team Name   
                                                </th>
                                                <th>
                                                    Is On Call
                                                </th>
                                                <th>
                                                    Is Deleted
                                                </th>
                                                <th>
                                                    Created Date
                                                </th>
                                                <th>
                                                    Deleted Date
                                                </th>
                                                <th>
                                                    Action 
                                                </th>
                                            </tr>
                                            <c:forEach items="${teams}"  var="team">
                                                <tr class="text-center">
                                                    <td>${ team.teamName }</td>
                                                    <td><input disabled type="checkbox" ${team.isOnCall == true ? "checked" : ""} /></td>
                                                    <td><input disabled type="checkbox" ${team.isDeleted == true ? "checked" : ""} readonly/></td>
                                                    <td>${ team.createdAt }</td>
                                                    <td>${ team.deletedAt }</td>
                                                    <td><a href="team/${ team.id}">View Details</a></td>
                                                </tr>
                                            </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${ teamCount == 0}">
                                <h4 style="text-align:center">No Employees</h4>
                            </c:when>
                        </c:choose>

                        <!--                       MODAL FOR DELETE SUCCESS                   -->
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
                                            <p class="text-success" >Team was successfully deleted.</p>
                                        </div>
                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:if>  

                    </section>
                </div>
            </div>
        </main>
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
