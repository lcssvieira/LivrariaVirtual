package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Exposicao;
import model.Obra;


public class ExposicaoDAO {
	private static final String INSERT = "INSERT INTO tb_exposicao (nome, id_museu,data_inicio, data_fim, secao, descricao) VALUES (?,?,?,?,?,?)";
	private static final String UPDATE = "UPDATE tb_exposicao set nome= ?, id_museu =?, data_inicio =?, data_fim=?, secao = ?, descricao = ? WHERE id=?";
	private static final String INSERT_OBRAS = "INSERT INTO tb_exposicao_obras (id_exposicao, id_obra) VALUES (?,?)";
	private static final String DELETE_OBRAS = "DELETE FROM tb_exposicao_obras where id_exposicao = ?";
	private static final String INSERT_DIAS_GRATUITOS = "INSERT INTO tb_dias_gratuitos(id_exposicao, dia_semana) VALUES (?,?)";
	private static final String DELETE_DIAS_GRATUITOS = "DELETE FROM tb_dias_gratuitos where id_exposicao = ?";
	private static final String SELECT = "SELECT * FROM tb_exposicao";
	private static final String DELETE = "DELETE FROM tb_exposicao WHERE id = ?";
	private static final String SELECT_BY_PK = "SELECT * FROM tb_exposicao WHERE id = ?";
	private static final String SELECT_BY_PK_OBRAS = "SELECT * FROM tb_exposicao_obras WHERE id_exposicao = ?";
	private static final String SELECT_BY_PK_DIAS_GRATUITOS = "SELECT * from tb_dias_gratuitos where id_exposicao = ?";
	private static final String SELECT_ULTIMO = "SELECT * FROM tb_exposicao ORDER BY id DESC  LIMIT 1";
	
