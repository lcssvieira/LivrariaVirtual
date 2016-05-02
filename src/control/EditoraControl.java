package control;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.EditoraDAO;
import model.Editora;

public class EditoraControl {
	EditoraDAO dao;
	ArrayList<Editora> editoras = new ArrayList<>();

	public EditoraControl() {
		dao = new EditoraDAO();
	}
	
	private Editora loadParameters(HttpServletRequest req) {
		Editora editora = new Editora();
		editora.setNome(req.getParameter("nome"));
		editora.setCnpj(req.getParameter("cnpj"));
		editora.setTelefones(req.getParameter("telfones"));
		editora.setLogradouro(req.getParameter("logradouro"));
		editora.setCep(req.getParameter("cep"));
		editora.setEndereco(req.getParameter("endereco"));
		editora.setNumero(req.getParameter("numero"));
		editora.setComplemento(req.getParameter("complemento"));
		editora.setBairro(req.getParameter("bairro"));
		editora.setBairro(req.getParameter("cidade"));
		editora.setUf(req.getParameter("uf"));
		return editora;
	}
	
	public Editora selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}

	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Editora editora = loadParameters(req);
		boolean cadastrado = dao.inserir(editora);
		req.setAttribute("adicionado", cadastrado);
		String paginaRetorno = "editora.jsp";
		if (cadastrado)
			paginaRetorno = "editoraConsulta.jsp";
		else
			req.setAttribute("editoraEditar", editora);
		listar(req, res);
		req.getRequestDispatcher(paginaRetorno).forward(req, res);
	}
	
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Editora editora = loadParameters(req);
		editora.setId(Integer.parseInt(req.getParameter("id")));
		boolean alterado = dao.atualizar(editora);
		String pageReturn = "editora.jsp";
		if (alterado) {
			listar(req, res);
			return;
		}
		req.setAttribute("editoraEditar", editora);
		req.getRequestDispatcher(pageReturn).forward(req, res);
	}

	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) 
			dao.deletar(id);	
		listar(req, res);
	}
	
	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		editoras = dao.carregaLista();
		req.setAttribute("listaEditoras", editoras);
		req.getRequestDispatcher("editoraConsulta.jsp").forward(req, res);
	}
	
	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Editora editora = dao.selectByPk(id);
			if (editora != null) {
				req.setAttribute("editoraEditar", editora);
				req.getRequestDispatcher("editora.jsp").forward(req, res);
			}
		}
	}
	
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		Editora editora = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
		String json = new Gson().toJson(editora);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);
		return json;
	}
}
