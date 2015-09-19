/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import com.easyhomeconta.model.Categoria;

/**
 * @author Alberto
 *
 */
public interface CategoriaService {

	/**
	 * Obtiene la categoria con el id pasado como parametro
	 * @param idCategoria
	 * @return
	 */
	public Categoria getCategoriaById(Integer idCategoria);
	
	/**
	 * Obtiene todas las categorias dadas de alta en base de datos por el usuario logado
	 * @param idUser
	 * @return
	 */
	public List<Categoria> getCategoriasForUser(Integer idUser);
	
	/**
	 * Modifica o crea una nueva categoria en base de datos para el usuario logado en funcion de si
	 * el id de categoria esta cargado o es nulo.
	 * @param categoria
	 * @param idUser. Id del usuario logado
	 */
	public void saveCategoria(Categoria categoria, Integer idUser);
	
	/**
	 * Elimina la categoria a la que pertenezca el id pasado como parametro
	 * @param idCategoria
	 */
	public void deleteCategoria(Integer idCategoria);
	
}
