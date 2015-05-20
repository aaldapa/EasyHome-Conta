package com.easyhomeconta.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.easyhomeconta.beans.TipoProductoBean;

/**
 * 
 * @author Alberto
 *
 */
public interface TipoProductoService {

	/**
	 * Obtiene todos tipos de producto de alta en base de datos y los mete en un bean para facilitar su renderizacion
	 * @param idUser
	 * @return
	 */
	public List<TipoProductoBean> getLstTipoProductosActivos();

	
	/**
	 * Lista para representar los tipos de producto en un combo
	 * @return
	 */
	public List<SelectItem> getLstTipoProductosActivosForCombo();
	
	/**
	 * Modifica o crea una nuevo tipo de producto en base de datos  en funcion de si
	 * el id esta cargado o es nulo.
	 * @param tipoProducto
	 * @return
	 */
	public TipoProductoBean saveTipoProducto(TipoProductoBean bean);
	
	/**
	 * Baja logica del tipo de producto al que pertenezca el id pasado como parametro
	 * @param idTipoProducto
	 */
	public void deleteTipoProducto(Integer idTipoProducto);

}
