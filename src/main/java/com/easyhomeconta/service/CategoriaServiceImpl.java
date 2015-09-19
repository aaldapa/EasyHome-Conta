/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.CategoriaDao;
import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.Categoria;
import com.easyhomeconta.model.User;

/**
 * @author Alberto
 *
 */
@Named
public class CategoriaServiceImpl implements CategoriaService {

	@Inject
	private CategoriaDao categoriaDao;
	
	@Inject
	private UserDao userDao;

	@Override
	public List<Categoria> getCategoriasForUser(Integer idUser) {
		List<Categoria> lstCategorias=categoriaDao.findCategoriaForUser(idUser);
		return lstCategorias;
	}

	@Override
	@Transactional
	public void saveCategoria(Categoria categoria, Integer idUsuario) {
		//Determino si se trata de una alta o una modificacion por medio del id
		User user=userDao.findById(idUsuario);
		categoria.setUser(user);
		if (categoria.getIdCategoria()==null)
			categoriaDao.create(categoria);
		else
			categoriaDao.update(categoria);
	}

	@Override
	@Transactional
	public void deleteCategoria(Integer idCategoria) {
		categoriaDao.delete(idCategoria);
	}

	@Override
	public Categoria getCategoriaById(Integer idCategoria) {
		Categoria categoria=categoriaDao.findById(idCategoria);
		return categoria;
	}

}
