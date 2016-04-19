<!-- Header -->
<div id="header-wrapper">
	<div id="header" class="container">

		<!-- Logo -->
		<h1 id="logo">
			<a href="./inicio.html">Museu</a>
		</h1>

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a class="icon fa-home" href="./inicio.jsp"><span>Página
							Inicial</span></a></li>
							
						<li><a class="fa fa-shopping-cart"><span>Vendas</span></a>
					 <ul>
						<li><a href="ServletMuseu.do?classe=VendaControl&metodo=listarDados">Nova venda</a></li>
						<!--<li><a
							href="ServletMuseu.do?classe=VendaControl&metodo=listarVendas">Histórico de vendas</a></li>-->
					</ul></li>
					
				<li><a href="#" class="icon fa-bar-chart-o"><span>Cadastros</span></a>
					<ul>

						<li><a href="#">Obras +</a>
							<ul>
								<li><a href="obras.jsp">Cadastrar</a></li>
								<li><a
									href="ServletMuseu.do?classe=ObrasControl&metodo=listarObras">Consulta</a></li>
							</ul></li>

						<li><a href="#">Visitante +</a>
							<ul>
								<li><a href="visitantes.jsp">Cadastrar</a></li>
								<li><a
									href="ServletMuseu.do?classe=VisitanteControl&metodo=listarVisitantes">Consulta</a></li>
							</ul></li>
					</ul></li>

				<li><a class="icon fa-cog" href="#"><span>Empréstimos</span></a>
							<ul>
						<li><a href="ServletMuseu.do?classe=EmprestimoControl&metodo=selectCadastrar">Cadastrar</a></li>
						<li><a
							href="ServletMuseu.do?classe=EmprestimoControl&metodo=listarEmprestimos">Consultar</a></li>
					</ul></li>

				<li><a class="fa fa-paint-brush"><span>Exposições</span></a>
					<ul>
						<li><a href="ServletMuseu.do?classe=ExposicaoControl&metodo=listarDados">Cadastrar</a></li>
						<li><a
							href="ServletMuseu.do?classe=ExposicaoControl&metodo=listarExposicoes">Consultar</a></li>
					</ul></li>

				<li><a class="fa fa-university icon"><span>Museus</span></a>
					<ul>
						<li><a href="museu.jsp">Cadastrar</a></li>
						<li><a
							href="ServletMuseu.do?classe=MuseuControl&metodo=listarMuseus">Consultar</a></li>
					</ul></li>
			</ul>
		</nav>

	</div>
</div>