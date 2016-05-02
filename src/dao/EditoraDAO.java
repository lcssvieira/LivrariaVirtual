package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.Editora;

public class EditoraDAO {
	private static String tbName = "tb_editora";
	private static String SELECT = String.format("SELECT * FROM %1$s",EditoraDAO.tbName);
	private static String SELECTBYPK = String.format("SELECT * FROM %1$s WHERE id = ?",EditoraDAO.tbName);
	private static String INSERT = String.format("INSERT INTO %1$s VALUES (nome,cnpj,telefones,logradouro,cep,endereco,numero,complemento,bairro,cidade,uf) VALUES (?,?,?,?,?,?,?,?,?,?,?)",EditoraDAO.tbName);
	private static String DELETE = String.format("DELETE FROM %1$s WHERE id=?",EditoraDAO.tbName);
	private static String UPDATE = String.format("UPDATE %1$s SET nome=?,cnpj=?,telefones=?,logradouro=?,cep=?,endereco=?,numero=?,complemento=?,bairro=?,cidade=?,uf=? WHERE id=?",EditoraDAO.tbName);

	public boolean inserir(Editora editora) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, editora.getNome());
			ps.setString(2, editora.getCnpj());
			ps.setString(3, editora.getTelefones());
			ps.setString(4, editora.getLogradouro());
			ps.setString(5, editora.getCep());
			ps.setString(6, editora.getEndereco());
			ps.setString(7, editora.getNumero());
			ps.setString(8, editora.getComplemento());
			ps.setString(9, editora.getBairro());
			ps.setString(10, editora.getCidade());
			ps.setString(11, editora.getUf());
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
			ps = DaoUtils.getConnection().prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			ArrayList<Editora> editoras = null;
			while (rs.next()) {
				if (editoras == null)
					editoras = new ArrayList<>();
				Editora editora = new Editora();
				editora.setId(rs.getInt("id"));
				editora.setNome(rs.getString("nome"));
				editora.setCnpj(rs.getString("cnpj"));
				editora.setTelefones(rs.getString("telefones"));
				editora.setLogradouro(rs.getString("logradouro"));
				editora.setCep(rs.getString("cep"));
				editora.setEndereco(rs.getString("endereco"));
				editora.setNumero(rs.getString("numero"));
				editora.setComplemento(rs.getString("complemento"));
				editora.setBairro(rs.getString("bairro"));
				editora.setCidade(rs.getString("cidade"));
				editora.setUf(rs.getString("uf"));
				editoras.add(editora);
			}

			return editoras;
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
			ps = DaoUtils.getConnection().prepareStatement(DELETE);
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
			ps = DaoUtils.getConnection().prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			Editora museu = null;
			while (rs.next()) {
				museu = new Editora();
				Editora editora = new Editora();
				editora.setId(rs.getInt("id"));
				editora.setNome(rs.getString("nome"));
				editora.setCnpj(rs.getString("cnpj"));
				editora.setTelefones(rs.getString("telefones"));
				editora.setLogradouro(rs.getString("logradouro"));
				editora.setCep(rs.getString("cep"));
				editora.setEndereco(rs.getString("endereco"));
				editora.setNumero(rs.getString("numero"));
				editora.setComplemento(rs.getString("complemento"));
				editora.setBairro(rs.getString("bairro"));
				editora.setCidade(rs.getString("cidade"));
				editora.setUf(rs.getString("uf"));
			}

			return museu;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public boolean atualizar(Editora editora) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, editora.getNome());
			ps.setString(2, editora.getCnpj());
			ps.setString(3, editora.getTelefones());
			ps.setString(4, editora.getLogradouro());
			ps.setString(5, editora.getCep());
			ps.setString(6, editora.getEndereco());
			ps.setString(7, editora.getNumero());
			ps.setString(8, editora.getComplemento());
			ps.setString(9, editora.getBairro());
			ps.setString(10, editora.getCidade());
			ps.setString(11, editora.getUf());
			ps.setLong(12, editora.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

}
