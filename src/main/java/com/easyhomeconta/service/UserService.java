package com.easyhomeconta.service;

import java.util.List;

import javax.faces.model.SelectItem;

import org.primefaces.model.UploadedFile;
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
	 * @param selectedFamilia. Familia seleccionada para realizar comprobacion antes de guardar
	 * @param cambiarFoto. Boleano que indica si se ha pulsado el boton de cambiarFoto y por tanto hay que cambiar la existente
	 * @param imagen. Imagen cargada desde la vista
	 * @param lstRoles. Lista de roles para el usuario seleccionados en la vista.
	 */
	public void saveUser(User user,Integer selectedFamilia, Boolean cambiarFoto, UploadedFile imagen, List<Rol> lstRoles);
	
	/**
	 * Elimina el usuario de la bd
	 * @param id
	 */
	public void deleteUser(Integer id);
	
	/**
	 * Comprueba si un username es valido para el usuario del formulario
	 * @param user
	 * @return
	 */
	public Boolean isUsernameValido(User user);
		
	/**
	 * Devuelve el usuario con id pasado como parametro
	 */
	public User getUserById(Integer id);
	
	/**
	 * Devuelve la lista de familiares que tiene un usuario (excluido el) para renderizarla en un selectedItems
	 * @param idUsuario
	 * @return
	 */
	public List<SelectItem> getFamiliaresForCombo(Integer idUsuario);
	
}
