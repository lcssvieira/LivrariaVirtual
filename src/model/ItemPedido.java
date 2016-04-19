package model;

public class ItemPedido {

	private long id;
	private Livro livro;
	private int quantidade;
	private Double percDesconto;
	
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}
	public Double getPercDesconto() {
		return percDesconto;
	}
	public void setPercDesconto(Double percDesconto) {
		this.percDesconto = percDesconto;
	}
	
}