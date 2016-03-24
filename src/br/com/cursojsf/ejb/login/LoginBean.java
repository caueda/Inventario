package br.com.cursojsf.ejb.login;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import br.com.cursojsf.entities.Usuario;

@Named
@Stateless
public class LoginBean implements Serializable {
	
	private static final long serialVersionUID = -1466922676838989315L;

	@PersistenceContext(unitName="inventario")
	private EntityManager em;
	
	public boolean autenticar(String email, String senha) throws Exception {		
		Query query = em.createNamedQuery("usuario.findByEmailSenha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		List<Usuario> usuario = query.getResultList();
		return (!usuario.isEmpty()) && (!usuario.get(0).equals(new Usuario()));
	}

	public boolean remoteLogin(String email, String senha) throws Exception {
		
		return autenticar(email, senha);
	}
	
}
