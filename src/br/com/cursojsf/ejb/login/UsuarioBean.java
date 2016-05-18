package br.com.cursojsf.ejb.login;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import br.com.cursojsf.entities.Usuario;

@Named
@TransactionManagement(TransactionManagementType.CONTAINER)
@Stateless
public class UsuarioBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4344896204368371422L;
	@PersistenceContext(unitName="inventario")
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Usuario> listAll(){
		return em.createNamedQuery("Usuario.listAll").getResultList();
	}
	
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Usuario saveOrUpdate(Usuario usuario) {
		em.persist(usuario);
		return usuario;
	}	
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public Usuario loadById(Long idUsuario) {
		Query query = em.createNamedQuery("Usuario.findById");
		query.setParameter("idUsuario", idUsuario);
		return (Usuario)query.getSingleResult();
	}
	
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
	public List<Usuario> list(Usuario usuarioPesquisa){
		StringBuilder sql = new StringBuilder("select vo from " + Usuario.class.getName() + " vo where 1=1 ");
		if(StringUtils.isNotBlank(usuarioPesquisa.getNome())) {
			sql.append(" and lower(vo.nome) like lower(:nome) ");
		} 
		if(StringUtils.isNotBlank(usuarioPesquisa.getEmail())) {
			sql.append(" and lower(vo.email) = lower(:email) ");
		}
		Query query = em.createQuery(sql.toString());
		if(StringUtils.isNotBlank(usuarioPesquisa.getNome())) {
			query.setParameter("nome", usuarioPesquisa.getNome());
		} 
		if(StringUtils.isNotBlank(usuarioPesquisa.getEmail())) {
			query.setParameter("email", usuarioPesquisa.getEmail());
		}
		
		return (List<Usuario>)query.getResultList();
	}
}

