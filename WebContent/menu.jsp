<!-- Header -->
<div id="header-wrapper">
	<div id="header" class="container">

		<!-- Logo -->
		<h1 id="logo">
			<a href="./inicio.jsp">Livraria Online</a>
		</h1>

		<!-- Nav -->
		<nav id="nav">
			<ul>
				<li><a class="icon fa-home" href="./inicio.jsp"><span>Página Inicial</span></a></li>
					<li><a href="#" class="icon fa-pencil-square-o"><span>Cadastros</span></a>
					<ul>
						<li><a href="#">Categorias de Livros +</a>
							<ul>
								<li><a href="categoria.jsp">Cadastrar</a></li>
								<li><a href="ServletLivraria.do?classe=CategoriaLivroControl&metodo=listar">Consultar</a></li>
							</ul>
						</li>
						<li><a href="#">Autores +</a>
							<ul>
								<li><a href="autor.jsp">Cadastrar</a></li>
								<li><a href="ServletLivraria.do?classe=AutorControl&metodo=listar">Consultar</a></li>
							</ul>
						</li>
						<li><a href="#">Editoras +</a>
							<ul>
								<li><a href="editora.jsp">Cadastrar</a></li>
								<li><a href="ServletMuseu.do?classe=EditoraControl&metodo=listar">Consultar</a></li>
							</ul>
						</li>
						<li><a href="#">Livros +</a>
							<ul>
								<li><a href="livro.jsp">Cadastrar</a></li>
								<li><a href="ServletMuseu.do?classe=LivroControl&metodo=listar">Consultar</a></li>
							</ul>
						</li>
					</ul></li>		
					<li><a class="icon fa-bar-chart-o"><span>Relatórios</span></a>
						 <ul>
							<li><a href="ServletMuseu.do?classe=VendaControl&metodo=listarDados">Vendas</a></li>
							<li><a href="ServletMuseu.do?classe=EmprestimoControl&metodo=selectCadastrar">Estoque</a></li>
						</ul>
					</li>
					<li><a class="icon fa-user"><span>Usuários</span></a>
						 <ul>
							<li><a href="usuario.jsp">Cadastrar</a></li>
							<li><a href="ServletMuseu.do?classe=UsuarioControl&metodo=listar">Consultar</a></li>
						</ul>
					</li>
			</ul>
		</nav>

	</div>
</div>