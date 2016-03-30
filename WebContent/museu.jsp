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
<link rel="stylesheet" href="./assets/css/bootstrap-datepicker3.min.css" />
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

					<h3>Manutenção de Museu</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="MuseuControl" /> <input
							type="hidden" name="metodo" value="cadastrar" />
						<c:if test="${not empty museuEditar}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${museuEditar.id}"/>" />
						</c:if>
						<div class="row 50%">
							<div class="12u">
								Nome: <input name="nome" placeholder="Nome" type="text"
									value="<c:out value="${museuEditar.nome}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								Nome do responsável: <input name="nomeResponsavel"
									placeholder="Nome do Responsável pelo Museu" type="text"
									value="<c:out value="${museuEditar.nomeResponsavel}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Fone Museu: <input name="fone" placeholder="Telefone do Museu"
									type="text" value="<c:out value="${museuEditar.fone}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Fone Responsável: <input name="foneResponsavel"
									placeholder="Telefone do Responsável pelo Museu" type="text"
									value="<c:out value="${museuEditar.foneResponsavel}"/>" />
							</div>
						</div>

						<div class="row 50%">
							<div class="12u">
								CEP: <input name="cep" placeholder="CEP" type="text"
									value="<c:out value="${museuEditar.cep}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								Endereço: <input name="endereco" placeholder="Endereço"
									type="text" value="<c:out value="${museuEditar.endereco}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Número: <input name="numero" placeholder="Número" type="text"
									value="<c:out value="${museuEditar.numero}"/>" />
							</div>

							<div class="6u 12u(mobile)">
								Complemento: <input name="complemento" placeholder="Complemento"
									type="text" value="<c:out value="${museuEditar.complemento}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Estado: <select name="estado" value="${museuEditar.estado}"
									selected>
									<option value="SP">São Paulo</option>
									<option value="RJ">Rio de Janeiro</option>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								E-mail Museu: <input name="email" placeholder="E-mail do Museu"
									type="text" value="<c:out value="${museuEditar.email}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								E-mail Museu: <input name="emailResponsavel"
									placeholder="E-mail do Responsável pelo Museu" type="text"
									value="<c:out value="${museuEditar.emailResponsavel}"/>" />
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
	<script src="./assets/js/bootstrap-datepicker.min.js"></script>
	<script src="./assets/js/locales/bootstrap-datepicker.pt-BR.min.js"></script>
	<script type="text/javascript">
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy",
			language : "pt-BR",
			todayHighlight : true
		});
	</script>
</body>
</html>