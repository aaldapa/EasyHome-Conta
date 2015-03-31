package com.easyhomeconta.dao;

import com.easyhomeconta.model.LogonInfo;

public interface LogonInfoDao extends GenericDao<LogonInfo> {

	
	/**
	 * Registro correspondiente a la ultima entrada de un usuario
	 * @param idUser
	 * @return
	 */
	public LogonInfo findLastLoginByIdUser(Integer idUser);
	/**
	 * Registro correspondiente a la ultima salida de un usuario
	 * @param idUser
	 * @return
	 */
	public LogonInfo findLastLogoutByIdUser(Integer idUser);
	
}
