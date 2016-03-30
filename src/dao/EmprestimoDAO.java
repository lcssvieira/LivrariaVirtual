package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Emprestimo;

public class EmprestimoDAO {
	private final static String INSERT = "INSERT INTO tb_emprestimo (id_museu,id_obra, data_inicio, data_fim, descricao) VALUES (?,?,?,?,?)";
	private final static String SELECT = "SELECT * FROM tb_emprestimo";
	private final static String SELECT_BY_PK = "SELECT * FROM tb_emprestimo WHERE id = ?";
	private final static String UPDATE = "UPDATE tb_emprestimo SET data_inicio = ?, data_fim=?, id_museu = ?, id_obra = ?, descricao = ? WHERE id=?";
	private final static String DELETE = "DELETE FROM tb_emprestimo WHERE id = ?";

	public boolean cadastrar(Emprestimo vo) {
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setLong(1, vo.getMuseu().getId());
			ps.setLong(2, vo.getObra().getId());
			ps.setDate(3, new java.sql.Date(vo.getDataInicio().getTime()));
			ps.setDate(4, new java.sql.Date(vo.getDataFim().getTime()));
			ps.setString(5, vo.getDescricao());
			boolean retorno = ps.executeUpdate() > 0;
			return retorno;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(null, ps, null);
		}
	}

	public boolean alterar(Emprestimo vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setDate(1, new java.sql.Date(vo.getDataInicio().getTime()));
			ps.setDate(2, new java.sql.Date(vo.getDataFim().getTime()));
			ps.setLong(3, vo.getMuseu().getId());
			ps.setLong(4, vo.getObra().getId());
			ps.setString(5, vo.getDescricao());
			ps.setLong(6, vo.getId());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Emprestimo> carregaLista() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			MuseuDAO museuDAO = new MuseuDAO();
			ObraDAO obraDAO = new ObraDAO();
			ArrayList<Emprestimo> emprestimos = new ArrayList<>();
			while (rs.next()) {
				Emprestimo e = new Emprestimo();
				e.setId(rs.getInt("id"));
				e.setMuseu(museuDAO.selectByPk(rs.getInt("id_museu")));
				e.setDataInicio(new java.util.Date(rs.getDate("data_inicio").getTime()));
				e.setDataFim(new java.util.Date(rs.getDate("data_fim").getTime()));
				e.setObra(obraDAO.selectByPk(rs.getInt("id_obra")));
				e.setDescricao(rs.getString("descricao"));
				emprestimos.add(e);
				e = null;
			}
			return emprestimos;
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

	public Emprestimo selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Emprestimo emprestimo = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECT_BY_PK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				emprestimo = new Emprestimo();
				emprestimo.setId(id);
				emprestimo.setDataInicio(new java.util.Date(rs.getDate("data_inicio").getTime()));
				emprestimo.setDataFim(new java.util.Date(rs.getDate("data_fim").getTime()));
				emprestimo.setDescricao(rs.getString("descricao"));
				emprestimo.setObra(new ObraDAO().selectByPk(rs.getInt("id_obra")));

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return emprestimo;
	}

}
