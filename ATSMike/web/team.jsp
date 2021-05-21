<%-- 
    Document   : Team
    Created on : Mar 19, 2021, 1:24:37 PM
    Author     : Nguyen Pham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>

        <title>ATS Application - Team Detail</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:if test="${team == null || team.errors.size() > 0}">
                        <h2>  Create Team</h2>
                    </c:if>
                    <c:if test="${team != null && team.errors.size() == 0}">
                        <h2>Team Detail</h2>
                    </c:if>
                    <section>
                        <c:set var="errors" value="${ error }" />
                        <c:if test="${ errors == null }">
                            <form method="POST" action="save" >
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-8">
                                        <div class="table-responsive-lg">
                                            <c:if test="${team == null || team.errors.size() > 0}">
                                                <div class="text-danger font-small"> *All fields are required. </div>
                                            </c:if>
                                            <table class="table table-striped table-hover">
                                                <c:if test="${team != null && team.id!= 0 }">
                                                    <tr>
                                                        <td class="font-weight-bold"><label>Team Id:</label></td>
                                                        <td>
                                                            ${ team.id }
                                                            <input type="hidden" value="${ team.id }" name="hdnTeamId" />                                
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>                    
                                                    <td class="font-weight-bold">Team Name: 
                                                    </td>
                                                    <td><input class="form-control" type="text" name="teamName" value='${ team.teamName }' placeholder="Enter team name"/></td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Is On Call:</td>
                                                    <td><input class="mr-2" type="radio" name="isOnCall" value='1'/> <span class="mr-5"> True</span> 
                                                        <input class="mr-2"  type="radio" name="isOnCall" value='0' checked/>False 
                                                    </td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Team Members:
                                                        <c:if test="${team == null && team.id == 0 }">
                                                            <span class="text-info font-small font-weight-normal "> Select two team members.</span> 
                                                        </c:if>
                                                    </td>
                                                    <td>
                                                        <c:choose>
                                                            <c:when test="${ team != null && team.id!= 0 }">
                                                                <c:forEach items="${employees}"  var="emp">
                                                                    <label lass="form-control" type="text">${emp.firstName} ${emp.lastName}</label><br>
                                                                </c:forEach>
                                                            </c:when>
                                                            <c:otherwise>  
                                                                <select name="members" multiple size="10" style="width: 300px">
                                                                    <c:forEach items="${employees}"  var="emp">
                                                                        <option value='${emp.id}'>${emp.firstName} ${emp.lastName}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </td>

                                                    <c:if test="${team != null && team.id != 0 }">
                                                    <tr>                    
                                                        <td class="font-weight-bold">Created Date:</td>
                                                        <td><fmt:formatDate value="${team.createdAt}" dateStyle="long"/> </td>
                                                    </tr>
                                                    <tr>                    
                                                        <c:if test="${team.updatedAt != null }">
                                                            <td class="font-weight-bold">Updated Date:</td>
                                                              <td><fmt:formatDate value="${team.updatedAt}" dateStyle="long"/> </td>
                                                        </c:if>
                                                    </tr>
                                                    <c:if test="${team.deletedAt != null }">
                                                        <tr>
                                                            <td class="font-weight-bold">Is Deleted:</td>
                                                            <td><label  name="deleteddAt" value='None'/> ${ team.isDeleted }</td>
                                                        </tr>
                                                        <tr> 
                                                            <td class="font-weight-bold">Deleted Date:</td>
                                                            <td><fmt:formatDate value="${team.deletedAt}" dateStyle="long"/> </td>
                                                        </tr>
                                                    </c:if>
                                                </c:if>
                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                                <div class="mt-5"></div>
                                <div class="row justify-content-lg-center mt-4">
                                    <c:choose>
                                        <c:when test="${ team != null && team.id!= 0 }">
                                            <c:if test="${team.deletedAt == null }">
                                                <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Save" name="action" />  
                                                <input class="btn btn-outline-primary  col-sm-2 mr-3" type="submit" value="Delete" name="action" />
                                                <c:if test="${team.isOnCall == false}">
                                                    <input class="btn btn-md btn-dark   col-sm-2 mr-3" type="submit" value="Place On Call" name="action" />
                                                </c:if>
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Create" name="action" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <!--  DELETE CONFIRMATION MODAL     -->
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

                                                    <p class="text-danger" >Are you sure you want to delete this team?</p>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                    <button  class="btn btn-outline-primary" type="submit" value="delete/${idToDelete}" name="action" >Delete Team</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>

                            </form>
                        </c:if>
                    </section>
                    <div class="mt-5">
                        <a  href="${pageContext.request.contextPath}/teams" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                            <c:if test="${team == null }">
                                Cancel
                            </c:if>
                            <c:if test="${team != null }">
                                View All All Teams
                            </c:if>
                        </span>
                    </div>
                </div>
            </div>
        </main>
        <c:if test="${error != null || team.errors.size() > 0}">
            <script type="text/javascript">
                $(window).on('load', function () {
                    $('#myModal').modal('show');
                });
            </script>
            <div class="modal fade" tabindex="-1" role="dialog" id="myModal">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header text-center">
                            <h5 class="modal-title font-weight-bold text-danger"> <i class="fa fa-exclamation-circle mr-2" aria-hidden="true"></i>Errors</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body mr-3">
                            <c:choose>
                                <c:when test="${ team.errors.size() > 0 }">
                                    <ul>
                                        <c:forEach items="${ team.errors }" var="err">
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
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
