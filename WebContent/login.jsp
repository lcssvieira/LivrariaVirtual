<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Livraria Online - Login</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="../assets/js/ie/html5shiv.js"></script><![endif]-->
<script src="./assets/js/jquery.min.js"></script>
<script src="./assets/js/bootstrap.min.js"></script>
<link rel="stylesheet" href="./assets/css/bootstrap.css" />
<link rel="stylesheet" href="./assets/css/main.css" />
<link rel="stylesheet" href="./assets/css/bootstrap-datepicker3.min.css" />
<link rel="stylesheet" href="./assets/css/login.css" />
<link rel="stylesheet" href="./assets/css/bootstrap3/bootstrap.min.css">
<link rel="stylesheet"
	href="./assets/css/bootstrap3/font-awesome.min.css">
<link rel="stylesheet" href="./assets/css/bootstrap3/bootsnipp.min.css">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
<body class="homepage">
	<div class="container">

		<div id="loginbox"
			class="mainbox col-md-6 col-md-offset-3 col-sm-6 col-sm-offset-3">

			<div class="row">
				<div style="font-size: 254px; line-height: 1.5em;">
					<i class="fa fa-university fa-5" style="padding: 0 0 0 150px;"></i>
				</div>
				<!-- <img src="assets/css/images/gun.gif" align="middle"
					style="padding: 50px 0 0 150px;" />-->
				<div class="iconmelon"></div>
			</div>

			<div class="panel panel-default">
				<div class="panel-heading">
					<div class="panel-title text-center">Gestor de Museu</div>
				</div>

				<div class="panel-body">
					<c:if test="${not empty erro}">
						<div class="alert alert-danger" role="alert">
							<c:out value="${erro}" />
						</div>
					</c:if>
					<form name="form" id="form" class="form-horizontal" method="POST"
						action="ServletMuseu.do">
						<input type="hidden" name="classe" value="AdminControl" /> <input
							type="hidden" name="metodo" value="login" />
						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-user"></i></span> <input id="user"
								type="text" class="form-control" name="user" value=""
								placeholder="Usuário">
						</div>

						<div class="input-group">
							<span class="input-group-addon"><i
								class="glyphicon glyphicon-lock"></i></span> <input id="password"
								type="password" class="form-control" name="password"
								placeholder="Senha">
						</div>

						<div class="form-group">
							<!-- Button -->
							<div class="col-sm-12 controls">
								<button type="submit" href="#"
									class="btn btn-primary pull-right">
									<i class="glyphicon glyphicon-log-in"></i> Login
								</button>
							</div>
						</div>

					</form>

				</div>
			</div>
		</div>
	</div>
</body>
</html>