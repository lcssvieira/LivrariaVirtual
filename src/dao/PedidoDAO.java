package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.FormaPagamento;
import model.Pedido;

public class PedidoDAO {

	private static String SELECT = "SELECT * FROM tb_venda";
	private static String SELECTBYPK = "SELECT * FROM tb_venda WHERE id=?";
	private static String INSERT = "INSERT INTO tb_venda (data, forma_pagamento) VALUES(?,?)"; 
	private static String DELETE = "DELETE FROM tb_venda where id = ?";
	private static String UPDATE = "UPDATE tb_venda SET data = ?, forma_pagamento = ? where id = ?";
	private static String DELETE_INGRESSOS = "DELETE FROM tb_ingresso WHERE id_venda = ?";
	public boolean inserir(Pedido venda){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setDate(1,new java.sql.Date(venda.getData().getTime()));
			ps.setLong(2, venda.getFormaPagamento().ordinal());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
	public boolean atualizar(Pedido venda) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setDate(1, new java.sql.Date(venda.getData().getTime()));
			ps.setDouble(2, venda.getFormaPagamento().ordinal());
			ps.setLong(3, venda.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
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
	
	public ArrayList<Pedido> carregaLista() throws Exception {
		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(SELECT);
		ResultSet rs = ps.executeQuery();
		ArrayList<Pedido> vendas = new ArrayList<>();
		while (rs.next()) {
			Pedido venda = new Pedido();
			venda.setId(rs.getLong("id"));
			venda.setData(new java.util.Date(rs.getDate("data").getTime()));
			venda.setFormaPagamento(FormaPagamento.values()[rs.getInt("forma_pagamento")]);
			vendas.add(venda);
			venda = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		return vendas;
	}
	
	public Pedido selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Pedido venda = null;
		try {
			conn = DaoUtils.getConnection();
			ps = conn.prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			if (rs.next()) {
				venda = new Pedido();
				venda.setId(id);
				venda.setData(new java.util.Date(rs.getDate("data").getTime()));
				venda.setFormaPagamento(FormaPagamento.values()[rs.getInt("forma_pagamento")]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, rs);
		}
		return venda;
	}
	
	public void deletarIngressos(Pedido venda){
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			ps = DaoUtils.getConnection().prepareStatement(DELETE_INGRESSOS);
			ps.setInt(1, (int)venda.getId());

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
}
