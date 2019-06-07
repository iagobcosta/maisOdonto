package br.com.planner.model;

import java.io.Serializable;

public class UsuarioModel implements Serializable {

	private static final long serialVersionUID = 2626184852262476790L;

	private int id;

	private String login;

	private String senha;

	private String usuario;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

}
