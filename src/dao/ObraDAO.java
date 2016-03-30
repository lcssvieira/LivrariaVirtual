package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Obra;

public class ObraDAO {
	private static String SELECT = "SELECT * FROM tb_obra";
	private static String SELECT_EMPRESTIMO = "SELECT * FROM tb_obra WHERE fl_emprestado =  ? ";
	private static String SELECTBYPK = "SELECT * FROM tb_obra WHERE id=?";
	private static String INSERT = "INSERT INTO tb_obra (nome, descricao,tipo,data,periodo,artista,valor_estimado) VALUES(?,?,?,?,?,?,?)";
	private static String DELETAR = "DELETE FROM tb_obra where id = ?";
	private static String UPDATE = "UPDATE tb_obra SET nome = ?, descricao = ?, tipo = ?, data =?, periodo=?, artista =?, valor_estimado = ? where id = ?";
	private static String UPDATE_EMPRESTIMO = "UPDATE tb_obra SET fl_emprestado = ? where id = ?";

	public boolean inserir(Obra obra) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, obra.getNome());
			ps.setString(2, obra.getDescricao());
			ps.setString(3, obra.getTipo());
			if (obra.getData() != null)
				ps.setDate(4, new java.sql.Date(obra.getData().getTime()));
			else
				ps.setDate(4, null);
			ps.setString(5, obra.getPeriodo());
			ps.setString(6, obra.getArtista());
			ps.setDouble(7, obra.getValorEstimado());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Obra> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Obra> obras = new ArrayList<>();
		while (rs.next()) {
			Obra obra = new Obra();
			obra.setId(rs.getInt("id"));
			obra.setArtista(rs.getString("artista"));
			if (rs.getDate("data") != null)
				obra.setData(new java.util.Date(rs.getDate("data").getTime()));
			obra.setDescricao(rs.getString("descricao"));
			obra.setNome(rs.getString("nome"));
			obra.setPeriodo(rs.getString("periodo"));
			obra.setTipo(rs.getString("tipo"));
			obras.add(obra);
			obra = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return obras;
	}

	public void deletar(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(DELETAR);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public Obra selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Obra obra = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				obra = new Obra();
				obra.setId(id);
				obra.setArtista(rs.getString("artista"));
				if (rs.getDate("data") != null)
					obra.setData(new java.util.Date(rs.getDate("data").getTime()));
				obra.setDescricao(rs.getString("descricao"));
				obra.setNome(rs.getString("nome"));
				obra.setTipo(rs.getString("tipo"));
				obra.setPeriodo(rs.getString("periodo"));
				obra.setValorEstimado(rs.getDouble("valor_estimado"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return obra;
	}

	public boolean atualizar(Obra obra) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, obra.getNome());
			ps.setString(2, obra.getDescricao());
			ps.setString(3, obra.getTipo());
			if (obra.getData() != null)
				ps.setDate(4, new java.sql.Date(obra.getData().getTime()));
			else
				ps.setDate(4, null);
			ps.setString(5, obra.getPeriodo());
			ps.setString(6, obra.getArtista());
			ps.setDouble(7, obra.getValorEstimado());
			ps.setLong(8, obra.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Obra> carregaListaEmprestimo(int flEmprestimo) throws SQLException {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT_EMPRESTIMO);
		ps.setInt(1, flEmprestimo);
		ResultSet rs = ps.executeQuery();
		ArrayList<Obra> obras = new ArrayList<>();
		while (rs.next()) {
			Obra obra = new Obra();
			obra.setId(rs.getInt("id"));
			obra.setArtista(rs.getString("artista"));
			if (rs.getDate("data") != null)
				obra.setData(new java.util.Date(rs.getDate("data").getTime()));
			obra.setDescricao(rs.getString("descricao"));
			obra.setNome(rs.getString("nome"));
			obra.setPeriodo(rs.getString("periodo"));
			obra.setTipo(rs.getString("tipo"));
			obra.setFl_emprestado(rs.getInt("fl_emprestado"));
			obras.add(obra);
			obra = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return obras;
	}

	public void atualizarEmprestado(Obra obra, int flEmprestado) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE_EMPRESTIMO);
			ps.setInt(1,flEmprestado);
			ps.setLong(2, obra.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
