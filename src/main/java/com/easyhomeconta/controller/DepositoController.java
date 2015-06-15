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

/**
 * @author Alberto
 *
 */
@Scope("view")
@Named(value="depositoBean")
public class DepositoController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(DepositoController.class);
	
	@Inject
	ProductoService productoService;
	
	@Inject
	UserService userService;
	
	private List<BancoForm> lstItems;	
	private User userLogado;
	
	/**
	 * Metodo que se ejecuta cada vez que se instancia
	 */
	@PostConstruct
	public void init(){
		lstItems=productoService.findAllByTypeForUser(getUserLogado().getIdUser(), new Integer(2));
	}
	
	/**
	 * Cargada la lista desde el metodo init con todos los items, devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		return "depositoList";
	}
	
	/**
	 * Se encarga de dar de baja el item y cargar de nuevo la pagina con la lista de items
	 * @param idProducto
	 * @return
	 */
	public String doCancelDeposito(Integer idProducto){
		productoService.deleteProducto(idProducto);
		lstItems=productoService.findAllByTypeForUser(getUserLogado().getIdUser(), new Integer(2));
		return "depositoList";
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
