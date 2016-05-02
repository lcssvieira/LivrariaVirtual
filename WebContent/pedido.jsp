<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Pedidosssssssssssssssssssssss</title>
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
										<option id="cliente_item" value="${cliente.id}" >${cliente.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Exposição: <select id="exposicaoSelecionada" name="exposicaoSelecionada">
									<option value="">Selecione uma exposição</option>
									<c:forEach items="${listaExposicoes}" var="exposicao">
										<option value="${exposicao.id}" diasGratuitos = "${exposicao.diasGratuitos}" valor_ingresso="${exposicao.valor_ingresso}"  id="exposicao_item">${exposicao.nome}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								Data da visita:
								<div class="input-group date">
									<input id="data_visita" type="text" class="form-control"
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
								<select id="descontoSelecionado" name="desconto">
								<option value="">Selecione um tipo de desconto</option>
								<option value="0" id="desconto_item">Estudante</option>
								<option value="1" id="desconto_item">Idade</option>
								</select>
							</div>
							<div class="12u">
								<div class="12u"style="padding-top:0.7cm;">
								<div style="color:darkblue" href=#tableIngressos onclick ="adicionarIngressoNaVenda();" type="submit" class="form-button-submit button">(+)adicionar</div>
								</div>
							</div>
						</div>
						<h3 style="margin-top: 2cm">Itens da Venda</h3>
						<div class="row 50%">
							<div class="12u">
								<table class="table table-striped table-hover table-condensed"
									name="tableIngressos" id="tableIngressos">
									<thead>
										<tr>
											<th style="display:none">ID</th>
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
											<tr class ="item_venda">
												<td style="display:none"><c:out value="${ingresso.getId()}"></c:out></td>
												<td><c:out value="${ingresso.getVisitante().getNome()}"></c:out></td>
												<td><c:out value="${ingresso.getExposicao().getNome()}"></c:out></td>
												<td><c:out value="${ingresso.getDataFormatada()}"></c:out></td>
												<td><c:out value="${ingresso.getTipoDesconto()}"></c:out></td>
												<td><c:out value="${ingresso.getValor()}"></c:out></td>
												<td><a href="#" onclick="removerItem'${ingresso.getId()}');">Excluir</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
						<div class="row 50%">
							<div class="6u 12u(mobile)">
								<h3 
								style="
								margin-top: 2px;
								font-weight: bold;" 
								class="valorTotalTitle">Valor total: <span style="color:red">R$ 0,00</span></h3>
							</div>
						</div>
						<div class="row 50%">
	
							<div class="6u 12u">
								Forma de Pagamento:
								<select id="formaPagamentoSelecionada" name="formaPagamento">
								<option value="">Selecione uma forma de pagamento</option>
								<option value="0" id="forma_pagto">Em espécie</option>
								<option value="1" id="forma_pagto">Cartão de débito</option>
								<option value="2" id="forma_pagto">Cartão de crédito</option>
								</select>
							</div>
						</div>
						
						
					</form>
					<div class="row 50%">
						<br/>
							<div class="12u">
								<button onclick="salvaVenda()" type="submit" class="form-button-submit button">Salvar</button>
							</div>
						</div>
						</article>

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
		
		
		function salvaVenda(){
			if (validaVenda()){
				alert("VENDA SALVA COM SUCESSO! Os ingressos serão impressos em alguns instantes.")
				window.location.href="inicio.jsp";
			}
			
		}
		
		function validaVenda(){
			// forma de pagamento
			var fmPagamento_selectBox = document.getElementById("formaPagamentoSelecionada");
			var formasPagamento = $(fmPagamento_selectBox).children();
			var formaPagamentoId = fmPagamento_selectBox.options[fmPagamento_selectBox.selectedIndex].value
			
			var valorTotal = converterMoneyTextEmDouble($(".valorTotalTitle").text().replace("Valor total: ",""));
			
			
			if(formaPagamentoId === "" && valorTotal >0){
				alert("ATENÇÃO: A FORMA DE PAGAMENTO NÃO FOI PREENCHIDA")
				return false;
			}
				
			
			var item_venda = $(".item_venda");
			if (item_venda.length < 1){
				alert("ATENÇÃO: A VENDA NÃO POSSUI ITENS");
				return false;
			}
				
			return true;
			
			
		}
		
		function adicionarIngressoNaVenda(){
			// obtem id da exposicao selecionada
			var exp_selectBox = document.getElementById("exposicaoSelecionada");
			var exposicoes = $(exp_selectBox).children();
			var exposicaoId = exp_selectBox.options[exp_selectBox.selectedIndex].value;	
			
			var cli_selectBox = document.getElementById("clienteSelecionado");
			var clientes =$(cli_selectBox).children();	
			var clienteId = cli_selectBox.options[cli_selectBox.selectedIndex].value;	
			
			var clienteNome =obtemClienteSelecionado(clienteId, clientes);
			var exposicaoNome =obtemExposicaoSelecionada(exposicaoId, exposicoes);
			var diasGratuitos =obtemDiasGratuitos(exposicaoId,exposicoes);
			var valorIngresso = obtemValorIngresso(exposicaoId, exposicoes);
			var dataVisita = $("#data_visita").val();
			var tipoDesconto = obtemDescontoSelecionado();
			var ingressoId = obtemId(clienteId,exposicaoId);
			
			if( clienteNome!=undefined && exposicaoNome!=undefined 
					&& diasGratuitos!=undefined && valorIngresso !=undefined
					&& dataVisita!=undefined && tipoDesconto!=undefined
					&& clienteNome!="Selecione um cliente" 
					&& exposicaoNome !="Selecione uma exposição" 
					&& dataVisita != null && dataVisita!=""){
				
				valorIngresso = calculaValorIngresso(valorIngresso, dataVisita, diasGratuitos, tipoDesconto)
				valorIngresso = converterEmMoneyText(valorIngresso);
				if (tipoDesconto.trim() == "Selecione um tipo de desconto"){
					tipoDesconto="";
				}
			
				
				if (naoAdicionado(ingressoId)){
					var html = "";
					html += '<tr class="item_venda">'
							+ '<td class="item_id" style="display:none">' + ingressoId + '</td>'
							+ '<td>' + clienteNome + '</td>'
							+ '<td>' + exposicaoNome + '</td>' 
							+ '<td>' + dataVisita + '</td>' 
							+ '<td>' + tipoDesconto + '</td>'
							+ '<td class="item_valor">' + valorIngresso + '</td>'
							+ '<td><a style="color:darkred;" href=#tableIngressos onclick=removerItem('+ingressoId+')>Remover da lista</a>'+'</td>'
							+ '</tr>';
					$("#tableIngressos").append(html);
					
					atualizaTotal()
					limpaCampos();
				}
			}	
		}
		
		function atualizaTotal(){
			var total = 0;
			var valor = $(".item_valor");
			if (valor != null && valor !=undefined)
			{
				for (var i=0;i<valor.length;i++){
					var item = valor[i];
					total+=converterMoneyTextEmDouble($(item).text());		
				}
			}
			$(".valorTotalTitle").html("");
			$(".valorTotalTitle").append("Valor total: <span style='color:red'>" + converterEmMoneyText(total)+"</span>");
		}
		
		function naoAdicionado(ingressoId){
			
			var id = $(".item_id");
			if (id === null || id ===undefined)
				return true;
			else
			{
				for (var i=0;i<id.length;i++){
					var item = id[i];
					if($(item).text() === ingressoId)
						return false;
				}
			}
			return true;
		}
	
		function obtemId(clienteId, exposicaoId){
			return clienteId.toString() + exposicaoId.toString();
		}
		
		function removerItem(ingressoId){
			var id = $(".item_id");
			if (id != null && id !=undefined)
			{
				for (var i=0;i<id.length;i++){
					var item = id[i];
					if($(item).text() == ingressoId){
						var parent = $(item).parent();
						$(parent).remove();
					}
						
				}
			}
			atualizaTotal();
		}
		
		function limpaCampos(){
			
		}
		
		function calculaValorIngresso(valorIngresso, dataVisita, diasGratuitos, tipoDesconto){
			
			if (diasGratuitos.length >0){
				for (i=0;i<diasGratuitos.length;i++){
					var diaGratuito = diasGratuitos[i];
					if (converteEmDiaDaSemana(dataVisita) == diaGratuito){
						return 0;
					}
				
				}
			}
			
			if (tipoDesconto != "" && tipoDesconto!="Selecione um tipo de desconto"){
				
				if (tipoDesconto =="Idade")
					return converterMoneyTextEmDouble(valorIngresso) * 0.0;
				
				return converterMoneyTextEmDouble(valorIngresso) * 0.5;
			}
			
			return valorIngresso;
		}
		
		function converteEmDiaDaSemana(data){
			// conveter data em dia da semana; retornar o número que representa o dia da semana, sendo domingo = 1 e sabado = 7
			return 0;
		}
		
		function converterMoneyTextEmDouble(text){
			if (text != undefined && text.trim() != null){
				var cleanText = text.replace("R$ ","").replace(",",".");
				return parseFloat(cleanText);
			}
			return 0;
			
		}
		
		function converterEmMoneyText(valor){
			
			var text = (valor < 1 ?"0":"") + Math.floor(valor*100);
			text="R$ " + text;
			var result= text.substr(0,text.length-2) + "," + text.substr(-2);
			if (result ==="R$ ,00")
				result = "R$ 0,00";
			
			return result;
		}
		
		function obtemValorIngresso(id, exposicoes){
			for (var i =0; i < exposicoes.length; i++){
				if ($(exposicoes[i]).attr('value') === id)
					return $(exposicoes[i]).attr('valor_ingresso');
			}
		}
		
		function obtemDiasGratuitos(id, exposicoes){
			for (var i =0; i < exposicoes.length; i++){
				if ($(exposicoes[i]).attr('value') === id)
					return $(exposicoes[i]).attr('diasGratuitos');
			}
		}
		function obtemExposicaoSelecionada(id, exposicoes){
			for (var i =0; i < exposicoes.length; i++){
				if ($(exposicoes[i]).attr('value') === id)
					return exposicoes[i].text;
			}
		}
		function obtemDescontoSelecionado(){
			var selectBox = document.getElementById("descontoSelecionado");
			var descontos = $(selectBox).children();
			var selectedId = selectBox.options[selectBox.selectedIndex].value;	
			for (var i =0; i < descontos.length; i++){
				if ($(descontos[i]).attr('value') === selectedId)
					return descontos[i].text;
			}
		}
		function obtemClienteSelecionado(id, clientes){
			for (var i =0; i < clientes.length; i++){
				if ($(clientes[i]).attr('value') == id){
					return clientes[i].text;
				}
					
			}
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