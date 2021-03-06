/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataIntegrityViolationException;

import com.easyhomeconta.forms.FamiliaForm;
import com.easyhomeconta.service.FamiliaService;
import com.easyhomeconta.utils.MyUtils;

/**
 * @author Alberto
 *
 */
@Scope("session")
@Named(value="familiaBean")
public class FamiliaController implements Serializable{

	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(FamiliaController.class);
	
	private List<FamiliaForm> lstFamilias;
	
	//FamiliaBean seleccionado
	private FamiliaForm selectedfamilia;
	//Flag que se utiliza para renderizar siempre que exista una fila seleccionada
	private Boolean selectedRow;
	
	//Titulo que se muestra en el formulario pop-up
	private String tituloForm;
	//Objeto con el contenido de la familia que se modifica o se crea
	private FamiliaForm familia;
	
	@Inject
	FamiliaService familiaService;
	
	/**
	 * Muestra el listado de familias
	 * @return
	 */
	public String doListFamilias(){
		log.info("Listado de familias");
		setLstFamilias(familiaService.getLstFamiliasAllBean());
		return "familiaList";
	}

	/**
	 * Al pulsar el boton nuevo del listado hacemos una llamada ajax para inicializar la nueva familia bean que se muestra en el popup
	 * @param event
	 */
	public void doNewForm(AjaxBehaviorEvent event) {
    	log.info("Click en nuevo");
    	tituloForm=MyUtils.getStringFromBundle("familias.form.title.nuevo");
    	familia=new FamiliaForm();
    } 
	
	/**
	 * Al pulsar en el boton editar del listado, hacemos una llamada ajax para cargar los valores de la familia seleccionada
	 * @param event
	 */
	public void doUpdateForm(AjaxBehaviorEvent event) {
    	log.info("Click en editar");
    	tituloForm=MyUtils.getStringFromBundle("familias.form.title.editar");
    	familia=selectedfamilia;
    } 
	
	/**
	 * Guarda el item del formulario en base de datos
	 * @return
	 */
	public void doSaveItem(){
		log.info("guardar familia");
		log.info("id: "+ familia.getIdFamilia()+ " nombre: " + familia.getNombre()) ;
		
		Boolean isNuevoFamilia=familia.getIdFamilia()==null?true:false;
		familia=familiaService.saveFamilia(familia);

		//Para familias nuevas añadimos la familia a la tabla
		if (isNuevoFamilia)			
			lstFamilias.add(familia);
	}
	
	/**
	 * Eliminar un item seleccionado
	 */
	public void doDeleteItem(){		
		log.info("eliminar item");
		selectedRow=false;
		try{
			familiaService.deleteFamilia(selectedfamilia.getIdFamilia());
			lstFamilias.remove(selectedfamilia);
		}catch(DataIntegrityViolationException e){
			selectedRow=true;
			MyUtils.addErrorMessage(MyUtils.getStringFromBundle("familias.listado.error.eliminar.familia.activa.summary"),MyUtils.getStringFromBundle("familias.listado.error.eliminar.familia.activa.detail"));
		}
		
		
	}
	
	/**
	 * Al seleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este caso, seria SelectEvent
     */
    public void onRowSelect(AjaxBehaviorEvent event) {  
    	log.info("Seleccion de fila");
    	setSelectedRow(true);
    }  

    /**
	 * Al deseleccionar una fila de la tabla llamamos a este metodo, que sirve para settear un atributo que es evaluado por los botones 
	 * eliminar y modificar de la parte inferior de la tabla para saber cuando deben de ser renderizados.
	 * @param event
     * Los metodos que reciben una peticion Ajax de jsf deben recibir un objeto AjaxBehaviorEvent.
     * Las peticiones Ajax de primefaces reciben objetos mas especificos relacionados con el evento que los dispara, por ejemplo  en este caso, seria SelectEvent
     */
    public void onRowUnselect(AjaxBehaviorEvent event) {
    	log.info("Deseleccion de fila");
    	setSelectedRow(false);
    } 
	
	public List<FamiliaForm> getLstFamilias() {
		return lstFamilias;
	}

	public void setLstFamilias(List<FamiliaForm> lstFamilias) {
		this.lstFamilias = lstFamilias;
	}


	public FamiliaForm getSelectedfamilia() {
		return selectedfamilia;
	}


	public void setSelectedfamilia(FamiliaForm selectedfamilia) {
		this.selectedfamilia = selectedfamilia;
	}


	public Boolean getSelectedRow() {
		return selectedRow;
	}


	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	public FamiliaForm getFamilia() {
		return familia;
	}

	public void setFamilia(FamiliaForm familia) {
		this.familia = familia;
	}

	public String getTituloForm() {
		return tituloForm;
	}

	public void setTituloForm(String tituloForm) {
		this.tituloForm = tituloForm;
	}

}
