package br.com.planner.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;

import br.com.planner.entity.UsuarioEntity;
import br.com.planner.model.UsuarioModel;
import br.com.planner.repository.UsuarioRepository;
import br.com.planner.uteis.Uteis;

@Named(value = "usuarioBean")
@SessionScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;

	private UsuarioModel usuarioModel;

	private UsuarioEntity usuario;

	private List<UsuarioModel> usuarios;

	private String senhaAtualDigitado;

	private String senhaAtual;

	private String usuarioLogado;

	boolean senhaAlterado = false;

	@PostConstruct
	public void init() {

		usuarioModel = new UsuarioModel();
		usuario = new UsuarioEntity();
		usuarios = usuarioRepository.getTadasOsUsuarios();
		inserirPrimeiroUsuario();

	}

	public void inserirPrimeiroUsuario() {
		if (usuarios.isEmpty()) {

			usuarioModel.setLogin("iagobc");
			usuarioModel.setSenha(usuarioRepository.convertStringToMd5("123").trim());
			usuarioModel.setUsuario("iago.barbosa");
			usuarioRepository.salvarNovoUsuario(usuarioModel);

		}
	}

	public void salvarUsuario() {
		try {
			if (!isUsuarioVazio()) {
				usuarioRepository.salvarNovoUsuario(usuarioModel);
				getUsuarios().add(usuarioModel);
				Uteis.MensagemInfo("Usuário cadastrado com sucesso!");
				usuarioModel = new UsuarioModel();
			} else {

				if (usuarioModel.getLogin().isEmpty()) {
					Uteis.MensagemErro("O campo login é obrigatório!");
				}
				if (usuarioModel.getUsuario().isEmpty()) {
					Uteis.MensagemErro("O campo usuário é obrigatório!");
				}
				if (usuarioModel.getSenha().isEmpty()) {
					Uteis.MensagemErro("O campo senha é obrigatório!");
				}
				Uteis.MensagemErro("Não foi possivel cadastrar o usuário!");
			}
		} catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}
	}

	public void excluirUsuario(UsuarioModel usuarioModel) {
		try {
			if (!isUsuarioVazio()) {
				usuarioRepository.excluirUsuario(usuarioModel.getId());
				usuarios.remove(usuarioModel);
				init();
				Uteis.MensagemInfo("Usuario excluído com sucesso !");
			} else {
				Uteis.MensagemInfo("Não foi possivel excluir o usuário !");
			}
		} catch (Exception e) {
			Uteis.MensagemInfo(e.toString());
		}

	}

	public UsuarioModel getUsuarioSession() {

		FacesContext facesContext = FacesContext.getCurrentInstance();

		UsuarioModel usuario = (UsuarioModel) facesContext.getExternalContext().getSessionMap()
				.get("usuarioAutenticado");

		return usuario;
	}

	public String logout() {

		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/index.xhtml?faces-redirect=true";
	}

	public String efetuarLogin() {

		UsuarioEntity usuarioEntity;

		if (StringUtils.isEmpty(usuarioModel.getUsuario()) || StringUtils.isBlank(usuarioModel.getUsuario())) {

			Uteis.Mensagem("Favor informar o login!");
			return null;
		} else if (StringUtils.isEmpty(usuarioModel.getSenha().trim())
				|| StringUtils.isBlank(usuarioModel.getSenha().trim())) {

			Uteis.Mensagem("Favor informara senha!");
			return null;
		} else {

			usuarioEntity = usuarioRepository.validaUsuario(usuarioModel);

			if (usuarioEntity != null) {

				usuarioModel.setSenha(null);
				usuarioModel.setId(usuarioEntity.getId());

				FacesContext facesContext = FacesContext.getCurrentInstance();

				facesContext.getExternalContext().getSessionMap().put("usuarioAutenticado", usuarioModel);

				usuarioLogado = getUsuarioSession().getUsuario();

				init();

				return "sistema/home?faces-redirect=true";
			} else {

				Uteis.Mensagem("Não foi possível efetuar o login com esse usuário e senha!");
				return null;
			}
		}

	}

	public void trocarSenha() {
		try {
			if (!isUsuarioVazio()) {
				if (senhaAtual.equals(senhaAtualDigitado)) {
					usuarioRepository.editarUsuario(usuarioModel);
					Uteis.MensagemInfo("Usuário alterado com sucesso!");
					usuarios.add(usuarioModel);
					this.init();
				} else {
					if (usuarioModel.getLogin().isEmpty()) {
						Uteis.MensagemErro("O campo login é obrigatório!");
					}
					if (usuarioModel.getUsuario().isEmpty()) {
						Uteis.MensagemErro("O campo usuário é obrigatório!");
					}
					Uteis.MensagemErro("A senha atual está incorreto");
					
				}
			}
		} catch (Exception e) {
			Uteis.MensagemErro(e.toString());
		}
	}

	public void editar(UsuarioModel usuarioModel) {
		senhaAtual = usuarioModel.getSenha().trim();
		this.usuarioModel = usuarioModel;

	}

	public boolean isUsuarioVazio() {
		return (usuarioModel.getLogin().isEmpty() || usuarioModel.getUsuario().isEmpty()
				|| usuarioModel.getSenha().isEmpty());
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getSenhaAtualDigitado() {
		return senhaAtualDigitado;
	}

	public void setSenhaAtualDigitado(String senhaAtualDigitado) {
		this.senhaAtualDigitado = senhaAtualDigitado;
	}

	public List<UsuarioModel> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<UsuarioModel> usuarios) {
		this.usuarios = usuarios;
	}

	public String getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(String usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
	}

	public UsuarioEntity getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioEntity usuario) {
		this.usuario = usuario;
	}

	public UsuarioModel getUsuarioModel() {
		return usuarioModel;
	}

	public void setUsuarioModel(UsuarioModel usuarioModel) {
		this.usuarioModel = usuarioModel;
	}

}