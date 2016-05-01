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
		Editora vo = new Editora();
		vo.setNome(req.getParameter("nome"));
		vo.setCnpj(req.getParameter("cnpj"));
		vo.setTelefones(req.getParameter("telfones"));
		vo.setLogradouro(req.getParameter("logradouro"));
		vo.setCep(req.getParameter("cep"));
		vo.setEndereco(req.getParameter("endereco"));
		vo.setNumero(req.getParameter("numero"));
		vo.setComplemento(req.getParameter("complemento"));
		vo.setBairro(req.getParameter("bairro"));
		vo.setBairro(req.getParameter("cidade"));
		vo.setUf(req.getParameter("uf"));
		return vo;
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
