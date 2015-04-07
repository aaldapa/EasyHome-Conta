package com.easyhomeconta.service;

import com.easyhomeconta.model.Enumeraciones.LogonType;
import com.easyhomeconta.model.LogonInfo;
import com.easyhomeconta.model.User;

public interface LogonService {
	
	/**
	 * Crea un registro en la tabla loginInfo con la fecha y hora a la que el usuario entra en la aplicacion
	 * @param user
	 */
	public void createLogin(User user);
	
	/**
	 * Crea un regitro en la tabla loginInfo con la fecha y hora a la que el usuario deja la aplicacion 
	 * @param user
	 * @param logonType
	 */
	public void createLogout(User user, LogonType logonType);
	
	/**
	 * Busca el registro que corresponde con la ultima vez que el usuario entro en la aplicacion
	 * @param id
	 * @return
	 */
	public LogonInfo findLastLoginByidUser(Integer id);
}
