package control;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;

import model.ItemPedido;
import model.TipoDesconto;
import utils.MuseuUtils;

public class ItemPedidoControl {

	ItemPedidoDAO dao= null;
	ClienteDAO visitanteDAO = null;
	ExposicaoDAO exposicaoDAO = null;
	PedidoDAO pedidoDAO = null;
	
	public ItemPedidoControl(){
		
		dao = new ItemPedidoDAO();
		visitanteDAO = new ClienteDAO();
		exposicaoDAO = new ExposicaoDAO();
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		boolean isAlterar = Boolean.parseBoolean(req.getParameter("alterar"));
		if (isAlterar)
			atualizar(req, res);
		ItemPedido ingresso = carregaParametros(req);
		String paginaRetorno = "inicio.html";
		if (ingresso != null) {
			if (dao == null)
				dao = new ItemPedidoDAO();
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
	
	private ItemPedido carregaParametros(HttpServletRequest req) {
		try {
			ItemPedido ingresso = new ItemPedido();
			ingresso.setVenda(pedidoDAO.selectByPk(Integer.parseInt(req.getParameter("venda"))));
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
	
	public ItemPedido selectById(int id) throws Exception {
		return dao.selectByPK(id);
	}
	
	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

	//TODO
	}
}
