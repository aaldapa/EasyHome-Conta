/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import com.easyhomeconta.model.Banco;

/**
 * @author Alberto
 *
 */
public interface BancoDao extends GenericDao<Banco> {

	/**
	 * Lista de bancos que tienen baja N
	 * @return
	 */
	public List<Banco> findLstBancosActivos(); 
	
	/**
	 * Dado un id de usuario y un tipo de producto, devuelve la lista de bancos que poseen tipos de producto a los que el 
	 * usuario puede acceder (con sus respectivos productos).
	 * Tipo 1 Cuentas, Tipo 2 Depositos, Tipo 3 (Ni cuentas ni depositos)
	 * @param idUser
	 * @param idTProducto. 1 Cuentas, 2 Depositos, 3 Todos menos cuentas y depositos y null todos.
	 * @return
	 */
	public List<Banco> findAllCompleteByTipoForUser(Integer idUser,	Integer idTProducto);
		
}
