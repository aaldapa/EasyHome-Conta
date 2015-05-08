/**
 * 
 */
package com.easyhomeconta.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.beans.TipoProductoBean;
import com.easyhomeconta.dao.GenericDaoImpl;
import com.easyhomeconta.dao.TipoProductoDao;
import com.easyhomeconta.model.TipoProducto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class TipoProductoServiceImpl extends GenericDaoImpl<TipoProducto> implements
		TipoProductoService {

	@Inject
	private TipoProductoDao tipoProductoDao;
	
	@Override
	public List<TipoProductoBean> getLstTipoProductosActivos() {
		List<TipoProducto> lstTipoProductos=tipoProductoDao.findTiposProductoActivo();
		
		List<TipoProductoBean> lstBeans=new ArrayList<TipoProductoBean>();
		
		for (TipoProducto tipoProducto:lstTipoProductos){
			TipoProductoBean bean=parseEntityToBean(tipoProducto);
			lstBeans.add(bean);
		}
		
		return lstBeans;
	}

	@Override
	@Transactional
	public TipoProductoBean saveTipoProducto(TipoProductoBean bean) {
		
		TipoProducto tipoProducto=parseBeanToEntity(bean);
		
		tipoProducto.setBaja(SiNo.N);
		
		if (bean.getIdTipoProducto()==null)
			tipoProducto=tipoProductoDao.create(tipoProducto);
		else
			tipoProducto=tipoProductoDao.update(tipoProducto);
		
		return parseEntityToBean(tipoProducto); 
		
		
	}

	@Override
	@Transactional
	public void deleteTipoProducto(Integer idTipoProducto) {
		TipoProducto tipoProducto=tipoProductoDao.findById(idTipoProducto);
		tipoProducto.setBaja(SiNo.S);
		tipoProductoDao.update(tipoProducto);
	}

	
	private TipoProductoBean parseEntityToBean(TipoProducto tipoProducto){
		TipoProductoBean bean=new TipoProductoBean();
		bean.setIdTipoProducto(tipoProducto.getIdTipoProducto());
		bean.setNombre(tipoProducto.getNombre());
		bean.setNotas(tipoProducto.getNotas());
		bean.setOperable((tipoProducto.getOperable().compareTo(SiNo.S)==0));
		
		return bean;
		
	}
	
	private TipoProducto parseBeanToEntity(TipoProductoBean bean){
		TipoProducto tipoProducto=new TipoProducto();
		tipoProducto.setIdTipoProducto(bean.getIdTipoProducto());
		tipoProducto.setNombre(bean.getNombre());
		tipoProducto.setNotas(bean.getNotas());
		tipoProducto.setOperable(bean.getOperable() ? SiNo.S : SiNo.N);
		
		return tipoProducto;
	}
}

