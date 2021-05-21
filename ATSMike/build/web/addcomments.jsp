<%-- 
    Document   : addcomments
    Created on : Apr 4, 2021, 12:16:59 PM
    Author     : DELL
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>
​
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="WEB-INF/jspf/header.jspf" %>
        ​
        <title>ATS Application - Job Detail</title>
    </head>
    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <div class="form-wrapper">
            <div class="heading">
                <c:choose>
                    <c:when test="${ isAddSuccessful != null  }">
                        <h2 class=" text-center mb-5 mt-5 ">Comment Addition - Successful</h2>
                        <h5 class="text-success text-center mb-5 mt-5" style="text-align:center"> The comment is added to your job. <h5>
                            </c:when>
                            <c:when test="${ isDeleted != null }">
                                <h2>Comment Deletion - Successful</h2>
                                <h5 class="text-success text-center mb-5 mt-5"> The comment record was successfully deleted from the database. <h5>
                                    </c:when>
                                    <c:when test="${ isFromEditComment != null }">
                                        <h2>Edit Comment  </h2>
                                    </c:when>
                                    <c:otherwise>
                                        <h2 class="mb-5 mt-5" style="text-align:center">Add Comment  </h2>
                                        </h2>
                                    </c:otherwise>
                                </c:choose>
                                <section>
                                    <c:set var="errors" value="${ error }" />
                                    <c:if test="${ errors == null }">
                                        <form method="POST" action="add comment">
                                            <div class="row justify-content-lg-center">
                                                <div class="col-lg-8">
                                                    <div class="table-responsive-lg">
                                                        <c:if test="${isFromCreate != null}">
                                                            <div class="text-danger font-small"> *All fields are required. </div>
                                                        </c:if>
                                                        <table class="table table-striped table-hover">
                                                            <c:if test="${job != null && job.id!= 0 }">
                                                                <tr>
                                                                    <td class="font-weight-bold"><label>Job Id:</label></td>
                                                                    <td>
                                                                        ${ job.id }
                                                                        <input type="hidden" value="${ job.id }" name="hdnJobId" />                                
                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                            <tr>  
                                                            <input type="hidden" value="${ comment.commentId }" name="hdnCommentId" /> 
                                                            <td class="font-weight-bold">Comment:</td>
                                                            <td><textarea class="form-control" name="comment" rows="4" cols="50" value="${ comment.comment }">${ comment.comment } </textarea>
                                                            </td>
                                                            </tr>


                                                        </table>

                                                        <div class="mt-5"></div>
                                                        <div class="row justify-content-lg-center mt-4">
                                                            <c:choose>
                                                                <c:when test="${ isFromEditComment != null }">
                                                                    <button   class="btn btn-md btn-dark mb-4" type="submit" value="editcomment" name="action" >Edit Comment</button>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <input class="btn btn-md btn-dark mb-4" type="submit" value="Add Comment" name="action" />
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </c:if>
                                </section>

                                <c:if test="${error != null || comment.errors.size() > 0}">
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
                                                        <c:when test="${ comment.errors.size() > 0 }">
                                                            <ul>
                                                                <c:forEach items="${ comment.errors }" var="err">
                                                                    <li class="ml-4 font-small"> <span class="mr-2  font-weight-bold">Error Code: </span> ${ err.code }  <span class="ml-2  font-weight-bold"> Desc: </span> ${err.description }</li>
                                                                    </c:forEach>
                                                            </ul>
                                                        </c:when>
                                                        <c:when test="${ error != null }">
                                                            <ul>
                                                                <c:forEach items="${ comment.errors }" var="err">
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


                                <div class="mt-5">
                                    <a  href="${pageContext.request.contextPath}/jobs" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">
                                        View All Jobs</span>
                                </div>

                                </div> 
                                </div>  ​
                                </div> 
                                </div>  ​

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
                                <%@include file="WEB-INF/jspf/footer.jspf" %>
                                </body>
                                </html>