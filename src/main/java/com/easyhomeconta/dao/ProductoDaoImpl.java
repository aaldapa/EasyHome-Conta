/**
 * 
 */
package com.easyhomeconta.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.easyhomeconta.model.Producto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class ProductoDaoImpl extends GenericDaoImpl<Producto> implements ProductoDao {

	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(ProductoDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findProductosForUser(Integer idUser) {
		
		Query query=entityManager.createQuery("select p from Producto p "
				+ " join fetch p.lstUsuarios as u"
				+ " join p.banco b "
				+ " join p.tipoProducto t"
				+ " where u.idUser = :idUser"
				+ " and p.baja = :baja");
		
		query.setParameter("idUser", idUser);
		query.setParameter("baja", SiNo.N);
		
		List<Producto> lstProductos=(List<Producto>) query.getResultList();
		
		return lstProductos;
	}

	@Override
	public BigDecimal getBalance(Integer idProducto) {
		//COALESCE is an abbreviated CASE expression that returns the first non-null operand
		Query query=entityManager.createQuery(" select COALESCE(p.importe,0) + COALESCE(SUM(o.importe),0) from Producto p "
				+ " join p.lstOperaciones o"
				+ " where p.idProducto= ?1");
		query.setParameter(1, idProducto);
		
		BigDecimal balance=(BigDecimal) query.getSingleResult();
		
		return balance;
	}

	@Override
	public Date getFechaUltimaOperacion(Integer idProducto) {
		Query query=entityManager.createQuery("select MAX(o.fecha) from Operacion o "
				+ " join o.producto p "
				+ "where p.idProducto = :idProducto");
		query.setParameter("idProducto", idProducto);
		
		Date fechaUltimaOperacion=(Date) query.getSingleResult(); 
		
		return fechaUltimaOperacion;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> findProductosOperablesForUser(Integer idUser) {
		Query query=entityManager.createQuery("select p from Producto p "
				+ " join fetch p.lstUsuarios as u"
				+ " join p.tipoProducto t"
				+ " where u.idUser = :idUser"
				+ " and p.baja = :baja"
				+ " and t.operable= :operable");
		
		query.setParameter("idUser", idUser);
		query.setParameter("baja", SiNo.N);
		query.setParameter("operable", SiNo.S);
		
		List<Producto> lstProductos=(List<Producto>) query.getResultList();
		
		return lstProductos;
		
	}

	@Override
	public Producto findWithBancoById(Integer idProducto) {
		Query query=entityManager.createQuery(" select p from Producto p"
				+ " join fetch p.banco b"
				+ " where p.idProducto = :idProducto");
		query.setParameter("idProducto", idProducto);
		Producto producto=(Producto) query.getSingleResult();
		return producto;
	}

	@Override
	public BigDecimal sumatorioProductos() {
				
		Query query=entityManager.createQuery(
				"  select sum(p.importe) from Producto p"
				+ " where p.baja= :baja");
		
		query.setParameter("baja", SiNo.N);
		BigDecimal sumatorio=(BigDecimal)query.getSingleResult();
		return sumatorio==null?new BigDecimal(0):sumatorio;
	}


}
