package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ClienteDAO;
import model.Cliente;
import utils.Utils;

public class ClienteControl {
	ClienteDAO dao = null;
	ArrayList<Cliente> clientes = new ArrayList<>();

	public ClienteControl() {
		dao = new ClienteDAO();
		clientes = new ArrayList<>();
	}	
	private Cliente loadParameters(HttpServletRequest req) {
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(req.getParameter("nome"));
			cliente.setRg(req.getParameter("rg"));
			cliente.setCpf(req.getParameter("cpf"));
			cliente.setEstadoCivil(req.getParameter("estado_civil"));
			cliente.setLogradouro(req.getParameter("logradouro"));
			cliente.setCep(req.getParameter("cep"));
			cliente.setEndereco(req.getParameter("endereco"));
			cliente.setNumero(req.getParameter("numero"));
			cliente.setComplemento(req.getParameter("complemento"));
			cliente.setBairro(req.getParameter("bairro"));
			cliente.setCidade(req.getParameter("cidade"));
			cliente.setUf(req.getParameter("uf"));
			cliente.setSexo(req.getParameter("sexo"));
			cliente.setDataNascimento(Utils.converteStringEmData(req.getParameter("data_nascimento")));
			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Cliente selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		if ( Boolean.parseBoolean(req.getParameter("alterar"))){
			alterar(req, res);
			return;
		}
		Cliente cliente = loadParameters(req);
		String paginaRetorno = "cliente.jsp";
		if (cliente != null) {
			if (dao == null)
				dao = new ClienteDAO();
			boolean inserido = dao.inserir(cliente);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "clienteConsulta.jsp";
			} else {
				paginaRetorno = "cliente.jsp";
			}
		}
		listar(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}
	
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Cliente cliente = loadParameters(req);
		cliente.setId(Long.parseLong(req.getParameter("id")));
		String paginaRetorno = "cliente.jsp";
		if (cliente != null) {
			if (dao == null)
				dao = new ClienteDAO();
			boolean inserido = dao.atualizar(cliente);
			req.setAttribute("adicionado", inserido);
			if (inserido) {
				paginaRetorno = "clienteConsulta.jsp";
			} else {
				paginaRetorno = "cliente.jsp";
			}
		}
		listar(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new ClienteDAO();
			dao.deletar(id);
		}
		listar(req, res);
	}

	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		clientes.clear();
		clientes = dao.carregaLista();
		req.setAttribute("listaClientes", clientes);
		req.getRequestDispatcher("clienteConsulta.jsp").forward(req, res);
	}

	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			if (dao == null)
				dao = new ClienteDAO();
			Cliente cliente = dao.selectByPk(id);
			if (cliente != null) {
				req.setAttribute("clienteEditar", cliente);
				req.getRequestDispatcher("cliente.jsp").forward(req, res);
			}
		}
	}
	
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		Cliente cliente = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
		String json = new Gson().toJson(cliente);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);
		return json;
	}
}
