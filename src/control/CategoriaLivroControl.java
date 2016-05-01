package control;

import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.CategoriaLivroDAO;
import model.CategoriaLivro;

public class CategoriaLivroControl {
	CategoriaLivroDAO dao;
	ArrayList<CategoriaLivro>categorias;
	
	public CategoriaLivroControl() {
		dao = new CategoriaLivroDAO();
		categorias = new ArrayList<>();
	}
	
	// cria um  objeto com dados enviados por parametro
	private CategoriaLivro loadParameters(HttpServletRequest req) throws ParseException {
		CategoriaLivro categoria = new CategoriaLivro();
		categoria.setDescricao(req.getParameter("descricao"));
		return categoria;
	}
	
	// obtem o obejto a partir do seu ID
	public CategoriaLivro selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}

	// cadastra um novo objeto
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		CategoriaLivro categoria = loadParameters(req);
		boolean cadastrado = dao.inserir(categoria);
		req.setAttribute("adicionado", cadastrado);
		if (cadastrado)
			listar(req, res);
		else {
			req.setAttribute("exposicaoEditar", categoria);
			req.getRequestDispatcher("exposicao.jsp").forward(req, res);
		}
	}
	
	// atualiza um objeto
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		CategoriaLivro categoria = loadParameters(req);
		categoria.setId(Long.parseLong(req.getParameter("id")));
		boolean alterado = dao.atualizar(categoria);
		String pageReturn = "categoria.jsp";
		if (alterado) {
			listar(req, res);
			return;
		}
		req.setAttribute("categoriaEditar", categoria);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}
	
	// deleta um objeto
	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
			int id = Integer.parseInt(req.getParameter("id"));
			if (id != 0) 
				dao.deletar(id);
			
			listar(req, res);
		}
	
	// carrega lista de objetos cadastrados , a partir do banco de dados
	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		categorias = dao.carregaLista();
		req.setAttribute("listaCategorias", categorias);
		req.getRequestDispatcher("categoriaConsulta.jsp").forward(req, res);
	}
	
	// carrega o CRUD com dados de um objeto
	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			CategoriaLivro categoria = dao.selectByPk(id);
			if (categoria != null) {
				req.setAttribute("categoriaEditar", categoria);
				req.getRequestDispatcher("categoria.jsp").forward(req, res);
			}
		}
	}

	// obtem o objeto em formato Json
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		CategoriaLivro categoria = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
		String json = new Gson().toJson(categoria);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);
		return json;
	}
}
