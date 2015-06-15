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
	
	/**
	 * Devuelve la categoria con la que un usuario a categorizado una operacion. En caso de no tener categorizacion devuelve null.
	 * @param idOperacion
	 * @param idUsuario
	 * @return
	 */
	public Categoria findCategorizacionUsuarioByIdOperacion(Long idOperacion,Integer idUsuario);
}
