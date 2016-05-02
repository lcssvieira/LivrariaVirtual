package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Usuario;

public class UsuarioDAO {
	private static String tbName = "tb_usuario";
	private static String SELECT = String.format("SELECT * FROM %1$s",UsuarioDAO.tbName);
	private static String SELECTBYPK = String.format("SELECT * FROM %1$s WHERE id=?", UsuarioDAO.tbName);
	private static String INSERT = String.format("INSERT INTO %1$s (nome, username, senha) VALUES(?,?,?)", UsuarioDAO.tbName);
	private static String DELETE = String.format("DELETE FROM %1$s where id = ?", UsuarioDAO.tbName);
	private static String UPDATE = String.format("UPDATE %1$s SET nome = ?, username=?, senha =? where id = ?", UsuarioDAO.tbName);

	public boolean inserir(Usuario usuario) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getUsername());
			ps.setString(3, usuario.getSenha());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Usuario> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Usuario> usuarios = new ArrayList<>();
		while (rs.next()) {
			Usuario usuario = new Usuario();
			usuario.setId_usuario(rs.getLong("id"));
			usuario.setNome(rs.getString("nome"));
			usuario.setUsername(rs.getString("username"));
			usuarios.add(usuario);
			usuario = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return usuarios;
	}

	public void deletar(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(DELETE);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public Usuario selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Usuario usuario = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				usuario = new Usuario();
				usuario.setId_usuario(rs.getLong("id"));
				usuario.setNome(rs.getString("nome"));
				usuario.setUsername(rs.getString("username"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return usuario;
	}

	public boolean atualizar(Usuario usuario) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, usuario.getNome());
			ps.setString(2, usuario.getUsername());
			ps.setString(3, usuario.getSenha());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
