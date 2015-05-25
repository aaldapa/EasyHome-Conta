package com.easyhomeconta.service;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.beans.BancoBean;
import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.model.Banco;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

@Named
public class BancoServiceImpl implements BancoService {

	private final Logger log = Logger.getLogger(BancoServiceImpl.class);
	
	@Inject
	BancoDao bancoDao;
	
	public Banco getBancoById(Integer id){
		Banco banco=bancoDao.findById(id);
		return banco;
	}
	
	@Override
	public List<SelectItem> getLstBancosActivosForCombo() {
		List<Banco>lstBancos=bancoDao.findLstBancosActivos();
		List<SelectItem> lstItems=new ArrayList<SelectItem>();
		
		for(Banco banco:lstBancos)
			lstItems.add(new SelectItem(banco.getIdBanco(), banco.getNombre()));
		
		return lstItems;
	}

	@Override
	public List<BancoBean> getLstBancosActivos() {
		 List<Banco>lstBancos=bancoDao.findLstBancosActivos();
		 List<BancoBean> lstBeans=new ArrayList<BancoBean>();
		 
		 for(Banco banco:lstBancos)
			 lstBeans.add(new BancoBean(banco.getIdBanco(), banco.getNombre(), banco.getLogo()));
		 
			 
		return lstBeans;
	}

	@Override
	@Transactional
	public BancoBean saveBanco(BancoBean bean, Boolean cambiarFoto) {
		
		if (bean.getIdBanco()==null){
			Banco newBanco=new Banco(null, bean.getNombre(), SiNo.N, parseUploadedFileToArrayByte(bean.getImagen()));
			newBanco=bancoDao.create(newBanco);
			//Guardo el id del nuevo banco para mostrarlo en el listado de bancos
			bean.setIdBanco(newBanco.getIdBanco());
		}
		else{
			Banco banco=bancoDao.findById(bean.getIdBanco());
			banco.setNombre(bean.getNombre());
			if (cambiarFoto){
				//Si se carga foto
//				if (bean.getImagen().getSize()!=0){
//					try{
//						banco.setLogo(IOUtils.toByteArray(bean.getImagen().getInputstream()));
//						bean.setLogoBanco(banco.getLogo());
//					}catch(Exception e){
//						log.error(e.getMessage());
//					}
//				}
				
				banco.setLogo(parseUploadedFileToArrayByte(bean.getImagen()));
				bean.setLogoBanco(banco.getLogo());
			}
			
			bancoDao.update(banco);
		}
		
		return bean;
	}

	@Override
	@Transactional
	public void deleteBanco(Integer idBanco) {
		Banco banco=bancoDao.findById(idBanco);
		banco.setBaja(SiNo.S);
		//Baja logica
		bancoDao.update(banco);
	}

	/**
	 * Convierte la imagen seleccionada en el formulario a un array de byte para ser guardado en bd
	 * @param img
	 * @return
	 */
	private byte[] parseUploadedFileToArrayByte(UploadedFile img){
		
		byte[] logo=null;
		if (img!=null && img.getSize()>0){
			try{
				logo=IOUtils.toByteArray(img.getInputstream());
			}catch(Exception e){
				log.error(e.getMessage());
			}
		}
		return logo;
	}
	
}
