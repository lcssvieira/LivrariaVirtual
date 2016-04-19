package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.ItemPedido;
import model.TipoDesconto;


public class ItemPedidoDAO {
	
	private static String SELECT = "SELECT * FROM tb_ingresso";
	private static String SELECTBYPK = "SELECT * FROM tb_ingresso WHERE id=?";
	private static String INSERT = "INSERT INTO tb_ingresso (id_venda, id_cliente, id_exposicao,data,valor,tipo_desconto,deconto) VALUES(?,?,?,?,?,?,?)"; 
	private static String DELETE = "DELETE FROM tb_ingresso where id = ?";
	private static String UPDATE = "UPDATE tb_ingresso SET id_venda = ?, id_cliente = ?, id_exposicao = ?, data =?, valor=?, tipo_desconto =?, desconto = ? where id = ?";

	private ClienteDAO visitanteDAO;
	private PedidoDAO pedidoDAO;
	private ExposicaoDAO exposicaoDAO;
	
	public ItemPedidoDAO(){
		this.visitanteDAO = new ClienteDAO();
		this.pedidoDAO = new PedidoDAO();
		this.exposicaoDAO = new ExposicaoDAO();
	}
	
	public boolean inserir(ItemPedido ingresso){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			ps.setLong(1, ingresso.getVenda().getId());
			ps.setLong(2, ingresso.getCliente().getId());
			ps.setLong(3, ingresso.getExposicao().getId());
			ps.setDate(4, new java.sql.Date(ingresso.getData().getTime()));
			ps.setDouble(5, ingresso.getValor());
			ps.setInt(6, ingresso.getTipoDesconto().ordinal());
			ps.setInt(7, ingresso.getDesconto());
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
			ps = DaoUtils.getConnection().prepareStatement(DELETE);
			ps.setInt(1, id);

			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
		
	}
	
	public boolean alterar(ItemPedido ingresso) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(UPDATE);
			ps.setLong(1, ingresso.getVenda().getId());
			ps.setLong(2, ingresso.getCliente().getId());
			ps.setLong(3, ingresso.getExposicao().getId());
			ps.setDate(4, new java.sql.Date(ingresso.getData().getTime()));
			ps.setDouble(5, ingresso.getValor());
			ps.setInt(6, ingresso.getTipoDesconto().ordinal());
			ps.setInt(7, ingresso.getDesconto());
			ps.setLong(8, ingresso.getId());
			return ps.executeUpdate() > 0;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
	public ItemPedido selectByPK(int id){
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECTBYPK);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			ItemPedido i = null;
			while (rs.next()) {
				i = new ItemPedido();
				i.setId(rs.getInt("id"));
				i.setVenda(pedidoDAO.selectByPk(rs.getInt("id_venda")));
				i.setCliente(visitanteDAO.selectByPk(rs.getInt("id_cliente")));
				i.setExposicao(exposicaoDAO.selectByPk(rs.getInt("id_exposicao")));
				i.setData(rs.getDate("data"));
				i.setValor(rs.getDouble("valor"));
				i.setTipoDesconto(TipoDesconto.values()[rs.getInt("tipo_desconto")]);
				i.setDesconto(rs.getInt("desconto"));
			
			}

			return i;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
	public ArrayList<ItemPedido> carregaLista() {
		Connection conn = null;
		PreparedStatement ps = null;
		
		try {
			ps = DaoUtils.getConnection().prepareStatement(SELECT);
			ResultSet rs = ps.executeQuery();
			ArrayList<ItemPedido> ingressos = null;
			while (rs.next()) {
				if (ingressos == null)
					ingressos = new ArrayList<>();
				ItemPedido i = new ItemPedido();
				i.setId(rs.getInt("id"));
				i.setVenda(pedidoDAO.selectByPk(rs.getInt("id_venda")));
				i.setCliente(visitanteDAO.selectByPk(rs.getInt("id_cliente")));
				i.setExposicao(exposicaoDAO.selectByPk(rs.getInt("id_exposicao")));
				i.setData(rs.getDate("data"));
				i.setValor(rs.getDouble("valor"));
				i.setTipoDesconto(TipoDesconto.values()[rs.getInt("tipo_desconto")]);
				i.setDesconto(rs.getInt("desconto"));
				ingressos.add(i);
			}

			return ingressos;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
		}
	}
	
}
