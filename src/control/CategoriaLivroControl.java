package control;

import java.text.ParseException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ExposicaoDAO;
import dao.EditoraDAO;
import dao.LivroDAO;
import model.Exposicao;
import model.Editora;
import model.Livro;
import utils.MuseuUtils;

public class CategoriaLivroControl {
	ExposicaoDAO exposicaoDao;
	LivroDAO livroDAO;
	ExposicaoDAO dao;
	ArrayList<Exposicao> exposicoes = new ArrayList<>();
	ArrayList<Livro> obras = new ArrayList<>();
	ArrayList<Editora> museus = new ArrayList<>();

	public CategoriaLivroControl() {
		exposicaoDao = new ExposicaoDAO();
		dao = new ExposicaoDAO();
		livroDAO = new LivroDAO();
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
		EditoraDAO museuDAO = new EditoraDAO();
		LivroDAO obrasDAO = new LivroDAO();
		Editora museu = museuDAO.selectByPk(Integer.parseInt(req.getParameter("museuSelecionado")));
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
		ArrayList<Livro> obras = new ArrayList<Livro>();
		for (int id : ids) {
			Livro o = obrasDAO.selectByPk(id);
			if (o != null)
				obras.add(o);
		}
		exposicaoDao.deletarObras(exposicao);
		exposicao.setObras(obras);
		exposicao.setNome(req.getParameter("nome"));
		exposicao.setDataInicio(MuseuUtils.converteStringEmData(req.getParameter("dataInicio")));
		exposicao.setDataFim(MuseuUtils.converteStringEmData(req.getParameter("dataFim")));
		exposicao.setDescricao(req.getParameter("descricao"));
		exposicao.setSecao(req.getParameter("secao"));
		if (req.getParameter("valor_ingresso")!= null){
			Double valor = MuseuUtils.converteMoneyTextEmDouble(req.getParameter("valor_ingresso"));
			exposicao.setValor_ingresso(valor);
		}
			
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
				EditoraDAO museuDAO = new EditoraDAO();
				museus = museuDAO.carregaLista();
				req.setAttribute("listaMuseus", museus);
				LivroDAO dao = new LivroDAO();
				obras = dao.carregaLista();
				req.setAttribute("listaObras", obras);
				
				req.setAttribute("exposicaoEditar", exposicao);
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
		this.obras.add(livroDAO.selectByPk(obraId));
	}

	public void listarDados(HttpServletRequest req, HttpServletResponse res) throws Exception {
		LivroDAO dao = new LivroDAO();
		obras = dao.carregaLista();
		req.setAttribute("listaObras", obras);
		
		EditoraDAO museuDAO = new EditoraDAO();
		museus = museuDAO.carregaLista();
		req.setAttribute("listaMuseus", museus);
	
		req.setAttribute("exposicaoEditar", new Exposicao());
		req.getRequestDispatcher("exposicao.jsp").forward(req, res);
	}

	public String getObraById(HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		String json = new Gson().toJson(new LivroControl().selectById(Integer.parseInt(req.getParameter("id"))));

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);

		return json;
	}
	
	public Exposicao selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}
	
	public String getExposicaoById(HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		String json = new Gson().toJson(this.selectById(Integer.parseInt(req.getParameter("id"))));

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);

		return json;
	}
}
