package model;

import java.text.ParseException;
import java.util.Date;

import utils.MuseuUtils;

public class Carrinho {

	private long id;
	private Cliente cliente;
	private Date data;
	private String dataFormatada;
	private Double valor;
	private int desconto;
	private TipoDesconto tipoDesconto;
	private Pedido venda;
	
	public Pedido getVenda() {
		return venda;
	}
	public void setVenda(Pedido venda) {
		this.venda = venda;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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