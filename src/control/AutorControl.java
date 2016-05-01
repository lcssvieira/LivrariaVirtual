package control;

import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.AutorDAO;
import model.Autor;
import utils.MuseuUtils;

public class AutorControl {
	AutorDAO dao;
	ArrayList<Autor> autores;

	public AutorControl() {
		dao = new AutorDAO();
		autores = new ArrayList<>();
	}	
	private Autor loadParameters(HttpServletRequest req) throws ParseException {
		Autor autor = new Autor();
		autor.setNome(req.getParameter("nome"));
		autor.setLocalNascimento(req.getParameter("local_nascimento"));
		autor.setLocalFalecimento(req.getParameter("local_falecimento"));
		autor.setBiografia(req.getParameter("biografia"));
		autor.setDataNascimento(MuseuUtils.converteStringEmData(req.getParameter("data_nascimento")));
		autor.setDataFalecimento(MuseuUtils.converteStringEmData(req.getParameter("data_falecimento")));
		return autor;
	}
	public Autor selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Autor autor = loadParameters(req);
		boolean cadastrado = dao.inserir(autor);
		req.setAttribute("inserido", cadastrado);
		if (cadastrado){;
			listar(req, res);}
		else {
			req.setAttribute("autorEditar", autor);
			req.getRequestDispatcher("autor.jsp").forward(req, res);
		}
	}

	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Autor autor = loadParameters(req);
		autor.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.atualizar(autor);
		String pageReturn = "autor.jsp";
		if (alterado) {
			listar(req, res);
			return;
		}
		req.setAttribute("autorEditar", autor);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) 
			dao.deletar(id);	
		listar(req, res);
	}
	
	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		autores = dao.carregaLista();
		req.setAttribute("listaAutores", autores);
		req.getRequestDispatcher("autorConsulta.jsp").forward(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Autor autor = dao.selectByPk(id);
			if (autor != null) {
				req.setAttribute("autorEditar", autor);
				req.getRequestDispatcher("autor.jsp").forward(req, res);
			};
		}
	}
	
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
			Autor autor = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
			String json = new Gson().toJson(autor);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			res.getWriter().print(json);
			return json;
	}
}
