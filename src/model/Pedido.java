package model;

import java.util.ArrayList;
import java.util.Date;
import utils.MuseuUtils;

public class Pedido {

	private long id;
	private Date data;
	private String dataFormatada,  listaIngressos,listaIds;
	private FormaPagamento formaPagamento;	
	private ArrayList<Livro> itens;
	
	public ArrayList<Livro> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Livro> itens) {
		this.itens = itens;
	}


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}


	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return id;
	}
	
	public String getDataFormatada() throws Exception {
		dataFormatada = MuseuUtils.converteDateEmString(data);
		return dataFormatada;
	}
	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}
	
	public String getListaIds() {
		return listaIds;
	}

	public void setListaIds(String listaIds) {
		this.listaIds = listaIds;
	}
	
	public String getListaIngressos() {
		return listaIngressos;
	}

	public void setListaObras(String listaIngressos) {
		this.listaIngressos = listaIngressos;
	}
}
