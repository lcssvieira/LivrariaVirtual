package control;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UsuarioDAO;
import model.Usuario;

public class AdminControl {
	UsuarioDAO dao = null;

	public AdminControl() {
		dao = new UsuarioDAO();
	}

	public void login(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String username = req.getParameter("user");
		String password = req.getParameter("password");
		// se encontra na base um usuario com login e senha, envia pra tela
		// principal
		if (dao.login(new Usuario(username, password)) != null)
			req.getRequestDispatcher("inicio.jsp").forward(req, res);
		else {
			req.setAttribute("erro", "Usuário inválido, por favor verifique login e senha.");
			req.getRequestDispatcher("login.jsp").forward(req, res);
		}
	}
}
