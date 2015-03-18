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
     * Devuelve una lista con todos los roles de la tabla Roles.
     * @return
     */
    public List<Rol> findAllRoles();
}
