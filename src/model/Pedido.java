package model;

import java.util.ArrayList;
import java.util.Date;
import utils.MuseuUtils;

public class Venda {

	private long id;
	private Date data;
	private String dataFormatada,  listaIngressos,listaIds;
	private FormaPagamento formaPagamento;	
	private ArrayList<Ingresso> itens;
	
	public ArrayList<Ingresso> getItens() {
		return itens;
	}

	public void setItens(ArrayList<Ingresso> itens) {
		this.itens = itens;
	}

	public Venda() {
		// TODO Auto-generated constructor stub
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
