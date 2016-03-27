package br.com.cursojsf.ejb.login;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
	
	@PostConstruct
	private void initializa(){
		/*
		 * It will be executed before any other method 
		 */
		System.out.println("Method init [LoginBean] invoked at " + new java.util.Date());
	}
	
	@PreDestroy
	private void destroy(){
		/*
		 * 
		 */
		System.out.println("Finalizing LoginBean at " + new java.util.Date());
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean autenticar(String email, String senha) throws Exception {		
		Query query = em.createNamedQuery("Usuario.findByEmailSenha");
		query.setParameter("email", email);
		query.setParameter("senha", senha);
		List<Usuario> usuarios = query.getResultList();
		if(usuarios != null && !usuarios.isEmpty()){
			Usuario user = usuarios.get(0);
			if(!user.equals(new Usuario())){
				user.setDataLogin(new Date());
				return true;
			}
		}
		return false;
	}

	public boolean remoteLogin(String email, String senha) throws Exception {
		
		return autenticar(email, senha);
	}
	
}
