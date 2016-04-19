package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.EditoraDAO;
import model.Editora;

public class EditoraControl {
	EditoraDAO dao;
	ArrayList<Editora> museus = new ArrayList<>();

	public EditoraControl() {
		dao = new EditoraDAO();
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Editora vo = loadParameters(req);
		boolean cadastrado = dao.cadastrar(vo);
		req.setAttribute("adicionado", cadastrado);
		String paginaRetorno = "museu.jsp";
		if (cadastrado)
			paginaRetorno = "museuConsulta.jsp";
		else
			req.setAttribute("museuEditar", vo);
		listarMuseus(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void listarMuseus(HttpServletRequest req, HttpServletResponse res) throws Exception {
		museus = dao.carregaLista();
		req.setAttribute("listaMuseus", museus);
		req.getRequestDispatcher("museuConsulta.jsp").forward(req, res);
	}

	private Editora loadParameters(HttpServletRequest req) {
		Editora vo = new Editora();

		vo.setNome(req.getParameter("nome"));
		vo.setNomeResponsavel(req.getParameter("nomeResponsavel"));
		vo.setFone(req.getParameter("fone"));
		vo.setFoneResponsavel(req.getParameter("foneResponsavel"));
		vo.setCep(req.getParameter("cep"));
		vo.setEndereco(req.getParameter("endereco"));
		vo.setNumero(req.getParameter("numero"));
		vo.setComplemento(req.getParameter("complemento"));
		vo.setEstado(req.getParameter("estado"));
		vo.setEmail(req.getParameter("email"));
		vo.setEmailResponsavel(req.getParameter("emailResponsavel"));

		return vo;
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		dao.deletar(Integer.parseInt(req.getParameter("id")));
		listarMuseus(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Editora museu = dao.selectByPk(id);
			if (museu != null) {
				req.setAttribute("museuEditar", museu);
				req.getRequestDispatcher("museu.jsp").forward(req, res);
			}
		}
	}

	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Editora vo = loadParameters(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.alterar(vo);
		String pageReturn = "museu.jsp";
		if (alterado) {
			listarMuseus(req, res);
			return;
		}
		req.setAttribute("museuEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}
}
