package com.easyhomeconta.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.easyhomeconta.beans.FamiliaForm;
import com.easyhomeconta.model.Familia;

/**
 * @author Alberto
 *
 */
public interface FamiliaService {

	/**
	 * Carga todas las familias de base de datos en una lista preparada para mostrar los
	 * datos en un combo.
	 * @return
	 */
	public List<SelectItem> getFamiliasAllForCombo();
	
	/**
	 * Carga todas las familias en una lista de beans para su representacion
	 * @return
	 */
	public List<FamiliaForm> getLstFamiliasAllBean();
	
	/**
	 * Obtiene una Familia por id
	 * @param id
	 * @return
	 */
	public Familia getFamiliaById(Integer id);
	
	/**
	 * Modifica o crea una familia en funcion del valor del id y devuelve un bean
	 * @param familia
	 * @return
	 */
	public FamiliaForm saveFamilia(FamiliaForm familia);
	
	/**
	 * Elimina una familia de base de datos
	 * @param idFamilia
	 */
	public void deleteFamilia(Integer idFamilia);
}
