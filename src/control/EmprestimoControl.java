package control;

import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EmprestimoDAO;
import dao.MuseuDAO;
import dao.ObraDAO;
import model.Emprestimo;
import utils.MuseuUtils;

public class EmprestimoControl {
	EmprestimoDAO dao;
	Emprestimo emprestimo;

	public EmprestimoControl() {
		dao = new EmprestimoDAO();
		emprestimo = new Emprestimo();
	}

	public void selectCadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("listaObras", new ObraDAO().carregaListaEmprestimo(0));
		req.setAttribute("listaMuseus", new MuseuDAO().carregaLista());
		req.getRequestDispatcher("emprestimo.jsp").forward(req, res);
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Emprestimo vo = loadParameters(req);
		boolean cadastrado = dao.cadastrar(vo);
		req.setAttribute("inserido", cadastrado);
		if (cadastrado){
			new ObraDAO().atualizarEmprestado(vo.getObra(), 1);
			listarEmprestimos(req, res);}
		else {
			req.setAttribute("emprestimoEditar", vo);
			req.getRequestDispatcher("emprestimo.jsp").forward(req, res);
		}
	}

	private Emprestimo loadParameters(HttpServletRequest req) throws ParseException {
		Emprestimo vo = new Emprestimo();
		vo.setMuseu(new MuseuDAO().selectByPk(Integer.parseInt(req.getParameter("museu"))));
		vo.setObra(new ObraDAO().selectByPk(Integer.parseInt(req.getParameter("obra"))));
		vo.setDataInicio(MuseuUtils.converteStringEmData(req.getParameter("dataInicio")));
		vo.setDataFim(MuseuUtils.converteStringEmData(req.getParameter("dataFim")));
		vo.setDescricao(req.getParameter("descricao"));
		return vo;
	}

	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Emprestimo vo = loadParameters(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.alterar(vo);
		String pageReturn = "exposicao.jsp";
		if (alterado) {
			listarEmprestimos(req, res);
			return;
		}
		req.setAttribute("emprestimoEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);

	}

	public void listarEmprestimos(HttpServletRequest req, HttpServletResponse res) throws Exception {
		req.setAttribute("listaEmprestimos", dao.carregaLista());
		req.getRequestDispatcher("emprestimoConsulta.jsp").forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		Emprestimo emprestimo = dao.selectByPk(id);
		if (id != 0) {
			if (dao == null)
				dao = new EmprestimoDAO();
			dao.deletar(id);
			new ObraDAO().atualizarEmprestado(emprestimo.getObra(), 0);
		}
		listarEmprestimos(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Emprestimo emprestimo = dao.selectByPk(id);
			if (emprestimo != null) {
				req.setAttribute("emprestimoEditar", emprestimo);
			}
			selectCadastrar(req, res);
		}
	}
}
