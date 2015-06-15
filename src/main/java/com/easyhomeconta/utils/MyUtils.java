/**
 * 
 */
package com.easyhomeconta.utils;

import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

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
		
	/**
	 * Obtiene el string correspondiente a la clave que se le pasa como parametro en el properties
	 * ubicado en el paquete de internacionalizacion (i18n)
	 * @param resourceBundle
	 * @return
	 */
	public static String getStringFromBundle(String bundle) {
		FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle msg = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());
        return msg.getString(bundle);
	}

	/**
	 * Añade mensaje de error general sin detalle
	 * @param errorMessage. Mensaje principal
	 */
	public static void addErrorMessage(String errorMessage) {
		addErrorMessage(null, errorMessage, null);
	}
	
	/**
	 * Añade mensaje de error global con detalle
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public static void addErrorMessage( String sumaryErrorMessage,	String detailErrorMessage) {
		addErrorMessage(null, sumaryErrorMessage, detailErrorMessage);
	}
	
	/**
	 * Añade mensaje de error y detalle para el componente indicado.
	 * @param componentId. Id del componente
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public static void addErrorMessage(String componentId, String sumaryErrorMessage,	String detailErrorMessage) {
		addMessage(componentId, sumaryErrorMessage, detailErrorMessage, FacesMessage.SEVERITY_ERROR);
	}

	/**
	 * Añade mensaje informativo sin detalle
	 * @param infoMessage
	 */
	public static void addInfoMessage(String infoMessage) {
		addInfoMessage(null, infoMessage, null);
	}
	
	/**
	 * Añade mensaje global con detalle
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public static void addInfoMessage(String sumaryInfoMessage,String detailInfoMessage) {
		addInfoMessage(null, sumaryInfoMessage, detailInfoMessage);
	}
	
	/**
	 * Añade mensaje informativo y detalle para el componente indicado.
	 * @param componentId. Id del componente
	 * @param sumaryErrorMessage. Mensaje principal 
	 * @param detailErrorMessage. Mensaje detallado
	 */
	public static void addInfoMessage(String componentId, String sumaryInfoMessage,String detailInfoMessage) {
		addMessage(componentId, sumaryInfoMessage, detailInfoMessage, FacesMessage.SEVERITY_INFO);
	}
	
	/**
	 * Añade un mensaje al contexto para mostrarlo en pantalla. 
	 * @param componentId. Id del componente para el que se desea mostrar el mensaje
	 * @param sumaryErrorMessage. Mensaje principal.
	 * @param detailMessage. Mensaje detallado.
	 * @param severity. Estado del mensaje (Error o Info)
	 */
	private  static void addMessage(String componentId, String sumaryMessage, String detailMessage, FacesMessage.Severity severity) {
		FacesMessage message=null;
		
		if (detailMessage==null)
			message=new FacesMessage(sumaryMessage);
		else
			message=new FacesMessage(sumaryMessage, detailMessage);
		
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}
	
}
