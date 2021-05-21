<%-- 
    Document   : Client View
    Created on : Mar 20, 2021
    Author     : Susannie Tiempo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<!DOCTYPE html>
<html>
    <head>
        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application - Client Detail</title>
    </head>
    <body>

        <%@include file="WEB-INF/jspf/navigation.jspf" %>
        <main>
            <div class="form-wrapper">
                <div class="heading">
                    <c:if test="${client == null || client.errors.size() > 0}">
                        <h2>  Create Client</h2>
                    </c:if>
                    <c:if test="${client != null && client.errors.size() == 0}">
                        <h2>Client Detail</h2>
                    </c:if>
                    <section>
                        <c:set var="errors" value="${ error }" />
                        <c:if test="${ errors == null }">
                            <form method="POST" action="save">
                                <div class="row justify-content-lg-center">
                                    <div class="col-lg-8">
                                        <div class="table-responsive-lg">
                                            <c:if test="${client == null || client.errors.size() > 0}">
                                                <div class="text-danger font-small"> *All fields are required. </div>
                                            </c:if>
                                            <table class="table table-striped table-hover">
                                                <c:if test="${client != null && client.id!= 0 }">
                                                    <tr>
                                                        <td class="font-weight-bold"><label>Client Id:</label></td>
                                                        <td>
                                                            ${ client.id }
                                                            <input type="hidden" value="${ client.id }" name="hdnClientId" />                                
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <tr>                    
                                                    <td class="font-weight-bold"> Client Name:</td>
                                                    <td><input class="form-control" type="text" name="clientname" value='${ client.name }' placeholder="Enter client name"/></td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Address:</td>
                                                    <td><input class="form-control" type="text" name="address" value='${ client.address }' placeholder="Enter complete address"/></td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Email Address:</td>
                                                    <td><input class="form-control" type="text" name="emailAddress" value='${ client.emailAddress }' placeholder="Valid email format: emailname@domain.com"/></td>
                                                </tr>
                                                <tr>                    
                                                    <td class="font-weight-bold">Phone Number:</td>
                                                    <td><input class="form-control" type="text" name="phoneNumber" value='${ client.phoneNumber }' placeholder="Valid phone number format: NNN-NNN-NNNN"/></td>
                                                </tr>
                                                <c:if test="${client != null && client.id!= 0 }">
                                                    <tr>                    
                                                        <td class="font-weight-bold">Created Date:</td>
                                                        <td><fmt:formatDate value="${client.createdAt}" dateStyle="long"/> </td>
                                                    </tr>

                                                    <c:if test="${client.updatedAt != null }">
                                                        <tr> 
                                                            <td class="font-weight-bold">Updated Date:</td>
                                                            <td><fmt:formatDate value="${client.updatedAt}" dateStyle="long"/> </td>
                                                        </tr>
                                                    </c:if>
                                                    <c:if test="${client.deletedAt != null }">
                                                        <tr>
                                                            <td class="font-weight-bold">Is Deleted:</td>
                                                            <td><label  name="deleteddAt" value='None'/> ${ client.isDeleted }</td>
                                                        </tr>
                                                        <tr> 
                                                            <td class="font-weight-bold">Deleted Date:</td>
                                                            <td><fmt:formatDate value="${client.deletedAt}" dateStyle="long"/> </td>
                                                        </tr>
                                                    </c:if>
                                                </c:if>
                                            </table>
                                            <div class="mt-5"></div>
                                            <div class="row justify-content-lg-center mt-4">
                                                <c:choose>
                                                    <c:when test="${ client != null && client.id!= 0 }">
                                                        <c:if test="${client.deletedAt == null}">
                                                            <input class="btn btn-md btn-dark  col-sm-2 mr-3" type="submit" value="Save" name="action" />   
                                                            <input class="btn btn-outline-primary  col-sm-2 mr-3" type="submit" value="Delete" name="action" />
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

                                                                <p class="text-danger" >Are you sure you want to delete this client?</p>
                                                            </div>
                                                            <div class="modal-footer">
                                                                <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                                                                <button  class="btn btn-outline-primary" type="submit" value="delete/${idToDelete}" name="action" >Delete Client</button>
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
                        <a  href="${pageContext.request.contextPath}/clients" class="btn btn-link  "> <i class="fa fa-chevron-circle-left fa-3x " aria-hidden="true" style="color: #E57400"></i> </a> <span class="text-center" style="color: #E57400">

                            <c:if test="${client == null }">
                                Cancel
                            </c:if>
                            <c:if test="${client != null }">
                                View All Clients
                            </c:if>
                        </span>
                    </div>
                    </main>
                    <c:if test="${client != null && client.errors.size() > 0}">
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
                                            <c:when test="${ client.errors.size() > 0 }">
                                                <ul>
                                                    <c:forEach items="${ client.errors }" var="err">
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
