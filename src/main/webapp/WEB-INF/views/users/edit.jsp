<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<%@ page pageEncoding="UTF-8" %>
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
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                Utilisateurs
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <!-- Horizontal Form -->
                    <div class="box">
                        <c:if test="${errorMessage != null}">
                        <div class="alert alert-error m-1">
                            <p>${errorMessage}</p>
                        </div>
                        </c:if>
                        <!-- form start -->
                        <form class="form-horizontal" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="last_name" class="col-sm-2 control-label">Nom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required id="last_name" name="last_name" placeholder="Nom" minlength="3" pattern=".*\S{3,}.*" title="Entrer un nom d'au moins trois caractères"  value="${client.lastName()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="first_name" class="col-sm-2 control-label">Prénom</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required id="first_name" name="first_name" placeholder="Prénom" minlength="3" pattern=".*\S{3,}.*" title="Entrer un prénom d'au moins trois caractères"  value="${client.firstName()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Courriel</label>
                                    <div class="col-sm-10">
                                        <input type="email" class="form-control" required id="email" name="email" placeholder="Courriel" value="${client.emailAddress()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="email" class="col-sm-2 control-label">Date de naissance</label>
                                    <div class="col-sm-10">
                                        <input type="date" class="form-control" required id="naissance" name="naissance" value="${client.birthDate()}">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Mettre à jour le client</button>
                            </div>
                            <!-- /.box-footer -->
                        </form>
                    </div>
                    <!-- /.box -->
                </div>
                <!-- /.col -->
            </div>
        </section>
        <!-- /.content -->
    </div>

    <%@ include file="/WEB-INF/views/common/footer.jsp" %>
</div>
<!-- ./wrapper -->

<%@ include file="/WEB-INF/views/common/js_imports.jsp" %>
</body>
</html>
