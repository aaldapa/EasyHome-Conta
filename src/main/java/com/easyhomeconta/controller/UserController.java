/**
 * Gestion de Usuarios
 */
package com.easyhomeconta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.easyhomeconta.model.Rol;
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
	private List<Rol> lstRoles=new ArrayList<Rol>();
	

	private User selectedUser= new User();
	private User user;
	
	private Boolean selectedRow;
	
	private Date ultimaConexion;

	/**
	 * Recibe el usuario al que pertenece el boton editar de la fila de la tabla y lo carga en el formulario 
	 * @param usuario
	 * @return
	 */
	public String doLoadForm(User usuario){
		setUser(usuario);
		return "userForm";
	}
	
	/**
	 * Toma el usuario seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setUser(selectedUser);
		//Cargo los roles
		this.lstRoles=userService.findAllRoles();
		//Setteo los roles que se van a mostrar en funcion de los que tiene el usuario
		for (Rol rolBean: lstRoles){
			for(Rol rol:user.getLstRoles()){
				if (rol.getIdRol().compareTo(rolBean.getIdRol())==0)
					rolBean.setSelected(true);
			}
		}
		
		return "userForm";
	}
	
	/**
	 * Carga un nuevo usuario en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setUser(new User());
		this.lstRoles=userService.findAllRoles();
		
		return "userForm";
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
		//Elimino todos los posibles roles que pudiese tener el usuario para dar de alta los que esten ahora checkeados
		user.setLstRoles(new ArrayList<Rol>());
		
		for (Rol rol:lstRoles){
			if (rol.getSelected())
				user.getLstRoles().add(rol);
		}
		
		//Si el id es null significa que estamos creando un nuevo user 
		if (user.getIdUser()==null){
			//se crea un usuario nuevo
			userService.createUser(user);
			//Se inserta en el arrayList para que se vea en el datetable
			lstUsers.add(user);
		}
		//Si el id no es null es porque se ha cargado un usuario y se trata de una modificacion
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
		lstUsers.remove(usuario);
	}
	
	/**
	 * Obtiene todos roles en una lista de beans
	 * @return
	 */
	public List<Rol> getLstRoles() {
		return lstRoles;
	}
	
	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
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

	public Date getUltimaConexion() {
		return ultimaConexion;
	}

	public void setUltimaConexion(Date ultimaConexion) {
		this.ultimaConexion = ultimaConexion;
	}
	
}
