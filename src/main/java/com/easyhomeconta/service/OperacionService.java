/**
 * 
 */
package com.easyhomeconta.service;

import java.io.InputStream;
import java.util.List;

import javax.faces.model.SelectItem;

import com.easyhomeconta.forms.OperacionForm;

/**
 * @author Alberto
 *
 */
public interface OperacionService {

	/**
	 * Devuelve la lista de productos que admiten operaciones a los que tiene acceso el usuario dado para ser
	 * mostrada en un combo.
	 * @param idUsuario.
	 * @return
	 */
	public List<SelectItem> getLstProductosOperables(Integer idUsuario);
	
	/**
	 * Devuelve la lista de categorias creadas por el usuario
	 * @param idUsuario
	 * @return
	 */
	public List<SelectItem> getLstCategorias(Integer idUsuario);
	
	/**
	 * Carga la lista de operaciones existente en el archivo xls.
	 * @param file. Hoja de calculo
	 * @param idProducto. Cuenta en la que se quieren introducir las operaciones
	 * @return
	 */
	public List<OperacionForm> getLstOperacionesFormXLS(InputStream file, Integer idProducto);
	
	
	/**
	 * Recibe la lista de operaciones y el producto al que pertenecen para guardar en base de datos 
	 * Tanto las operaciones como la categorizacion seleccionada de cada operacion.
	 * @param lstOperacionesForm
	 * @param idProducto
	 */
	public void saveOperaciones(List<OperacionForm>lstOperacionesForm, Integer idProducto);
	
}
