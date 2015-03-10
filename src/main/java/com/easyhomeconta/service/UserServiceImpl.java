package com.easyhomeconta.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.User;
/**
 * 
 * @author Alberto
 *
 */
@Named
public class UserServiceImpl implements UserService {

	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(UserServiceImpl.class);
	
	@Inject
	private UserDao userDao;
	
	
	@Override
	public List<User> findAllUsers() {
		List<User> lstUsers=userDao.findAll();
		return lstUsers;
				
	}
	
	@Override
	@Transactional
	public void createUser(User user) {
		userDao.create(user);
	}

	@Override
	public User getUser(Integer id) {
		User user=userDao.findById(id);
		return user;
	}

	@Override
	@Transactional
	public void deleteUser(Integer id) {
		userDao.delete(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {
		userDao.update(user);
	}

	/**
	 * Se sobre escribe el metodo de la interface UserDetailsService de la que extiende la interface
	 * creada por mi UserService.
	 */
	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
		User usuario=userDao.loadUserByUsername(username);
		return usuario;
	}

	

}
