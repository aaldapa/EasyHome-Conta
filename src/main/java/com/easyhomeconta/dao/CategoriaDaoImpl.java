/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.easyhomeconta.model.Categoria;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named
public class CategoriaDaoImpl extends GenericDaoImpl<Categoria> implements
		CategoriaDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findCategoriaForUser(Integer idUser) {
		Query query = entityManager.createQuery("select c from Categoria c where "
		    		+ " c.user.idUser= :idUser"
		    		+ " order by c.nombre");
	    	query.setParameter("idUser", idUser);
		    
	    List<Categoria> lstCategorias= (List<Categoria>)query.getResultList();
	    return lstCategorias;
	}

	@Override
	public Categoria findCategorizacionUsuarioByIdOperacion(Long idOperacion, Integer idUsuario) {
		Query query=entityManager.createQuery(
				" select cats from Operacion o "
				+ " left join o.lstCategorias cats"
				+ " where o.idOperacion= :idOperacion"
				+ " and cats.user.idUser = :idUser");
		query.setParameter("idOperacion", idOperacion);
		query.setParameter("idUser", idUsuario);
		
		try {
			Categoria categorizacion=(Categoria)query.getSingleResult();
			return categorizacion;
		} catch (NoResultException e) {
			return null;
		}
		
		
	}

}
