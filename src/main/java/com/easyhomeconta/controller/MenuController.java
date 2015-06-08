/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

/**
 * Controller para gestionar la class="active" de la entrada o modulo del menu principal en la que nos encontramos.
 * Para mostrar esta entrada marcada
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Scope("request")
@Named(value="menuBean")
public class MenuController implements Serializable {


	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(MenuController.class);
	
	private String ubicacionActiva;
	
	
	@PostConstruct
	private void init(){
		//Capturo la url a la que se esta haciendo el request
		String path=FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();
		
		//Troceo la ruta eliminando las barras
		String [] carpetas=path.split("/");
		
		if (carpetas.length>=3)
			//La 3 posicion del array contiene la carpeta con el nombre del modulo al que se hace referencia. En el caso de dashboard, no tiene carpeta por eso quitamos la extension 
			ubicacionActiva=carpetas[2].replace(".xhtml", ""); 

		//Si la ubicacion la carpeta operacion, para activar subopciones del menu, como todas estan en la misma carpeta
		if (ubicacionActiva!=null && ubicacionActiva.equals("operacion")){
			//Si nos encontramos en la vista operacion/import.xhtml cambio la ubicacion activa a import
			if (carpetas[3].replace(".xhtml", "").equals("import"))
				ubicacionActiva=carpetas[3].replace(".xhtml", "");
		}
	}
	
	public String getUbicacionActiva() {		
		return ubicacionActiva;
	}

	public void setUbicacionActiva(String ubicacionActiva) {
		this.ubicacionActiva = ubicacionActiva;
	}

	

}
