package br.com.planner.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.planner.model.TarefaModel;
import br.com.planner.repository.TarefaRepository;
import br.com.planner.uteis.Uteis;

@Named(value = "tarefaBean")
@SessionScoped
public class TarefaBean implements Serializable {

	private static final long serialVersionUID = -8365406874027606596L;

	private List<TarefaModel> tarefas;

	@Inject
	private TarefaRepository tarefaRepository;

	@Inject
	private UsuarioBean usuarioBean;

	private TarefaModel tarefaModel;

	/***
	 * CARREGA AS TAREFAS NA INICIALIZAÇÃO
	 */
	@PostConstruct
	public void init() {

		tarefas = tarefaRepository.getTadasAsTarefas(usuarioBean.getUsuarioSession().getId());

		tarefaModel = new TarefaModel();

	}

	/**
	 * SALVA UM NOVO REGISTRO DE TAREFA
	 */
	public void salvarNovaTarefa() {
		try {
			if (!isTarefaVazia()) {
				tarefaModel.setUsuarioModel(usuarioBean.getUsuarioSession());
				tarefaRepository.salvarNovaTarefa(tarefaModel);
				getTarefas().add(tarefaModel);
				init();
				Uteis.MensagemInfo("Registro cadastrado com sucesso");
				tarefaModel = new TarefaModel();
			} else {
				if (tarefaModel.getTitulo().isEmpty()) {
					Uteis.MensagemErro("O campo titulo deve ser preenchido!");
				}
				if (tarefaModel.getDescricao().isEmpty()) {
					Uteis.MensagemErro("O campo descrição deve ser preenchido!");
				}
				if (tarefaModel.getPrioridade().isEmpty()) {
					Uteis.MensagemErro("O campo prioridade deve ser preenchido!");
				}
				Uteis.MensagemErro("Impossível cadastrar a tarefa!");

			}
		} catch (Exception e) {
			Uteis.MensagemErro("Impossível cadastrar a tarefa!");
		}

	}

	public void excluirTarefa(TarefaModel tarefaModel) {
		try {
			if (tarefaModel != null) {
				tarefaRepository.excluirTarefa(tarefaModel.getId());
				tarefas.remove(tarefaModel);
				init();
				Uteis.MensagemInfo("tarefa excluído com sucesso !");
			} else {
				Uteis.MensagemInfo("Não foi possivel excluir o tarefa !");
			}
		} catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}

	}

	public void editarTarefa() {
		try {
			if (!isTarefaVazia()) {
				tarefaRepository.editarTarefa(tarefaModel);
				tarefas.add(tarefaModel);
				init();
				Uteis.MensagemInfo("Tarefa excluída com sucesso!");
			} else {
				if (tarefaModel.getTitulo().isEmpty()) {
					Uteis.MensagemErro("O título deve ser preenchido!");
				}
				if (tarefaModel.getDescricao().isEmpty()) {
					Uteis.MensagemErro("A descrição deve ser preenchido!");
				}
				if (tarefaModel.getPrioridade().isEmpty()) {
					Uteis.MensagemErro("A prioridade deve ser preenchido!");
				}

				Uteis.MensagemErro("Não foi possível editar o registro!");
			}

		} catch (Exception e) {
			Uteis.MensagemErro(e.toString());
		}
	}

	public void editar(TarefaModel tarefaModel) {

		this.tarefaModel = tarefaModel;

	}
	
	public void cancelar() {
		tarefaModel = new TarefaModel();
	}

	public boolean isTarefaVazia() {
		return (tarefaModel.getTitulo().isEmpty() || tarefaModel.getDescricao().isEmpty()
				|| tarefaModel.getPrioridade().isEmpty());
	}

	public TarefaModel getTarefaModel() {
		return tarefaModel;
	}

	public void setTarefaModel(TarefaModel tarefaModel) {
		this.tarefaModel = tarefaModel;
	}

	public List<TarefaModel> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<TarefaModel> tarefas) {
		this.tarefas = tarefas;
	}

}
