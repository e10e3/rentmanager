<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>
<%@include file="/WEB-INF/views/common/head.jsp"%>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@ include file="/WEB-INF/views/common/header.jsp" %>
    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="/WEB-INF/views/common/sidebar.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-3">

                    <!-- Profile Image -->
                    <div class="box box-primary">
                        <div class="box-body box-profile">
                            <h3 class="profile-username text-center">${vehicle.constructor()} ${vehicle.model()}</h3>
                            <p>${vehicle.seatCount()} places</p>
                            <ul class="list-group list-group-unbordered">
                                <li class="list-group-item">
                                    <b>Réservation·s</b> <a class="pull-right">${reservations.size()}</a>
                                </li>
                                <li class="list-group-item">
                                    <b>Conducteur·s</b> <a class="pull-right">${renterClients.size()}</a>
                                </li>
                            </ul>
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/cars/edit?id=${vehicle.identifier()}">
                                <i class="fa fa-edit"></i> Modifier
                            </a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/cars/delete?id=${vehicle.identifier()}">
                                <i class="fa fa-trash"></i> Supprimer
                            </a>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
                <div class="col-md-9">
                    <div class="nav-tabs-custom">
                        <ul class="nav nav-tabs">
                            <li class="active"><a href="#rents" data-toggle="tab">Réservations</a></li>
                            <li><a href="#drivers" data-toggle="tab">Conducteurs</a></li>
                        </ul>
                        <div class="tab-content">
                            <div class="active tab-pane" id="rents">
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Client</th>
                                            <th>Date de debut</th>
                                            <th>Date de fin</th>
                                        </tr>
                                        <c:forEach items="${reservations}" var="reservation">
                                        <tr>
                                            <td>${reservation.identifier()}</td>
                                            <td>${reservation.renterClient()}</td>
                                            <td>${reservation.startDate()}</td>
                                            <td>${reservation.endDate()}</td>
                                        </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                            <div class="tab-pane" id="drivers">
                                <!-- /.box-header -->
                                <div class="box-body no-padding">
                                    <table class="table table-striped">
                                        <tr>
                                            <th style="width: 10px">#</th>
                                            <th>Nom</th>
                                            <th>Prénom</th>
                                            <th>date de naissance</th>
                                            <th>Courriel</th>
                                        </tr>
                                        <c:forEach items="${renterClients}" var="client">
                                        <tr>
                                            <td>${client.identifier()}</td>
                                            <td>${client.lastName()}</td>
                                            <td>${client.firstName()}</td>
                                            <td>${client.birthDate()}</td>
                                            <td>${client.emailAddress()}</td>
                                        </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                            <!-- /.tab-pane -->
                        </div>
                        <!-- /.tab-content -->
                    </div>
                    <!-- /.nav-tabs-custom -->
                </div>
                <!-- /.col -->
            </div>
            <!-- /.row -->

        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
