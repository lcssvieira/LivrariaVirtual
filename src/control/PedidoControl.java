package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.*;
import model.*;
import utils.MuseuUtils;


public class PedidoControl {
	PedidoDAO pedidoDAO;
	ItemPedidoDAO ingressoDAO;
	ArrayList<Visitante> visitantes = new ArrayList<>() ;
	ArrayList<Exposicao> exposicoes = new ArrayList<>();
	ArrayList<ItemPedido> ingressos = new ArrayList<>();
	ArrayList<Pedido> vendas = new ArrayList<>();
	
	public PedidoControl(){
		
		pedidoDAO = new PedidoDAO();
		ingressoDAO = new ItemPedidoDAO();
		visitantes = new ArrayList<>();
		exposicoes = new ArrayList<>();
		ingressos = new ArrayList<>();
		vendas = new ArrayList<>();
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.getRequestDispatcher("inicio.html").forward(req, res);
		
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			atualizar(req, res);
			return;
		}
		Pedido vo = carregaParametros(req);
		boolean cadastrado = pedidoDAO.inserir(vo);
		req.setAttribute("adicionado", cadastrado);
		if (cadastrado)
			listarVendas(req, res);
		else {
			req.setAttribute("vendaEditar", vo);
			req.getRequestDispatcher("vendas.jsp").forward(req, res);
		}
	}
	
	private Pedido carregaParametros(HttpServletRequest req) {
		try {
		
			Pedido venda = new Pedido();
			venda.setData(MuseuUtils.converteStringEmData(req.getParameter("data")));
			
			if (req.getParameter("forma_pagamento") != null)
				venda.setFormaPagamento(FormaPagamento.values()[Integer.parseInt(req.getParameter("forma_pagamento"))]);
			
			venda.setItens(ingressos);
			
			String listaIngressos = req.getParameter("ingressosId");
			
			venda.setListaIds(listaIngressos);
			if (listaIngressos.startsWith(","))
				listaIngressos = listaIngressos.substring(1, listaIngressos.length());

			int[] ids = new int[listaIngressos.split(",").length];
			try {
				for (int i = 0; i < ids.length; i++)
					ids[i] = Integer.parseInt(listaIngressos.split(",")[i]);
			} catch (Exception e) {
				e.getMessage();
			}
			ArrayList<ItemPedido> ingressos = new ArrayList<ItemPedido>();
			for (int id : ids) {
				ItemPedido o = ingressoDAO.selectByPK(id);
				if (o != null)
					ingressos.add(o);
			}
			pedidoDAO.deletarIngressos(venda);
			venda.setItens(ingressos);
			venda.setData(MuseuUtils.converteStringEmData(req.getParameter("data")));
			if(req.getParameter("forma_pagamento") !=null)
				venda.setFormaPagamento(FormaPagamento.values()[Integer.parseInt(req.getParameter("forma_pagamento"))]);
			
			return venda;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void atualizar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Pedido vo = carregaParametros(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = pedidoDAO.atualizar(vo);
		String pageReturn = "venda.jsp";
		if (alterado) {
			listarDados(req, res);
			return;
		}
		req.setAttribute("vendaEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}
	
	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Pedido venda = pedidoDAO.selectByPk(id);
			if (venda != null) {
				req.setAttribute("vendaEditar", venda);
				req.setAttribute("listaIngressos", venda.getItens());
				req.getRequestDispatcher("venda.jsp").forward(req, res);
			}
		}
	}
	
	public void listarDados(HttpServletRequest req, HttpServletResponse res) throws Exception {

		ExposicaoDAO exposicaoDAO = new ExposicaoDAO();
		exposicoes = exposicaoDAO.carregaLista();
		req.setAttribute("listaExposicoes", exposicoes);
		
		ClienteDAO visitanteDAO = new ClienteDAO();
		visitantes = visitanteDAO.carregaLista();
		req.setAttribute("listaClientes", visitantes);
		
		ItemPedidoDAO ingressoDAO = new ItemPedidoDAO();
		ingressos = ingressoDAO.carregaLista();
		req.setAttribute("listaIngressos", ingressos);
		
		req.setAttribute("vendaEditar", new Pedido());
		req.getRequestDispatcher("vendas.jsp").forward(req, res);
	}
	
	
	public void listarVendas(HttpServletRequest req, HttpServletResponse res) throws Exception {
		vendas = pedidoDAO.carregaLista();
		req.setAttribute("listaVendas", vendas);
		req.getRequestDispatcher("vendaConsulta.jsp").forward(req, res);
	}
	

	public void adicionarNaList(int ingressoId) {
		this.ingressos.add(ingressoDAO.selectByPK(ingressoId));
	}
	
	public String getOIngressoById(HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		String json = new Gson().toJson(new ItemPedidoControl().selectById(Integer.parseInt(req.getParameter("id"))));

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);

		return json;
	}
}
