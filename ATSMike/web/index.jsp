
<%@page import="com.nbcc.atssystem.business.HomeServiceFactory"%>
<%@page import="com.nbcc.atssystem.business.IHomeService"%>
<%@page import="com.nbcc.atssystem.models.ICostVM"%>
<%@include file="WEB-INF/jspf/taglibraries.jspf" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.google.gson.Gson"%>
<%@ page import="com.google.gson.JsonObject"%>

<%
    Gson gsonObj = new Gson();
    Map<Object, Object> map = null;
    List<Map<Object, Object>> list = new ArrayList<Map<Object, Object>>();
    IHomeService service = HomeServiceFactory.createInstance();

    List<ICostVM> monthlyCosts = service.getMonthlyCost(2021);
    List<ICostVM> lastYearCosts = service.getMonthlyCost(2020);

    int count = 0;

    for (ICostVM cost : monthlyCosts) {
        count++;

        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getBillable());
        list.add(map);
    }

    String dataPoints1 = gsonObj.toJson(list);

    list = new ArrayList<Map<Object, Object>>();
    count = 0;
    for (ICostVM cost : monthlyCosts) {
        count++;
        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getRevenue());
        list.add(map);
    }
    String dataPoints2 = gsonObj.toJson(list);

    list = new ArrayList<Map<Object, Object>>();
    count = 0;
    for (ICostVM cost : monthlyCosts) {
        count++;
        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getCost());
        list.add(map);
    }

    String dataPoints3 = gsonObj.toJson(list);
    count = 0;
    list = new ArrayList<Map<Object, Object>>();

    for (ICostVM cost : lastYearCosts) {
        count++;
        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getBillable());
        list.add(map);

    }

    String dataPoints4 = gsonObj.toJson(list);

    list = new ArrayList<Map<Object, Object>>();
    count = 0;
    for (ICostVM cost : lastYearCosts) {
        count++;
        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getRevenue());
        list.add(map);

    }

    String dataPoints5 = gsonObj.toJson(list);

    list = new ArrayList<Map<Object, Object>>();
    count = 0;
    for (ICostVM cost : lastYearCosts) {
        count++;
        map = new HashMap<Object, Object>();
        map.put("label", cost.getMonth() == 1 ? "January"
                : cost.getMonth() == 2 ? "February"
                : cost.getMonth() == 3 ? "March"
                : cost.getMonth() == 4 ? "April"
                : cost.getMonth() == 5 ? "May"
                : cost.getMonth() == 6 ? "June"
                : cost.getMonth() == 7 ? "July"
                : cost.getMonth() == 8 ? "August"
                : cost.getMonth() == 9 ? "September"
                : cost.getMonth() == 10 ? "October"
                : cost.getMonth() == 11 ? "November"
                : cost.getMonth() == 12 ? "December" : "");
        map.put("x", count);

        map.put("y", cost.getCost());
        list.add(map);

    }

    String dataPoints6 = gsonObj.toJson(list);


