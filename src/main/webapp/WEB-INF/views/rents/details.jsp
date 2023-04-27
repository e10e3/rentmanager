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
                            <dl>
                                <dt>Client</dt>
                                <dd>${reservation.renterClient()}</dd>
                                <dt>Véhicule</dt>
                                <dd>${reservation.rentedVehicle()}</dd>
                                <dt>Début</dt>
                                <dd>${reservation.startDate()}</dd>
                                <dt>Fin</dt>
                                <dd>${reservation.endDate()}</dd>
                            </dl>
                            <a class="btn btn-success" href="${pageContext.request.contextPath}/rents/edit?id=${reservation.identifier()}">
                                <i class="fa fa-edit"></i> Modifier
                            </a>
                            <a class="btn btn-danger" href="${pageContext.request.contextPath}/rents/delete?id=${reservation.identifier()}">
                                <i class="fa fa-trash"></i> Supprimer
                            </a>
                        </div>
                        <!-- /.box-body -->
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
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
