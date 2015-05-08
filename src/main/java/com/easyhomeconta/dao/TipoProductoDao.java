/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import com.easyhomeconta.model.TipoProducto;

/**
 * @author Alberto
 *
 */
public interface TipoProductoDao extends GenericDao<TipoProducto> {

	public List<TipoProducto> findTiposProductoActivo();
	
}
