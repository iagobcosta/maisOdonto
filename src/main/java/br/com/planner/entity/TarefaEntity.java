package br.com.planner.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_tarefa")
@NamedQueries({

		@NamedQuery(name = "TarefaEntity.findAll", query = "SELECT t FROM TarefaEntity t JOIN FETCH t.usuarioEntity WHERE t.usuarioEntity.id = :userId ORDER BY t.dataCriacao asc")

})
public class TarefaEntity implements Serializable {

	private static final long serialVersionUID = 1184556906266095982L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Integer id;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descricao")
	private String descricao;

	@Column(name = "prioridade")
	private String prioridade;

	@Column(name = "data_criacao")
	private Date dataCriacao;

	@JoinColumn
	@ManyToOne
	private UsuarioEntity usuarioEntity;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public UsuarioEntity getUsuarioEntity() {
		return usuarioEntity;
	}

	public void setUsuarioEntity(UsuarioEntity usuarioEntity) {
		this.usuarioEntity = usuarioEntity;
	}

}
