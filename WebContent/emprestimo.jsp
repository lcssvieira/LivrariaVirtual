<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Museu</title>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<!--[if lte IE 8]><script src="../assets/js/ie/html5shiv.js"></script><![endif]-->
<link rel="stylesheet" href="./assets/css/bootstrap.css" />
<link rel="stylesheet" href="./assets/css/main.css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
</head>
<body class="homepage">
	<div id="page-wrapper">

		<jsp:include page="menu.jsp" />

		<!-- Main -->
		<div id="main-wrapper">
			<div id="main" class="container">
				<div id="content">

					<!-- Post -->
					<article class="box post"> <header>

					<h3>Novo Empréstimo</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="EmprestimoControl" /> <input
							type="hidden" name="metodo" value="cadastrar" />
						<c:if test="${not empty emprestimoEditar}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${emprestimoEditar.id}"/>" />
						</c:if>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Museu: <select id="museu" name="museu"
								>
									<c:forEach items="${listaMuseus}" var="museu">
										<option value="${museu.id}">${museu.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="6u 12u(mobile)">
								Obra: <select id="obra" name="obra">
									<c:forEach items="${listaObras}" var="obra">
										<option value="${obra.id}">${obra.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data de início: <input name="dataInicio"
									placeholder="Data em dd/mm/aaaa" type="text"
									value="<c:out value="${emprestimoEditar.dataInicioFormatada}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Data fim: <input name="dataFim"
									placeholder="Data em dd/mm/aaaa" type="text"
									value="<c:out value="${emprestimoEditar.dataFimFormatada}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								Descrição:
								<textarea name="descricao" placeholder="Descrição"><c:out
										value="${emprestimoEditar.descricao}" /></textarea>
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								<button type="submit" class="form-button-submit button">Salvar</button>
							</div>
						</div>
					</form></article>

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
</body>
</html>