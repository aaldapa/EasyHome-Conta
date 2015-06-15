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
    public List<User> findAll();
    
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
     
     /**
      * Lista con los usuarios a los que le pertenece un producto concreto.
      * @param idProducto
      * @return
      */
     public List<User> findUsersByProducto(Integer idProducto);
     
     /**
      * Borra el usuario de la base de datos
      * @param user
      */
     public void deleteUser(User user);
     
     
     /**
      * Obtiene el usuario con los roles cargados
      * @param idUser
      * @return
      */
     public User findById(Integer idUser);
     
}
