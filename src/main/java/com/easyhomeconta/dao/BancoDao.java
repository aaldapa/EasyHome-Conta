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
	
}
