<%-- 
    Document   : task
    Created on : 20-Mar-2021, 3:42:07 PM
    Author     : Guilherme Ferreira
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>

        <title>ATS Application</title>
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
                                    <h2>Task Detail</h2>
                                </c:otherwise>
                            </c:choose>

                            <section>
                                <c:set var="errors" value="${ error }" />
                                <c:if test="${ errors == null }">
                                    <form method="POST" action="save">
                                        <div class="row justify-content-lg-center">
                                            <div class="col-lg-8">
                                                <div class="table-responsive-lg">
                                                    <c:if test="${task == null || task.errors.size() > 0}">
                                                        <div class="text-danger font-small"> *All fields are required. </div>
                                                    </c:if>
                                                    <table class="table table-striped table-hover">
                                                        <c:if test="${task != null && task.id!= 0 }">
                                                            <tr>
                                                                <td class="font-weight-bold"><label>Task Id:</label></td>
                                                                <td>
                                                                    ${ task.id }
                                                                    <input type="hidden" value="${ task.id }" name="hdnTaskId" />                                
                                                                </td>
                                                            </tr>
                                                        </c:if>
                                                        <tr>                    
                                                            <td class="font-weight-bold">Task Name:</td>
                                                            <td><input class="form-control" type="text" name="taskName" value='${ task.name }' placeholder="Enter task name"/></td>
                                                        </tr>
                                                        <tr>                    
                                                            <td class="font-weight-bold">Description:</td>
                                                            <td><input class="form-control" type="text" name="description" value='${ task.description }' placeholder="Enter task description"/></td>
                                                        </tr>
                                                        <tr>                    
                                                            <td class="font-weight-bold">Duration (mins): </td>
                                                            <td><input class="form-control" type="text" name="duration" value='${ task.duration }' placeholder="Enter duration in minutes"/></td>
                                                        </tr>
                                                        <c:if test="${ task.updatedAt != null }">
                                                            <tr>
                                                                <td class="font-weight-bold">Updated Date:</td>
                                                                <td><fmt:formatDate value="${task.updatedAt}" dateStyle="long"/> </td>
                                                            </tr>
                                                        </c:if>
                                                    </table>

                                                    <div class="mt-5"></div>
                                                    <div class="row justify-content-lg-center mt-4">
                                                        <c:choose>
                                                            <c:when test="${ task != null && task.id!= 0 }">

                                                                <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Save" name="action" />    
                                                                <input class="btn btn-outline-primary  col-sm-2 mr-3" type="submit" value="Delete" name="action" />
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

                                                                        <p class="text-danger" >Are you sure you want to delete this task?</p>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                        <button  class="btn btn-outline-primary" type="submit" value="delete/${idToDelete}" name="action" >Delete Task</button>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:if>

                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </c:if>
                            </section>
                            <div class="mt-5">
                                <a  href="${pageContext.request.contextPath}/tasks" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                                    <c:if test="${task == null }">
                                        Cancel
                                    </c:if>
                                    <c:if test="${task != null }">
                                        View All Tasks
                                    </c:if>
                                </span>
                            </div>
                            </div>
                            </div>
                            </main>
                            <c:if test="${error != null || task.errors.size() > 0}">
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
                                                    <c:when test="${ task.errors.size() > 0 }">
                                                        <ul>
                                                            <c:forEach items="${ task.errors }" var="err">
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

