package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.Enumeraciones.SiNo;
/**
 * 
 * @author Alberto
 *
 */
@Named
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	private static final long serialVersionUID = 7680494513108369255L;
	
	@Override
	public User loadUserByUsername(String username) {
	    Query query = entityManager.createQuery("select u from User u where "
	    		+ " u.username= :username");
	    query.setParameter("username", username);
	    User user = (User) query.getSingleResult();
	    return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findAll(){
		Query query = entityManager.createQuery("select u from User u where "
				+ " u.enabled = :enabled"
				+ " and u.accountNonExpired = :aExpired "
				+ " and u.accountNonLocked = :locked "
				+ " and u.credentialsNonExpired = :cExpired");
		query.setParameter("enabled", true);
		query.setParameter("aExpired", true);
		query.setParameter("locked", true);
		query.setParameter("cExpired", true);
		List<User> lstUser=(List<User>) query.getResultList();
		return lstUser;
	};
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> findAllRoles() {
		Query query=entityManager.createQuery("select r from Rol r");
		List<Rol> lstRoles=query.getResultList();
		return lstRoles;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersbyUsername(String username) {
		Query query = entityManager.createQuery("select u from User u where "
	    		+ " u.username= :username");
	    query.setParameter("username", username);
	    List<User> lstUsers = (List<User>) query.getResultList();
	    return lstUsers;

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUsersByProducto(Integer idProducto) {
		
		Query query=entityManager.createQuery(" select u from User u "
				+ " join fetch u.lstProductos as p" 
				+ " where p.idProducto = :idProducto"
				+ " and p.baja = :baja");
		
		query.setParameter("idProducto", idProducto);
		query.setParameter("baja", SiNo.N);
		List<User> lstUsers=(List<User>)query.getResultList();
		
		return lstUsers;
	}

	@Override
	public void deleteUser(User user) {
		entityManager.remove(user);
	}
}
