/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import com.easyhomeconta.model.Categoria;

/**
 * @author Alberto
 *
 */
public interface CategoriaDao extends GenericDao<Categoria> {

	/**
	 * Devuelve la lista de categorias del usuario
	 * @param idUser
	 * @return
	 */
	public List<Categoria> findCategoriaForUser (Integer idUser);
	
}
