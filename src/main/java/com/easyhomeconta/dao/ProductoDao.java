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
	
	
	/**
	 * Obtiene todos los productos operables para el id de usuario dado
	 * @param idUser
	 * @return
	 */
	public List<Producto> findProductosOperablesForUser (Integer idUser);
	
	/**
	 * Devuelve el producto con los datos del banco cargados
	 * @param idProducto
	 * @return
	 */
	public Producto findWithBancoById(Integer idProducto);
	
	/**
	 * Obtiene el sumatorio de saldos iniciales de productos no dados de baja
	 * @return
	 */
	public BigDecimal sumatorioProductos();
	
}
