/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.easyhomeconta.beans.BancoBean;
import com.easyhomeconta.service.BancoService;

/**
 * @author Alberto
 *
 */
@RequestScoped
@Named(value="bancoBean")
public class BancoController extends BasicManageBean {

	private static final long serialVersionUID = 1L;
	private final Logger log = Logger.getLogger(BancoController.class);
	
	@Inject
	BancoService bancoService;
	
	private List<BancoBean> lstItems=new ArrayList<BancoBean>();
	
	private BancoBean selectedItem;
	private BancoBean banco;
	private Boolean selectedRow;
	private Boolean cambiarFoto;	
	
	/**
	 * Carga la lista con todos los items y devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		lstItems=bancoService.getLstBancosActivos();
		return "bancoList";
	}
	
	/**
	 * Carga un nuevo item en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setCambiarFoto(false);
		setBanco(new BancoBean());
		return "bancoForm";
	}
	
	/**
	 * Toma el item seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setCambiarFoto(false);
		setBanco(selectedItem);
		return "bancoForm";
	}
	
	/**
	 * Guarda un item en base de datos y vuelve al listado
	 * @return
	 */
	public String doSaveItem(){		

		//Pregunto si la imagen que se ha seleccionado es valida (si se ha seleccionado alguna), si no lo es muestro mensajes
		if (!isValidImage())
			return null;
		
		//Si se trata de un nuevo item lo añadimos a la lista
		Boolean isNuevoItem=banco.getIdBanco()==null?true:false;
		banco=bancoService.saveBanco(banco,cambiarFoto);
		
		//Si se trata de un nuevo item lo añadimos a la lista
		if (isNuevoItem)
			lstItems.add(banco);
		
		return "bancoList";
	}
	
	/**
	 * Elimina el item seleccionado de la tabla mediante el boton inferior
	 */
	public void doDeleteItem(){
		//Doy de bajael item en la base de datos
		bancoService.deleteBanco(selectedItem.getIdBanco());
		//Elimino el item de la tabla		
		lstItems.remove(selectedItem);
		selectedRow=false;
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
    	selectedRow=true;
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
    }


	/**
	 * Validacion de archivo subido. Debe de ser una imagen inferior a 25K
	 * @return
	 */
	private Boolean isValidImage(){
		Boolean isValid=true;
		
		//Validamos tamaño máximo y formatos admitidos		
		if (banco.getImagen().getSize()>0){
		
			if (banco.getImagen().getSize()>25000){
				addErrorMessage(getStringFromBundle("banco.form.error.imagen.sumary"), getStringFromBundle("banco.form.error.imagen.size.detail"));
				isValid=false;
			}
			if (!banco.getImagen().getContentType().startsWith("image")){
				addErrorMessage(getStringFromBundle("banco.form.error.imagen.sumary"), getStringFromBundle("banco.form.error.imagen.formato.detail"));
				isValid=false;
			}
		}
		
		return isValid;
	}
	
    
    
	public List<BancoBean> getLstItems() {
		return lstItems;
	}

	public void setLstItems(List<BancoBean> lstItems) {
		this.lstItems = lstItems;
	}

	public BancoBean getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(BancoBean selectedItem) {
		this.selectedItem = selectedItem;
	}

	public BancoBean getBanco() {
		return banco;
	}

	public void setBanco(BancoBean banco) {
		this.banco = banco;
	}

	public Boolean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	public Boolean getCambiarFoto() {
		return cambiarFoto;
	}

	public void setCambiarFoto(Boolean cambiarFoto) {
		this.cambiarFoto = cambiarFoto;
	}

	
	public List<String> getImages() {
		List<String> l = new ArrayList<String>();
				
		for (BancoBean b : bancoService.getLstBancosActivos())
			l.add(b.getIdBanco().toString());
		return l;
	}

	public StreamedContent getImage() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest myRequest = (HttpServletRequest) context.getExternalContext().getRequest();
		String imageID = myRequest.getParameter("imageID");
		log.info((String) myRequest.getParameter("imageID"));
		if (imageID!=null)
			return new DefaultStreamedContent(new ByteArrayInputStream(bancoService.getBancoById(Integer.parseInt(imageID)).getLogo()));
		else
			return new DefaultStreamedContent();
		
	}
	
}
