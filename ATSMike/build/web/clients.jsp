
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Client Index</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <h2>All Clients</h2>
                    <form method="POST" action="clients">

                        <div class="d-flex justify-content-center">
                            <div class="container" style=" padding: 20px; margin: 0; list-style: none; margin-bottom: 30px; padding-top: 35px; background-color: rgba(134, 158, 158, 0.1); border-radius: 3px;">
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-4">
                                        <div>
                                            <input class="form-control form-control-lg text-center" type="text" name="searchString" value='${ searchString }' style = "border-color: #17D8E2; font-size: medium;" placeholder = "Enter client name"/>
                                        </div>
                                    </div>
                                </div>

                                <div class="row justify-content-lg-center mt-4">
                                    <button type="submit" value="Search" name="action"  class="btn btn-md btn-dark  col-sm-2 mr-3"> <i class="fas fa-search"></i> Search</button>
                                    <button type="submit" value="reset"  name="action"  class="btn btn-outline-secondary col-sm-2" > <i class="fa fa-refresh"></i> Refresh</button>
                                </div>

                            </div>

                        </div>
                    </form>
                    <div class="mt-5"></div>
                    <section>
                        <c:set var="clientCount" value="${ clients.size()}" />
                        <c:choose>
                            <c:when test="${ clientCount > 0}">
                                <div class="table-responsive-lg">
                                    <table class="table table-striped table-hover">
                                        <thead class="thead-dark">
                                            <tr class="text-center">
                                                <th>
                                                    Name
                                                </th>
                                                <th>
                                                    Email Address
                                                </th>
                                                <th>
                                                    Phone Number
                                                </th>
                                                <th>
                                                    Action
                                                </th>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${clients}"  var="client">
                                            <tr  class="font-small text-center">
                                                <td>${ client.name }</td>
                                                <td>${ client.emailAddress }</td>
                                                <td>${ client.phoneNumber }</td>
                                                <td><a href="client/${ client.id}" >View Details</a> </td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${ clientCount == 0}">
                                <h4 style="text-align:center">No clients to show.</h4>
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
                                        <p class="text-success" >Client was successfully deleted.</p>
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
        </main>
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
