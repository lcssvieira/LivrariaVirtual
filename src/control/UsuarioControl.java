package control;

import java.text.ParseException;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import dao.UsuarioDAO;
import model.Usuario;


public class UsuarioControl {
	UsuarioDAO dao;
	ArrayList<Usuario> usuarios;
	public UsuarioControl() {
		dao = new UsuarioDAO();
		usuarios = new ArrayList<>();	
	}
	
	private Usuario loadParameters(HttpServletRequest req) throws ParseException {
		Usuario usuario = new Usuario();
		usuario.setNome(req.getParameter("nome"));
		usuario.setUsername(req.getParameter("username"));
		usuario.setSenha(req.getParameter("senha"));
		return usuario;
	}
	
	public Usuario selectById(int id) throws Exception {
		return dao.selectByPk(id);
	}
	
	public void cadastrar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (Boolean.parseBoolean(req.getParameter("alterar"))) {
			alterar(req, res);
			return;
		}
		Usuario usuario = loadParameters(req);
		boolean cadastrado = dao.inserir(usuario);
		req.setAttribute("inserido", cadastrado);
		if (cadastrado){
			listar(req, res);}
		else {
			req.setAttribute("usuarioEditar", usuario);
			req.getRequestDispatcher("usuario.jsp").forward(req, res);
		}
	}
	
	public void alterar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		Usuario usuario = loadParameters(req);
		usuario.setId_usuario(Long.parseLong(req.getParameter("id")));
		boolean alterado = dao.atualizar(usuario);
		String pageReturn = "usuario.jsp";
		if (alterado) {
			listar(req, res);
			return;
		}
		req.setAttribute("usuarioEditar", usuario);
		req.getRequestDispatcher(pageReturn).forward(req, res);

	}
	
	public void deletar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) 
			dao.deletar(id);
		listar(req, res);
	}

	public void listar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		usuarios =  dao.carregaLista();
		req.setAttribute("listaUsuarios",usuarios);
		req.getRequestDispatcher("usuarioConsulta.jsp").forward(req, res);
	}
	
	public void editar(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int id = Integer.parseInt(req.getParameter("id"));
		if (id != 0) {
			Usuario usuario = dao.selectByPk(id);
			if (usuario != null) {
				req.setAttribute("usuarioEditar", usuario);
				req.getRequestDispatcher("usuario.jsp").forward(req, res);
			}
		}
	}
	
	public String obterJson (HttpServletRequest req, HttpServletResponse res) throws NumberFormatException, Exception {
		Usuario usuario = dao.selectByPk(Integer.parseInt(req.getParameter("id")));
		String json = new Gson().toJson(usuario);
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().print(json);
		return json;
	}
}
