package com.easyhomeconta.dao;

import javax.inject.Named;
import javax.persistence.Query;

import com.easyhomeconta.model.User;
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
		
	    Query query = entityManager.createQuery("select u FROM User u "
	    		+ "join fetch u.lstRoles where "
	    		+ " u.username= :username");
	    query.setParameter("username", username);
	    User user = (User) query.getSingleResult();
	    
	    return user;
	}
}
