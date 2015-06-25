/**
 * 
 */
package com.easyhomeconta.dao;

import java.math.BigDecimal;
import java.util.List;

import com.easyhomeconta.model.TipoProducto;

/**
 * @author Alberto
 *
 */
public interface TipoProductoDao extends GenericDao<TipoProducto> {

	/**
	 * Devuelve los tipos de productos activos, es decir, baja=N.
	 * @return
	 */
	public List<TipoProducto> findTiposProductoActivo();
	
	/**
	 * Devuelve el sumatorio de ingresos + gastos (balance) de las operaciones para todos
	 * los productos del tipo indicado. 
	 * @param idTProducto
	 * @return
	 */
	public BigDecimal getSumatorioOperacionesByTProducto(Integer idTProducto);
	
	/**
	 * Devuelve el sumatorio de los saldos iniciales de los productos del tipo indicado
	 * @param idTProducto
	 * @return
	 */
	public BigDecimal getSumatorioProductosByTProducto(Integer idTProducto);
	
	
}
