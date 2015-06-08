/**
 * Gestion de Usuarios
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.FamiliaService;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@Scope("session")
@Named(value="userBean")
public class UserController extends BasicManageBean implements Serializable{

	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(UserController.class);
	
	@Inject
	private UserService userService;
	
	@Inject
	private FamiliaService familiaService;

	private List<User> lstUsers=new ArrayList<User>();	
	private List<Rol> lstRoles=new ArrayList<Rol>();

	//Lista para el combo
	private List<SelectItem> lstFamilias=new ArrayList<SelectItem>();
	private Integer selectedFamilia;

	private User selectedUser= new User();
	private User user;
	
	private Boolean selectedRow;
		
	private UploadedFile imagen;
	private Boolean cambiarFoto;
	
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
	 * Recibe el usuario al que pertenece el boton editar de la fila de la tabla y lo carga en el formulario 
	 * @param usuario
	 * @return
	 */
	public String doLoadForm(User usuario){
		setUser(usuario);
		setCambiarFoto(false);
		return "userForm";
	}
	
	/**
	 * Toma el usuario seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setUser(selectedUser);
		//Por defecto, no se ha pulsado cambiar foto
		setCambiarFoto(false);
		//Cargo los roles
		this.lstRoles=userService.findAllRoles();
		//Setteo los roles que se van a mostrar en funcion de los que tiene el usuario
		for (Rol rolBean: lstRoles){
			for(Rol rol:user.getLstRoles()){
				if (rol.getIdRol().compareTo(rolBean.getIdRol())==0)
					rolBean.setSelected(true);
			}
		}
		
		//cargo el combo de familias
		this.lstFamilias=familiaService.getFamiliasAllForCombo();
		//Coloco el valor del combo
		this.selectedFamilia=new Integer(user.getFamilia()!=null?user.getFamilia().getIdFamilia():0);
		
		return "userForm";
	}
	
	/**
	 * Carga un nuevo usuario en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setUser(new User());
		//Por defecto, no se ha pulsado cambiar foto
		setCambiarFoto(false);
		//cargo la lista de roles
		this.lstRoles=userService.findAllRoles();
		//cargo la lisra de familias
		this.lstFamilias=familiaService.getFamiliasAllForCombo();
		//Coloco a 0 la familia del combo
		this.selectedFamilia=new Integer("0");
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
		return saveUser("userList");
	}
	
	public String doUserProfile(){
		//Capturo el id del usuario logado
		Integer idUserLogado=((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser();
		//Cargo el usuario con los datos de la base de datos
		user=userService.getUserById(idUserLogado);
		
		//Por defecto, no se ha pulsado cambiar foto
		setCambiarFoto(false);
		//Cargo los roles
		this.lstRoles=userService.findAllRoles();
		//Setteo los roles que se van a mostrar en funcion de los que tiene el usuario
		for (Rol rolBean: lstRoles){
			for(Rol rol:user.getLstRoles()){
				if (rol.getIdRol().compareTo(rolBean.getIdRol())==0)
					rolBean.setSelected(true);
			}
		}
		
		log.info("Usuario: "+user.getUserNameForSession()+" .Familia: "+(user.getFamilia()!=null?user.getFamilia().getNombre():"SIN FAMILIA"));
		
		//cargo el combo de familias
		this.lstFamilias=familiaService.getFamiliasAllForCombo();

		//Coloco el valor del combo
		this.selectedFamilia=new Integer(user.getFamilia()!=null?user.getFamilia().getIdFamilia():0);
		
		return "userProfile";
	}
	
	/**
	 * Guarda las modifiaciones en el expediente del usuario logado
	 */
	public String doSaveProfile(){
		return saveUser(null);
	}
	
	
	/**
	 * Al seleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este cas, seria SelectEvent
     */
    public void onRowSelect(AjaxBehaviorEvent event) {  
    	log.info("Seleccion de fila");
    	setSelectedRow(true);

    	//Seleccionamos la opcion de familia que tenga el usuario
    	if (selectedUser.getFamilia()!=null)
    		setSelectedFamilia(selectedUser.getFamilia().getIdFamilia());
    	else
    		setSelectedFamilia(new Integer ("0"));
  }  

    /**
	 * Al deseleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados.
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este cas, seria SelectEvent
     */
    public void onRowUnselect(AjaxBehaviorEvent event) {
    	log.info("Deseleccion de fila");
    	selectedRow=false;
    	//Familia seleccionada en el combo de familias = 0
    	setSelectedFamilia(new Integer ("0"));
    }    
    
    /**
	 * Elimina el usuario de la tabla y de la base de datos
	 * @param usuario
	 */
	private void deleteUser(User usuario){
		selectedRow=false;
		//Borro el usuario de la base de datos
		userService.deleteUser(usuario.getIdUser());
		//Elimino el usuario de la tabla		
		lstUsers.remove(usuario);
	}
	
	/**
	 * Guarda el formulario de usuario / expediente, tanto si se trata de una modificacion como si se trata de un usuario nuevo.
	 * @param outcome
	 * @return
	 */
	private String saveUser(String outcome){
		
		//Pregunto si la imagen que se ha seleccionado es valida (si se ha seleccionado alguna), si no lo es muestro mensajes
		if (!isValidImage())
			return null;
		
		//Validacion de username
		if (!userService.isUsernameValido(user)){
			addErrorMessage(getStringFromBundle("usuarios.form.error.usuario.duplicado.sumary"),getStringFromBundle("usuarios.form.error.usuario.duplicado.detail"));
			return null;
		}
		
		log.info("Id de familia guardada:"+selectedFamilia+". Formulario:"+(outcome!=null?"Usuario":"Expediente"));

		//Si es un usuario nuevo lo añado a la datatable
		if (user.getIdUser()==null)
			lstUsers.add(user);
		
		userService.saveUser(user, selectedFamilia,cambiarFoto, imagen, lstRoles);
		
		//Si venimos del expediente al modificar mostramos mensaje de que se ha modificado el expediente con exito porque no se viaja a ninguna pagina
		if (outcome==null)
			addInfoMessage(getStringFromBundle("usuarios.form.save.success.sumary"),getStringFromBundle("usuarios.form.save.success.detail"));
		
		return outcome;
		
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
	
	
	public List<Rol> getLstRoles() {
		return lstRoles;
	}
	
	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
	}
	
	public List<SelectItem> getLstFamilias() {
		return lstFamilias;
	}

	public void setLstFamilias(List<SelectItem> lstFamilias) {
		this.lstFamilias = lstFamilias;
	}

	public Integer getSelectedFamilia() {
		return selectedFamilia;
	}

	public void setSelectedFamilia(Integer selectedFamilia) {
		this.selectedFamilia = selectedFamilia;
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
	
}
