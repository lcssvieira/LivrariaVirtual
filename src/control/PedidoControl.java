package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.*;
import model.*;
import utils.Utils;


public class PedidoControl {
	PedidoDAO dao;
	ArrayList<Pedido> pedidos;
	
	public PedidoControl(){
		dao = new PedidoDAO();
		pedidos = new ArrayList<>();
	}
	
	private Pedido loadParameters(HttpServletRequest req) {
		try {
		
			Pedido pedido = new Pedido();
			pedido.setDataVenda(Utils.converteStringEmData(req.getParameter("data_venda")));	
			pedido.setNumero(Long.parseLong(req.getParameter("numero")));
			pedido.setFormaPagamento(FormaPagamento.values()[Integer.parseInt(req.getParameter("forma_pagamento"))]);
			
			// obtem cliente
			ClienteControl clienteCtrl = new ClienteControl();
			Cliente cliente = clienteCtrl.selectById(Integer.parseInt(req.getParameter("clienteId")));
			
			// obtem itens do pedido
			String [] itensContent = req.getParameter("itensPedido").split(";");
			ArrayList<ItemPedido> itens = new ArrayList<>();		
			for (String itemContent : itensContent) {
				// obtem dados de cada item
				String [] dados = itemContent.split(",");
				int livroId = Integer.parseInt(dados[0]);
				Double percDesconto =  Utils.converteMoneyTextEmDouble(dados[1]);
				int quantidade = Integer.parseInt(dados[2]);

				LivroControl livroCtrl = new LivroControl();
				Livro livro = livroCtrl.selectById(livroId);
				ItemPedido item = new ItemPedido();
				item.setLivro(livro);
				item.setPercDesconto(percDesconto);
				item.setQuantidade(quantidade);
				itens.add(item);
			}
			pedido.setCliente(cliente);
			pedido.setItens(itens);			
			return pedido;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Pedido selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		req.getRequestDispatcher("pedido.jsp").forward(req, res);
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Pedido vo = loadParameters(req);
		boolean cadastrado = dao.inserir(vo);
		req.setAttribute("adicionado", cadastrado);
		if (cadastrado)
			listar(req, res);
		else {
			req.setAttribute("pedidoEditar", vo);
			req.getRequestDispatcher("pedido.jsp").forward(req, res);
		}
	}
	
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		Pedido vo = loadParameters(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.atualizar(vo);
		String pageReturn = "pedido.jsp";
		if (alterado) {
			listar(req, res);
			return;
		}
		req.setAttribute("pedidoEditar", vo);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}
	
	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) 
			dao.deletar(id);
		listar(req, res);
	}

	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		pedidos = dao.carregaLista();
		req.setAttribute("lstaPedidos", pedidos);
		req.getRequestDispatcher("pedidoConsulta.jsp").forward(req, res);
	}
}
