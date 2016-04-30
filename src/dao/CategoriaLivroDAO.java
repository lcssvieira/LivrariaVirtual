package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CategoriaLivro;

public class CategoriaLivroDAO {
	private static String tbName = "tb_categoria_livro";
	private static String SELECT = String.format("SELECT * FROM {0}",CategoriaLivroDAO.tbName);
	private static String SELECTBYPK = String.format("SELECT * FROM {0} WHERE id=?", CategoriaLivroDAO.tbName);
	private static String INSERT = String.format("INSERT INTO {0} (descricao) VALUES(?)", CategoriaLivroDAO.tbName);
	private static String DELETE = String.format("DELETE FROM {0} where id = ?", CategoriaLivroDAO.tbName);
	private static String UPDATE = String.format("UPDATE {0} SET descricao = ? where id = ?", CategoriaLivroDAO.tbName);

	public boolean inserir(CategoriaLivro categoria) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, categoria.getDescricao());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<CategoriaLivro> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<CategoriaLivro> categorias = new ArrayList<>();
		while (rs.next()) {
			CategoriaLivro categoria = new CategoriaLivro();
			categoria.setId(rs.getLong("id"));
			categoria.setDescricao(rs.getString("descricao"));
			categorias.add(categoria);
			categoria = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return categorias;
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

	public CategoriaLivro selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		CategoriaLivro categoria = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				categoria = new CategoriaLivro();
				categoria.setId(rs.getLong("id"));
				categoria.setDescricao(rs.getString("descricao"));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return categoria;
	}

	public boolean atualizar(CategoriaLivro categoria) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, categoria.getDescricao());
			ps.setLong(2, categoria.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
