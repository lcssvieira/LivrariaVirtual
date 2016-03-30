package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ObraDAO;
import model.Obra;
import utils.MuseuUtils;

public class ObrasControl {
	ObraDAO dao = null;
	ArrayList<Obra> obras = new ArrayList<>();

	public ObrasControl(){
		dao = new ObraDAO();
	}
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean isAlterar = Boolean.parseBoolean(req.getParameter("alterar"));
		if (isAlterar)
			atualizar(req, res);
	
		ArrayList<String> msgValidacao= new ArrayList<>();
		boolean validacao = this.validar(req, msgValidacao);
		String paginaRetorno = "obras.jsp";
		
		if (validacao) {
			Obra obra = carregaParametros(req);
			if (dao == null)
				dao = new ObraDAO();
			boolean inserido = dao.inserir(obra);
			req.setAttribute("adicionado", inserido);
			if (inserido)
				paginaRetorno = "obrasConsulta.jsp";
				
			listarObras(req, res);
			req.getRequestDispatcher(paginaRetorno).forward(req, res);
		}
		else{
			req.setAttribute("retornoValidacao", msgValidacao);
			req.getRequestDispatcher(paginaRetorno).forward(req, res);
		}
		
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new ObraDAO();
			dao.deletar(id);
		}
		listarObras(req, res);
	}

	public void listarObras(HttpServletRequest req, HttpServletResponse res) throws Exception {

		obras.clear();
		if (dao == null)
			dao = new ObraDAO();
		obras = dao.carregaLista();
		req.setAttribute("listaObras", obras);
		req.getRequestDispatcher("obrasConsulta.jsp").forward(req, res);
	}

	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Obra obra = carregaParametros(req);
		obra.setId(Long.parseLong(req.getParameter("id")));
		String paginaRetorno = "inicio.html";
		if (obra != null) {
			if (dao == null)
				dao = new ObraDAO();
			boolean inserido = dao.atualizar(obra);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "obrasConsulta.jsp";
			} else {
				paginaRetorno = "obras.jsp";
			}
		}
		listarObras(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new ObraDAO();
			Obra obra = dao.selectByPk(id);
			if (obra != null) {
				req.setAttribute("obraEditar", obra);
				req.getRequestDispatcher("obras.jsp").forward(req, res);
			}
		}
	}

	private Obra carregaParametros(HttpServletRequest req) {
		try {
			Obra obra = new Obra();
			obra.setArtista(req.getParameter("artista"));
			
			if (req.getParameter("data").trim() != null && req.getParameter("data").length() >0)
				obra.setData(MuseuUtils.converteStringEmData(req.getParameter("data")));
			else
				obra.setData(null);
			obra.setDescricao(req.getParameter("descricao"));
			obra.setNome(req.getParameter("nome"));
			obra.setPeriodo(req.getParameter("periodo"));
			obra.setTipo(req.getParameter("tipo"));
			obra.setValorEstimado(Double.parseDouble(req.getParameter("valorEstimado")));
			return obra;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Obra selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}
	
	public boolean validar(HttpServletRequest req, ArrayList<String> outPut){
		
		if (req.getParameter("nome")== null || req.getParameter("nome").trim().length() == 0)
			outPut.add("O campo 'Nome' não pode estar vazio.");
		
		if (req.getParameter("artista") == null|| req.getParameter("artista").trim().length() == 0)
			outPut.add("O campo 'Artista' é obrigatório.");
		
		if (req.getParameter("valorEstimado") != null){
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
