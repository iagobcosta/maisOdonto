package br.com.planner.repository;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import br.com.planner.entity.UsuarioEntity;
import br.com.planner.model.UsuarioModel;
import br.com.planner.uteis.Uteis;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 223265077565433418L;

	EntityManager entityManager;

	public UsuarioEntity validaUsuario(UsuarioModel usuarioModel) {

		try {

			Query query = Uteis.JpaEntityManager().createNamedQuery("UsuarioEntity.findUser");

			query.setParameter("usuario", usuarioModel.getUsuario());
			query.setParameter("senha", usuarioModel.getSenha().trim());

			return (UsuarioEntity) query.getSingleResult();

		} catch (Exception e) {

			return null;
		}
	}

	/***
	 * MÉTODO RESPONSÁVEL POR SALVAR UM USUARIO
	 * 
	 * @param pessoaModel
	 */
	public void salvarNovoUsuario(UsuarioModel usuarioModel) {

		UsuarioEntity usuarioEntity;

		entityManager = Uteis.JpaEntityManager();

		usuarioEntity = new UsuarioEntity();

		usuarioEntity.setLogin(usuarioModel.getLogin());
		usuarioEntity.setSenha(usuarioModel.getSenha());
		usuarioEntity.setUsuario(usuarioModel.getUsuario());

		entityManager.persist(usuarioEntity);
		entityManager.flush();

	}

	public void excluirUsuario(int id) {
		entityManager = Uteis.JpaEntityManager();

		UsuarioEntity usuarioEntity = obterIdUsuario(id);
		entityManager.remove(usuarioEntity);
	}
	
	

	public String convertStringToMd5(String valor) {
		MessageDigest mDigest;
		try {
			mDigest = MessageDigest.getInstance("MD5");

			byte[] valorMD5 = mDigest.digest(valor.getBytes("UTF-8"));

			StringBuffer sb = new StringBuffer();
			for (byte b : valorMD5) {
				sb.append(Integer.toHexString((b & 0xFF) | 0x100).substring(1, 3));
			}

			return sb.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<UsuarioModel> getTadasOsUsuarios() {

		List<UsuarioModel> usuariosModel = new ArrayList<UsuarioModel>();

		entityManager = Uteis.JpaEntityManager();

		Query query = entityManager.createNamedQuery("UsuarioEntity.findAll");

		@SuppressWarnings("unchecked")
		Collection<UsuarioEntity> usuariosEntity = (Collection<UsuarioEntity>) query.getResultList();

		UsuarioModel usuarioModel = null;

		for (UsuarioEntity usuarioEntity : usuariosEntity) {

			usuarioModel = new UsuarioModel();
			usuarioModel.setId(usuarioEntity.getId());
			usuarioModel.setLogin(usuarioEntity.getLogin());
			usuarioModel.setSenha(usuarioEntity.getSenha());
			usuarioModel.setUsuario(usuarioEntity.getUsuario());

			usuariosModel.add(usuarioModel);

		}
		return usuariosModel;

	}

	public void editarUsuario(UsuarioModel usuarioModel) {
		entityManager = Uteis.JpaEntityManager();

		UsuarioEntity usuarioEntity = obterIdUsuario(usuarioModel.getId());

		usuarioEntity.setLogin(usuarioModel.getLogin());
		usuarioEntity.setUsuario(usuarioModel.getUsuario());
		usuarioEntity.setSenha(usuarioModel.getSenha().trim());

		entityManager.merge(usuarioEntity);

	}

	private UsuarioEntity obterIdUsuario(int id) {

		entityManager = Uteis.JpaEntityManager();

		return entityManager.find(UsuarioEntity.class, id);
	}

}
