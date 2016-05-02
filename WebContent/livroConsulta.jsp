<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Livros</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="./assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="./assets/css/bootstrap.css" />
<link rel="stylesheet" href="./assets/css/main.css" />
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body class="homepage">
	<div id="page-wrapper">

		<jsp:include page="menu.jsp" />

		<!-- Main -->
		<div id="main-wrapper">
			<div id="main" class="container">
				<div id="content">
					<c:if test="${not empty adicionado}">
						<div class="alert alert-success" role="alert">Dados salvos
							com sucesso.</div>
					</c:if>

					<!-- Post -->
					<h3>Consulta de Livros</h3>
					<table class="table table-condensed table-hover  table-striped">
						<thead>
							<tr>
								<th>ID</th>
								<th>Nome</th>
								<th>Artista</th>
								<th>Tipo</th>
								<th>Data</th>
								<th colspan="2">Ações</th>
							</tr>
						</thead>
						<tbody>
							<!-- Loop para popular tabela de obras -->
							<c:forEach items="${requestScope.listaObras}" var="obra">
								<tr>
									<td><c:out value="${obra.id}"></c:out></td>
									<td><c:out value="${obra.nome}"></c:out></td>
									<td><c:out value="${obra.artista}"></c:out></td>
									<td><c:out value="${obra.tipo}"></c:out></td>
									<td><c:out value="${obra.dataFormatada}"></c:out></td>
									<td><a href="#"
										onclick="editar('${obra.id}','ObrasControl');return false;">Editar</a></td>
									<td><a href="#"
										onclick="deletar('${obra.id}','ObrasControl');return false;">Excluir</a></td>
								</tr>
							</c:forEach>
						</tbody>
						<tfoot></tfoot>
					</table>

				</div>
			</div>
		</div>
	</div>
	<!-- Scripts -->
	<script src="./assets/js/jquery.min.js"></script>
	<script src="./assets/js/jquery.dropotron.min.js"></script>
	<script src="./assets/js/skel.min.js"></script>
	<script src="./assets/js/skel-viewport.min.js"></script>
	<script src="./assets/js/util.js"></script>
	<!--[if lte IE 8]><script src="assets/js/ie/respond.min.js"></script><![endif]-->
	<script src="./assets/js/main.js"></script>
	<script src="./assets/js/bootstrap.min.js"></script>
	<script src="./assets/js/functions.js"></script>

</body>
</html>