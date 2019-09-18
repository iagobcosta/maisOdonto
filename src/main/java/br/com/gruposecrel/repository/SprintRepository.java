package br.com.gruposecrel.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.gruposecrel.entity.SprintEntity;
import br.com.gruposecrel.entity.UsuarioEntity;
import br.com.gruposecrel.model.SprintModel;
import br.com.gruposecrel.uteis.Uteis;

public class SprintRepository implements Serializable {

	private static final long serialVersionUID = -3648349103674465840L;

	EntityManager entityManager;

	public List<SprintModel> buscarTodasSprints() {

		List<SprintModel> lstSprint = new ArrayList<SprintModel>();

		entityManager = Uteis.JpaEntityManager();

		Query query = entityManager.createNamedQuery("SprintEntity.findAll");

		@SuppressWarnings("unchecked")
		Collection<SprintEntity> sprintsEntity = (Collection<SprintEntity>) query.getResultList();

		SprintModel sprintModel = null;

		for (SprintEntity sprintEntity : sprintsEntity) {

			sprintModel = new SprintModel();
			sprintModel.setId(sprintEntity.getId());
			sprintModel.setNome(sprintEntity.getNome());
			sprintModel.setDescricao(sprintEntity.getDescricao());
			sprintModel.setDataInicio(sprintEntity.getDataInicio());
			sprintModel.setDataFim(sprintEntity.getDataFim());
			lstSprint.add(sprintModel);
		}

		return lstSprint;

	}

	public void cadastrar(SprintModel sprintModel) {
		entityManager = Uteis.JpaEntityManager();

		SprintEntity sprintEntity;

		sprintEntity = new SprintEntity();
		sprintEntity.setNome(sprintModel.getNome());
		sprintEntity.setDescricao(sprintModel.getDescricao());
		sprintEntity.setDataInicio(sprintModel.getDataInicio());
		sprintEntity.setDataFim(sprintModel.getDataFim());
		
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, sprintModel.getUsuarioModel().getId());
		
		sprintEntity.setUsuarioEntity(usuarioEntity);
		
		entityManager.persist(sprintEntity);
		
		entityManager.flush();

	}
	
	public void excuir(Long id) {
		entityManager = Uteis.JpaEntityManager();
		
		SprintEntity sprintEntity = obterIdSprint(id);
		
		entityManager.remove(sprintEntity);
		
	}
	
	private SprintEntity obterIdSprint(Long id) {
		entityManager = Uteis.JpaEntityManager();
		return entityManager.find(SprintEntity.class, id);
	}
	
	
}
