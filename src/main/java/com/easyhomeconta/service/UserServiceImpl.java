package com.easyhomeconta.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.Rol;
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
		//Le insertamos fecha de creacion
		user.setFechaCreacion(new Date());
		
		//Por defecto, creamos al usuario con los atributos a true
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setEnabled(true);
		user.setCredentialsNonExpired(true);
		userDao.create(user);
		
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

	@Override
	public List<Rol> findAllRoles() {
		List<Rol> lstRoles=userDao.findAllRoles();
		return lstRoles;
	}

	/**
	 * Devuelve true si el username ya esta elegido y false en caso contrario.
	 */
	@Override
	public Boolean isUsernameInDB(String username) {
		List<User> lstUsers=userDao.findUsersbyUsername(username);
		if (lstUsers.isEmpty())
			return false;
		else
			return true;
	}
	/**
	 * Devuelve el usuario con id pasado como parametro
	 */
	@Override
	public User getUserById(Integer id) {
		User user=userDao.findById(id);
		return user;
	}

}
