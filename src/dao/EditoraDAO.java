package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Editora;

public class EditoraDAO {
	private static final String CADASTRAR = "INSERT INTO tb_museu (nome, nome_responsavel, fone, fone_responsavel,"
			+ " cep, endereco, numero,complemento, estado,email, email_responsavel) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
	private static final String CONSULTA_BASICA = "SELECT id, nome, nome_responsavel, fone, fone_responsavel FROM tb_museu";
	private static final String DELETAR = "DELETE FROM tb_museu WHERE id=?";
	private static final String SELECT_BY_ID = "SELECT * FROM tb_museu WHERE id = ?";
	private static final String ALTERAR = "UPDATE tb_museu SET nome = ?, nome_responsavel=?, fone=?, fone_responsavel=?, cep=?, "
			+ "endereco=?, numero =?, complemento = ?, estado =?, email=  ?, email_responsavel=? WHERE id=?";

	public boolean cadastrar(Editora vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(CADASTRAR);
			ps.setString(1, vo.getNome());
			ps.setString(2, vo.getNomeResponsavel());
			ps.setString(3, vo.getFone());
			ps.setString(4, vo.getFoneResponsavel());
			ps.setString(5, vo.getCep());
			ps.setString(6, vo.getEndereco());
			ps.setString(7, vo.getNumero());
			ps.setString(8, vo.getComplemento());
			ps.setString(9, vo.getEstado());
			ps.setString(10, vo.getEmail());
			ps.setString(11, vo.getEmailResponsavel());

			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Editora> carregaLista() {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(CONSULTA_BASICA);
			ResultSet rs = ps.executeQuery();
			ArrayList<Editora> museus = null;
			while (rs.next()) {
				if (museus == null)
					museus = new ArrayList<>();
				Editora m = new Editora();
				m.setId(rs.getInt("id"));
				m.setNome(rs.getString("nome"));
				m.setNomeResponsavel(rs.getString("nome_responsavel"));
				m.setFone(rs.getString("fone"));
				m.setFoneResponsavel(rs.getString("fone_responsavel"));
				museus.add(m);
			}

			return museus;
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
			ps = DaoUtils.getConnection().prepareStatement(DELETAR);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public Editora selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Editora museu = null;
			while (rs.next()) {
				museu = new Editora();
				museu.setId(rs.getInt("id"));
				museu.setNome(rs.getString("nome"));
				museu.setNomeResponsavel(rs.getString("nome_responsavel"));
				museu.setFone(rs.getString("fone"));
				museu.setFoneResponsavel(rs.getString("fone_responsavel"));
				museu.setCep(rs.getString("cep"));
				museu.setComplemento(rs.getString("complemento"));
				museu.setEmail(rs.getString("email"));
				museu.setEmailResponsavel(rs.getString("email_responsavel"));
				museu.setEndereco(rs.getString("endereco"));
				museu.setEstado(rs.getString("estado"));
				museu.setNumero(rs.getString("numero"));
			}

			return museu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public boolean alterar(Editora vo) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(ALTERAR);
			ps.setString(1, vo.getNome());
			ps.setString(2, vo.getNomeResponsavel());
			ps.setString(3, vo.getFone());
			ps.setString(4, vo.getFoneResponsavel());
			ps.setString(5, vo.getCep());
			ps.setString(6, vo.getEndereco());
			ps.setString(7, vo.getNumero());
			ps.setString(8, vo.getComplemento());
			ps.setString(9, vo.getEstado());
			ps.setString(10, vo.getEmail());
			ps.setString(11, vo.getEmailResponsavel());
			ps.setLong(12, vo.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

}
