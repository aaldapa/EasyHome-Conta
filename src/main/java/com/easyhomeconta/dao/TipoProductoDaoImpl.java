package com.easyhomeconta.dao;

import java.math.BigDecimal;
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

	@Override
	public BigDecimal getSumatorioOperacionesByTProducto(Integer idTProducto) {
		
		Query query = entityManager.createQuery(
				" select  sum(op.importe) "
				+ " from Operacion op "
				+ " where op.producto.idProducto in "
					+ "( select p.idProducto from Producto p "
					+ "where p.tipoProducto.idTipoProducto= :idTProducto)");
		query.setParameter("idTProducto", idTProducto);
		
		BigDecimal sumatorio=(BigDecimal)query.getSingleResult();
		
		return sumatorio==null?new BigDecimal(0):sumatorio;
	}

	@Override
	public BigDecimal getSumatorioProductosByTProducto(Integer idTProducto) {
		Query query = entityManager.createQuery(
				" select  sum(p.importe) "
				+ " from Producto p "
				+ " where p.tipoProducto.idTipoProducto= :idTProducto)");
		query.setParameter("idTProducto", idTProducto);
		
		BigDecimal sumatorio=(BigDecimal)query.getSingleResult();
		
		return sumatorio==null?new BigDecimal(0):sumatorio;
	}

	

}
