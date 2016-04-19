package model;

public class Usuario {
	private long id;
	private String nome;
	private String username;
	private String senha;
	
	public Usuario(String username, String password) {
		this.username = username;
		this.senha = password;
	}

	public Usuario() {
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
}
