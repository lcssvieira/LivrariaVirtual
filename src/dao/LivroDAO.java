package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.Autor;
import model.CategoriaLivro;
import model.Editora;
import model.Livro;
import model.LivroFiltro;

public class LivroDAO {
	private static String tbLivro = "tb_livro";
	private static String tbCategorias = "tb_livros_categorias";
	private static String tbAutores = "tb_livros_autores";	
	private static String tbEditora = "tb_editora";
	private static String tbCategoria = "tb_categoria";
	private static String tbAutor = "tb_autor";	
	private static String SELECTBYPK = String.format("SELECT * FROM %1$s WHERE id=?",LivroDAO.tbLivro);
	private static String INSERT = String.format("INSERT INTO %1$s (id_editora, isbn,titulo,formato,sumario,resumo,data_publicacao, preco_custo,preco_venda, margem_lucro, quantidade_estoque,estoque_minimo, numero_paginas) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)",LivroDAO.tbLivro);
	private static String DELETE = String.format("DELETE FROM %1$s where id = ?",LivroDAO.tbLivro);
	private static String UPDATE = String.format("UPDATE %1$s SET id_editora=?, isbn=?,titulo=?,formato=?,sumario=?,resumo=?,data_publicacao=?, preco_custo=?,preco_venda=?, margem_lucro=?, quantidade_estoque=?,estoque_minimo=?, numero_paginas =? where id = ?",LivroDAO.tbLivro);
	private static String INSERTCATEGORIAS = String.format("INSERT INTO %1$s (id_livro,id_categoria) VALUES(?,?)", LivroDAO.tbCategorias);
	private static String INSERTAUTORES = String.format("INSERT INTO %1$s (id_livro,id_autor) VALUES(?,?)", LivroDAO.tbAutores);
	private static String DELETEAUTORES = String.format("DELETE FROM %1$s where id_livro = ?",LivroDAO.tbAutores);
	private static String DELETECATEGORIAS = String.format("DELETE FROM %1$s where id_livro = ?",LivroDAO.tbCategorias);
	private static String SELECTCATEGORIAS = String.format("SELECT * FROM %1$s where id_livro = ?",LivroDAO.tbCategorias);
	private static String SELECTAUTORES = String.format("SELECT * FROM %1$s where id_livro = ?",LivroDAO.tbAutores);
	
	private static String SELECT = String.format("SELECT * FROM %1$s",LivroDAO.tbLivro);
	private CategoriaLivroDAO categoriaDAO = new CategoriaLivroDAO();
	private AutorDAO autorDAO = new AutorDAO();
	
