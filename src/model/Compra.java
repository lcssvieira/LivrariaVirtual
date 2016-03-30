package model;

import java.text.ParseException;
import java.util.Date;

import utils.MuseuUtils;

public class Emprestimo {
	private long id;
	private Museu museu;
	private Obra obra;
	private Date dataInicio, dataFim;
	private String dataInicioFormatada, dataFimFormatada, descricao;
	
	
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public Museu getMuseu() {
		return museu;
	}
	public void setMuseu(Museu museu) {
		this.museu = museu;
	}
	public Obra getObra() {
		return obra;
	}
	public void setObra(Obra obra) {
		this.obra = obra;
	}
	public Date getDataInicio() {
		return dataInicio;
	}
	public void setDataInicio(Date dataInicio) throws ParseException {
		this.dataInicio = dataInicio;
		setDataInicioFormatada(MuseuUtils.converteDateEmString(dataInicio));
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) throws ParseException {
		this.dataFim = dataFim;
		setDataFimFormatada(MuseuUtils.converteDateEmString(dataFim));
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
