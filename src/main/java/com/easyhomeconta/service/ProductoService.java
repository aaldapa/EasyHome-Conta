/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import com.easyhomeconta.beans.ProductoBean;
import com.easyhomeconta.model.Producto;

/**
 * @author Alberto
 *
 */
public interface ProductoService {

	/**
	 * Obtiene todas los productos dadas de alta en base de datos (baja N) por el usuario logado
	 * y los transforma en bean para representarlos
	 * @param idUser
	 * @return
	 */
	public List<ProductoBean> getProductosForUser(Integer idUser);
	
	/**
	 * Modifica o crea un nuevo producto en base de datos para el usuario logado en funcion de si
	 * el id del producto esta cargado o es nulo.
	 * @param bean
	 * @param idUser
	 * @return
	 */
	public ProductoBean saveProducto(ProductoBean bean, Integer idUser);
	
	/**
	 * Guarda en bd el producto que recibe
	 * @param producto
	 * @return
	 */
	public Producto updateProducto(Producto producto);
	
	/**
	 * Da de baja el producto al que pertenezca el id pasado como parametro
	 * @param idProducto
	 */
	public void deleteProducto(Integer idProducto);
	
}
