/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import com.easyhomeconta.model.Banco;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class BancoDaoImpl extends GenericDaoImpl<Banco> implements BancoDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> findLstBancosActivos() {
		Query query = entityManager.createQuery("select b from Banco b where "
				+ " b.baja=:baja");
		query.setParameter("baja", SiNo.N);
		
		List<Banco> lstBancos=(List<Banco>)query.getResultList();
		return lstBancos;
	}


}
