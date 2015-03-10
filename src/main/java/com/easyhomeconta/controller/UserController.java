/**
 * Gestion de Usuarios
 */
package com.easyhomeconta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.easyhomeconta.model.User;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@Named(value="userBean")
@RequestScoped
public class UserController {

	private final Logger log = Logger.getLogger(UserController.class);
	
	@Inject
	private UserService userService;
	
	private List<User> lstUsers=new ArrayList<User>();
	
	private User selectedUser= new User();
	private User user;
	
	private Boolean selectedRow;

	/**
	 * Recibe el usuario al que pertenece el boton editar de la fila de la tabla y lo carga en el formulario 
	 * @param usuario
	 * @return
	 */
	public String doLoadForm(User usuario){
		setUser(usuario);
		return "formulario";
	}
	
	/**
	 * Toma el usuario seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setUser(selectedUser);
		return "formulario";
	}
	
	/**
	 * Carga un nuevo usuario en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setUser(new User());
		return "formulario";
	}
	
	/**
	 * Eliminar usuario mediante el "row boton" de la tabla
	 * @param usuario
	 */
	public void doDeleteUser(User usuario){		
		this.deleteUser(usuario);
	}
	
	/**
	 * Elimina el usuario seleccionado de la tabla mediante el boton inferior
	 */
	public void doDeleteUsers(){
		this.deleteUser(selectedUser);
    }
	
	/**
	 * Guarda un usuario en base de datos y vuelve al listado
	 */
	public String doSaveUser(){
		//Si el id es null se crea un usuario nuevo 
		if (user.getIdUser()==null){
			//TODO: Validar que el username no este ya utilizado
			userService.createUser(user);
		//Si el id no es null se modifica el usuario
		}
		else
			userService.updateUser(user);
		return "userList";
		
	}
	/**
	 * Carga la lista con todos los usuarios y devuelve el outcome userList para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListUsers(){
		lstUsers=userService.findAllUsers();
		return "userList";
	}
	
	/**
	 * Al seleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este cas, seria SelectEvent
     */
    public void onRowSelect(AjaxBehaviorEvent event) {  
    	log.info(event);
    	setSelectedRow(true);
  }  

    /**
	 * Al deseleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados.
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este cas, seria SelectEvent
     */
    public void onRowUnselect(AjaxBehaviorEvent event) {
    	selectedRow=false;
    }  
	
	
	/**
	 * Elimina el usuario de la tabla y de la base de datos
	 * @param usuario
	 */
	private void deleteUser(User usuario){
		//Borro el usuario de la base de datos
		userService.deleteUser(usuario.getIdUser());
		//Elimino el usuario de la tabla		
		lstUsers.remove(selectedUser);
	}
	
	public List<User> getLstUsers() {
		return lstUsers;
	}

	public void setLstUsers(List<User> lstUsers) {
		this.lstUsers = lstUsers;
	}

	public User getSelectedUser() {
		return selectedUser;
	}

	public void setSelectedUser(User selectedUser) {
		this.selectedUser = selectedUser;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

}
