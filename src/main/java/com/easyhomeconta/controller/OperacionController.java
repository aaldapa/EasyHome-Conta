/**
 * 
 */
package com.easyhomeconta.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.forms.OperacionForm;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.OperacionService;
import com.easyhomeconta.service.UserService;
import com.sun.faces.context.flash.ELFlash;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named(value="operacionBean")
@Scope("view")
public class OperacionController extends BasicManageBean {

	private final Logger log = Logger.getLogger(OperacionController.class);
	
	@Inject
	OperacionService operacionService;
	
	@Inject
	UserService userService;
	
	private Integer  idProducto;
	private UploadedFile archivo;
	private List<SelectItem> lstProductos;
	private List<SelectItem> lstCategorias;
	private List<OperacionForm> lstOperacionesForm;

	private List<OperacionForm> selectedOperacionesForm;
	
	/**
	 * Al entrar en una vista en la que se llama algun metodo del controllador, primero se llama al init.
	 * En esta ocasion, cargo el valor del idProducto que se ha introducido en el scope flash desde lel controller CuentaBean.
	 * Lo hago asi porque con el scope vista no recoge parametros de otras vista y no quiero usar el scope session.
	 */
	@PostConstruct
	public void init (){
		this.idProducto=(Integer) ELFlash.getFlash().get("idProducto");	
	}
	
	/**
	 * Carga la vista de consulta desde la vista de cuentas corrientes
	 * @param idProducto
	 * @return
	 */
	public String doListItems(Integer idProducto){
		setIdProducto(idProducto);
		log.info(this.idProducto);
		return "operacionList";
	}
	
	/**
	 * Carga la vista de consulta
	 * @return
	 */
	public String doListItems(){
		log.info(this.idProducto);
		return "operacionList";
	}
	
	/**
	 * Carga la vista del formulario de importacion
	 * @param idProducto
	 * @return
	 */
	public String doLoadImportForm(Integer idProducto){		
		
		log.info(this.idProducto);
		clearSessionObjects();
		this.idProducto=idProducto;
		return "operacionImportForm";
	}

	/**
	 * Carga la vista del formulario de importacion
	 * @param idProducto
	 * @return
	 */
	public String doLoadImportForm(){
		log.info(this.idProducto);
		clearSessionObjects();		
		return "operacionImportForm";
	}
	
	/**
	 * Guarda en base de datos las operaciones seleccionadas y las elimina de la tabla
	 */
	public void doSaveItems(){
		//Si la lista de operaciones seleccionadas no es nula ni esta vacia los guardo en bd
		if (selectedOperacionesForm!=null&& !selectedOperacionesForm.isEmpty()){
			
			//Validacion de idProducto
			if(idProducto==null)
				addErrorMessage(getStringFromBundle("error"), getStringFromBundle("operacion.form.importar.guardar.error.detail"));
			else{
				operacionService.saveOperaciones(selectedOperacionesForm, idProducto);
				addInfoMessage(getStringFromBundle("success"),selectedOperacionesForm.size()+" "+getStringFromBundle("operacion.form.importar.guardar.detail"));
				removeSelectedFromList();
			}
		}
	}
	
	/**
	 * Eliminar la seleccion de la tabla
	 */
	public void doDeleteSelection(){
		//Si la lista de operaciones seleccionadas no es nula ni esta vacia los borro de la tabla
		if (selectedOperacionesForm!=null && !selectedOperacionesForm.isEmpty()){
			addInfoMessage(getStringFromBundle("success"),selectedOperacionesForm.size()+" "+getStringFromBundle("operacion.form.importar.eliminar.detail"));
			removeSelectedFromList();
		}
	}
	
	/**
	 * Eliminar seleccion de la lista de operaciones cargadas
	 */
	private void removeSelectedFromList(){
		//Elimino de la tabla los elementos seleccionados
		for(OperacionForm o:selectedOperacionesForm)
			lstOperacionesForm.remove(o);
	}

	private void clearSessionObjects(){
		if(lstOperacionesForm!=null)
			lstOperacionesForm.clear();
		if (selectedOperacionesForm!=null)
			selectedOperacionesForm.clear();
		this.idProducto=null;
	}
	
	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		
		if (!PhaseId.INVOKE_APPLICATION.equals(event.getPhaseId())) {
	        event.setPhaseId(PhaseId.INVOKE_APPLICATION);
	        event.queue();
	    } else {
	        //do stuff here, #{ngoPhotoBean.description} is set
	    	log.info(idProducto);
	    	
	    	if (event.getFile().equals(null)) 
				addErrorMessage(getStringFromBundle("operacion.form.importar.cargar.error.summary"),getStringFromBundle("operacion.form.importar.cargar.error.detail"));
			
			try {
				lstOperacionesForm=operacionService.getLstOperacionesFormXLS(event.getFile().getInputstream(), idProducto);

				//Como la p:datetable necesita que los items tengan id, recorro la lista añadiendole la posicion en el id
				for (int i = 0; i < lstOperacionesForm.size(); i++) 
					lstOperacionesForm.get(i).setId(new Long(i+1));
				
				//cargo la lista de categorias
				setLstCategorias(operacionService.getLstCategorias(getUserLogado().getIdUser()));
				
			} catch (Exception e) {
				addErrorMessage(getStringFromBundle("operacion.form.importar.cargar.error.summary"),getStringFromBundle("operacion.form.importar.cargar.error.detail"));
			}
	    }
		
		
		
    }

	/**
	 * @return the lstProductos
	 */
	public List<SelectItem> getLstProductos() {
		lstProductos=operacionService.getLstProductosOperables(getUserLogado().getIdUser());
		return lstProductos;
	}
	
	/**
	 * @param lstProductos the lstProductos to set
	 */
	public void setLstProductos(List<SelectItem> lstProductos) {
		this.lstProductos = lstProductos;
	}
	
	/**
	 * Cargo al usuario logado existente en el Contexto de spring security para obtener su id y referencias
	 * @return
	 */
	public User getUserLogado() {
		User userLogado=userService.getUserById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser());
		return userLogado;
	}

	/**
	 * @return the archivo
	 */
	public UploadedFile getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo the archivo to set
	 */
	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public List<OperacionForm> getLstOperacionesForm() {
		return lstOperacionesForm;
	}

	public void setLstOperacionesForm(List<OperacionForm> lstOperacionesForm) {
		this.lstOperacionesForm = lstOperacionesForm;
	}

	/**
	 * @return the lstCategorias
	 */
	public List<SelectItem> getLstCategorias() {
		return lstCategorias;
	}

	/**
	 * @param lstCategorias the lstCategorias to set
	 */
	public void setLstCategorias(List<SelectItem> lstCategorias) {
		this.lstCategorias = lstCategorias;
	}

	/**
	 * @return the selectedOperacionesForm
	 */
	public List<OperacionForm> getSelectedOperacionesForm() {
		return selectedOperacionesForm;
	}

	/**
	 * @param selectedOperacionesForm the selectedOperacionesForm to set
	 */
	public void setSelectedOperacionesForm(List<OperacionForm> selectedOperacionesForm) {
		this.selectedOperacionesForm = selectedOperacionesForm;
	}
	
}
