package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.easyhomeconta.model.TipoProducto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

@SuppressWarnings("serial")
@Named
public class TipoProductoDaoImpl extends GenericDaoImpl<TipoProducto> implements
		TipoProductoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoProducto> findTiposProductoActivo() {
				
		Query query = entityManager.createQuery("select tp from TipoProducto tp where "
				+ " tp.baja=:baja");
		query.setParameter("baja", SiNo.N);
		
		List<TipoProducto> lstProductos=(List<TipoProducto>)query.getResultList();
				
		return lstProductos;
	}

	

}
