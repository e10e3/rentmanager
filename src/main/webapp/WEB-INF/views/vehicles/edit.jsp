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
                Voitures
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
                        <!-- Le  type de methode http qui sera appel� lors de action submit du formulaire -->
                        <!-- est décrit an l'attribut "method" de la balise forme -->
                        <!-- action indique à quelle "cible" sera envoyée la requête, ici notre Servlet qui sera bind sur -->
                        <!-- /vehicles/create -->
                        <form class="form-horizontal" method="post">
                            <div class="box-body">
                                <div class="form-group">
                                    <label for="manufacturer" class="col-sm-2 control-label">Marque</label>
									<!-- Pour récupérer la valeur rentrée dans un champ input de cette jsp au niveau de votre servlet -->
									<!-- vous devez passer par les méthodes getParameter de l'objet request, est spécifiant la valeur -->
									<!-- de l'attribut "name" de l'input -->
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required id="manufacturer" name="manufacturer" placeholder="Marque" pattern=".*\S+.*" title="Entrer un nom de marque" value="${vehicle.constructor()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="modele" class="col-sm-2 control-label">Modèle</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" required id="modele" name="modele" placeholder="Modèle" pattern=".*\S+.*" title="Entrer un modèle" value="${vehicle.model()}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="seats" class="col-sm-2 control-label">Nombre de places</label>
                                    <div class="col-sm-10">
                                        <input type="number" class="form-control" required id="seats" name="seats" placeholder="Nombre de places" min="2" max="9" value="${vehicle.seatCount()}">
                                    </div>
                                </div>
                            </div>
                            <!-- /.box-body -->
                            <div class="box-footer">
                                <button type="submit" class="btn btn-info pull-right">Modifier le véhicule</button>
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
