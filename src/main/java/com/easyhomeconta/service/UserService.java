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
	
	/**
	 * Carga la lista de usuarios activos. Permiso de Administrador requerido
	 * @return
	 */
	public List<User> findAllUsers();
	
	/**
	 * Carga todos los roles existentes en bd.
	 * @return
	 */
	public List<Rol> findAllRoles();
	
	/**
	 * Da de alta en bd el usuario pasado como parametro
	 * @param user
	 */
	public void createUser(User user);
	
	/**
	 * Modifica los datos en bd del usuario pasado como parametro
	 * @param user
	 */
	
	public void updateUser(User user);
	
	
	/**
	 * Crea o modifica el usuario en funcion de si el usuario pasado como parametro tiene o no id
	 * @param user
	 */
	public void saveUser(User user);
	
	/**
	 * Elimina el usuario de la bd
	 * @param id
	 */
	public void deleteUser(Integer id);
	
	/**
	 * Comprueba si un username se encuentra en la base de datos
	 * @param username
	 * @return
	 */
	public Boolean isUsernameInDB(String username);
		
	/**
	 * Devuelve el usuario con id pasado como parametro
	 */
	public User getUserById(Integer id);
	
}
