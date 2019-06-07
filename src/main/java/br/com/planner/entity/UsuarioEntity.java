package br.com.planner.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Table(name = "tb_usuario")
@Entity

@NamedQueries({

		@NamedQuery(name = "UsuarioEntity.findUser", query = "SELECT u FROM UsuarioEntity u WHERE u.usuario = :usuario AND u.senha = :senha"),

		@NamedQuery(name = "UsuarioEntity.findAll", query = "SELECT u FROM UsuarioEntity u")

})
public class UsuarioEntity implements Serializable {

	private static final long serialVersionUID = 4789869255271174681L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "login")
	private String login;

	@Column(name = "senha")
	private String senha;

	@Column(name = "usuario")
	private String usuario;

	@OneToMany	
	private List<TarefaEntity> tarefaEntity;

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
