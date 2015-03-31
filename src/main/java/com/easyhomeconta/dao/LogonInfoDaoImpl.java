package com.easyhomeconta.dao;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.easyhomeconta.model.LogonInfo;

@Named
public class LogonInfoDaoImpl extends GenericDaoImpl<LogonInfo> implements LogonInfoDao{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6738133601619879336L;

	@Override
	public LogonInfo findLastLoginByIdUser(Integer idUser) {
		LogonInfo access= new LogonInfo();
		
		Query query=entityManager.createQuery(" select a from LogonInfo a join fetch a.user "
				+ " where a.user.idUser= :idUser and a.logonType='LOGIN' "
				+ " order by a.fecha desc");
		query.setParameter("idUser", idUser);
		query.setMaxResults(1);
		
		try{
			access=(LogonInfo) query.getSingleResult();
		}catch(NoResultException ex){
			ex.printStackTrace();
		}
		
		return access;
	}

	@Override
	public LogonInfo findLastLogoutByIdUser(Integer idUser) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
