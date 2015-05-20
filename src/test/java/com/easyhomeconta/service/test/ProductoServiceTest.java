package com.easyhomeconta.service.test;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.dao.TipoProductoDao;
import com.easyhomeconta.dao.UserDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/spring/*.xml"})
public class ProductoServiceTest {

	@Inject
	ProductoDao productoDao; 
	
	@Inject
	UserDao userDao;
	
	@Inject
	BancoDao bancoDao;
	
	@Inject
	TipoProductoDao tProductoDao;
	
	@Test
	public void testGetProductosForUser() {
		fail("Not yet implemented");
	}

	@Test
	public void testSaveProducto() {
		fail("Not yet implemented");
	}

	@Test
	public void testDeleteProducto() {
		fail("Not yet implemented");
	}

}
