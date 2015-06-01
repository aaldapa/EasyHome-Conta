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

import com.easyhomeconta.beans.TipoProductoForm;
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
	
	private List<TipoProductoForm> lstTipoProductos=new ArrayList<TipoProductoForm>();
	
	private TipoProductoForm selectedItem;
	private TipoProductoForm tipoProducto;
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
		setTipoProducto(new TipoProductoForm());
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
    	//Los items 1 y 2 estan reservados para las cuentas corrientes y los depositos y no pueden ser modificados ni eliminados
    	if (selectedItem.getIdTipoProducto()>2)
    		selectedRow=true;
    	else
    		selectedRow=false;
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

	public List<TipoProductoForm> getLstTipoProductos() {
		return lstTipoProductos;
	}

	public void setLstTipoProductos(List<TipoProductoForm> lstTipoProductos) {
		this.lstTipoProductos = lstTipoProductos;
	}

	public TipoProductoForm getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(TipoProductoForm selectedItem) {
		this.selectedItem = selectedItem;
	}

	public TipoProductoForm getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProductoForm tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Boolean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

}
