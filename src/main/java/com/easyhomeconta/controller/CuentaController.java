/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.forms.BancoForm;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.ProductoService;
import com.easyhomeconta.service.UserService;
import com.sun.faces.context.flash.ELFlash;

/**
 * @author Alberto
 *
 */
@Scope("view")
@Named(value="cuentaBean")
public class CuentaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(CuentaController.class);
	
	@Inject
	ProductoService productoService;
	
	@Inject
	UserService userService;
	
	private List<BancoForm> lstItems;
	
	private User userLogado;


	/**
	 * Cuando desde la vista se instancia al controller, el primer metodo que se ejecuta es el init, desde el que cargo la lista de productos
	 */
	@PostConstruct
	public void init(){
		lstItems=productoService.findAllByTypeForUser(getUserLogado().getIdUser(), new Integer (1));
	}
	
	/**
	 * Cargada la lista desde el metodo init con todos los items, devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		return "cuentaList";
	}
	
	/**
	 * Carga el id de producto en el contexto flash para hacerlo accesible desde la pagina de
	 * importacion de managed bean OperacionController
	 * @param idProducto
	 * @return
	 */
	public String doLoadImportForm(Integer idProducto){		
		log.info(idProducto);
		ELFlash.getFlash().put("idProducto", idProducto);
		
		return "operacionImportForm";
	}
	
	/**
	 * Carga el id de producto en el contexto flash para hacerlo accesible desde la pagina de
	 * consultas de managed bean OperacionController
	 * @param idProducto
	 * @return
	 */
	public String doLoadConsultas(Integer idProducto){		
		log.info(idProducto);
		ELFlash.getFlash().put("idProducto", idProducto);
		
		return "operacionList";
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


	public List<BancoForm> getLstItems() {
		return lstItems;
	}


	public void setLstItems(List<BancoForm> lstItems) {
		this.lstItems = lstItems;
	}

}
