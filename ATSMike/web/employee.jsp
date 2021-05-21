<%--
    Document   : employee
    Created on : Mar 19, 2021, 1:24:37 PM
    Author     : Nguyen Pham & Guilherme Ferreira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Employee Detail</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:choose>
                        <c:when test="${isFromCreate != null}">
                            <h2>  Create Employee</h2>
                        </c:when>
                        <c:when test="${ savedTask != null }">
                            <h2>Task Updated - Successful</h2>
                            <h5 class="text-success text-center mb-5 mt-5"> The task record was successfully updated. <h5>
                                </c:when>
                                <c:otherwise>
                                    <h2>Employee Detail</h2>  
                                </c:otherwise>
                            </c:choose>
                            <section>
                                <c:set var="errors" value="${ error }" />
                                <c:if test="${ errors == null }">
                                    <form method="POST" action="save">
                                        <div class="row justify-content-lg-center">
                                            <div class="col-lg-8">
                                                <div class="table-responsive-lg">
                                                    <c:if test="${isFromCreate != null}">
                                                        <div class="text-danger font-small"> *All fields are required. </div>
                                                    </c:if>
                                                    <table class="table table-striped table-hover">
                                                        <c:if test="${employee != null && employee.id!= 0 }">
                                                            <tr>
                                                                <td class="font-weight-bold"><label>Employee Id:</label></td>
                                                                <td>
                                                                    ${ employee.id }
                                                                    <input type="hidden" value="${ employee.id }" name="hdnEmployeeId" />                                
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                        <tr>                    
                                                            <td class="font-weight-bold">First Name:</td>
                                                            <td><input class="form-control " type="text" name="firstName" value='${ employee.firstName }' placeholder="Enter first name"/></td>
                                                        </tr>
                                                        <tr>                    
                                                            <td class="font-weight-bold">Last Name:</td>
                                                            <td><input class="form-control" type="text" name="lastName" value='${ employee.lastName }' placeholder="Enter last name"/></td>
                                                        </tr>
                                                        <tr>                    
                                                            <td class="font-weight-bold">SIN:</td>
                                                            <td><input class="form-control" type="text" name="sin" value='${ employee.SIN }' placeholder="Valid format: NNNNNNNNN"/></td>
                                                        </tr>
                                                        <tr>                    
                                                            <td class="font-weight-bold">Hourly Rate (CAD):</td>
                                                            <td><input class="form-control" type="text" name="hourlyRate" value='${ employee.hourlyRate }' placeholder="Enter hourly rate in CAD"/></td>
                                                        </tr>

                                                        <c:if test="${employee != null && employee.id!= 0 }">
                                                            <tr>                    
                                                                <td class="font-weight-bold">Creation Date:</td>
                                                                <td><fmt:formatDate value="${employee.createdAt}" dateStyle="long"/> </td>
                                                            </tr>

                                                            <c:if test="${employee.updatedAt != null }">
                                                                <tr> 
                                                                    <td class="font-weight-bold">Updated Date:</td>
                                                                    <td><fmt:formatDate value="${employee.updatedAt}" dateStyle="long"/> </td>
                                                                </tr>
                                                            </c:if>

                                                            <c:if test="${employee.deletedAt != null}">
                                                                <tr>                    
                                                                    <td class="font-weight-bold">Is Deleted:</td>
                                                                    <td><label  name="deleteddAt" value='None'/> ${ employee.isDeleted }</td>
                                                                </tr>
                                                                <tr>                    
                                                                    <td class="font-weight-bold">Deleted Date:</td>
                                                                    <td><fmt:formatDate value="${employee.deletedAt}" dateStyle="long"/> </td>
                                                                </tr>
                                                            </c:if>

                                                            <tr>
                                                                <td class="font-weight-bold">Team Name:</td>
                                                                <c:if test="${teams.size() == 0 }">
                                                                    <td><label class="form-control" type="text" name="teamName" value='None'/> No team assigned</td>
                                                                </c:if>
                                                                <c:if test="${teams.size() > 0 }">
                                                                    <td><input class="form-control" type="text" name="teamName" value='<c:forEach items="${teams}" var="team">${team.teamName} </c:forEach>'/></td>
                                                                    </c:if>
                                                            </tr> 

                                                        </c:if>



                                                    </table>
                                                </div>
                                            </div>
                                        </div>

                                        <c:if test="${employee != null && employee.id!= 0 }">
                                            <h2 style="text-align:center"> Skills
                                            </h2>

                                            <section>
                                                <c:set var="taskCount" value="${ tasks.size()}" />
                                                <c:choose>
                                                    <c:when test="${ taskCount > 0}">
                                                        <div class="row justify-content-lg-center">
                                                            <div class="col-lg-8">
                                                                <div class="table-responsive-lg">
                                                                    <table class="table table-striped table-hover">
                                                                        <tr class="bg-dark text-light text-center">
                                                                            <th>
                                                                                Task Name
                                                                            </th>
                                                                            <th>
                                                                                Task Duration (in minutes)
                                                                            </th>
                                                                            <th>
                                                                                Action
                                                                            </th>

                                                                        </tr>
                                                                        <c:forEach items="${tasks}"  var="currentTask">
                                                                            <tr class="text-center" name="taskSelected" value="${ currentTask.id }">
                                                                            <input type="hidden" value="${ currentTask.id }" name="taskDeletion${currentTask.id}" />    
                                                                            <td>${ currentTask.name }</td>
                                                                            <td>${ currentTask.duration }</td>
                                                                            <td>
                                                                                <button  class="btn btn-outline-primary" type="submit" value="Remove Skill/${currentTask.id}" name="action" >Remove Skill</button>
                                                                            </td>
                                                                            </tr>
                                                                        </c:forEach>


                                                                    </table>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:when>
                                                    <c:when test="${ taskCount == 0}">
                                                        <h4 style="text-align:center">No Skills Added</h4>
                                                    </c:when>
                                                </c:choose>
                                            </section>
                                        </c:if>

                                        <div class="mt-5"></div>
                                        <div class="row justify-content-lg-center mt-4">
                                            <c:choose>
                                                <c:when test="${ employee != null && employee.id!= 0 }">
                                                    <c:if test="${employee.deletedAt == null}">
                                                        <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Save" name="action" />
                                                        <input class="btn btn-outline-primary  col-sm-2 mr-3" type="submit" value="Delete" name="action" />
                                                        <a href="${pageContext.request.contextPath}/employee/${employee.id}/addskills.jsp" class="btn btn-md btn-dark  col-sm-2 mr-3">Add Skills</a>   
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
                                                            <p class="text-danger" >Are you sure you want to delete ${itemToDelete}?</p>
                                                        </div>
                                                        <div class="modal-footer">
                                                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                            <c:choose>
                                                                <c:when test="${ itemToDelete == 'skill' }">
                                                                    <button  class="btn btn-outline-primary" type="submit" value="delete skill/${idToDelete}" name="action" >Delete Skill</button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <button  class="btn btn-outline-primary" type="submit" value="delete/${idToDelete}" name="action" >Delete Employee</button>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:if>


                                    </form>
                                </c:if>
                            </section>

                            <div class="mt-5">
                                <a  href="${pageContext.request.contextPath}/employees" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                    <c:if test="${employee == null }">
                                        Cancel
                                    </c:if>
                                    <c:if test="${employee != null }">
                                        View All Employees
                                    </c:if>
                                </span>
                            </div>
                            </div>
                            </div>
                            </main>

                            <c:if test="${error != null || employee.errors.size() > 0}">
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
                                                    <c:when test="${ employee.errors.size() > 0 }">
                                                        <ul>
                                                            <c:forEach items="${ employee.errors }" var="err">
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
                                                <p class="text-success" >Task was added successfully to the employee.</p>
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