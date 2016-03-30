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

					<h3>Manuten��o de Obras</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="ObrasControl" /> <input
							type="hidden" name="metodo" value="cadastrar" />
						<c:if test="${not empty obraEditar}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${obraEditar.id}"/>" />
						</c:if>
						<c:if test="${not empty msgValidacao}">
						<div class="alert alert-danger" role="alert">
							<script>alert("test")</script>
						</div>
						</c:if>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Nome da Obra: <input name="nome" placeholder="Nome da Obra"
									type="text" value="<c:out value="${obraEditar.nome}"/>" />
							</div>
							<div class="6u 12u(mobile)">
								Artista: <input name="artista" placeholder="Artista" type="text"
									value="<c:out value="${obraEditar.artista}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Tipo: <select name="tipo" 
									value="<c:out value="${obraEditar.tipo}"/>">
									<option value="">Selecione um tipo</option>
									<option value="Pintura" ${obraEditar.tipo == "Pintura" ? 'selected' : ''} > Pintura</option>
									<option value="Escultura" ${obraEditar.tipo == "Escultura" ? 'selected' : ''}>Escultura</option>
									<option value="Arquitectura" ${obraEditar.tipo == "Arquitectura" ? 'selected' : ''}>Arquitectura</option>
									<option value="Dan�a" ${obraEditar.tipo == "Dan�a" ? 'selected' : ''}>Dan�a</option>
									<option value="M�sica" ${obraEditar.tipo == "M�sica" ? 'selected' : ''}>M�sica</option>
									<option value="Literatura" ${obraEditar.tipo == "Literatura" ? 'selected' : ''}>Literatura</option>
									<option value="Cinema" ${obraEditar.tipo == "Cinema" ? 'selected' : ''}>Cinema</option>
									<option value="Televis�o" ${obraEditar.tipo == "Televis�o" ? 'selected' : ''}>Televis�o</option>
									<option value="Banda desenhada" ${obraEditar.tipo == "Banda desenhada" ? 'selected' : ''}>Banda desenhada</option>
									<option value="Jogos de V�deo ou modelismo ferrovi�rio" ${obraEditar.tipo == "Jogos de V�deo ou modelismo ferrovi�rio" ? 'selected' : ''}>Jogos de V�deo ou modelismo ferrovi�rio</option>
									<option value="Multim�dia/multim�dia ou arte digital" ${obraEditar.tipo == "Multim�dia/multim�dia ou arte digital" ? 'selected' : ''}>Multim�dia/multim�dia ou arte digital</option>
									</select>
							</div>
								<div class="6u 12u(mobile)">
								Per�odo art�stico: <select name="periodo"   value="<c:out value="${obraEditar.periodo}"/>" >
								<option value="">Selecione um per�odo</option>
								<option value="Arte da antiguidade" ${obraEditar.periodo == "Arte da antiguidade" ? 'selected' : ''}>Arte da antiguidade</option>
								<option value="Arte da Mesopot�mia" ${obraEditar.periodo == "Arte da Mesopot�mia" ? 'selected' : ''}>Arte da Mesopot�mia</option>
								<option value="Arte do vale do Nilo" ${obraEditar.periodo == "Arte do vale do Nilo" ? 'selected' : ''}>Arte do vale do Nilo </option>
								<option value="Arte celta" ${obraEditar.periodo == "Arte celta" ? 'selected' : ''}>Arte celta</option>
								<option value="Arte germ�nica" ${obraEditar.periodo == "Arte germ�nica" ? 'selected' : ''}>Arte germ�nica</option>
								<option value="Arte eg�ia" ${obraEditar.periodo == "Arte eg�ia" ? 'selected' : ''}>Arte eg�ia</option>
								<option value="Arte fen�cia" ${obraEditar.periodo == "Arte fen�cia" ? 'selected' : ''}>Arte fen�cia</option>
								<option value="Arte da Antiguidade Cl�ssica" ${obraEditar.periodo == "Arte da Antiguidade Cl�ssica" ? 'selected' : ''}>Arte da Antiguidade Cl�ssica</option>
								<option value="Arte do cristianismo" ${obraEditar.periodo == "Arte do cristianismo" ? 'selected' : ''}>Arte do cristianismo</option>
								<option value="Arte da Idade M�dia" ${obraEditar.periodo == "Arte da Idade M�dia" ? 'selected' : ''}>Arte da Idade M�dia</option>
								<option value="Arte do Renascimento � modernidade" ${obraEditar.periodo == "Arte do Renascimento � modernidade" ? 'selected' : ''}>Arte do Renascimento � modernidade</option>
								<option value="Arte contempor�nea" ${obraEditar.periodo == "Arte contempor�nea" ? 'selected' : ''}>Arte contempor�nea</option>
								<option value="Arte na Am�rica" ${obraEditar.periodo == "Arte na Am�rica" ? 'selected' : ''}>Arte na Am�rica</option>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data de cria��o:
								<div class="input-group date">
									<input type="text" class="form-control"
										style="padding: 1.75em 1em 1.75em 1em;"
										placeholder="dd/mm/aaaa" name="data" value="<c:out value="${obraEditar.dataFormatada}"/>"><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
							<div class="6u 12u(mobile)">
								Valor Estimado: <input name="valorEstimado"
									placeholder="Valor Estimado" type="text"
									value="<c:out value="${obraEditar.valorEstimado}"/>" />
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Descri��o:
								<textarea name="descricao" placeholder="Descri��o"><c:out
										value="${obraEditar.descricao}" /></textarea>
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