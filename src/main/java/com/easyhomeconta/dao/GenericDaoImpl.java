/**
 * 
 */
package com.easyhomeconta.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


/**
 * @author Alberto
 *
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {


	private static final long serialVersionUID = 1173123309122477278L;

	@PersistenceContext
	protected EntityManager entityManager;
	
	private Class<T> entityClass;
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDaoImpl() {
		Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        entityClass = (Class) pt.getActualTypeArguments()[0];
	}
	
	@Override
	public T create(final T entity) {
		entityManager.persist(entity);
		return entity;
	}

	@Override
	public T update(final T entity) {
		entityManager.merge(entity);
		return entity;
	}

	@Override
	public void delete(final Object id) {
		entityManager.remove(entityManager.getReference(entityClass, id));
		
	}

	@Override
	public T findById(final Object id) {
		return (T) entityManager.find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAllActiveUsers() {
		final StringBuffer queryString = new StringBuffer("SELECT o from ");
        queryString.append(entityClass.getSimpleName()).append(" o ");
        
        final Query query = entityManager.createQuery(queryString.toString());
        List<T> resultList= query.getResultList();
        
        return resultList;
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public long countAll(final Map params){
		final StringBuffer queryString = new StringBuffer("SELECT count(o) from ");

        queryString.append(entityClass.getSimpleName()).append(" o ");
        queryString.append(this.getQueryClauses(params, null));

        final Query query = this.entityManager.createQuery(queryString.toString());

        return (Long) query.getSingleResult();
	}

	
	/**
	 * Metodo que construye la query necesaria para realizar la consulta pertinente en funcion de los parametros
	 * @param params para los parametros
	 * @param orderParams para los criterios de ordenacion
	 * @return
	 */
	private String getQueryClauses(final Map<String, Object> params, final Map<String, Object> orderParams) {
        final StringBuffer queryString = new StringBuffer();
        if ((params != null) && !params.isEmpty()) {
                queryString.append(" where ");
                for (final Iterator<Map.Entry<String, Object>> it = params.entrySet().iterator(); it.hasNext();) {
                        final Map.Entry<String, Object> entry = it.next();
                        if (entry.getValue() instanceof Boolean) {
                                queryString.append(entry.getKey()).append(" is ").append(entry.getValue()).append(" ");
                        } else {
                                if (entry.getValue() instanceof Number) {
                                        queryString.append(entry.getKey()).append(" = ").append(entry.getValue());
                                } else {
                                        // string equality
                                        queryString.append(entry.getKey()).append(" = '").append(entry.getValue()).append("'");
                                }
                        }
                        if (it.hasNext()) {
                                queryString.append(" and ");
                        }
                }
        }
        if ((orderParams != null) && !orderParams.isEmpty()) {
                queryString.append(" order by ");
                for (final Iterator<Map.Entry<String, Object>> it = orderParams.entrySet().iterator(); it.hasNext();) {
                        final Map.Entry<String, Object> entry = it.next();
                        queryString.append(entry.getKey()).append(" ");
                        if (entry.getValue() != null) {
                                queryString.append(entry.getValue());
                        }
                        if (it.hasNext()) {
                                queryString.append(", ");
                        }
                }
        }
        return queryString.toString();
	}

}
