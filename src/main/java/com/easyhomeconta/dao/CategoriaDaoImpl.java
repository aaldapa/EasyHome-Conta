/**
 * 
 */
package com.easyhomeconta.dao;

import java.util.List;

import javax.inject.Named;
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
		    		+ " c.user.idUser= :idUser");
	    	query.setParameter("idUser", idUser);
		    
	    List<Categoria> lstCategorias= (List<Categoria>)query.getResultList();
	    return lstCategorias;
	}

}
