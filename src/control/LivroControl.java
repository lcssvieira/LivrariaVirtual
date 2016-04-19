package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.LivroDAO;
import model.Livro;
import utils.MuseuUtils;

public class LivroControl {
	LivroDAO dao = null;
	ArrayList<Livro> obras = new ArrayList<>();

	public LivroControl(){
		dao = new LivroDAO();
	}
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean isAlterar = Boolean.parseBoolean(req.getParameter("alterar"));
		if (isAlterar)
			atualizar(req, res);
	
		ArrayList<String> msgValidacao= new ArrayList<>();
		boolean validacao = this.validar(req, msgValidacao);
		String paginaRetorno = "obras.jsp";
		
		if (validacao) {
			Livro obra = carregaParametros(req);
			if (dao == null)
				dao = new LivroDAO();
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
				dao = new LivroDAO();
			dao.deletar(id);
		}
		listarObras(req, res);
	}

	public void listarObras(HttpServletRequest req, HttpServletResponse res) throws Exception {

		obras.clear();
		if (dao == null)
			dao = new LivroDAO();
		obras = dao.carregaLista();
		req.setAttribute("listaObras", obras);
		req.getRequestDispatcher("obrasConsulta.jsp").forward(req, res);
	}

	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Livro obra = carregaParametros(req);
		obra.setId(Long.parseLong(req.getParameter("id")));
		String paginaRetorno = "inicio.html";
		if (obra != null) {
			if (dao == null)
				dao = new LivroDAO();
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
				dao = new LivroDAO();
			Livro obra = dao.selectByPk(id);
			if (obra != null) {
				req.setAttribute("obraEditar", obra);
				req.getRequestDispatcher("obras.jsp").forward(req, res);
			}
		}
	}

	private Livro carregaParametros(HttpServletRequest req) {
		try {
			Livro obra = new Livro();
			obra.setArtista(req.getParameter("artista"));
			
			if (req.getParameter("data").trim() != null && req.getParameter("data").length() >0)
				obra.setData(MuseuUtils.converteStringEmData(req.getParameter("data")));
			else
				obra.setData(null);
			obra.setDescricao(req.getParameter("descricao"));
			obra.setNome(req.getParameter("nome"));
			obra.setPeriodo(req.getParameter("periodo"));
			obra.setTipo(req.getParameter("tipo"));
			if (req.getParameter("valorEstimado") != null && req.getParameter("valorEstimado").length()>0){
				Double valor = MuseuUtils.converteMoneyTextEmDouble(req.getParameter("valorEstimado"));
				obra.setValorEstimado(valor);
			}
			
			return obra;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Livro selectById(int id) throws Exception {
		return dao.selectByPk(id);
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
