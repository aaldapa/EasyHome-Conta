package com.easyhomeconta.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.model.Banco;

@Named
public class BancoServiceImpl implements BancoService {

	@Inject
	BancoDao bancoDao;
	
	@Override
	public List<SelectItem> getLstBancosActivosForCombo() {
		List<Banco>lstBancos=bancoDao.findLstBancosActivos();
		List<SelectItem> lstItems=new ArrayList<SelectItem>();
		
		for(Banco banco:lstBancos)
			lstItems.add(new SelectItem(banco.getIdBanco(), banco.getNombre()));
		
		return lstItems;
	}

}
