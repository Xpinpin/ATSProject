
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Job Index</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <h2>All Jobs</h2>
                    <form method="POST" action="jobs">

                        <div class="d-flex justify-content-center">
                            <div class="container" style=" padding: 20px; margin: 0; list-style: none; margin-bottom: 30px; padding-top: 35px; background-color: rgba(134, 158, 158, 0.1); border-radius: 3px;">
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-4">
                                        <div>
                                            <input class="form-control form-control-lg text-center" type="date" name="searchString" value='${ searchString }' style = "border-color: #17D8E2; font-size: medium;" />
                                        </div>
                                    </div>
                                </div>

                                <div class="row justify-content-lg-center mt-4">
                                    <button type="submit" value="Search" name="action"  class="btn btn-md btn-dark  col-sm-2 mr-3"> <i class="fas fa-search"></i> Search</button>
                                    <button type="reset" value="reset" class="btn btn-outline-secondary col-sm-2" > <i class="fa fa-refresh"></i> Reset</button>
                                </div>
                            </div>
                        </div>
                    </form>



                    <div id="chart_div"></div>
                    <div class="mt-5"></div>
                    <section>
                        <c:set var="jobCount" value="${ vms.size()}" />
                        <c:choose>
                            <c:when test="${ jobCount > 0}">
                                <div class="table-responsive-lg">
                                    <table class="table table-striped table-hover">
                                        <thead class="thead-dark">
                                            <tr class="text-center">

                                                <th>
                                                    Teams Name
                                                </th>
                                                <th>
                                                    Client Name
                                                </th>
                                                <th>
                                                    Job Description
                                                </th>
                                                <th>
                                                    Start
                                                </th>
                                                <th>
                                                    End
                                                </th>
                                                <th>
                                                    Action
                                                </th>
                                            </tr>
                                        </thead>
                                        <c:forEach items="${vms}"  var="vm">
                                            <tr  class="font-small text-center">

                                                <td>${ vm.teamName }</td>
                                                <td>${ vm.clientName }</td>
                                                <td>${ vm.job.description  }</td>
                                                <td>${ vm.job.start  }</td>
                                                <td>${ vm.job.end  }</td>
                                                <td><a href="job/${vm.job.id}" >View Details</a> </td>

                                            </tr>
                                        </c:forEach>

                                    </table>
                                </div>
                            </c:when>
                            <c:when test="${ jobCount == 0}">
                                <h4 style="text-align:center">No jobs to show.</h4>
                            </c:when>
                        </c:choose>
                    </section>
                </div>
            </div>
        </main>
    </body>
    <%@include file="WEB-INF/jspf/footer.jspf" %>
</html>
