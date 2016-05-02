<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Clientes</title>
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

					<h3>Manutenção de Visitantes</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="VisitanteControl" /> <input
							type="hidden" name="metodo" value="cadastrar" />
						<c:if test="${not empty visitanteEditar}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${visitanteEditar.id}"/>" />
						</c:if>
						<div class="row 50%">
							<div class="12u">
								Nome: <input name="nome" placeholder="Nome" type="text"
									value="<c:out value="${visitanteEditar.nome}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Meio de transporte: <input name="transporte"
									placeholder="Meio de Transporte utilizado" type="text"
									value="<c:out value="${visitanteEditar.transporte}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Escolaridade: <input name="escolaridade"
									placeholder="Escolaridade" type="text"
									value="<c:out value="${visitanteEditar.escolaridade}"/>" />
							</div>
						</div>

						<div class="row 50%">
							<div class="12u">
								Endereço: <input name="endereco" placeholder="Endereço"
									type="text"
									value="<c:out value="${visitanteEditar.endereco}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Número: <input name="numero" placeholder="Número" type="text"
									value="<c:out value="${visitanteEditar.numero}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Complemento: <input name="complemento" placeholder="Complemento"
									type="text"
									value="<c:out value="${visitanteEditar.complemento}"/>" />
							</div>
						</div>
						<div class="row 50%">
						<div class="6u 12u(mobile)">
								CEP: <input name="cep" placeholder="CEP" type="text"
									value="<c:out value="${visitanteEditar.cep}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Nacionalidade: <input name="nacionalidade"
									placeholder="Nacionalidade" type="text"
									value="<c:out value="${visitanteEditar.nacionalidade}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data de nascimento:
								<div class="input-group date">
									<input type="text" class="form-control"
										style="padding: 1.75em 1em 1.75em 1em;"
										placeholder="dd/mm/aaaa" name="dataNascimento"
										value="${visitanteEditar.dataFormatada}"><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>

							<div class="6u 12u(mobile)">
								Gênero: <select name="genero">
									<option value = "">Selecione um gênero</option>
									<option value="1" ${visitanteEditar.genero =="1" ? 'selected' : ''} >Feminino</option>
									<option value="2"  ${visitanteEditar.genero =="2" ? 'selected' : ''}>Masculino</option>
								</select>
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