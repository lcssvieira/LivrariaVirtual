package model;

public enum TipoDesconto {
	ESTUDANTE(50), IDOSO(50);

	private int valor;

	private TipoDesconto(int valor) {
		this.valor = valor;
	}

	public int getValor() {
		return valor;
	}
}


