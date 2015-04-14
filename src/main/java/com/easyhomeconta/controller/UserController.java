/**
 * Gestion de Usuarios
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@Named(value="userBean")
@RequestScoped
public class UserController extends BasicManageBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(UserController.class);
	
	@Inject
	private UserService userService;
	
	private List<User> lstUsers=new ArrayList<User>();	
	private List<Rol> lstRoles=new ArrayList<Rol>();
	

	private User selectedUser= new User();
	private User user;
	
	private Boolean selectedRow;
		
	private UploadedFile imagen;
	private Boolean cambiarFoto=false;
	
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
		
		//Pregunto si la imagen que se ha seleccionado es valida (si se ha seleccionado alguna), si no lo es muestro mensajes
		if (!isValidImage())
			return null;
		
		//Elimino todos los posibles roles que pudiese tener el usuario para dar de alta los que esten ahora checkeados
		user.setLstRoles(new ArrayList<Rol>());
				
		for (Rol rol:lstRoles){
			if (rol.getSelected())
				user.getLstRoles().add(rol);
		}
		
		
		//Si el id es null significa que estamos creando un NUEVO USER 
		if (user.getIdUser()==null){
			
			//Si el username introducido no existe en la bd 
			if (!userService.isUsernameInDB(user.getUsername())){
				gestionarRoles();
				gestionarFoto();
				//se crea un usuario nuevo
				userService.createUser(user);
				//Se inserta en el arrayList para que se vea en el datetable
				lstUsers.add(user);
			}
			else{
				addInfoMessage(getStringFromBundle("usuarios.form.error.usuario.duplicado.sumary"),getStringFromBundle("usuarios.form.error.usuario.duplicado.detail"));
				return null;
			}
			
		}
		//Si el id no es null es porque se ha cargado un usuario y se trata de una modificacion
		else{
			gestionarRoles();
			gestionarFoto();
			userService.updateUser(user);
		}
		
		
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

	public UploadedFile getImagen() {
		return imagen;
	}

	public void setImagen(UploadedFile imagen) {
		this.imagen = imagen;
	}
	
	public Boolean getCambiarFoto() {
		return cambiarFoto;
	}

	public void setCambiarFoto(Boolean cambiarFoto) {
		this.cambiarFoto = cambiarFoto;
	}

	/**
	 * Gestiona la carga de la fotografia en el User dependiendo de si se ha seleccionado foto o no 
	 * y de si se trata de un usuario nuevo o una modificacion.
	 */
	private void gestionarFoto(){
		
		byte[] imagenArrayBytes=null;
		
		
		//Si hemos cargado el cambio de foto
		if (cambiarFoto){
			//Si se carga foto
			if (imagen.getSize()!=0){
				try{
					imagenArrayBytes=IOUtils.toByteArray(imagen.getInputstream());
				}catch(Exception e){
					log.error(e.getMessage());
				}
			}
		}
		//Si no hemos cargado el cambio de foto, 
		else{
			//Si estamos realizando una MODIFICACION del USUARIO 
			if (user.getIdUser()!=null)
				//Debemos de cargar la imagen que ya teneia guardada en BD 
				imagenArrayBytes=userService.getUserById(user.getIdUser()).getPhoto();
		}
		
		user.setPhoto(imagenArrayBytes);
	}
	
	/**
	 * Gestiona los roles borrando primero todos los existentes anteriormente
	 * y añadiendo despues los seleccionados.
	 */
	private void gestionarRoles(){
		//Elimino todos los posibles roles que pudiese tener el usuario para dar de alta los que esten ahora checkeados
		user.setLstRoles(new ArrayList<Rol>());
						
		for (Rol rol:lstRoles){
			if (rol.getSelected())
				user.getLstRoles().add(rol);
		}
	}
	/**
	 * Validacion de archivo subido. Debe de ser una imagen inferior a 55K
	 * @return
	 */
	private Boolean isValidImage(){
		Boolean isValid=true;
		
		//Validamos tamaño máximo y formatos admitidos		
		if (imagen.getSize()>0){
		
			if (imagen.getSize()>55000){
				addErrorMessage(getStringFromBundle("usuarios.form.error.imagen.sumary"), getStringFromBundle("usuarios.form.error.imagen.size.detail"));
				isValid=false;
			}
			if (!imagen.getContentType().startsWith("image")){
				addErrorMessage(getStringFromBundle("usuarios.form.error.imagen.sumary"), getStringFromBundle("usuarios.form.error.imagen.formato.detail"));
				isValid=false;
			}
		}
		
		return isValid;
	}
	
}
