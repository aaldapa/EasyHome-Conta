package com.easyhomeconta.utils;

public class Enumeraciones {

	
	/**
	 * Tipo de Logon o acceso a la aplicacion.
	 * @author Alberto
	 *
	 */
	public enum LogonType{LOGIN, LOGOUT, TIMEDOUT}
	
	/**
	 * Campo utilizado para guardar S o N en base de datos (usado por ejemplo en bajas logicas)
	 * @author Alberto
	 *
	 */
	public enum SiNo {S, N}
	
	/**
	 * Tipos de operaciones utilizados para identificar si las mismas pertenecen a un gasto o a un ingreso.
	 * @author Alberto
	 */
	public enum TipoOperacion{GASTO, INGRESO}
}
