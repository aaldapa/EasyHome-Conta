/**
 * 
 */
package com.easyhomeconta.dao.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.NoResultException;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;

/**
 * @author Alberto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/*.xml"})
public class UserDaoTest extends AbstractTransactionalJUnit4SpringContextTests{

	private final Logger log = Logger.getLogger(UserDaoTest.class);
	
	@Inject
	UserDao userDao;
	
	/**
	 * Test method for {@link com.easyhomeconta.dao.UserDaoImpl#loadUserByUsername(java.lang.String)}.
	 */
	@Test
	public void testLoadUserByUsername() {
		
		User user=null;
		user=userDao.loadUserByUsername("Alberto");
		//Que el usuario exista
		assertNotNull(user);
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.UserDaoImpl#findAll()}.
	 */
	@Test
	public void testFindAllActiveUsers() {
		
		List<User> lstUser=userDao.findAll();
		//Existen usuarios activos
		assertTrue(lstUser.size()>0);
		
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.UserDaoImpl#findAllRoles()}.
	 */
	@Test
	public void testFindAllRoles() {
		List<Rol> lstRoles=userDao.findAllRoles();
		//Existen roles
		assertTrue(lstRoles.size()>0);
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.UserDaoImpl#findUsersbyUsername(java.lang.String)}.
	 */
	@Test
	public void testFindUsersbyUsername() {
		List<User> lstUser=userDao.findUsersbyUsername("Alberto");
		//Existen solamente 1 usuario con username Alberto
		assertTrue(lstUser.size()==1);
	}

	
	/**
	* La anotación @Rollback la indicará a spring que los cambios que involucre el método de
	* prueba no deben de comprometerse en forma permanente en la base de datos.
	* Debemos de tener en cuenta que en la ejecución del método de prueba se crea una nueva transacción y si
	* la capa de servicios (userService.*) crea otra transacción entonces la primera transacción se suspende
	* y esta ultima transacción se comprometerá si se ejecuta satisfactoriamente, por lo que el método del servicio
	* debería estar especificado de la siguiente manera:
	* @Transactional(propagation=Propagation.REQUIRED) // esto es por default, y no como:
	* @Transactional(propagation=Propagation.REQUIRES_NEW) // esto no tomaría en cuenta la anotación @Rollback
	* public void create(User user) {...}
	* */
	@Test
	@Rollback
	public void testCreate() {
		User user=new User();
		user.setFechaCreacion(new Date());
		user.setNombre("Nombre");
		user.setApellido1("Apellido1");
		
		user.setPassword("password");
		user.setUsername("pruebaTest");
		
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		assertNotNull(userDao.create(user).getIdUser());
		
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.GenericDaoImpl#update(java.lang.Object)}.
	 */
	@Test
	public void testUpdate() {
		User user=null;
		user=userDao.loadUserByUsername("Alberto");
		user.setApellido1("Cuestaa");
		//Modificar un apellido
		userDao.update(user);
		
		User userCreado=userDao.loadUserByUsername("Alberto");
		log.info(userCreado.getApellido1());
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.GenericDaoImpl#delete(java.lang.Object)}.
	 */
	@Test(expected = NoResultException.class)
	@Rollback
	public void testDelete() {
		//Eliminar el usuario de username Alberto
		userDao.delete(new Integer(1));
		userDao.findById(1);
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.GenericDaoImpl#findById(java.lang.Object)}.
	 */
	@Test
	public void testFindById() {
		User user=userDao.findById(new Integer (1));
		for (Producto p:user.getLstProductos())
			log.info("id Prod. :" +p.getIdProducto());
	}

	/**
	 * Test method for {@link com.easyhomeconta.dao.GenericDaoImpl#countAll(java.util.Map)}.
	 */
	@Test
	public void testCountAll() {
		log.info(userDao.countAll(null)+" usuarios en bd");
		
	}

}
