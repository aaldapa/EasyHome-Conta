/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import com.easyhomeconta.beans.BancoForm;

/**
 * @author Alberto
 *
 */
public interface CuentaService {

	public List<BancoForm> findAllForUser(Integer idUser);
	
}
