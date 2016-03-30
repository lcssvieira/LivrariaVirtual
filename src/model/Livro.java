package model;

import java.util.Date;

import utils.MuseuUtils;

public class Obra {
	private long id;
	private String nome, descricao;
	private String artista, tipo, periodo;
	private Date data;
	private String dataFormatada;
	private double valorEstimado;
	private int fl_emprestado;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String obra) {
		this.nome = obra;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getArtista() {
		return artista;
	}
	public void setArtista(String artista) {
		this.artista = artista;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getPeriodo() {
		return periodo;
	}
	public void setPeriodo(String periodo) {
		this.periodo = periodo;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getDataFormatada() throws Exception {
		dataFormatada = MuseuUtils.converteDateEmString(data);
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	public double getValorEstimado() {
		return valorEstimado;
	}
	public void setValorEstimado(double valorEstimado) {
		this.valorEstimado = valorEstimado;
	}
	public int getFl_emprestado() {
		return fl_emprestado;
	}
	public void setFl_emprestado(int fl_emprestado) {
		this.fl_emprestado = fl_emprestado;
	}
	
}
