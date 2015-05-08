/**
 * 
 */
package com.easyhomeconta.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.easyhomeconta.beans.TipoProductoBean;
import com.easyhomeconta.service.TipoProductoService;

/**
 * @author Alberto
 *
 */
@Named(value="tProductoBean")
public class TipoProductoController extends BasicManageBean{

	private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(TipoProductoController.class);
	
	@Inject
	private TipoProductoService tipoProductoService;
	
	private List<TipoProductoBean> lstTipoProductos=new ArrayList<TipoProductoBean>();
	
	private TipoProductoBean selectedItem;
	private TipoProductoBean tipoProducto;
	private Boolean selectedRow;
	
	/**
	 * Carga la lista con todos los items y devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		lstTipoProductos=tipoProductoService.getLstTipoProductosActivos();
		return "tipoProductoList";
	}
	
	/**
	 * Toma el item seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setTipoProducto(selectedItem);
		return "tipoProductoForm";
	}
	
	/**
	 * Carga un nuevo item en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setTipoProducto(new TipoProductoBean());
		return "tipoProductoForm";
	}
		
	/**
	 * Elimina el item seleccionado de la tabla mediante el boton inferior
	 */
	public void doDeleteItem(){
		selectedRow=false;
		//Borro el item de la base de datos
		tipoProductoService.deleteTipoProducto(selectedItem.getIdTipoProducto());
		//Elimino el item de la tabla		
		lstTipoProductos.remove(selectedItem);
    }
	
	/**
	 * Guarda un item en base de datos y vuelve al listado
	 * @return
	 */
	public String doSaveItem(){		

		Boolean isNuevoItem=tipoProducto.getIdTipoProducto()==null?true:false;
		tipoProducto=tipoProductoService.saveTipoProducto(tipoProducto);
		
		//Si se trata de un nuevo item lo añadimos a la lista
		if (isNuevoItem)
			lstTipoProductos.add(tipoProducto);
		
		return "tipoProductoList";
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

	public List<TipoProductoBean> getLstTipoProductos() {
		return lstTipoProductos;
	}

	public void setLstTipoProductos(List<TipoProductoBean> lstTipoProductos) {
		this.lstTipoProductos = lstTipoProductos;
	}

	public TipoProductoBean getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TipoProductoBean selectedItem) {
		this.selectedItem = selectedItem;
	}

	public TipoProductoBean getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProductoBean tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Boolean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

}
