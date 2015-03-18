package com.easyhomeconta.controller;
/**
 * Interface que servira para implementar funcionalidades inherentes a todos los
 * managedBeans que utilicemos en la capa controladora.
 * @author cuesta
 *
 */
public interface IBasicManageBean {

	/**
	 * Añade mensaje informativo y detalle para el componente indicado.
	 * @param componentId. Id del componente
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public void addInfoMessage(String componentId, String sumaryErrorMessage, String detailErrorMessage);
	/**
	 * Añade mensaje global con detalle
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public void addInfoMessage(String sumaryErrorMessage, String detailErrorMessage);
	
	/**
	 * Añade mensaje informativo sin detalle
	 * @param infoMessage
	 */
	public void addInfoMessage(String infoMessage);
	
	/**
	 * Añade mensaje de error y detalle para el componente indicado.
	 * @param componentId. Id del componente
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public void addErrorMessage(String componentId, String sumaryErrorMessage, String detailErrorMessage);
	
	/**
	 * Añade mensaje de error global con detalle
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public void addErrorMessage(String sumaryErrorMessage, String detailErrorMessage);
	
	/**
	 * Añade mensaje de error general sin detalle
	 * @param errorMessage. Mensaje principal
	 */
	public void addErrorMessage(String errorMessage); 
	
	/**
	 * Obtiene el string correspondiente a la clave que se le pasa como parametro en el properties
	 * ubicado en el paquete de internacionalizacion (i18n)
	 * @param resourceBundle
	 * @return
	 */
	public String getString(String resourceBundle);
	
}
