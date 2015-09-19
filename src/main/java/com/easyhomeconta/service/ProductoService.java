/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import com.easyhomeconta.forms.BancoForm;
import com.easyhomeconta.forms.ProductoForm;
import com.easyhomeconta.model.Producto;

/**
 * @author Alberto
 *
 */
public interface ProductoService {

	/**
	 * Obtiene el producto cuyo id es pasado como parametro
	 * @param idProducto
	 * @return
	 */
	public Producto getProductoById(Integer idProducto);
	
	/**
	 * Obtiene todas los productos dadas de alta en base de datos (baja N) por el usuario logado
	 * y los transforma en bean para representarlos
	 * @param idUser
	 * @return
	 */
	public List<ProductoForm> getProductosForUser(Integer idUser);
	
	/**
	 * Modifica o crea un nuevo producto en base de datos para el usuario logado en funcion de si
	 * el id del producto esta cargado o es nulo.
	 * @param bean
	 * @param idUser
	 * @return
	 */
	public ProductoForm saveProducto(ProductoForm bean, Integer idUser);
	
	/**
	 * Da de baja el producto al que pertenezca el id pasado como parametro
	 * @param idProducto
	 */
	public void deleteProducto(Integer idProducto);
	
	/**
	 * Devuelve la lista de bancos con la lista de tipos de productos a los que el usuario tiene acceso lista para ser renderizada
	 * @param idUser
	 * @param idTProducto
	 * @return
	 */
	public List<BancoForm> findAllByTypeForUser(Integer idUser, Integer idTProducto);
	
}