%>
<!DOCTYPE html>
<html>
    <head>

        <%@include file="WEB-INF/jspf/header.jspf" %>
        <title>ATS Application</title>

        <script type="text/javascript">
            window.onload = function () {

                var chart = new CanvasJS.Chart("chartContainer", {
                    theme: "light2",
                    title: {
                        text: "Historical Performance"
                    },
                    subtitles: [{
                            text: "Two Year Period"
                        }],
                    axisY: {
                        title: "Canadian Dollars ($CAD)"
                    },
                    toolTip: {
                        shared: true,
                        reversed: true
                    },

                    data: [{
                            type: "stackedColumn",
                            name: "Billable ${year}",
                            showInLegend: true,
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints1);%>
                        },
                        {
                            type: "stackedColumn",
                            name: "Revenue ${year}",
                            showInLegend: true,
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints2);%>
                        },
                        {
                            type: "stackedColumn",
                            name: "Cost ${year}",
                            showInLegend: true,
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints3);%>
                        },
                        {
                            type: "stackedColumn",
                            name: "Billable ${lastYear}",
                            showInLegend: true,
                            axisYType: "secondary",
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints4);%>
                        },
                        {
                            type: "stackedColumn",
                            name: "Revenue ${lastYear}",
                            showInLegend: true,
                            axisYType: "secondary",
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints5);%>
                        },
                        {
                            type: "stackedColumn",
                            name: "Cost ${lastYear}",
                            axisYType: "secondary",
                            showInLegend: true,
                            yValueFormatString: "#,##0 $CAD",
                            dataPoints: <%out.print(dataPoints6);%>
                        }
                    ]
                });
                chart.render();

            }
        </script>

    </head>

    <body>
        <%@include file="WEB-INF/jspf/navigation.jspf" %>

    <body id="page-top">

        <!-- Page Wrapper -->
        <div id="wrapper">


            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">

                <!-- Main Content -->
                <div id="content">

                    <!-- Topbar -->
                    <nav class="navbar navbar-expand navbar-light bg-white topbar mb-4 static-top shadow">

                        <!-- Sidebar Toggle (Topbar) -->
                        <button id="sidebarToggleTop" class="btn btn-link d-md-none rounded-circle mr-3">
                            <i class="fa fa-bars"></i>
                        </button>

                        <!-- Topbar Search -->
                        <form
                            class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                            <div class="input-group">
                                <input type="text" class="form-control bg-light border-0 small" placeholder="Search for..."
                                       aria-label="Search" aria-describedby="basic-addon2">
                                <div class="input-group-append">
                                    <button class="btn btn-primary" type="button">
                                        <i class="fas fa-search fa-sm"></i>
                                    </button>
                                </div>
                            </div>
                        </form>



                    </nav>
                    <!-- End of Topbar -->

                    <!-- Begin Page Content -->
                    <div class="container-fluid">

                        <!-- Page Heading -->
                        <div class="d-sm-flex align-items-center justify-content-center mb-4">
                            <h1 class="h3 mb-0 text-gray-800">Dashboard</h1>
                        </div>

                        <!-- Content Row -->
                        <div class="row d-sm-flex align-items-center justify-content-center">

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-primary shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">
                                                    Revenue (Monthly)</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">$${monthlyRevenueToDate}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-calendar fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-danger shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                                    Cost (Monthly)</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">$${monthlyCostToDate}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-tag fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-info shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-info text-uppercase mb-1">
                                                    Number of Jobs (Current Day)</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">${numberOfJobs}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-clipboard-list  fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>

                        <!-- Content Row -->
                        <div class="row d-sm-flex align-items-center justify-content-center">

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-success shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-success text-uppercase mb-1">
                                                    Revenue (Annual)</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">$${yearlyRevenueToDate}0</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Earnings (Monthly) Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-danger shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-danger text-uppercase mb-1">
                                                    Cost (Annual)</div>
                                                <div class="h5 mb-0 font-weight-bold text-gray-800">$${yearlyCostToDate}</div>
                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-tag fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- Pending Requests Card Example -->
                            <div class="col-xl-3 col-md-6 mb-4">
                                <div class="card border-left-warning shadow h-100 py-2">
                                    <div class="card-body">
                                        <div class="row no-gutters align-items-center">
                                            <div class="col mr-2">
                                                <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">
                                                    Team on Call</div>
                                                <div class="h6 mb-0 font-weight-bold text-gray-800">${onCallTeam != null ? onCallTeam.teamName : "NO TEAM"}</div>
                                                <c:if test="${members != null}">
                                                    <c:forEach items="${members}"  var="emp">
                                                        <div class="mb-0 text-gray-800 font-small">${emp.firstName} ${emp.lastName}</div>

                                                    </c:forEach>
                                                </c:if>

                                            </div>
                                            <div class="col-auto">
                                                <i class="fas fa-comments fa-2x text-gray-300"></i>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- Content Row -->

                        <div class="row">

                            <!-- Area Chart -->
                            <div class="col-xl-6 col-sm-4">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <form method="POST" action="">
                                        <input type="hidden" value="${ shownYear }" name="hdnYear" />                                

                                        <div
                                            class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                            <h6 class="m-0 font-weight-bold text-primary">Historical Data For Months in ${shownYear}</h6>
                                            <div class="dropdown no-arrow">
                                                <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink"
                                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                    <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                                                </a>
                                                <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in"
                                                     aria-labelledby="dropdownMenuLink">
                                                    <div class="dropdown-header">Select Year</div>

                                                    <input class="dropdown-item btn-link" type="submit" value="${year}" name="action" />

                                                    <input class="dropdown-item btn-link" type="submit" value="${lastYear}" name="action" />

                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="card shadow mb-4">
                                        <!-- Card Body -->
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered font-small" id="dataTable" width="90%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Period (Month)</th>
                                                            <th>Cost ($)</th>
                                                            <th>Revenue ($)</th>
                                                            <th>Billable ($)</th>
                                                        </tr>
                                                    </thead>

                                                    <tfoot>
                                                        <tr>
                                                            <th>Period (Month)</th>
                                                            <th>Cost ($)</th>
                                                            <th>Revenue ($)</th>
                                                            <th>Billable ($)</th>

                                                        </tr>
                                                    </tfoot>
                                                    <tbody>
                                                        <c:forEach items="${monthlyCosts}"  var="monthlyCost">
                                                            <tr>
                                                                <td>${
                                                                    monthlyCost.getMonth() == 1 ? "January" 
                                                                        :  monthlyCost.getMonth() == 2 ? "February" 
                                                                        :monthlyCost.getMonth() == 3 ? "March" 
                                                                        :monthlyCost.getMonth() == 4 ? "April" 
                                                                        :monthlyCost.getMonth() == 5 ? "May" 
                                                                        :monthlyCost.getMonth() == 6 ? "June" 
                                                                        :monthlyCost.getMonth() == 7 ? "July" 
                                                                        :monthlyCost.getMonth() == 8 ? "August" 
                                                                        :monthlyCost.getMonth() == 9 ? "September" 
                                                                        :monthlyCost.getMonth() == 10 ? "October" 
                                                                        :monthlyCost.getMonth() == 11 ?"November" 
                                                                        :monthlyCost.getMonth() == 12 ? "December" : "" 

                                                                    }



                                                                </td>
                                                                <td>${monthlyCost.getCost()}</td>
                                                                <td>${monthlyCost.getRevenue()}</td>
                                                                <td>${monthlyCost.getBillable()}</td>
                                                            </tr>

                                                        </c:forEach>



                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                </div>


                            </div>

                            <!-- Pie Chart -->
                            <div class="col-xl-6 col-lg-5">
                                <div class="card shadow mb-4">
                                    <!-- Card Header - Dropdown -->
                                    <form method="POST" action="filter">
                                        <div
                                            class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                                            <h6 class="m-0 font-weight-bold text-primary">Historical Performance (Yearly)</h6>
                                            <div class="dropdown no-arrow">

                                            </div>
                                        </div>
                                    </form>

                                    <!-- Card Body -->
                                    <div class="card shadow mb-4">
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-bordered font-small" id="dataTable" width="90%" cellspacing="0">
                                                    <thead>
                                                        <tr>
                                                            <th>Period (Year)</th>
                                                            <th>Cost ($)</th>
                                                            <th>Revenue ($)</th>
                                                            <th>Billable ($)</th>
                                                        </tr>
                                                    </thead>

                                                    <tfoot>
                                                        <tr>
                                                            <th>Period</th>
                                                            <th>Cost ($)</th>
                                                            <th>Revenue ($)</th>
                                                            <th>Billable ($)</th>

                                                        </tr>
                                                    </tfoot>
                                                    <tbody>
                                                        <c:forEach items="${yearlyCosts}"  var="yearlyCost">
                                                            <tr>
                                                                <td>${yearlyCost.getYear()}</td>
                                                                <td>${yearlyCost.getCost()}</td>
                                                                <td>${yearlyCost.getRevenue()}</td>
                                                                <td>${yearlyCost.getBillable()}</td>
                                                            </tr>

                                                        </c:forEach>



                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>


                        </div>

                        <div class="row d-flex flex-column">

                            <!-- Area Chart -->
                            <div class="col-xl-10 col-sm-4 ">
                                <div class="card shadow mb-4 " >
                                    <!-- Card Header - Dropdown -->
                                    <div id="chartContainer" style="height: 370px; width: 100%;"></div>
                                </div>
                            </div>
                        </div>



                        <!-- /.container-fluid -->

                    </div>
                    <!-- End of Main Content -->
                    <!-- Bootstrap core JavaScript-->
                    <script src="vendor/jquery/jquery.min.js"></script>
                    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
                    <!-- Core plugin JavaScript-->
                    <script src="vendor/jquery-easing/jquery.easing.min.js"></script>
                    <!-- Custom scripts for all pages-->
                    <script src="https://canvasjs.com/assets/script/canvasjs.min.js"></script>
                    <script src="${pageContext.request.contextPath}/js/demo/chart-bar-demo.js"></script>
                    </body>
                    <%@include file="WEB-INF/jspf/footer.jspf" %>
                    <%@include file="WEB-INF/jspf/modals.jspf" %>
                    </html>
