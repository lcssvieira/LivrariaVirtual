package control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;

import model.Ingresso;
import model.TipoDesconto;
import utils.MuseuUtils;

public class IngressoControl {

	IngressoDAO dao= null;
	VisitanteDAO visitanteDAO = null;
	ExposicaoDAO exposicaoDAO = null;
	VendaDAO vendaDAO = null;
	
	public IngressoControl(){
		
		dao = new IngressoDAO();
		visitanteDAO = new VisitanteDAO();
		exposicaoDAO = new ExposicaoDAO();
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean isAlterar = Boolean.parseBoolean(req.getParameter("alterar"));
		if (isAlterar)
			atualizar(req, res);
		Ingresso ingresso = carregaParametros(req);
		String paginaRetorno = "inicio.html";
		if (ingresso != null) {
			if (dao == null)
				dao = new IngressoDAO();
			boolean inserido = dao.inserir(ingresso);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "obrasConsulta.jsp";
			} else {
				paginaRetorno = "obras.jsp";
			}
		}
		
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}
	
	private Ingresso carregaParametros(HttpServletRequest req) {
		try {
			Ingresso ingresso = new Ingresso();
			ingresso.setVenda(vendaDAO.selectByPk(Integer.parseInt(req.getParameter("venda"))));
			ingresso.setCliente(visitanteDAO.selectByPk(Integer.parseInt(req.getParameter("visitante"))));
			ingresso.setExposicao(exposicaoDAO.selectByPk(Integer.parseInt(req.getParameter("exposicao"))));
			ingresso.setData(MuseuUtils.converteStringEmData(req.getParameter("data")));
			ingresso.setValor(Double.parseDouble(req.getParameter("valor")));
			ingresso.setDesconto(Integer.parseInt(req.getParameter("desconto")));
			ingresso.setTipoDesconto(TipoDesconto.values()[Integer.parseInt(req.getParameter("tipo_desconto"))]);

			return ingresso;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Ingresso selectById(int id) throws Exception {
		return dao.selectByPK(id);
	}
	
	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

	//TODO
	}
}
