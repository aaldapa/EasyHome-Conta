package com.easyhomeconta.dao;

import java.util.List;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;

public interface UserDao extends GenericDao<User> {
	
	/**
     * Devuelve un Object User que coincida con el username
     * @param username
     * @return
     */
    public User loadUserByUsername(String username);
    
    /**
     * Devuelve tan solo los usuarios activos de la base de datos.
     * @return
     */
    public List<User> findAllActiveUsers();
    
    /**
     * Devuelve una lista con todos los roles de la tabla Roles.
     * @return
     */
    public List<Rol> findAllRoles();
    
    /**
     * Devuelve la lista de usuarios cuyo username coincide con el pasado como parametro
     * Este metodo es utilizado para validar que no exista ya un username utilizado.
     * @param username
     * @return
     */
     public List<User> findUsersbyUsername(String username);
}
