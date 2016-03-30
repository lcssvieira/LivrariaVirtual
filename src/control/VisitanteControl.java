package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.VisitanteDAO;
import model.Visitante;
import utils.MuseuUtils;

public class VisitanteControl {
	VisitanteDAO dao = null;
	ArrayList<Visitante> visitantes = new ArrayList<>();

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean isAlterar = Boolean.parseBoolean(req.getParameter("alterar"));
		if (isAlterar)
			atualizar(req, res);
		Visitante visitante = carregaParametros(req);
		String paginaRetorno = "inicio.html";
		if (visitante != null) {
			if (dao == null)
				dao = new VisitanteDAO();
			boolean inserido = dao.inserir(visitante);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "visitantesConsulta.jsp";
			} else {
				paginaRetorno = "visitantes.jsp";
			}
		}
		listarVisitantes(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new VisitanteDAO();
			dao.deletar(id);
		}
		listarVisitantes(req, res);
	}

	public void listarVisitantes(HttpServletRequest req, HttpServletResponse res) throws Exception {
		visitantes.clear();
		if (dao == null)
			dao = new VisitanteDAO();
		visitantes = dao.carregaLista();
		req.setAttribute("listaVisitantes", visitantes);
		req.getRequestDispatcher("visitantesConsulta.jsp").forward(req, res);
	}

	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Visitante visitante = carregaParametros(req);
		visitante.setId(Long.parseLong(req.getParameter("id")));
		String paginaRetorno = "inicio.html";
		if (visitante != null) {
			if (dao == null)
				dao = new VisitanteDAO();
			boolean inserido = dao.atualizar(visitante);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "visitantesConsulta.jsp";
			} else {
				paginaRetorno = "visitantes.jsp";
			}
		}
		listarVisitantes(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new VisitanteDAO();
			Visitante visitante = dao.selectByPk(id);
			if (visitante != null) {
				req.setAttribute("visitanteEditar", visitante);
				req.getRequestDispatcher("visitantes.jsp").forward(req, res);
			}
		}
	}

	private Visitante carregaParametros(HttpServletRequest req) {
		try {
			Visitante visitante = new Visitante();
			visitante.setCep(req.getParameter("cep"));
			visitante.setDataNascimento(MuseuUtils.converteStringEmData(req.getParameter("dataNascimento")));
			visitante.setNome(req.getParameter("nome"));
			visitante.setComplemento(req.getParameter("complemento"));
			visitante.setEndereco(req.getParameter("endereco"));
			visitante.setEscolaridade(req.getParameter("escolaridade"));
			visitante.setGenero(Integer.parseInt(req.getParameter("genero")));
			visitante.setNumero(req.getParameter("numero"));
			visitante.setNacionalidade(req.getParameter("nacionalidade"));
			visitante.setTransporte(req.getParameter("transporte"));
			return visitante;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
