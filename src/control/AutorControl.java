package control;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.AutorDAO;
import dao.EditoraDAO;
import dao.LivroDAO;
import model.Autor;
import utils.MuseuUtils;

public class AutorControl {
	AutorDAO dao;
	Autor autor;

	public AutorControl() {
		dao = new AutorDAO();
		autor = new Autor();
	}

	public void selectCadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LivroDAO livroDAO = new LivroDAO();
		EditoraDAO museuDAO = new EditoraDAO();
		//req.setAttribute("listaObras", obraDAO.carregaListaEmprestimo(0));
		req.setAttribute("listaObras", livroDAO.carregaLista());
		req.setAttribute("listaMuseus", museuDAO.carregaLista());
		req.getRequestDispatcher("autor.jsp").forward(req, res);
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Autor vo = loadParameters(req);
		boolean cadastrado = dao.cadastrar(vo);
		req.setAttribute("inserido", cadastrado);
		if (cadastrado){
			new LivroDAO().atualizarEmprestado(vo.getObra(), 1);
			listarEmprestimos(req, res);}
		else {
			req.setAttribute("autorEditar", vo);
			req.getRequestDispatcher("autor.jsp").forward(req, res);
		}
	}

	private Autor loadParameters(HttpServletRequest req) throws ParseException {
		Autor vo = new Autor();
		vo.setMuseu(new EditoraDAO().selectByPk(Integer.parseInt(req.getParameter("museu"))));
		vo.setObra(new LivroDAO().selectByPk(Integer.parseInt(req.getParameter("obra"))));
		vo.setDataInicio(MuseuUtils.converteStringEmData(req.getParameter("dataInicio")));
		vo.setDataFim(MuseuUtils.converteStringEmData(req.getParameter("dataFim")));
		vo.setDescricao(req.getParameter("descricao"));
		return vo;
	}

	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Autor vo = loadParameters(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.alterar(vo);
		String pageReturn = "exposicao.jsp";
		if (alterado) {
			listarEmprestimos(req, res);
			return;
		}
		req.setAttribute("autorEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);

	}

	public void listarEmprestimos(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("listaAutores", dao.carregaLista());
		req.getRequestDispatcher("autorConsulta.jsp").forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		Autor emprestimo = dao.selectByPk(id);
		if (id != 0) {
			if (dao == null)
				dao = new AutorDAO();
			dao.deletar(id);
			new LivroDAO().atualizarEmprestado(emprestimo.getObra(), 0);
		}
		listarEmprestimos(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Autor emprestimo = dao.selectByPk(id);
			if (emprestimo != null) {
				req.setAttribute("autorEditar", emprestimo);
			}
			selectCadastrar(req, res);
		}
	}
}
