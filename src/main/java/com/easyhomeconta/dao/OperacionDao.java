/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.Date;
import java.util.List;

import com.easyhomeconta.model.Operacion;
import com.easyhomeconta.model.Producto;

/**
 * @author Alberto
 *
 */
public interface OperacionDao extends GenericDao<Operacion> {


	/**
	 * Dado un id de operacion devuelve su producto.
	 * @param id
	 * @return
	 */
	public Producto getProductoByIdOperacion(Long id);
	
	/**
	 * Obtiene la lista de operaciones para todos o un producto determinado que se hayan producido en el rango de fechas indicado. Cada operacion
	 * tendra cargada la categoria con la que el usuario logado ha categorizado la operacion. 
	 * @param fInicio
	 * @param fFin
	 * @param idProducto. Si es nulo devuelve la lista de operaciones para todos los productos
	 * @param idCategoria
	 * @param busqueda
	 * @param idUsuario
	 * @return
	 */
	public List<Operacion> findOperacionesWithCategoria(Date fInicio, Date fFin, Integer idProducto,  Integer idCategoria, String busqueda, Integer idUsuario);
	
	/**
	 * Sobreescribre GenericDao para devolver la operacion con los atributos complejos cargado
	 * @param id
	 * @return
	 */
	public Operacion findById(Long id);
	
	
	/**
	 * Obtiene todas las operaciones para un producto
	 * @param idProducto
	 * @return
	 */
	public List<Operacion> findAllByIdProducto(Integer idProducto);
	
}
