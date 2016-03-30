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
<link rel="stylesheet" href="./assets/css/bootstrap-select.css" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!--[if lte IE 8]><link rel="stylesheet" href="assets/css/ie8.css" /><![endif]-->
<style>
.dropdown-menu, .filter-option pull-left, .btn dropdown-toggle btn-default
	{
	color: gray !important;
}
</style>
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

					<h3>Manuntenção de Venda</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="VendaControl" /> <input
							type="hidden" name="metodo" value="cadastrar" /> <input
							type="hidden" name="ingressosId" id="ingressosId" />
						<c:if test="${not empty vendaEditar.data}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${vendaEditar.id}"/>" />
						</c:if>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Visitante: <select id="clienteSelecionado" name="clienteSelecionado">
									<option value="">Selecione um cliente</option>
									<c:forEach items="${listaClientes}" var="cliente">
										<option value="${cliente.id}">${cliente.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Exposição: <select id="exposicaoSelecionada" name="exposicaoSelecionada">
									<option value="">Selecione uma exposição</option>
									<c:forEach items="${listaExposicoes}" var="exposicao">
										<option value="${exposicao.id}">${exposicao.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data da visita:
								<div class="input-group date">
									<input type="text" class="form-control"
										style="padding: 1.75em 1em 1.75em 1em;"
										placeholder="dd/mm/aaaa" name="data"><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Desconto:
								<select id="desconto" name="desconto">
									<c:forEach items="${listaTiposDesconto}" var="desconto">
										<option value="${desconto.valor}">${desconto.toString()}</option>
									</c:forEach>
								</select>
							</div>
							<div class="12u">
								<a href=# onclick ="adicionarIngressoNaVenda();">(+) Adicionar na lista de itens da venda</a>
							</div>
						</div>
						<h3 style="margin-top: 2cm">Itens da Venda</h3>
						<div class="row 50%">
							<div class="12u">
								<table class="table table-striped table-hover table-condensed"
									name="tableIngressos" id="tableIngressos">
									<thead>
										<tr>
											<th>Nome do visitante</th>
											<th>Exposição</th>
											<th>Data da Visita</th>
											<th>Tipo de desconto</th>
											<th>Valor unitário</th>
											<th>#</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${vendaEditar.listaIngressos}" var="ingresso">
											<tr>
												<td><c:out value="${ingresso.getVisitante().getNome()}"></c:out></td>
												<td><c:out value="${ingresso.getExposicao().getNome()}"></c:out></td>
												<td><c:out value="${ingresso.getDataFormatada()}"></c:out></td>
												<td><c:out value="${ingresso.getTipoDesconto()}"></c:out></td>
												<td><c:out value="${ingresso.getValor()}"></c:out></td>
												<td><a href="#" onclick="excluir('${ingresso.getId()}');">Excluir</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
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
		var ingressos = new Array();
		ingressos.push('${vendaEditar.listaIds}');
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy",
			language : "pt-BR",
			todayHighlight : true
		});
		
		function adicionarIngressoNaVenda(){
			alert("TODO");
		}

		function excluir(id) {
			var index = ingressos.indexOf(id);
			if (index > -1) {
				ingressos.splice(index, 1);
			}
		}
	</script>
	<script src="./assets/js/bootstrap-select.js"></script>
</body>
</html>