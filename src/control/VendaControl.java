package control;

import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.*;
import model.*;
import utils.MuseuUtils;


public class VendaControl {
	VendaDAO vendaDAO;
	IngressoDAO ingressoDAO;
	ArrayList<Visitante> visitantes = new ArrayList<>() ;
	ArrayList<Exposicao> exposicoes = new ArrayList<>();
	ArrayList<Ingresso> ingressos = new ArrayList<>();
	ArrayList<Venda> vendas = new ArrayList<>();
	
	public VendaControl(){
		
		vendaDAO = new VendaDAO();
		ingressoDAO = new IngressoDAO();
		visitantes = new ArrayList<>();
		exposicoes = new ArrayList<>();
		ingressos = new ArrayList<>();
		vendas = new ArrayList<>();
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			atualizar(req, res);
			return;
		}
		Venda vo = carregaParametros(req);
		boolean cadastrado = vendaDAO.inserir(vo);
		req.setAttribute("adicionado", cadastrado);
		if (cadastrado)
			listarVendas(req, res);
		else {
			req.setAttribute("exposicaoEditar", vo);
			req.getRequestDispatcher("exposicao.jsp").forward(req, res);
		}
	}
	
	private Venda carregaParametros(HttpServletRequest req) {
		try {
		
			Venda venda = new Venda();
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
			ArrayList<Ingresso> ingressos = new ArrayList<Ingresso>();
			for (int id : ids) {
				Ingresso o = ingressoDAO.selectByPK(id);
				if (o != null)
					ingressos.add(o);
			}
			vendaDAO.deletarIngressos(venda);
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

		Venda vo = carregaParametros(req);
		vo.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = vendaDAO.atualizar(vo);
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
			Venda venda = vendaDAO.selectByPk(id);
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
		
		VisitanteDAO visitanteDAO = new VisitanteDAO();
		visitantes = visitanteDAO.carregaLista();
		req.setAttribute("listaClientes", visitantes);
		
		IngressoDAO ingressoDAO = new IngressoDAO();
		ingressos = ingressoDAO.carregaLista();
		req.setAttribute("listaIngressos", ingressos);
		
		req.setAttribute("listaTiposDesconto", Arrays.asList(TipoDesconto.values()));
		
		req.setAttribute("vendaEditar", new Venda());
		req.getRequestDispatcher("vendas.jsp").forward(req, res);
	}
	
	
	public void listarVendas(HttpServletRequest req, HttpServletResponse res) throws Exception {
		vendas = vendaDAO.carregaLista();
		req.setAttribute("listaVendas", vendas);
		req.getRequestDispatcher("vendaConsulta.jsp").forward(req, res);
	}
	

	public void adicionarNaList(int ingressoId) {
		this.ingressos.add(ingressoDAO.selectByPK(ingressoId));
	}
	
	public String getOIngressoById(HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		String json = new Gson().toJson(new IngressoControl().selectById(Integer.parseInt(req.getParameter("id"))));

		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);

		return json;
	}
}
