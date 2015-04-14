/**
 * 
 */
package com.easyhomeconta.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 * Clase de utilidades
 * @author Alberto
 *
 */
public class MyUtils {

	/**
	 * Codifica el password mediante BCrypt 
	 * @param password
	 * @return password codificada
	 */
	public static String codificarPasswordBcrypt(String password){
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
		
		return hashedPassword ;
	}
	
}
