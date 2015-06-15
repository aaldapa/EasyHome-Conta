/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.service.RolService;

/**
 * @author Alberto
 *
 */
@Scope("session")
@Named(value="rolBean")
public class RolController implements Serializable {

	
	private static final long serialVersionUID = 1L;

	private final Logger log = Logger.getLogger(RolController.class);
	
	private List<Rol> lstRoles; 
	
	@Inject
	RolService rolService;
	
	public String doListItems(){
		log.info("Lista roles");
		lstRoles=rolService.findAllRoles();
		return "rolList"; 
		
	}

	public List<Rol> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
	}
	
}
