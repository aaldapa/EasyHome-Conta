/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * @author cuesta
 *
 */
@SuppressWarnings("serial")
public class BasicManageBean implements IBasicManageBean, Serializable {
	
	public void addErrorMessage(String errorMessage) {
		addErrorMessage(null, errorMessage, null);
	}
	
	public void addErrorMessage( String sumaryErrorMessage,	String detailErrorMessage) {
		addErrorMessage(null, sumaryErrorMessage, detailErrorMessage);
	}
	
	public void addErrorMessage(String componentId, String sumaryErrorMessage,	String detailErrorMessage) {
		addMessage(componentId, sumaryErrorMessage, detailErrorMessage, FacesMessage.SEVERITY_ERROR);
	}

	public void addInfoMessage(String infoMessage) {
		addInfoMessage(null, infoMessage, null);
	}
	
	public void addInfoMessage(String sumaryInfoMessage,String detailInfoMessage) {
		addInfoMessage(null, sumaryInfoMessage, detailInfoMessage);
	}
	
	public void addInfoMessage(String componentId, String sumaryInfoMessage,String detailInfoMessage) {
		addMessage(componentId, sumaryInfoMessage, detailInfoMessage, FacesMessage.SEVERITY_INFO);
	}

	public String getString(String bundle) {
		FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle msg = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());
        return msg.getString(bundle);
	}
	
	/**
	 * 
	 * @param componentId. Id del componente para el que se desea mostrar el mensaje
	 * @param sumaryErrorMessage. Mensaje principal.
	 * @param detailMessage. Mensaje detallado.
	 * @param severity. Estado del mensaje (Error o Info)
	 */
	private void addMessage(String componentId, String sumaryMessage,	String detailMessage, FacesMessage.Severity severity) {
		FacesMessage message=null;
		
		if (detailMessage==null)
			message=new FacesMessage(sumaryMessage);
		else
			message=new FacesMessage(sumaryMessage, detailMessage);
		
		message.setSeverity(severity);
		FacesContext.getCurrentInstance().addMessage(componentId, message);
	}
}
