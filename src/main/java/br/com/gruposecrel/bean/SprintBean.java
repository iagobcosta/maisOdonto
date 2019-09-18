package br.com.gruposecrel.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.gruposecrel.model.SprintModel;
import br.com.gruposecrel.repository.SprintRepository;
import br.com.gruposecrel.uteis.Uteis;

@Named(value = "sprintBean")
@SessionScoped
public class SprintBean implements Serializable {

	private static final long serialVersionUID = 4736562518697613546L;

	private List<SprintModel> sprints;

	@Inject
	private SprintRepository sprintRepository;

	@Inject
	private UsuarioBean usuarioBean;

	private SprintModel sprintModel;

	

	@PostConstruct
	public void init() {
		
		sprints = sprintRepository.buscarTodasSprints();
		sprintModel = new SprintModel();
	}

	/**
	 * SALVANDO NOVA SPRINT
	 */
	public void salvarSprint() {
		
	}
	
	public void cadastrarSprint() {
		try {
			if(!isComposVazios()) {
				sprintModel.setUsuarioModel(usuarioBean.getUsuarioSession());
				sprintRepository.cadastrar(sprintModel);
				getSprints().add(sprintModel);
				init();
				Uteis.MensagemInfo("Sprint cadastrada com sucesso");
				sprintModel = new SprintModel();
			}else {
				Uteis.MensagemErro("preencha todos os campos!");
			}
		}catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}
		
	}
	
	public void editarSprint() {
		try {
			if(!isComposVazios()) {
				Uteis.MensagemInfo("mode de edição não implementado!!!");
			}
		}catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}
		
	}
	
	public void excluirSprint(SprintModel sprintModel) {
		try {
			if(sprintModel != null) {
				sprintRepository.excuir(sprintModel.getId());
				sprints.remove(sprintModel);
				init();
				Uteis.MensagemInfo("Sprint excluída com sucesso");
			}else {
				Uteis.MensagemErro("Não possível excluir Sprint");
			}
		}catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}
	}
	
	public String visualizarSprint() {
		return "visualizarSprint.xhtml";
	}
	
	public void editarSelecionado(SprintModel sprintModel) {
		this.sprintModel = sprintModel;
	}
	
	public void cancelar() {
		this.sprintModel = new SprintModel();
	}

	boolean isComposVazios() {
		return (sprintModel.getNome().isEmpty() || sprintModel.getDescricao().isEmpty()
				|| sprintModel.getDataInicio() == null || sprintModel.getDataFim() == null);
	}

	public List<SprintModel> getSprints() {
		return sprints;
	}

	public void setSprints(List<SprintModel> sprints) {
		this.sprints = sprints;
	}
	
	public SprintModel getSprintModel() {
		return sprintModel;
	}

	public void setSprintModel(SprintModel sprintModel) {
		this.sprintModel = sprintModel;
	}

}
