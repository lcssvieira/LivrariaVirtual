package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import model.Usuario;

public class UsuarioDAO {
	private static String LOGIN = " SELECT * FROM tb_usuario WHERE username = ? and senha = ?";

	public Usuario login(Usuario u) {
		Usuario usuario = null;
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(LOGIN);
			ps.setString(1, u.getUsername());
			ps.setString(2, u.getSenha());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setSenha(u.getSenha());
				usuario.setUsername(u.getUsername());
				usuario.setId(rs.getLong("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
		return usuario;
	}
}