	public boolean cadastrar(Exposicao exposicao) {
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, exposicao.getNome());
			ps.setLong(2, exposicao.getMuseu().getId());
			ps.setDate(3, new java.sql.Date(exposicao.getDataInicio().getTime()));
			ps.setDate(4, new java.sql.Date(exposicao.getDataFim().getTime()));
			ps.setString(5, exposicao.getSecao());
			ps.setString(6, exposicao.getDescricao());
			boolean retorno = ps.executeUpdate() > 0;
			exposicao.setId(selectUltimoInserido().getId());
			deletarObras(exposicao);
			cadastrarObras(exposicao);
			return retorno;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(null, ps, null);
		}
	}

	private Exposicao selectUltimoInserido() {
		Connection conn = null;
		PreparedStatement ps = null;
		MuseuDAO museuDAO = new MuseuDAO();
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT_ULTIMO);
			ResultSet rs = ps.executeQuery();
			Exposicao e = null;
			while (rs.next()) {
				e = new Exposicao();
				e.setId(rs.getInt("id"));
				e.setMuseu(museuDAO.selectByPk(rs.getInt("id_museu")));
				e.setNome(rs.getString("nome"));
				e.setSecao(rs.getString("secao"));
				e.setDataInicio(rs.getDate("data_inicio"));
				e.setDataFim(rs.getDate("data_fim"));
				e.setObras(this.carregaObrasPorExposicao(rs.getInt("id")));
				e.setgetDiasGratuitos(this.carregaDiasGratuitos(rs.getInt("id")));
			}
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	private void cadastrarObras(Exposicao vo) {
		PreparedStatement ps = null;
		for (Obra obra : vo.getObras()) {
			try {
				ps = DaoUtils.getConnection().prepareStatement(INSERT_OBRAS);
				ps.setLong(1, vo.getId());
				ps.setLong(2, obra.getId());
				ps.executeUpdate();
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				DaoUtils.fechaConexoes(null, ps, null);
			}
		}
	}

	public ArrayList<Exposicao> carregaLista() {
		
		Connection conn = null;
		PreparedStatement ps = null;
		try{
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			MuseuDAO museuDAO = new MuseuDAO();
			ArrayList<Exposicao> exposicoes = new ArrayList<>();
			while (rs.next()) {
				Exposicao e = new Exposicao();
				e.setId(rs.getInt("id"));
				e.setMuseu(museuDAO.selectByPk(rs.getInt("id_museu")));
				e.setNome(rs.getString("nome"));
				e.setSecao(rs.getString("secao"));
				e.setDataInicio(new java.util.Date(rs.getDate("data_inicio").getTime()));
				e.setDataFim(new java.util.Date(rs.getDate("data_fim").getTime()));
				e.setObras(this.carregaObrasPorExposicao(rs.getInt("id")));
				e.setgetDiasGratuitos(this.carregaDiasGratuitos(rs.getInt("id")));
				exposicoes.add(e);
				e = null;
			}
			return exposicoes;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
		
	}

	public void deletar(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			Exposicao exposicao = this.selectByPk(id);
			if (exposicao != null){
				this.deletarObras(exposicao);
				this.deletarDiasGratuitos(exposicao);
				ps = DaoUtils.getConnection().prepareStatement(DELETE);
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public Exposicao selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		MuseuDAO museuDAO = new MuseuDAO();
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT_BY_PK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Exposicao e = null;
			while (rs.next()) {
				e = new Exposicao();
				e.setId(rs.getInt("id"));
				e.setMuseu(museuDAO.selectByPk(rs.getInt("id_museu")));
				e.setNome(rs.getString("nome"));
				e.setSecao(rs.getString("secao"));
				e.setDataInicio(rs.getDate("data_inicio"));
				e.setDataFim(rs.getDate("data_fim"));
				e.setObras(this.carregaObrasPorExposicao(id));
				e.setgetDiasGratuitos(this.carregaDiasGratuitos(id));
				e.setDescricao(rs.getString("descricao"));
			}
			return e;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public boolean alterar(Exposicao exposicao) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, exposicao.getNome());
			ps.setLong(2, exposicao.getMuseu().getId());
			ps.setDate(3, new java.sql.Date(exposicao.getDataInicio().getTime()));
			ps.setDate(4, new java.sql.Date(exposicao.getDataFim().getTime()));
			ps.setString(5, exposicao.getSecao());
			ps.setString(6, exposicao.getDescricao());
			ps.setLong(7, exposicao.getId());
			
			this.alterarObras(exposicao);
			this.alterarDiasGratuitos(exposicao);
			
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
	private void alterarDiasGratuitos(Exposicao exposicao) {
		
		this.deletarDiasGratuitos(exposicao);
		this.cadastrarDiasGratuitos(exposicao);
		
	}

	private void cadastrarDiasGratuitos(Exposicao exposicao) {
		PreparedStatement ps = null;
		for (int dia : exposicao.getDiasGratuitos()) {
			try {
				ps = DaoUtils.getConnection().prepareStatement(INSERT_DIAS_GRATUITOS);
				ps.setLong(1, exposicao.getId());
				ps.setLong(2, dia);
				ps.executeUpdate();
			} catch (Exception e) {
				e.getStackTrace();
			} finally {
				DaoUtils.fechaConexoes(null, ps, null);
			}
		}
	}
	
	private void deletarDiasGratuitos(Exposicao exposicao) {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			ps = DaoUtils.getConnection().prepareStatement(DELETE_DIAS_GRATUITOS);
			ps.setInt(1, (int)exposicao.getId());
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public void alterarObras(Exposicao exposicao){
		
		this.deletarObras(exposicao);
		this.cadastrarObras(exposicao);
		
	}
	
	public void deletarObras(Exposicao exposicao){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			ps = DaoUtils.getConnection().prepareStatement(DELETE_OBRAS);
			ps.setInt(1, (int)exposicao.getId());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Obra> carregaObrasPorExposicao(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ObraDAO obraDAO = new ObraDAO();
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT_BY_PK_OBRAS);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Obra> obras = null;
			while (rs.next()) {
				if (obras == null)
					obras = new ArrayList<>();
				obras.add(obraDAO.selectByPk(rs.getInt("id_obra")));
			}
			return obras;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
	public ArrayList<Integer> carregaDiasGratuitos(int id){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT_BY_PK_DIAS_GRATUITOS);
			ps.setLong(1, id);
			ResultSet rs = ps.executeQuery();
			ArrayList<Integer> diasGratuitos = null;
			while (rs.next()) {
				if (diasGratuitos == null)
					diasGratuitos = new ArrayList<>();
				diasGratuitos.add(rs.getInt("dia_semana"));
			}
			return diasGratuitos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

}