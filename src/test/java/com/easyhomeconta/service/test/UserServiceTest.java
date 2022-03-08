package com.easyhomeconta.service.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easyhomeconta.model.Rol;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.UserService;

/**
 * @author Alberto
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/*.xml"})
public class UserServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	private final Logger log = Logger.getLogger(UserServiceTest.class);
	
	@Inject
	UserService userService;
	
	@Test
	/**
	 * Prueba con un usuario con permiso de administracion para ejecutar el metodo
	 */
	public void testFindAllUsers() {
		//Get the user by username from configured user details service
        UserDetails userDetails = userService.loadUserByUsername ("Alberto");
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
		
		List<User> lstUser=userService.findAllUsers();
		assertTrue(lstUser.size()>0);
	}
	
	/**
	 * Prueba cargando un usuario sin permiso de administrador para ejecutar el metodo
	 */
	@Test(expected = AccessDeniedException.class)
	public void testFindAllUsers1() {

        UserDetails userDetails = userService.loadUserByUsername ("Prueba");
        Authentication authToken = new UsernamePasswordAuthenticationToken (userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
		
		userService.findAllUsers();
	}

	@Test
	public void testCreateUser() {
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
		userService.createUser(user);
		
		//User userCreado=userService.getUserById(user.getIdUser());
		
		//log.info(userCreado.getUsername());
	
	}

	@Test
	public void testDeleteUser() {
		userService.deleteUser(new Integer (1));
	}

	@Test
	public void testUpdateUser() {
		User user=null;
		user=userService.getUserById(new Integer (1));
		user.setApellido1("Cuestaa");
		//Modificar un apellido
		userService.updateUser(user);
		
		User userCreado=userService.getUserById(new Integer(1));
		log.info(userCreado.getApellido1());
	}

	@Test
	public void testLoadUserByUsername() {
		User user=(User)userService.loadUserByUsername("Alberto");
		assertNotNull(user);
	}

	@Test
	public void testFindAllRoles() {
		List<Rol> lstRoles= userService.findAllRoles();
		assertTrue(lstRoles.size()>0);
	}

	@Test
	public void testGetUserById() {
		assertNotNull(userService.getUserById(new Integer (1)));
	}

}
