package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Visitante;

public class ClienteDAO {
	private static String SELECT = "SELECT * FROM tb_visitante";
	private static String SELECTBYPK = "SELECT * FROM tb_visitante WHERE id=?";
	private static String INSERT = "INSERT INTO tb_visitante (nome, escolaridade,cep,endereco,numero,complemento,genero, data_nascimento, nacionalidade, meio_transporte) VALUES(?,?,?,?,?,?,?,?,?,?)";
	private static String DELETAR = "DELETE FROM tb_visitante where id = ?";
	private static String UPDATE = "UPDATE tb_visitante SET nome = ?, escolaridade = ?, cep = ?, endereco =?, numero=?, complemento =? , genero=?,data_nascimento = ?,nacionalidade = ?, meio_transporte=? where id = ?";

	public boolean inserir(Visitante visitante) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, visitante.getNome());
			ps.setString(2, visitante.getEscolaridade());
			ps.setString(3, visitante.getCep());
			ps.setString(4, visitante.getEndereco());
			ps.setString(5, visitante.getNumero());
			ps.setString(6, visitante.getComplemento());
			ps.setInt(7, visitante.getGenero());
			ps.setDate(8, new java.sql.Date(visitante.getDataNascimento().getTime()));
			ps.setString(9, visitante.getNacionalidade());
			ps.setString(10, visitante.getTransporte());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Visitante> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Visitante> visitantes = new ArrayList<>();
		while (rs.next()) {
			Visitante visitante = new Visitante();
			visitante.setId(rs.getInt("id"));
			visitante.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
			visitante.setNome(rs.getString("nome"));
			visitante.setCep(rs.getString("cep"));
			visitante.setComplemento(rs.getString("complemento"));
			visitante.setEndereco(rs.getString("endereco"));
			visitante.setEscolaridade(rs.getString("escolaridade"));
			visitante.setGenero(rs.getInt("genero"));
			visitante.setNumero(rs.getString("numero"));
			visitante.setNacionalidade(rs.getString("nacionalidade"));
			visitante.setTransporte(rs.getString("meio_transporte"));
			visitantes.add(visitante);
			visitante = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return visitantes;
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

	public Visitante selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Visitante visitante = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				visitante = new Visitante();
				visitante.setId(id);
				visitante.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
				visitante.setNome(rs.getString("nome"));
				visitante.setCep(rs.getString("cep"));
				visitante.setComplemento(rs.getString("complemento"));
				visitante.setEndereco(rs.getString("endereco"));
				visitante.setEscolaridade(rs.getString("escolaridade"));
				visitante.setGenero(rs.getInt("genero"));
				visitante.setNumero(rs.getString("numero"));
				visitante.setNacionalidade(rs.getString("nacionalidade"));
				visitante.setTransporte(rs.getString("meio_transporte"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return visitante;
	}

	public boolean atualizar(Visitante visitante) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, visitante.getNome());
			ps.setString(2, visitante.getEscolaridade());
			ps.setString(3, visitante.getCep());
			ps.setString(4, visitante.getEndereco());
			ps.setString(5, visitante.getNumero());
			ps.setString(6, visitante.getComplemento());
			ps.setInt(7, visitante.getGenero());
			ps.setDate(8, new java.sql.Date(visitante.getDataNascimento().getTime()));
			ps.setString(9, visitante.getNacionalidade());
			ps.setString(10, visitante.getTransporte());
			ps.setLong(11, visitante.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
