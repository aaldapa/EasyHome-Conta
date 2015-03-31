/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.model.User;
import com.easyhomeconta.service.LogonService;

/**
 * Controlador para el Login
 * @author Alberto
 *
 */
@Named(value="loginBean")
@SessionScoped
public class LoginController extends BasicManageBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final Logger log = Logger.getLogger(LoginController.class);

	private String username;
	private String password;
	
	private Authentication authentication;
	
	@Inject 
	private AuthenticationManager authenticationManager;
	
	@Inject
	private LogonService accessService;
	
	public LoginController() {
		super();
	}
	
	public String doLogin() throws ServletException, IOException{

		try{
			log.info("Login started for User with Name: "+username);
		    
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.username, this.password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		 
		} catch (AuthenticationException e) {
	    	log.info("Login failed: " + e.getMessage());
	    	
	    	//Recogemos el string del properties
//	    	FacesContext context = FacesContext.getCurrentInstance();
//	        ResourceBundle msg = ResourceBundle.getBundle("i18n.messages", context.getViewRoot().getLocale());
	        
	    	//Añadimos mensaje a la pagina
//	        FacesMessage facesMsg = new FacesMessage(
//	        FacesMessage.SEVERITY_ERROR, msg.getString("login.error.autenticacion"), msg.getString("login.error.credenciales"));
//	        FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	        
	    	//Simplificado utilizando metodos en la superclase
	    	addErrorMessage(getString("login.error.autenticacion"),getString("login.error.credenciales"));
	    	
	        return null;
		}

		//Guardamos la entrada en la tabla de accesos
		User userLogado=(User) authentication.getPrincipal();
		accessService.createLogin(userLogado);

		accessService.findLastLoginByidUser(userLogado.getIdUser());

		return "entrar";
	}

	/**
	 * Gestion del logout
	 * @return
	 */
	 
	public String doLogout(){
		
		//Limpiamos en contexto de seguridad para resetear el login del usuario
		SecurityContextHolder.clearContext();
		
		// Guardo en session el atributo logoutManual para preguntar por el en el MySessionListener, que se encarga de gestionar los cierres de sessiones
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		HttpSession httpSession = request.getSession(false);
		httpSession.setAttribute("logoutManual", true);
		httpSession.invalidate();
		
		return "logout";
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	public AuthenticationManager getAuthenticationManager() {
		return authenticationManager;
	}

	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

}
