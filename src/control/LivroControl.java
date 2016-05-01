package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.LivroDAO;
import model.Autor;
import model.CategoriaLivro;
import model.Editora;
import model.Livro;
import utils.MuseuUtils;

public class LivroControl {
	LivroDAO dao = null;
	ArrayList<Livro> livros;

	public LivroControl(){
		dao = new LivroDAO();
		livros = new ArrayList<>();
	}	
	private Livro loadParameters(HttpServletRequest req) {
		try {
			Livro livro = new Livro();
			
			EditoraControl editoraCtrl = new EditoraControl();
			CategoriaLivroControl categoriaCtrl = new CategoriaLivroControl();
			AutorControl autorCtrl = new AutorControl();
			
			// obtem editora
			Editora editora = editoraCtrl.selectById(Integer.parseInt(req.getParameter("EditoraId")));
			
			// obtem categorias
			String [] categoriasIds = req.getParameter("categorias").split(";");
			ArrayList<CategoriaLivro> categorias = new ArrayList<>();
			for (String id : categoriasIds) {
				CategoriaLivro categoria = categoriaCtrl.selectById(Integer.parseInt(id));
				categorias.add(categoria);
			}
			
			// obtem autores
			String [] autoresIds = req.getParameter("autores").split(";");
			ArrayList<Autor> autores = new ArrayList<>();
			for (String id : autoresIds) {
				Autor autor = autorCtrl.selectById(Integer.parseInt(id));
				autores.add(autor);
			}
			
			livro.setEditora(editora);
			livro.setCategorias(categorias);
			livro.setAutores(autores);		
			livro.setIsbn(req.getParameter("isbn"));
			livro.setTitulo(req.getParameter("titulo"));
			livro.setFormato(req.getParameter("formato"));			
			livro.setSumario(req.getParameter("sumario"));
			livro.setResumo(req.getParameter("resumo"));
			livro.setDataPublicacao(MuseuUtils.converteStringEmData(req.getParameter("data_publicacao")));
			livro.setQuantidadeEstoque(Integer.parseInt(req.getParameter("quantidade_estoque")));
			livro.setEstoqueMinimo(Integer.parseInt(req.getParameter("estoque_minimo")));
			livro.setNumeroPaginas(Integer.parseInt(req.getParameter("numero_paginas")));
			Double precoCusto = MuseuUtils.converteMoneyTextEmDouble(req.getParameter("preco_custo"));
			Double precoVenda = MuseuUtils.converteMoneyTextEmDouble(req.getParameter("preco_venda"));
			Double margemLucro = MuseuUtils.converteMoneyTextEmDouble(req.getParameter("margem_lucro"));
			livro.setPrecoCusto(precoCusto);
			livro.setPrecoVenda(precoVenda);
			livro.setMargemLucro(margemLucro);
		
			return livro;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Livro selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		ArrayList<String> msgValidacao= new ArrayList<>();
		boolean validacao = this.validar(req, msgValidacao);
		String paginaRetorno = "livro.jsp";
		
		if (validacao) {
			Livro obra = loadParameters(req);
			if (dao == null)
				dao = new LivroDAO();
			boolean inserido = dao.inserir(obra);
			req.setAttribute("adicionado", inserido);
			if (inserido)
				paginaRetorno = "livroConsulta.jsp";
				
			listar(req, res);
			req.getRequestDispatcher(paginaRetorno).forward(req, res);
		}
		else{
			req.setAttribute("retornoValidacao", msgValidacao);
			req.getRequestDispatcher(paginaRetorno).forward(req, res);
		}
		
	}
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Livro livro = loadParameters(req);
		livro.setId(Long.parseLong(req.getParameter("id")));
		String paginaRetorno = "livro.jsp";
		if (livro != null) {
			if (dao == null)
				dao = new LivroDAO();
			boolean inserido = dao.atualizar(livro);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "livroConsulta.jsp";
			} else {
				paginaRetorno = "livro.jsp";
			}
		}
		listar(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0)
			dao.deletar(id);	
		listar(req, res);
	}

	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		livros = dao.carregaLista();
		req.setAttribute("listaLivros", livros);
		req.getRequestDispatcher("livroConsulta.jsp").forward(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Livro livro = dao.selectByPk(id);
			if (livro != null) {
				req.setAttribute("livroEditar", livro);
				req.getRequestDispatcher("livro.jsp").forward(req, res);
			}
		}
	}
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		Livro livro = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
		String json = new Gson().toJson(livro);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);
		return json;
	}
	
	public boolean validar(HttpServletRequest req, ArrayList<String> outPut){
		
		if (req.getParameter("nome")== null || req.getParameter("nome").trim().length() == 0)
			outPut.add("O campo 'Nome' não pode estar vazio.");
		
		if (req.getParameter("artista") == null|| req.getParameter("artista").trim().length() == 0)
			outPut.add("O campo 'Artista' é obrigatório.");
		
		if (req.getParameter("valorEstimado") != null && req.getParameter("valorEstimado") !=""){
			try{
				Double.parseDouble(req.getParameter("valorEstimado"));
			}
			catch (Exception e){
				outPut.add("O campo 'Valor Estimado' não possui valor numérico válid.o");
			}
		}		
		if (outPut.isEmpty()) 
			return true;
		else
			return false;
	}
}
