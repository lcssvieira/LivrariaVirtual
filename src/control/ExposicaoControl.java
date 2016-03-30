package control;

import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ExposicaoDAO;
import dao.MuseuDAO;
import dao.ObraDAO;
import model.Exposicao;
import model.Museu;
import model.Obra;
import utils.MuseuUtils;

public class ExposicaoControl {
	ExposicaoDAO exposicaoDao;
	ObraDAO obraDAO;
	ExposicaoDAO dao;
	ArrayList<Exposicao> exposicoes = new ArrayList<>();
	ArrayList<Obra> obras = new ArrayList<>();

	public ExposicaoControl() {
		exposicaoDao = new ExposicaoDAO();
		dao = new ExposicaoDAO();
		obraDAO = new ObraDAO();
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Exposicao vo = loadParameters(req);
		boolean cadastrado = dao.cadastrar(vo);
		req.setAttribute("adicionado", cadastrado);
		if (cadastrado)
			listarExposicoes(req, res);
		else {
			req.setAttribute("exposicaoEditar", vo);
			req.getRequestDispatcher("exposicao.jsp").forward(req, res);
		}
	}

	public void listarExposicoes(HttpServletRequest req, HttpServletResponse res) throws Exception {
		exposicoes = dao.carregaLista();
		req.setAttribute("listaExposicoes", exposicoes);
		req.getRequestDispatcher("exposicaoConsulta.jsp").forward(req, res);
	}

	private Exposicao loadParameters(HttpServletRequest req) throws ParseException {
		Exposicao exposicao = new Exposicao();
		MuseuDAO museuDAO = new MuseuDAO();
		ObraDAO obrasDAO = new ObraDAO();
		Museu museu = museuDAO.selectByPk(Integer.parseInt(req.getParameter("museu")));
		exposicao.setMuseu(museu);
		exposicao.setObras(obras);
		String listaObras = req.getParameter("obrasId");
		exposicao.setListaIds(listaObras);
		if (listaObras.startsWith(","))
			listaObras = listaObras.substring(1, listaObras.length());

		int[] ids = new int[listaObras.split(",").length];
		try {
			for (int i = 0; i < ids.length; i++)
				ids[i] = Integer.parseInt(listaObras.split(",")[i]);
		} catch (Exception e) {
			e.getMessage();
		}
		ArrayList<Obra> obras = new ArrayList<Obra>();
		for (int id : ids) {
			Obra o = obrasDAO.selectByPk(id);
			if (o != null)
				obras.add(o);
		}
		exposicaoDao.deletarObras(exposicao);
		exposicao.setObras(obras);
		exposicao.setNome(req.getParameter("nome"));
		exposicao.setDataInicio(MuseuUtils.converteStringEmData(req.getParameter("dataInicio")));
		exposicao.setDataFim(MuseuUtils.converteStringEmData(req.getParameter("dataFim")));
		exposicao.setDescricao(req.getParameter("descricao"));
		return exposicao;
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new ExposicaoDAO();
			dao.deletar(id);
		}
		listarExposicoes(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Exposicao exposicao = dao.selectByPk(id);
			if (exposicao != null) {
				req.setAttribute("exposicaoEditar", exposicao);
				req.setAttribute("listaObras", exposicao.getObras());
				req.getRequestDispatcher("exposicao.jsp").forward(req, res);
			}
		}
	}

	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Exposicao vo = loadParameters(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.alterar(vo);
		String pageReturn = "exposicao.jsp";
		if (alterado) {
			listarExposicoes(req, res);
			return;
		}
		req.setAttribute("exposicaoEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}

	public void adicionarNaList(int obraId) {
		this.obras.add(obraDAO.selectByPk(obraId));
	}

	public void listarObras(HttpServletRequest req, HttpServletResponse res) throws Exception {
		ObraDAO dao = new ObraDAO();
		obras = dao.carregaLista();
		req.setAttribute("listaObras", obras);
		req.setAttribute("exposicaoEditar", new Exposicao());
		req.getRequestDispatcher("exposicao.jsp").forward(req, res);
	}

	public String getObraById(HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		String json = new Gson().toJson(new ObrasControl().selectById(Integer.parseInt(req.getParameter("id"))));

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);

		return json;
	}
}
