<%-- 
    Document   : tasks
    Created on : 20-Mar-2021, 5:39:47 PM
    Author     : gui_a
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Task Index</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <h2>All Tasks</h2>
                    <section>
                        <c:set var="taskCount" value="${ tasks.size()}" />
                        <c:choose>
                            <c:when test="${ taskCount > 0}">
                                <div class="table-responsive-lg">
                                    <table class="table table-striped table-hover">
                                        <thead class="thead-dark">
                                            <tr class="text-center ">
                                                <th>
                                                    Task Name
                                                </th>
                                                <th>
                                                    Description 
                                                </th>
                                                <th>
                                                    Action
                                                </th>

                                            </tr>
                                            <c:forEach items="${tasks}"  var="tas">
                                                <tr class="text-center">
                                                    <td>${ tas.name }</td>
                                                    <td>${ tas.description }</td>
                                                    <td><a href="task/${ tas.id}">View Details</a></td>

                                                </tr>
                                            </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${ taskCount == 0}">
                                <h4 style="text-align:center">There are no tasks.</h4>
                            </c:when>
                        </c:choose>
                    </section>

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
                                        <p class="text-success" >Task was successfully deleted.</p>
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
        </div>
    </main>
</body>
<%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
