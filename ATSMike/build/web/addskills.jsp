<%-- 
    Document   : addskills
    Created on : 28-Mar-2021, 3:21:21 PM
    Author     : Guilherme Ferreira
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
        <div class="form-wrapper">
            <div class="heading">
                <h1 class=" mt-5 font-weight-bold text-center" >Add Skills   </h1>

                <h3 class="text-center text-dark"> Employee: ${employee.firstName} ${employee.lastName} 
                </h3>
                <h3 style="text-align:center">Current Skills
                </h3>

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

                                            </tr>
                                            <c:forEach items="${tasks}"  var="task">
                                                <tr class="text-center">
                                                    <td>${ task.name }</td>
                                                    <td>${ task.duration }</td>

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
                <!--Section for available tasks-->
                <h3 class="text-center text-primary font-weight-bold">Select Skills to be Added</h3>
                <section>
                    <c:set var="avTaskCount" value="${ availableTasks.size()}" />
                    <c:choose>
                        <c:when test="${ avTaskCount > 0}">
                            <form method="POST" action="add skill">
                                <input type="hidden" value="${employee.id}" name="hdnEmployeeId" />  
                                <div class="row justify-content-lg-center mt-4">
                                    <div class="">
                                        <select class="form-control" name="selectedTasks" multiple size="10" style="width: 500px">
                                            <c:forEach items="${availableTasks}"  var="task">
                                                <option value='${task.id}'>${task.name}</option>
                                            </c:forEach>
                                        </select><br><br>

                                        <div class="text-center">
                                            <input class="btn btn-md btn-dark mb-4 text-center" type="submit" value="Add Skill(s)" name="action" />
                                        </div>
                                    </div>


                                </div>
                            </form>
                            <div class="mt-5 ">
                                <a  href="${pageContext.request.contextPath}/employees" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">
                                    View All Employees</span>
                            </div>
                        </c:when>
                        <c:when test="${ avTaskCount == 0}">
                            <h4 style="text-align:center">There are no skills to add.</h4>
                        </c:when>
                    </c:choose>
                </section>

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

            </div> 
        </div> 


        <%@include file="WEB-INF/jspf/footer.jspf" %>
    </body>
</html>
