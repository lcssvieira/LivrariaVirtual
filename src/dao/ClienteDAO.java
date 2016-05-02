package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Cliente;

public class ClienteDAO {
	private static String tbName = "tb_cliente";
	private static String tbUsuario = "tb_usuario";
	private static String SELECT = String.format("SELECT * FROM %1$s, %2$s where %1$s.id_usuario = %2$s.id",ClienteDAO.tbName,ClienteDAO.tbUsuario);
	private static String SELECTBYPK = String.format("SELECT * FROM %1$s, %2$s WHERE %1$s.id=? AND %1$s.id_usuario = %2$s.id", ClienteDAO.tbName, ClienteDAO.tbUsuario);
	private static String INSERT = String.format("INSERT INTO %1$s (rg, cpf, estado_civil, logradouro, cep, endereco, numero, complemento, bairro, cidade,uf,sexo,data_nascimento) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)", ClienteDAO.tbName);
	private static String DELETE = String.format("DELETE FROM %1$s where id = ?", ClienteDAO.tbName);
	private static String UPDATE = String.format("UPDATE %1$s SET rg = ?, cpf=?, estado_civil =?, logradouro=?, cep = ?, endereco =?, numero=?, complemento =? , bairro=?,cidade = ?, uf=?,sexo=?,data_nascimento =? where id = ?", ClienteDAO.tbName);

	public boolean inserir(Cliente cliente) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setString(1, cliente.getRg());
			ps.setString(2, cliente.getCpf());
			ps.setString(3, cliente.getEstadoCivil());
			ps.setString(4, cliente.getLogradouro());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getEndereco());
			ps.setString(7, cliente.getNumero());
			ps.setString(8, cliente.getComplemento());
			ps.setString(9, cliente.getBairro());
			ps.setString(10, cliente.getCidade());
			ps.setString(11, cliente.getUf());
			ps.setString(12, cliente.getSexo());
			ps.setDate(13, new java.sql.Date(cliente.getDataNascimento().getTime()));
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}

	public ArrayList<Cliente> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Cliente> clientes = new ArrayList<>();
		while (rs.next()) {
			Cliente cliente = new Cliente();
			cliente.setId(rs.getInt("id"));
			cliente.setId_usuario(rs.getInt("id_usuario"));
			cliente.setRg(rs.getString("rg"));
			cliente.setNome(rs.getString("nome"));
			cliente.setCpf(rs.getString("cpf"));
			cliente.setEstadoCivil(rs.getString("estado_civil"));
			cliente.setLogradouro(rs.getString("logradouro"));
			cliente.setCep(rs.getString("cep"));
			cliente.setEndereco(rs.getString("endereco"));
			cliente.setNumero(rs.getString("numero"));
			cliente.setComplemento(rs.getString("complemento"));
			cliente.setBairro(rs.getString("bairro"));
			cliente.setCidade(rs.getString("cidade"));
			cliente.setUf(rs.getString("uf"));
			cliente.setSexo(rs.getString("sexo"));
			cliente.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
			clientes.add(cliente);
			cliente = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return clientes;
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

	public Cliente selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Cliente cliente = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setId_usuario(rs.getInt("id_usuario"));
				cliente.setRg(rs.getString("rg"));
				cliente.setNome(rs.getString("nome"));
				cliente.setCpf(rs.getString("cpf"));
				cliente.setEstadoCivil(rs.getString("estado_civil"));
				cliente.setLogradouro(rs.getString("logradouro"));
				cliente.setCep(rs.getString("cep"));
				cliente.setEndereco(rs.getString("endereco"));
				cliente.setNumero(rs.getString("numero"));
				cliente.setComplemento(rs.getString("complemento"));
				cliente.setBairro(rs.getString("bairro"));
				cliente.setCidade(rs.getString("cidade"));
				cliente.setUf(rs.getString("uf"));
				cliente.setSexo(rs.getString("sexo"));
				cliente.setDataNascimento(new java.util.Date(rs.getDate("data_nascimento").getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return cliente;
	}

	public boolean atualizar(Cliente cliente) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setString(1, cliente.getRg());
			ps.setString(2, cliente.getCpf());
			ps.setString(3, cliente.getEstadoCivil());
			ps.setString(4, cliente.getLogradouro());
			ps.setString(5, cliente.getCep());
			ps.setString(6, cliente.getEndereco());
			ps.setString(7, cliente.getNumero());
			ps.setString(8, cliente.getComplemento());
			ps.setString(9, cliente.getBairro());
			ps.setString(10, cliente.getCidade());
			ps.setString(11, cliente.getUf());
			ps.setString(12, cliente.getSexo());
			ps.setDate(13, new java.sql.Date(cliente.getDataNascimento().getTime()));
			ps.setLong(14, cliente.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
