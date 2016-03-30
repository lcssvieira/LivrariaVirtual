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

					<h3>Manutenção de Obras</h3>
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
									<option value="Dança" ${obraEditar.tipo == "Dança" ? 'selected' : ''}>Dança</option>
									<option value="Música" ${obraEditar.tipo == "Música" ? 'selected' : ''}>Música</option>
									<option value="Literatura" ${obraEditar.tipo == "Literatura" ? 'selected' : ''}>Literatura</option>
									<option value="Cinema" ${obraEditar.tipo == "Cinema" ? 'selected' : ''}>Cinema</option>
									<option value="Televisão" ${obraEditar.tipo == "Televisão" ? 'selected' : ''}>Televisão</option>
									<option value="Banda desenhada" ${obraEditar.tipo == "Banda desenhada" ? 'selected' : ''}>Banda desenhada</option>
									<option value="Jogos de Vídeo ou modelismo ferroviário" ${obraEditar.tipo == "Jogos de Vídeo ou modelismo ferroviário" ? 'selected' : ''}>Jogos de Vídeo ou modelismo ferroviário</option>
									<option value="Multimédia/multimídia ou arte digital" ${obraEditar.tipo == "Multimédia/multimídia ou arte digital" ? 'selected' : ''}>Multimédia/multimídia ou arte digital</option>
									</select>
							</div>
								<div class="6u 12u(mobile)">
								Período artístico: <select name="periodo"   value="<c:out value="${obraEditar.periodo}"/>" >
								<option value="">Selecione um período</option>
								<option value="Arte da antiguidade" ${obraEditar.periodo == "Arte da antiguidade" ? 'selected' : ''}>Arte da antiguidade</option>
								<option value="Arte da Mesopotâmia" ${obraEditar.periodo == "Arte da Mesopotâmia" ? 'selected' : ''}>Arte da Mesopotâmia</option>
								<option value="Arte do vale do Nilo" ${obraEditar.periodo == "Arte do vale do Nilo" ? 'selected' : ''}>Arte do vale do Nilo </option>
								<option value="Arte celta" ${obraEditar.periodo == "Arte celta" ? 'selected' : ''}>Arte celta</option>
								<option value="Arte germânica" ${obraEditar.periodo == "Arte germânica" ? 'selected' : ''}>Arte germânica</option>
								<option value="Arte egéia" ${obraEditar.periodo == "Arte egéia" ? 'selected' : ''}>Arte egéia</option>
								<option value="Arte fenícia" ${obraEditar.periodo == "Arte fenícia" ? 'selected' : ''}>Arte fenícia</option>
								<option value="Arte da Antiguidade Clássica" ${obraEditar.periodo == "Arte da Antiguidade Clássica" ? 'selected' : ''}>Arte da Antiguidade Clássica</option>
								<option value="Arte do cristianismo" ${obraEditar.periodo == "Arte do cristianismo" ? 'selected' : ''}>Arte do cristianismo</option>
								<option value="Arte da Idade Média" ${obraEditar.periodo == "Arte da Idade Média" ? 'selected' : ''}>Arte da Idade Média</option>
								<option value="Arte do Renascimento à modernidade" ${obraEditar.periodo == "Arte do Renascimento à modernidade" ? 'selected' : ''}>Arte do Renascimento à modernidade</option>
								<option value="Arte contemporânea" ${obraEditar.periodo == "Arte contemporânea" ? 'selected' : ''}>Arte contemporânea</option>
								<option value="Arte na América" ${obraEditar.periodo == "Arte na América" ? 'selected' : ''}>Arte na América</option>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data de criação:
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
								Descrição:
								<textarea name="descricao" placeholder="Descrição"><c:out
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