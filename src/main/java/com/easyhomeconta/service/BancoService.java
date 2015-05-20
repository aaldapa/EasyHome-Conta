/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import javax.faces.model.SelectItem;

/**
 * @author Alberto
 *
 */
public interface BancoService {

	/**
	 * Devuelve una lista de bancos activos (baja N) preparada para ser renderizada en un combo
	 * @return
	 */
	public List<SelectItem> getLstBancosActivosForCombo();
	
}
