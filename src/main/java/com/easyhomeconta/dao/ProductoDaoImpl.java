/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.easyhomeconta.model.Producto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class ProductoDaoImpl extends GenericDaoImpl<Producto> implements
		ProductoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findProductosForUser(Integer idUser) {
		
		Query query=entityManager.createQuery("select p from Producto p "
				+ " join fetch p.lstUsuarios as u" 
				+ " where u.idUser = :idUser"
				+ " and p.baja = :baja");
		
		query.setParameter("idUser", idUser);
		query.setParameter("baja", SiNo.N);
		
		List<Producto> lstProductos=(List<Producto>) query.getResultList();
		
		return lstProductos;
	}

}
