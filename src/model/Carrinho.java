package model;

import java.text.ParseException;
import java.util.Date;

import utils.MuseuUtils;

public class Ingresso {

	private long id;
	private Visitante cliente;
	private Exposicao exposicao;
	private Date data;
	private String dataFormatada;
	private Double valor;
	private int desconto;
	private TipoDesconto tipoDesconto;
	private Venda venda;
	
	public Venda getVenda() {
		return venda;
	}
	public void setVenda(Venda venda) {
		this.venda = venda;
	}
	public Visitante getCliente() {
		return cliente;
	}
	public void setCliente(Visitante cliente) {
		this.cliente = cliente;
	}
	public Exposicao getExposicao() {
		return exposicao;
	}
	public void setExposicao(Exposicao exposicao) {
		this.exposicao = exposicao;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) throws ParseException {
		this.data = data;
		this.dataFormatada = MuseuUtils.converteDateEmString(data);
	}
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public TipoDesconto getTipoDesconto() {
		return tipoDesconto;
	}
	public void setTipoDesconto(TipoDesconto tipoDesconto) {
		this.tipoDesconto = tipoDesconto;
	}
	
	public void setDesconto(int desconto) {
		this.desconto = desconto;
	}
	
	public int getDesconto() {
		return desconto;
	}
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDataFormatada(){
		
		return this.dataFormatada;
		
	}
	
}