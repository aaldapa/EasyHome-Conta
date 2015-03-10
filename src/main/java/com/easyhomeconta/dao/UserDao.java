package com.easyhomeconta.dao;

import com.easyhomeconta.model.User;

public interface UserDao extends GenericDao<User> {

	/**
     * Devuelve un Object User que coincida con el username
     *
     * @param username
     * @return
     */
    public User loadUserByUsername(String username);

}
