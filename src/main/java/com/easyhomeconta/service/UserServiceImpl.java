package com.easyhomeconta.service;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.MyUtils;
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
	@PreAuthorize("hasAnyRole('Administrador')")
	public List<User> findAllUsers() {
		List<User> lstUsers=userDao.findAllActive();
		return lstUsers;			
	}
	
	@Override
	@Transactional
	public void createUser(User user) {
		//Encriptacion de contraseña
		user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
		
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
	public void saveUser(User user) {
		
		//Si no tiene id crea nuevo usuario
		if (user.getIdUser()==null){
			
			//Encriptacion de contraseña
			user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
			
			//Le insertamos fecha de creacion
			user.setFechaCreacion(new Date());
			
			//Por defecto, creamos al usuario con los atributos a true
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setEnabled(true);
			user.setCredentialsNonExpired(true);
			
			userDao.create(user);
		}
		else{
			User oldUser=userDao.findById(user.getIdUser());
			//Comparo la pass actual con la de la bd para ver si hay guardar la pass encriptada
			if (!user.getPassword().equals(oldUser.getPassword()))
				user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
			
			userDao.update(user);
		}
		
	}
	
	@Override
	@Transactional
	public void deleteUser(Integer id) {
		userDao.delete(id);
	}

	@Override
	@Transactional
	public void updateUser(User user) {

		User oldUser=userDao.findById(user.getIdUser());
		//Comparo la pass actual con la de la bd para ver si hay guardar la pass encriptada
		if (!user.getPassword().equals(oldUser.getPassword()))
			user.setPassword(MyUtils.codificarPasswordBcrypt(user.getPassword()));
		
		userDao.update(user);
	}

	
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


	@Override
	public Boolean isUsernameValido(User user) {
		List<User> lstUsers=userDao.findUsersbyUsername(user.getUsername());		 
		
		//Si es usuario nuevo
		if (user.getIdUser()==null)
			//si no se encuentra ocupado devolvemos true
			return lstUsers.isEmpty();
		else {
			//Si es un usuario existente comparo el username que tiene en base de datos antes
			User userBd=userDao.findById(user.getIdUser());
			if (userBd.getUsername().compareTo(user.getUsername())!=0)
				return lstUsers.isEmpty();
			
			return true;
		}
	}
	
	@Override
	public User getUserById(Integer id) {
		User user=userDao.findById(id);
		return user;
	}
}
