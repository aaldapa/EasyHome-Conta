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

	@SuppressWarnings("unchecked")
	@Override
	public List<Banco> findAllCompleteByTipoForUser(Integer idUser, Integer idTProducto) {
		
		StringBuffer sql=new StringBuffer();
		
		sql.append(" select distinct(b) from Banco b "
				+ " join fetch b.lstProductos p"
				+ " join p.tipoProducto "
				+ " join p.lstUsuarios as u" 
				+ " where u.idUser = :idUser ");
		
		//Si el Tipo de producto es null no filtramos por tipo de producto
		if (idTProducto!=null){
			//Si el tipo es diferente de 3 indicamos el tipo de producto
			if (idTProducto<3)
				sql.append(" and  p.tipoProducto.idTipoProducto = :idTipoProducto");
			else
				sql.append(" and  p.tipoProducto.idTipoProducto > :idTipoProducto");
		}
		
		Query query=entityManager.createQuery(sql.toString());
		
		//Si el Tipo de producto es null no filtramos por tipo de producto
		if (idTProducto!=null)
			query.setParameter("idTipoProducto", idTProducto);
		
		query.setParameter("idUser", idUser);
		
		entityManager.createQuery(sql.toString());
		
		List<Banco> lstBancos= (List<Banco>)query.getResultList();
		return lstBancos;
	}

	

//	@SuppressWarnings("unchecked")
//	@Override
//	public List<Banco> findAllCompleteByTipoForUser(Integer idUser, Integer idTProducto) {
//		Query query=entityManager.createQuery("select distinct(b) from Banco b "
//				+ " join fetch b.lstProductos p" 
//				+ " join p.lstUsuarios as u" 
//				+ " join p.banco "
//				+ " where u.idUser = :idUser "
//				+ " and  p.tipoProducto.idTipoProducto = :idTipoProducto"
//				+ " and p.banco.idBanco=b.idBanco"
//				);
//		
//		
//		query.setParameter("idUser", idUser);
//		query.setParameter("idTipoProducto", idTProducto);
//		
//		List<Banco> lstBancos= (List<Banco>)query.getResultList();
//		return lstBancos;
//	}

}
