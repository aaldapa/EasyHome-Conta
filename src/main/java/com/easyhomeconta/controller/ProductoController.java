/**
 * 
 */
package com.easyhomeconta.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.beans.ProductoBean;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.BancoService;
import com.easyhomeconta.service.ProductoService;
import com.easyhomeconta.service.TipoProductoService;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@Named(value="productoBean")
public class ProductoController extends BasicManageBean {

	private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(ProductoController.class);
	
	@Inject
	private ProductoService productoService;
	
	@Inject
	private TipoProductoService tProductoService;
	
	@Inject
	private BancoService bancoService;
	
	@Inject
	private UserService userService;
	
	//Entidad en la que se carga el usuario logado desde el get
	private User userLogado;
	
	private List<SelectItem>familiares=new ArrayList<SelectItem>();
	
	
	//Inicializo la clase con la fecha actual para compara en la tabla los productos que tienen fecha de vencimiento inferior para marcarlos en otro color
	private Date currentDate=new Date();
	
	private List<ProductoBean> lstItems=new ArrayList<ProductoBean>();
	
	private ProductoBean selectedItem;
	private ProductoBean producto;
	private Boolean selectedRow;
	
	
	//Listas para el combos
	private List<SelectItem> lstBancos=new ArrayList<SelectItem>();
	private List<SelectItem> lstTProductos=new ArrayList<SelectItem>();
		
	/**
	 * Carga la lista con todos los items y devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		lstItems=productoService.getProductosForUser(getUserLogado().getIdUser());
		return "productoList";
	}
	
	/**
	 * Carga un nuevo item en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		lstBancos=bancoService.getLstBancosActivosForCombo();
		lstTProductos=tProductoService.getLstTipoProductosActivosForCombo();
		familiares=userService.getFamiliaresForCombo(getUserLogado().getIdUser());
		setProducto(new ProductoBean());
		//Marco el producto como propietario del usuario logado
		producto.setOwner(true);
		return "productoForm";
	}
	
	/**
	 * Toma el item seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		lstBancos=bancoService.getLstBancosActivosForCombo();
		lstTProductos=tProductoService.getLstTipoProductosActivosForCombo();
		familiares=userService.getFamiliaresForCombo(getUserLogado().getIdUser());
		setProducto(selectedItem);
		return "productoForm";
	}
		
	/**
	 * Elimina el item seleccionado de la tabla mediante el boton inferior
	 */
	public void doDeleteItem(){
		//Comprobacion de propietario
		if (selectedItem.getOwner()){
			selectedRow=false;
			//Doy de bajael item en la base de datos
			productoService.deleteProducto(selectedItem.getIdProducto());
			//Elimino el item de la tabla		
			lstItems.remove(selectedItem);
		}
		//Si el usuario no es el propietario muestro mensaje de error.
		else{
			selectedRow=true;
			addErrorMessage(getStringFromBundle("productos.listado.error.eliminar.owner.sumary"), getStringFromBundle("productos.listado.error.eliminar.owner.detail"));
		}
    }
	
	/**
	 * Guarda un item en base de datos y vuelve al listado
	 * @return
	 */
	public String doSaveItem(){		
		//Si se trata de un nuevo item lo añadimos a la lista
		Boolean isNuevoItem=producto.getIdProducto()==null?true:false;
		producto=productoService.saveProducto(producto, getUserLogado().getIdUser());
		
		//Si se trata de un nuevo item lo añadimos a la lista
		if (isNuevoItem)
			lstItems.add(producto);
		
		return "productoList";
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

	
	public List<ProductoBean> getLstItems() {
		return lstItems;
	}

	public void setLstItems(List<ProductoBean> lstProductos) {
		this.lstItems = lstProductos;
	}

	public ProductoBean getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(ProductoBean selectedItem) {
		this.selectedItem = selectedItem;
	}

	public ProductoBean getProducto() {
		return producto;
	}

	public void setProducto(ProductoBean producto) {
		this.producto = producto;
	}

	public Boolean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(Boolean selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * Cargo al usuario logado existente en el Contexto de spring security para obtener su id y referencias
	 * @return
	 */
	public User getUserLogado() {
		userLogado=userService.getUserById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser());
		return userLogado;
	}

	public void setUserLogado(User userLogado) {
		this.userLogado = userLogado;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public List<SelectItem> getLstBancos() {
		return lstBancos;
	}

	public void setLstBancos(List<SelectItem> lstBancos) {
		this.lstBancos = lstBancos;
	}

	public List<SelectItem> getLstTProductos() {
		return lstTProductos;
	}

	public void setLstTProductos(List<SelectItem> lstTProductos) {
		this.lstTProductos = lstTProductos;
	}
	
	public List<SelectItem> getFamiliares() {
		return familiares;
	}

	public void setFamiliares(List<SelectItem> familiares) {
		this.familiares = familiares;
	}


}
