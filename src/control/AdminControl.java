package control;
import dao.UsuarioDAO;
public class AdminControl {
	UsuarioDAO dao = null;

	public AdminControl() {
		dao = new UsuarioDAO();
	}
}
