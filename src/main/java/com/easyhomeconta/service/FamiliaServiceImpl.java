/**
 * 
 */
package com.easyhomeconta.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.FamiliaDao;
import com.easyhomeconta.forms.FamiliaForm;
import com.easyhomeconta.model.Familia;

/**
 * @author Alberto
 *
 */
@Named
public class FamiliaServiceImpl implements FamiliaService {

	private final Logger log = Logger.getLogger(FamiliaServiceImpl.class);
	
	@Inject
	FamiliaDao familiaDao;
	
	@Override
	public List<SelectItem> getFamiliasAllForCombo() {
		
		List<Familia> lstFamilias=familiaDao.findAll();
		List<SelectItem>lstFamiliasCombo=new ArrayList<SelectItem>();
		
		for(Familia fam:lstFamilias)
			lstFamiliasCombo.add(new SelectItem(fam.getIdFamilia(),fam.getNombre()));
		
		return lstFamiliasCombo;
	}

	@Override
	public Familia getFamiliaById(Integer id) {
		Familia familia=familiaDao.findById(id);
		return familia;
	}

	@Override
	public List<FamiliaForm> getLstFamiliasAllBean() {
		 
		List<Familia> lstFamilias=familiaDao.findAll();
		List<FamiliaForm> lstFamiliasBean=new ArrayList<FamiliaForm>();
		
		//Cargo la lista con las familias parseadas a beans
		for (Familia fam:lstFamilias)
			lstFamiliasBean.add(familiaParseFamiliaBean(fam));
		
		return lstFamiliasBean;
	}
	
	private FamiliaForm familiaParseFamiliaBean(Familia familia){
		FamiliaForm familiaBean=new FamiliaForm(familia.getIdFamilia(), familia.getNombre());
		return familiaBean;
	}

	@Override
	@Transactional
	public FamiliaForm saveFamilia(FamiliaForm bean) {
		Familia familia=new Familia();
		
		//Si el id esta cargado realizamos una modificacion
		if (bean.getIdFamilia()!=null){
			familia=familiaDao.findById(bean.getIdFamilia());
			familia.setNombre(bean.getNombre());
			familiaDao.update(familia);
		}
		//Si se trata de una familia nueva 
		else{
			familia.setNombre(bean.getNombre());
			familia=familiaDao.create(familia);
			log.info(familia.getIdFamilia());
		}
		
		return familiaParseFamiliaBean(familia);
	}

	@Override
	@Transactional
	public void deleteFamilia(Integer idFamilia) {
		familiaDao.delete(idFamilia);	
	}


}
