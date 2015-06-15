/**
 * 
 */
package com.easyhomeconta.service;

import java.io.InputStream;
import java.util.Date;
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
	 * Carga la consulta en una lista para ser renderizada en la datetable
	 * @param fInicio
	 * @param fFin
	 * @param idProducto
	 * @param idCategoria
	 * @param busqueda
	 * @param idUsuario
	 * @return
	 */
	public ResultadoConsultaForm getLstOperacionesForm(Date fInicio, Date fFin, Integer idProducto, Integer idCategoria, String busqueda, Integer idUsuario );
	
	/**
	 * Carga la lista de operaciones existente en el archivo xls.
	 * @param file. Hoja de calculo
	 * @param idProducto. Cuenta en la que se quieren introducir las operaciones
	 * @return
	 */
	public List<OperacionForm> getLstOperacionesFormXLS(InputStream file, Integer idProducto);
	
	/**
	 * Recibe la lista de operaciones y el producto al que pertenecen para guardar en base de datos. 
	 * Tanto las operaciones como la categorizacion seleccionada de cada operacion. Como tilizamos el metodo
	 * para modificar operaciones exitentes y guardar nuevas operaciones importadas de un excel, mediante el 
	 * parametro accion podemos distingir cada caso.
	 * @param lstOperacionesForm
	 * @param idProducto
	 * @param accion Posibles valores {IMPORT, UPDATE}
	 */
	public void saveOperaciones(List<OperacionForm> lstOperacionesForm,
			Integer idProducto, String accion);
	
	/**
	 * Crea o modifica una operacion en base de datos en funcion del id. Si el id es null crea.
	 * @param operacion
	 */
	public void saveOperacion (OperacionForm operacion);
	
	/**
	 * Elimina la las operaciones de base de datos con los ids existentes en la lista de operaciones seleccionas
	 * @param lstOperacionesForm
	 */
	public void deleteOperaciones(List<OperacionForm> lstOperacionesForm);
}
