/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import com.easyhomeconta.model.Producto;

/**
 * @author Alberto
 *
 */
public interface ProductoDao extends GenericDao<Producto> {

	/**
	 * Busca los productos para el usuario con el id pasado como parametro
	 * @param idUser
	 * @return
	 */
	public List<Producto> findProductosForUser (Integer idUser);
	
	
	
}
