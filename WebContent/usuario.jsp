<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Usuários</title>
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

					<h3>Cadastro de Usuário</h3>
					<c:if test="${not empty inserido}">
						<div class="alert alert-danger" role="alert">
							<strong>Erro!</strong>Ocorreu um erro ao inserir os dados.
							Insira-os novamente.
						</div>
					</c:if>
					<form method="POST" action="ServletMuseu.do">
						<input type="hidden" name="classe" value="ExposicaoControl" /> <input
							type="hidden" name="metodo" value="cadastrar" /> <input
							type="hidden" name="obrasId" id="obrasId" />
						<c:if test="${not empty exposicaoEditar.nome}">
							<input type="hidden" name="alterar" value="true" />
							<input type="hidden" name="id"
								value="<c:out value="${exposicaoEditar.id}"/>" />
						</c:if>
						<div class="row 50%">
							<div class="12u">
								Nome da exposição: <input name="nome"
									placeholder="Nome da exposição" type="text"
									value="<c:out value="${exposicaoEditar.nome}"/>" />
							</div>
						</div>
					
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Valor do Ingresso: <input name="valor_ingresso" id="valor_ingresso"
									placeholder="Valor do Ingresso" type="text"
									value="<c:out value="${exposicaoEditar.valorFormatado}"/>" />
							</div>
						</div>
						<div class="row 50%">
								<div class="6u 12u(mobile)">
								Museu: <select id="clienteSelecionado" name="museuSelecionado">
									<option value="">Selecione um museu</option>
									<c:forEach items="${listaMuseus}" var="museu">
		
									<option id="museu_item" value="${museu.id}" >${museu.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="row 50%">
							<div class="12u">
								Localização interna: <input name="secao"
									placeholder="Seção, Sala ou Ala do Museu" type="text"
									value="<c:out value="${exposicaoEditar.secao}"/>" />
							</div>
						</div>
						

						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Obra: <select id="obrasSelecionadas" name="obrasSelecionadas">
									<option value="">Selecione uma obra</option>
									<c:forEach items="${listaObras}" var="obra">
										<option value="${obra.id}">${obra.nome}</option>
									</c:forEach>
								</select>
							</div>
					
							<div class="6u 12u(mobile)">
								<div class="12u"style="padding-top:0.7cm;">
								<div onclick="adicionarObraNaExposicao();" type="submit" class="form-button-submit button">(+)adicionar</div>
								</div>
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								Descrição:
								<textarea name="descricao" placeholder="Descrição da exposição"><c:out
										value="${exposicaoEditar.descricao}" /></textarea>
							</div>
						</div>

						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data início:
									<div class="input-group date">
									<input id="dataInicio" type="text" class="form-control"
										style="padding: 1.75em 1em 1.75em 1em;"
										placeholder="dd/mm/aaaa" name="data"
										value="<c:out value="${exposicaoEditar.dataInicioFormatada}"/>"
										><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								</div>
								
							</div>
							<div class="6u 12u(mobile)">
								Data fim:
									<div class="input-group date">
									<input id="dataFim" type="text" class="form-control"
										style="padding: 1.75em 1em 1.75em 1em;"
										placeholder="dd/mm/aaaa" name="data"
										value="<c:out value="${exposicaoEditar.dataFimFormatada}"/>"
										><span
										class="input-group-addon"><i
										class="glyphicon glyphicon-th"></i></span>
								</div>
							</div>
						</div>
						<div class="row 50%">
							<div class="12u">
								<table class="table table-striped table-hover table-condensed"
									name="tableObras" id="tableObras">
									<thead>
										<tr id="item_obra">
											<th style="display:none">Id</th>
											<th>Nome da Obra</th>
											<th>Tipo da Obra</th>
											<th>Período Histórico</th>
											<th>Artista</th>
											<th>#</th>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${exposicaoEditar.getObras()}" var="obra">
											<tr id="item_obra">
												<td class="obra_id" style="display:none"><c:out value="${obra.getId()}"></c:out></td>
												<td><c:out value="${obra.nome}"></c:out></td>
												<td><c:out value="${obra.tipo}"></c:out></td>
												<td><c:out value="${obra.periodo}"></c:out></td>
												<td><c:out value="${obra.artista}"></c:out></td>
												<td><a style="color:darkred;" href=#tableObras onclick="excluir('${obra.id}');">Remover da lista</a></td>
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
	
		var obras = new Array();
		obras.push('${exposicaoEditar.listaIds}');
		$('.input-group.date').datepicker({
			format : "dd/mm/yyyy",
			language : "pt-BR",
			todayHighlight : true
		});
		function adicionarObraNaExposicao() {
			var selectBox = document.getElementById("obrasSelecionadas");
			var valor = selectBox.options[selectBox.selectedIndex].value;
			if (valor != "" && naoAdicionado(valor)){
				obras.push(valor);
				document.forms[0].obrasId.value = obras;
				$
						.post(
								"ServletMuseu.do?classe=ExposicaoControl&metodo=getObraById",
								{
									id : valor
								}, function(obra) {
									var html = "";
									html 	+='<tr>' 
											+ '<td class="obra_id" style="display:none">' + valor + '</td>'
											+ '<td>' + obra.nome + '</td>'
											+ '<td>' + obra.tipo + '</td>' 
											+ '<td>'+ obra.periodo + '</td>' 
											+ '<td>'+ obra.artista + '</td>' 
											+ '<td><a style="color:darkred;" href=#tableObras onclick=excluir('+obra.id+')>Remover da lista</a>'+'</td>'
											+ '</tr>';
									$("#tableObras").append(html);
								}, "json");
			}
			
		}
		

	function converterMoneyTextEmDouble(text){
			if (text != undefined && text.trim() != null){
				var cleanText = text.replace("R$ ","").replace(",",".");
				return parseFloat(cleanText);
			}
			return 0;
			
		}
	function naoAdicionado(obraId){
		
		var id = $(".obra_id");
		if (id === null || id ===undefined)
			return true;
		else
		{
			for (var i=0;i<id.length;i++){
				var item = id[i];
				if($(item).text() === obraId)
					return false;
			}
		}
		return true;
	}

	
	function excluir(id) {
			var id = $("#obra_id");
			if (id != null && id !=undefined)
			{	
				for (var i=0;i<id.length;i++){
					var item = id[i];
					if($(item).text() == id.text()){
						var parent = $(item).parent();
						$(parent).remove();
					}
						
				}
			}
		}
	</script>
	<script src="./assets/js/bootstrap-select.js"></script>
</body>
</html>