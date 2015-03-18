package com.easyhomeconta.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
/**
 * 
 * @author Alberto
 * Extiende de UserDetailsService para realizar la gestion del login mediante bd desde la implemetacion de 
 * esta interface.
 */
public interface UserService extends UserDetailsService{

	public List<User> findAllUsers();
	public List<Rol> findAllRoles();
	public void createUser(User user);
	public void updateUser(User user);
	public void deleteUser(Integer id);
}
