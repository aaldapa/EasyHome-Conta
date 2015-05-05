package com.easyhomeconta.service;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.LogonInfoDao;
import com.easyhomeconta.model.LogonInfo;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.Enumeraciones.LogonType;

@Named
public class LogonServiceImpl implements LogonService {

	@Inject
	LogonInfoDao logonInfoDao;
	
	@Override
	@Transactional
	public void createLogin(User user) {
		LogonInfo login=new LogonInfo();
		login.setFecha(new Date());
		login.setUser(user);
		login.setLogonType(LogonType.LOGIN);
		
		logonInfoDao.create(login);
	}

	@Override
	@Transactional
	public void createLogout(User user, LogonType logonType) {
		LogonInfo login=new LogonInfo();
		login.setFecha(new Date());
		login.setUser(user);
		login.setLogonType(logonType);

		logonInfoDao.create(login);

	}

	@Override
	public LogonInfo findLastLoginByidUser(Integer id) {
		LogonInfo logonInfo=logonInfoDao.findLastLoginByIdUser(id);
		return logonInfo;
	}

}