	public boolean inserir(Livro livro) throws Exception {
		Connection conn = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		int result = 0;
		try {
			ps = DaoUtils.getConnection().prepareStatement(INSERT);
			Editora  editora = livro.getEditora();
			if (editora != null) {
				// insere livro
				ps.setLong(1, editora.getId());
				ps.setString(2, livro.getIsbn());
				ps.setString(3, livro.getTitulo());
				ps.setString(4, livro.getFormato());
				ps.setString(5, livro.getSumario());
				ps.setString(6, livro.getResumo());
				ps.setDate(7,new java.sql.Date(livro.getDataPublicacao().getTime()));
				ps.setDouble(8, livro.getPrecoCusto());
				ps.setDouble(9, livro.getPrecoVenda());
				ps.setDouble(10, livro.getMargemLucro());
				ps.setInt(11, livro.getQuantidadeEstoque());
				ps.setInt(12, livro.getEstoqueMinimo());
				ps.setInt(13, livro.getNumeroPaginas());
				result = ps.executeUpdate();
				// insere categorias
				ps2 = DaoUtils.getConnection().prepareStatement(INSERTCATEGORIAS);
				for (CategoriaLivro categoria : livro.getCategorias()) {
					ps2.setLong(1, livro.getId());
					ps2.setLong(2, categoria.getId());
					result+= ps2.executeUpdate();
				}
				// insere autores
				ps3 = DaoUtils.getConnection().prepareStatement(INSERTAUTORES);
				for (Autor autor : livro.getAutores()) {
					ps3.setLong(1, livro.getId());
					ps3.setLong(2, autor.getId());
					result+= ps3.executeUpdate();
				}
			}
			return result > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps, null);
			DaoUtils.fechaConexoes(conn, ps2, null);
			DaoUtils.fechaConexoes(conn, ps3, null);
		}
	}
	
	public ArrayList<Livro> carregaLista() throws Exception {
		return carregaLista(LivroFiltro.nenhum, "");
	}

	public ArrayList<Livro> carregaLista(LivroFiltro filtro, String parametro) throws Exception {
		String statement = SELECT;
		
		if (filtro == LivroFiltro.titulo) 
			statement = String.format("%1$s WHERE titulo like '%%2$s%'", statement, parametro);
		else if (filtro == LivroFiltro.editora)
			statement = String.format("$1$d, %3$s WHERE $2$d.id_editora = %3$s.id AND %3$s.nome like '%%4$s%' ", statement, tbLivro,  tbEditora, parametro);
		else if (filtro == LivroFiltro.categoria)
			statement = String.format("%1$s, %2$s, %3$s WHERE %2$s.id = %3$s.id_livro AND %3$s.id_categoria = %4$s.id AND %4$s.descricao like '%%5$s%'  ", statement, tbLivro, tbCategorias,  tbCategoria, parametro);
		else if (filtro == LivroFiltro.autor)
			statement = String.format("%1$s, %2$s, %3$s WHERE %2$s.id = %3$s.id_livro AND %3$s.id_autor= %4$s.id AND %4$s.nome like '%%5$s%'  ", statement, tbLivro, tbAutores,  tbAutor, parametro);

		Connection conn = DaoUtils.getConnection();
		PreparedStatement ps = conn.prepareStatement(statement);
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = ps.executeQuery();
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ArrayList<Livro> livros = new ArrayList<>();
		while (rs.next()) {
			// carrega livro
			Livro livro = new Livro();
			livro.setId(rs.getInt("id"));
			livro.setIsbn(rs.getString("isbn"));
			livro.setTitulo(rs.getString("titulo"));
			livro.setFormato(rs.getString("formato"));
			livro.setSumario(rs.getString("sumario"));
			livro.setResumo(rs.getString("resumo"));
			livro.setDataPublicacao(new java.util.Date(rs.getDate("data_publicacao").getTime()));
			livro.setPrecoCusto(rs.getDouble("preco_custo"));
			livro.setPrecoVenda(rs.getDouble("preco_venda"));
			livro.setMargemLucro(rs.getDouble("margem_lucro"));
			livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
			livro.setEstoqueMinimo(rs.getInt("estoque_minimo"));
			livro.setNumeroPaginas(rs.getInt("numero_paginas"));
			
			// carrega categorias
			ArrayList<CategoriaLivro>categorias = new ArrayList<>();
			ps2 = conn.prepareStatement(SELECTCATEGORIAS);
			ps2.setLong(1,livro.getId());
			rs2 = ps2.executeQuery();
			while (rs2.next()) {
				int id  = rs2.getInt("id_categoria");
				CategoriaLivro categoria = categoriaDAO.selectByPk(id);
				categorias.add(categoria);
			}
			
			livro.setCategorias(categorias);
			
			// carrega autores
			ArrayList<Autor>autores = new ArrayList<>();
			ps3 = conn.prepareStatement(SELECTAUTORES);
			ps3.setLong(1,livro.getId());
			rs3 = ps3.executeQuery();
			while (rs3.next()) {
				int id  = rs3.getInt("id_autor");
				Autor autor = autorDAO.selectByPk(id);
				autores.add(autor);
			}
			livro.setAutores(autores);
			livros.add(livro);
			livro = null;
		}
		DaoUtils.fechaConexoes(conn, ps, rs);
		DaoUtils.fechaConexoes(conn, ps2, rs2);
		DaoUtils.fechaConexoes(conn, ps3, rs3);
		return livros;
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

	public Livro selectByPk(int id) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Livro livro = null;
		try {
			conn = DaoUtils.getConnection();
			ps1 = conn.prepareStatement(SELECTBYPK);
			ps1.setInt(1, id);
			rs = ps1.executeQuery();
			if (rs.next()) {
				livro = new Livro();
				livro.setId(rs.getInt("id"));
				livro.setIsbn(rs.getString("isbn"));
				livro.setTitulo(rs.getString("titulo"));
				livro.setFormato(rs.getString("formato"));
				livro.setSumario(rs.getString("sumario"));
				livro.setResumo(rs.getString("resumo"));
				livro.setDataPublicacao(new java.util.Date(rs.getDate("data_publicacao").getTime()));
				livro.setPrecoCusto(rs.getDouble("preco_custo"));
				livro.setPrecoVenda(rs.getDouble("preco_venda"));
				livro.setMargemLucro(rs.getDouble("margem_lucro"));
				livro.setQuantidadeEstoque(rs.getInt("quantidade_estoque"));
				livro.setEstoqueMinimo(rs.getInt("estoque_minimo"));
				livro.setNumeroPaginas(rs.getInt("numero_paginas"));
				
				// carrega categorias
				ArrayList<CategoriaLivro>categorias = new ArrayList<>();
				ps2 = conn.prepareStatement(SELECTCATEGORIAS);
				ps2.setLong(1,livro.getId());
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					CategoriaLivro categoria = categoriaDAO.selectByPk(rs2.getInt("id_categoria"));
					categorias.add(categoria);
				}
				
				livro.setCategorias(categorias);
				
				// carrega autores
				ArrayList<Autor>autores = new ArrayList<>();
				ps3 = conn.prepareStatement(SELECTAUTORES);
				ps3.setLong(1,livro.getId());
				rs3 = ps3.executeQuery();
				while (rs3.next()) {
					Autor autor = autorDAO.selectByPk(rs3.getInt("id_autor"));
					autores.add(autor);
				}
				livro.setAutores(autores);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DaoUtils.fechaConexoes(conn, ps1, rs);
			DaoUtils.fechaConexoes(conn, ps2, rs2);
			DaoUtils.fechaConexoes(conn, ps3, rs3);
		}
		return livro;
	}

	public boolean atualizar(Livro livro) {
		Connection conn = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		int result = 0;
		try {
			
			Editora  editora = livro.getEditora();
			if (editora != null) {
				
				// deleta ref categorias para inseri-las novamente
				ps2 = DaoUtils.getConnection().prepareStatement(DELETECATEGORIAS);
				ps2.setLong(1, livro.getId());
				result+= ps2.executeUpdate();
				
				// deleta ref autores para inseri-las novamente
				ps3 = DaoUtils.getConnection().prepareStatement(DELETEAUTORES);
				ps3.setLong(1, livro.getId());
				result+= ps3.executeUpdate();
				
				// atualiza livro
				ps1 = DaoUtils.getConnection().prepareStatement(UPDATE);
				ps1.setLong(1, editora.getId());
				ps1.setString(2, livro.getIsbn());
				ps1.setString(3, livro.getTitulo());
				ps1.setString(4, livro.getFormato());
				ps1.setString(5, livro.getSumario());
				ps1.setString(6, livro.getResumo());
				ps1.setDate(7,new java.sql.Date(livro.getDataPublicacao().getTime()));
				ps1.setDouble(8, livro.getPrecoCusto());
				ps1.setDouble(9, livro.getPrecoVenda());
				ps1.setDouble(10, livro.getMargemLucro());
				ps1.setInt(11, livro.getQuantidadeEstoque());
				ps1.setInt(12, livro.getEstoqueMinimo());
				ps1.setInt(13, livro.getNumeroPaginas());
				ps1.setLong(14,livro.getId());
				result = ps1.executeUpdate();
				
				// insere categorias
				ps4 = DaoUtils.getConnection().prepareStatement(INSERTCATEGORIAS);
				for (CategoriaLivro categoria : livro.getCategorias()) {
					ps4.setLong(1, livro.getId());
					ps4.setLong(2, categoria.getId());
					result+= ps4.executeUpdate();
				}
				// insere autores
				ps5 = DaoUtils.getConnection().prepareStatement(INSERTAUTORES);
				for (Autor autor : livro.getAutores()) {
					ps5.setLong(1, livro.getId());
					ps5.setLong(2, autor.getId());
					result+= ps5.executeUpdate();
				}
			}
			return result > 0;
		} catch (Exception e) {
			e.getStackTrace();
			return false;
		} finally {
			DaoUtils.fechaConexoes(conn, ps1, null);
			DaoUtils.fechaConexoes(conn, ps2, null);
			DaoUtils.fechaConexoes(conn, ps3, null);
			DaoUtils.fechaConexoes(conn, ps4, null);
			DaoUtils.fechaConexoes(conn, ps5, null);
		}
	}
}
