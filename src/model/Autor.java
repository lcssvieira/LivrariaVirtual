package model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import utils.MuseuUtils;

public class Autor {
	private long id;
	private String nome, secao, descricao, listaObras, listaIds;
	private Date dataInicio, dataFim;
	private String dataInicioFormatada, dataFimFormatada;

	public String getDataInicioFormatada() {
		return dataInicioFormatada;
	}

	public void setDataInicioFormatada(String dataInicioFormatada) {
		this.dataInicioFormatada = dataInicioFormatada;
	}

	public String getDataFimFormatada() {
		return dataFimFormatada;
	}

	public void setDataFimFormatada(String dataFimFormatada) {
		this.dataFimFormatada = dataFimFormatada;
	}

	private ArrayList<Integer> diasGratuitos;

	public String getSecao() {
		if (secao != null)
			return secao;
		else
			return "";
	}

	public void setSecao(String secao) {
		this.secao = secao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) throws ParseException {
		this.dataInicio = dataInicio;
		this.setDataInicioFormatada(MuseuUtils.converteDateEmString(dataInicio));
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) throws ParseException {
		this.dataFim = dataFim;
		this.setDataFimFormatada(MuseuUtils.converteDateEmString(dataFim));
	}

	public ArrayList<Integer> getDiasGratuitos() {
		return diasGratuitos;
	}

	public void setgetDiasGratuitos(ArrayList<Integer> diasGratuitos) {
		this.diasGratuitos = diasGratuitos;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getListaObras() {
		return listaObras;
	}

	public void setListaObras(String listaObras) {
		this.listaObras = listaObras;
	}

	public String getListaIds() {
		return listaIds;
	}

	public void setListaIds(String listaIds) {
		this.listaIds = listaIds;
	}

}
