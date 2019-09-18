package br.com.gruposecrel.model;

import java.io.Serializable;
import java.util.Date;

public class SprintModel implements Serializable {

	private static final long serialVersionUID = -891798161877040426L;

	public SprintModel() {
		
	}
	
	private Long id;

	private String nome;
	private String descricao;
	private Date dataInicio;
	private Date dataFim;
	
	private UsuarioModel usuarioModel;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}
	
	
	
	
}
