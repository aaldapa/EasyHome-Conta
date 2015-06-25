/**
 * 
 */
package com.easyhomeconta.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import org.apache.log4j.Logger;

import com.easyhomeconta.model.Operacion;
import com.easyhomeconta.model.OperacionView;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;
import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class OperacionDaoImpl extends GenericDaoImpl<Operacion> implements OperacionDao {

	private final Logger log = Logger.getLogger(OperacionDaoImpl.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Operacion> findOperacionesWithCategoria(Date fInicio, Date fFin, Integer idProducto, Integer idCategoria, String busqueda, Integer idUsuario) {
		
		log.info(fInicio+ " "+ fFin + " "+ idProducto + " "+ idCategoria +" "+ busqueda + " "+ idUsuario);
		
		List<Operacion> lstOperaciones=new ArrayList<Operacion>();
		List<Operacion> lstOperacionesConCategoria=new ArrayList<Operacion>();
		List<Operacion> lstOperacionesSinCategoria=new ArrayList<Operacion>();
		StringBuffer lstIdOperaciones=new StringBuffer();
		
		
		//Busqueda de operaciones CON CATEGORIA
		Query query1=null;
		StringBuffer sql1=new StringBuffer();
			
		sql1.append(" select op from Operacion op "
			+ " left join fetch op.lstCategorias cats"
			+ " join op.producto pr"
			+ " where op.fecha between :fInicio and :fFin "
			+ " and cats.user.idUser= :idUser"
	   	);
		
		if (idCategoria!=null && idCategoria.intValue()>0)
			sql1.append(" and cats.idCategoria = :idCategoria");
		
		if (idProducto!=null)
			sql1.append(" and pr.idProducto = :idProducto");
		else{
			sql1.append( " and pr.idProducto in (select p.idProducto from Producto p "
					+ " join p.lstUsuarios us "
					+ " where us.idUser = :idUser "
					+ " and p.baja = :baja)");
		}
		
		if (busqueda!=null && !busqueda.isEmpty())
			sql1.append(" and op.concepto like :concepto" );
		
		query1=entityManager.createQuery(sql1.toString());
		query1.setParameter("fInicio", fInicio, TemporalType.DATE);
		query1.setParameter("fFin", fFin, TemporalType.DATE);
		query1.setParameter("idUser",idUsuario);
		
		if (idProducto!=null)
			query1.setParameter("idProducto", idProducto);
		else{
			query1.setParameter("baja", SiNo.N);		
		}
		 
		if (idCategoria!=null){
			if (idCategoria>0)
				query1.setParameter("idCategoria", idCategoria);
		}
		
		if (busqueda!=null && !busqueda.isEmpty())
			query1.setParameter("concepto", "%"+busqueda+"%");

		//La lista de ids de operaciones CON CATEGORIA se necesita siempre aunque en ocasiones no se carguen en la lista.
		lstOperacionesConCategoria=(List<Operacion>) query1.getResultList();
		//Las operaciones CON CATEGORIA NO SE AÑADEN a la lista si se solicitan operaciones con CATEGORIA=NINGUNA */ 
		if (idCategoria==null || idCategoria.intValue()>0 )
			lstOperaciones.addAll(lstOperacionesConCategoria);

		
		//Busqueda de operaciones SIN CATEGORIA. Estas se añaden cuando se solicitan operaciones con CATEGORIA=TODAS o CATEGORIAS=NINGUNA
		if (idCategoria==null || idCategoria.intValue()==0){

			//Cargo la lista de ids de las operacion con categoria
			for (int i =0;i<lstOperacionesConCategoria.size();i++ ){
				String id=lstOperacionesConCategoria.get(i).getIdOperacion().toString();
				if (i!=0)
					lstIdOperaciones.append(", ");
				lstIdOperaciones.append(id);
			}
			
			Query query2=null;
			
			StringBuffer sql2=new StringBuffer();
			
			sql2.append(" select op from Operacion op "
				+ " join op.producto pr, User u"
				+ " where op.fecha between :fInicio and :fFin "
				+ " and u.idUser= :idUser ");
			
			//Si la lista no esta vacia
			if (lstIdOperaciones.length()>0)
				sql2.append(" and op.idOperacion not in ("+lstIdOperaciones.toString()+")");

			if (idProducto!=null)
				sql2.append(" and pr.idProducto = :idProducto");
			else{
				sql2.append( " and pr.idProducto in (select p.idProducto from Producto p "
						+ " join p.lstUsuarios us "
						+ " where us.idUser = :idUser "
						+ " and p.baja = :baja)");
			}
			
			if (busqueda!=null && !busqueda.isEmpty())
				sql2.append(" and op.concepto like :concepto" );
			
			query2=entityManager.createQuery(sql2.toString());
			
			query2.setParameter("idUser",idUsuario);
			
			query2.setParameter("fInicio", fInicio, TemporalType.DATE);
			query2.setParameter("fFin", fFin, TemporalType.DATE);
			
			if (idProducto!=null)
				query2.setParameter("idProducto", idProducto);
			else
				query2.setParameter("baja", SiNo.N);
			
			if (busqueda!=null && !busqueda.isEmpty())
				query2.setParameter("concepto", "%"+busqueda+"%");	
			
			lstOperacionesSinCategoria=(List<Operacion>) query2.getResultList();
			
			
			/* Añado las operaciones sin categoria*/
			lstOperaciones.addAll(lstOperacionesSinCategoria);
						
		}
		
		//Obtenida la lista, la ordeno por fecha e idOperacion
		Collections.sort(lstOperaciones,Operacion.orderByFechaAndIdAsc);
		
		return lstOperaciones;
	}

	@Override
	public Producto getProductoByIdOperacion(Long id) {
		Query query=entityManager.createQuery(
				  " select p from Operacion o "
				+ " join o.producto p "
				+ " where o.idOperacion= :idOperacion");
		
		query.setParameter("idOperacion", id);
		
		Producto producto= (Producto) query.getSingleResult(); 
		
		return producto;
	}

	@Override
	public Operacion findById(Long id) {
		Query query1=entityManager.createQuery(
				" select o from Operacion o "
				+ " join fetch o.lstCategorias cats"
				+ " join o.producto p"
				+ " where o.idOperacion= :idOperacion");
		
		query1.setParameter("idOperacion", id);
		
		try {
			return (Operacion) query1.getSingleResult();
		} catch (Exception e) {
			Query query2=entityManager.createQuery(
					" select o from Operacion o "
					+ " where o.idOperacion= :idOperacion");
			
			query2.setParameter("idOperacion", id);
			
			return (Operacion) query2.getSingleResult();
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Operacion> findAllByIdProducto(Integer idProducto) {
		Query query=entityManager.createQuery(" select o from Operacion o "
				+ " join o.producto p"
				+ " where p.idProducto= :idProducto");
		
		query.setParameter("idProducto", idProducto);
	
		List<Operacion> lstOperaciones=(List<Operacion>) query.getResultList();
		return lstOperaciones;
	}

	@Override
	public BigDecimal sumatorioOperaciones(TipoOperacion tipoOperacion, Date inicio, Date fin) {
		
		StringBuffer sql =new StringBuffer();
		
		sql.append(" select sum(op.importe) from Operacion op"
				+ " join op.producto p "
				+ " where p.baja= :baja");
				
		if (inicio!=null && fin!=null)
				sql.append(	" and op.fecha between :fInicio and :fFin ");
		
		if (tipoOperacion!=null && tipoOperacion.compareTo(TipoOperacion.INGRESO)==0)
			sql.append(	" and op.importe >=0 ");
		
		if (tipoOperacion!=null && tipoOperacion.compareTo(TipoOperacion.GASTO)==0)
			sql.append(	" and op.importe <0 ");
		
		Query query=entityManager.createQuery(sql.toString());
		
		query.setParameter("baja", SiNo.N);
		
		if (inicio!=null && fin!=null){
			query.setParameter("fInicio", inicio, TemporalType.DATE);
			query.setParameter("fFin", fin, TemporalType.DATE);
		}
			
		BigDecimal sumatorio=(BigDecimal)query.getSingleResult();
		return sumatorio==null?new BigDecimal(0):sumatorio;
	}

	@Override
	public List<OperacionView> getVistaBalancesMes(Integer year) {
		
		Query query=entityManager.createQuery(
				" select vista from OperacionView vista "
				+ "where vista.anio=  :year group by vista.mesNumero");
		
		query.setParameter("year", year);
		
		@SuppressWarnings("unchecked")
		List<OperacionView> lstOperacionesView=(List<OperacionView>) query.getResultList();
		
		return lstOperacionesView;
	}
	
	
	
}
