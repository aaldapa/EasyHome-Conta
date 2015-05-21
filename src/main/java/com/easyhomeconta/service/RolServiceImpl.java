/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import com.easyhomeconta.dao.RolDao;
import com.easyhomeconta.model.Rol;

/**
 * @author Alberto
 *
 */
@Named
public class RolServiceImpl implements RolService {

	
	@Inject
	RolDao rolDao;
	
	@Override
	public List<Rol> findAllRoles() {
		List<Rol> lstRoles=rolDao.findAll();
		return lstRoles;
	}
}
