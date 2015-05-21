/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import com.easyhomeconta.model.Rol;

/**
 * @author Alberto
 *
 */
public interface RolService {

	/**
	 * Carga todos los roles existentes en bd.
	 * @return
	 */
	public List<Rol> findAllRoles();
	
}
