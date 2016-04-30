package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Autor;
public class AutorDAO {
	private static String tbName = "tb_autor";
	private static String SELECT = String.format("SELECT * FROM {0}", AutorDAO.tbName);
	private static String SELECTBYPK = String.format("SELECT * FROM {0} WHERE id=?",AutorDAO.tbName);
	private static String INSERT = String.format("INSERT INTO {0} (nome, local_nascimento,local_falecimento, biografia, data_nascimento, data_falecimento) VALUES(?,?,?,?,?,?)",AutorDAO.tbName);
	private static String DELETE = String.format("DELETE FROM {0} where id = ?",AutorDAO.tbName);
	private static String UPDATE = String.format("UPDATE {0} SET nome =?, local_nascimento =?,local_falecimento =?, biografia =?, data_nascimento =?, data_falecimento =? where id = ?",AutorDAO.tbName);

	public boolean inserir(Autor autor) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, autor.getNome());
			ps.setString(2, autor.getLocalNascimento());
			ps.setString(3, autor.getLocalFalecimento());
			ps.setString(4, autor.getBiografia());
			ps.setDate(5, new java.sql.Date(autor.getDataNascimento().getTime()));
			ps.setDate(6, new java.sql.Date(autor.getDataFalecimento().getTime()));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Autor> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Autor>autores = new ArrayList<>();
		while (rs.next()) {
			Autor autor = new Autor();
			autor.setId(rs.getInt("id"));
			autor.setNome(rs.getString("nome"));
			autor.setLocalNascimento(rs.getString("local_nascimento"));
			autor.setLocalFalecimento(rs.getString("local_falecimento"));
			autor.setBiografia(rs.getString("biografia"));
			autor.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
			autor.setDataFalecimento(new java.util.Date(rs.getDate("data_falecimento").getTime()));
			autores.add(autor);
			autor = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return autores;
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

	public Autor selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Autor autor = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				autor = new Autor();
				autor.setId(rs.getInt("id"));
				autor.setNome(rs.getString("nome"));
				autor.setLocalNascimento(rs.getString("local_nascimento"));
				autor.setLocalFalecimento(rs.getString("local_falecimento"));
				autor.setBiografia(rs.getString("biografia"));
				autor.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
				autor.setDataFalecimento(new java.util.Date(rs.getDate("data_falecimento").getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return autor;
	}

	public boolean atualizar(Autor autor) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, autor.getNome());
			ps.setString(2, autor.getLocalNascimento());
			ps.setString(3, autor.getLocalFalecimento());
			ps.setString(4, autor.getBiografia());
			ps.setDate(5, new java.sql.Date(autor.getDataNascimento().getTime()));
			ps.setDate(6, new java.sql.Date(autor.getDataFalecimento().getTime()));
			ps.setLong(7,autor.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
