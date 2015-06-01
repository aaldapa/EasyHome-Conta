/**
 * 
 */
package com.easyhomeconta.dao;

import java.math.BigDecimal;
import java.util.Date;
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
	
	/**
	 * Busca las cuentas corrientes para el usuario con el id pasado como parametro
	 * @param idUser
	 * @param idTProducto
	 * @return
	 */
	public List<Producto> findAllByTipoForUser (Integer idUser, Integer idTProducto);
	
	/**
	 * Obtiene el balance total de un producto, es decir, la suma de todos los movimiento u operaciones mas el importe de del producto (importe de apertura) 
	 * @param idProducto
	 * @return
	 */
	public BigDecimal getBalance(Integer idProducto);
	
	/**
	 * Obtine la fecha de la ultima operacion. Util para saber desde que fecha esta sin actualizar el producto.
	 * @param idProducto
	 * @return
	 */
	public Date getFechaUltimaOperacion(Integer idProducto);
	
	
}
