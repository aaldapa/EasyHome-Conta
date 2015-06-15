/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.model.Categoria;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.CategoriaService;

/**
 * @author Alberto
 *
 */
@Scope("session")
@Named(value="categoriaBean")
public class CategoriaController implements Serializable {


	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(CategoriaController.class);
	
	//Entidad en la que se carga el usuario logado desde el get
	private User userLogado;
	
	@Inject
	private CategoriaService categoriaService;
	
	private List<Categoria> lstCategorias=new ArrayList<Categoria>();
	
	private Categoria selectedItem;
	private Categoria categoria;
	private Boolean selectedRow;
	
	/**
	 * Toma el item seleccionado de la tabla al pulsar sobre el boton editar de la parte inferior de la tabla y lo carga en el formulario
	 * @return
	 */
	public String doLoadForm(){
		setCategoria(selectedItem);
		return "categoriaForm";
	}
	
	/**
	 * Carga un nuevo item en blanco en el formulario cuando se pulsa el boton nuevo
	 * @return
	 */
	public String doNewForm(){
		setCategoria(new Categoria());
		return "categoriaForm";
	}
		
	/**
	 * Elimina el item seleccionado de la tabla mediante el boton inferior
	 */
	public void doDeleteItem(){
		selectedRow=false;
		//Borro el item de la base de datos
		categoriaService.deleteCategoria(selectedItem.getIdCategoria());
		//Elimino el item de la tabla		
		lstCategorias.remove(selectedItem);
		
    }
	
	/**
	 * Guarda un item en base de datos y vuelve al listado
	 * @return
	 */
	public String doSaveItem(){		
		//Si se trata de un nuevo item lo añadimos a la lista
		if (categoria.getIdCategoria()==null)
			lstCategorias.add(categoria);
		categoriaService.saveCategoria(categoria, getUserLogado().getIdUser());
		return "categoriaList";
	}
	/**
	 * Carga la lista con todos los items y devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		lstCategorias=categoriaService.getCategoriasForUser(getUserLogado().getIdUser());
		return "categoriaList";
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

	public List<Categoria> getLstCategorias() {
		return lstCategorias;
	}

	public void setLstCategorias(List<Categoria> lstCategorias) {
		this.lstCategorias = lstCategorias;
	}

	public Categoria getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(Categoria selectedItem) {
		this.selectedItem = selectedItem;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
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
		userLogado=(User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userLogado;
	}

	public void setUserLogado(User userLogado) {
		this.userLogado = userLogado;
	}
	
	
}
