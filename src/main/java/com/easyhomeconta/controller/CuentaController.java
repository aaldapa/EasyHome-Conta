/**
 * 
 */
package com.easyhomeconta.controller;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.beans.BancoForm;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.CuentaService;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@Named(value="cuentaBean")
public class CuentaController extends BasicManageBean {

private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(CuentaController.class);
	
	@Inject
	CuentaService cuentaService;
	
	@Inject
	UserService userService;
	
	private List<BancoForm> lstItems;
	
	private User userLogado;
	
	/**
	 * Carga la lista con todos los items y devuelve el outcome para mostrar la pagina del listado.
	 * Se utiliza en la llamada desde el menu y desde el volver del formulario 
	 * @return
	 */
	public String doListItems(){
		lstItems=cuentaService.findAllForUser(getUserLogado().getIdUser());
		return "cuentaList";
	}
	 
	public String doLoadForm(Integer idProducto){
		log.info(idProducto);
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
