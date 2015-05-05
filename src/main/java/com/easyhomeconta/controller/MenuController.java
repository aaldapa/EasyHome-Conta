/**
 * 
 */
package com.easyhomeconta.controller;

import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;

/**
 * Controller para gestionar la class="active" de la entrada o modulo del menu principal en la que nos encontramos.
 * Para mostrar esta entrada marcada
 * @author Alberto
 *
 */
@Named(value="menuBean")
public class MenuController {

	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(MenuController.class);
	
	private String ubicacionActiva;

	/**
	 * 
	 */
	public MenuController() {
		// TODO Auto-generated constructor stub
	}
	
	public String getUbicacionActiva() {
		//Capturo la url a la que se esta haciendo el request
		String path=FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
		
		//Troceo la ruta eliminando las barras
		String [] carpetas=path.split("/");
		
		if (carpetas.length>=3)
			//La 3 posicion del array contiene la carpeta con el nombre del modulo al que se hace referencia. En el caso de dashboard, no tiene carpeta por eso quitamos la extension 
			ubicacionActiva=carpetas[2].replace(".xhtml", ""); 
				
		return ubicacionActiva;
	}

	public void setUbicacionActiva(String ubicacionActiva) {
		this.ubicacionActiva = ubicacionActiva;
	}

	

}
