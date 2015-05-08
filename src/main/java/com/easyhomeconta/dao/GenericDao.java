package com.easyhomeconta.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author Alberto
 *
 * @param <T>
 */
public interface GenericDao <T> extends Serializable{
	
	/**
	 * Metodo para crear una nueva entidad
	 * @param entity
	 * @return
	 */
	public T create(T entity);
    
	/**
     * Metodo que modifica la entidad
     * @param entity
     * @return
     */
	public T update(T entity);
	
	/**
	 * Metodo que elimina la entidad
	 * @param entity
	 */
    public void delete(Object id);
    
    /**
     * Metodo que devuelve la entidad que cuyo id corresponde con el parametro pasado
     * @param id
     * @return
     */
    public T findById(Object id);
    
    /**
     * Metodo que devuelve todas las entidades
     * @return
     */
    public List<T> findAll();
    
    /**
     * Metodo que devuelve el numero de registros totales para una entidad
     * @param params
     * @return
     */
    @SuppressWarnings("rawtypes")
	public long countAll(final Map params);

}
