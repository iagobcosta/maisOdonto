package br.com.planner.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.planner.entity.TarefaEntity;
import br.com.planner.entity.UsuarioEntity;
import br.com.planner.model.TarefaModel;
import br.com.planner.model.UsuarioModel;
import br.com.planner.uteis.Uteis;

public class TarefaRepository implements Serializable {

	private static final long serialVersionUID = -5361771555822652962L;
	EntityManager entityManager;

	/***
	 * MÉTODO PARA CONSULTAR A TAREFA DO USUARIO
	 * 
	 * @return
	 */
	public List<TarefaModel> getTadasAsTarefas(Integer usuarId) {

		List<TarefaModel> tarefasModel = new ArrayList<TarefaModel>();

		entityManager = Uteis.JpaEntityManager();

		Query query = entityManager.createNamedQuery("TarefaEntity.findAll").setParameter("userId", usuarId);

		@SuppressWarnings("unchecked")
		Collection<TarefaEntity> tarefasEntity = (Collection<TarefaEntity>) query.getResultList();

		TarefaModel tarefaModel = null;

		for (TarefaEntity tarefaEntity : tarefasEntity) {

			tarefaModel = new TarefaModel();
			tarefaModel.setId(tarefaEntity.getId());
			tarefaModel.setDataCriacao(tarefaEntity.getDataCriacao());
			tarefaModel.setTitulo(tarefaEntity.getTitulo());
			tarefaModel.setDescricao(tarefaEntity.getDescricao());
			tarefaModel.setPrioridade(tarefaEntity.getPrioridade());

			UsuarioEntity usuarioEntity = tarefaEntity.getUsuarioEntity();

			UsuarioModel usuarioModel = new UsuarioModel();
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			tarefaModel.setUsuarioModel(usuarioModel);
			tarefasModel.add(tarefaModel);

		}
		return tarefasModel;

	}

	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UMA NOVA TAREFA
	 * 
	 * @param pessoaModel
	 */
	public void salvarNovaTarefa(TarefaModel tarefaModel) {

		entityManager = Uteis.JpaEntityManager();

		TarefaEntity tarefaEntity;

		tarefaEntity = new TarefaEntity();
		tarefaEntity.setDataCriacao(new Date());
		tarefaEntity.setTitulo(tarefaModel.getTitulo());
		tarefaEntity.setDescricao(tarefaModel.getDescricao());
		tarefaEntity.setPrioridade(tarefaModel.getPrioridade());

		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, tarefaModel.getUsuarioModel().getId());

		tarefaEntity.setUsuarioEntity(usuarioEntity);

		entityManager.persist(tarefaEntity);		

		entityManager.flush();

	}

	public void excluirTarefa(int id) {
		entityManager = Uteis.JpaEntityManager();

		TarefaEntity tarefaEntity = obterIdTarefa(id);
		entityManager.remove(tarefaEntity);

	}
	
	public void editarTarefa(TarefaModel tarefaModel){
		 
		entityManager =  Uteis.JpaEntityManager();
 
		TarefaEntity tarefaEntity = obterIdTarefa(tarefaModel.getId());
 
		tarefaEntity.setTitulo(tarefaModel.getTitulo());
		tarefaEntity.setDescricao(tarefaModel.getDescricao());
		tarefaEntity.setPrioridade(tarefaModel.getPrioridade());
		tarefaEntity.setDataCriacao(new Date());
		
		UsuarioEntity usuarioEntity = entityManager.find(UsuarioEntity.class, tarefaModel.getUsuarioModel().getId());

		tarefaEntity.setUsuarioEntity(usuarioEntity);
 
		entityManager.merge(tarefaEntity);
	}

	private TarefaEntity obterIdTarefa(int id) {

		entityManager = Uteis.JpaEntityManager();

		return entityManager.find(TarefaEntity.class, id);
	}
}
