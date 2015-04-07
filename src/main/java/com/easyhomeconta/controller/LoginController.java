/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.model.Enumeraciones.LogonType;
import com.easyhomeconta.model.LogonInfo;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.LogonService;
import com.easyhomeconta.service.UserService;

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
	private LogonService logonService;
	
	@Inject
	private UserService userService;
	
	public LoginController() {
		super();
	}
	
	public String doLogin() throws ServletException, IOException{

		try{
			log.info("El usuario "+username+ " se ha logado en la aplicación");
		    
			authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.username, this.password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		 
		} catch (AuthenticationException e) {
	    	log.info("Login error: " + e.getMessage());
	    	
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
		//LogonInfo logonInfo=logonService.findLastLoginByidUser(userLogado.getIdUser());
		//userLogado.setFechaUltimoLogin(logonInfo.getFecha());
		logonService.createLogin(userLogado);
		
		return "entrar";
	}

	/**
	 * Gestion del logout
	 * @return
	 */
	 
	public String doLogout(){
		
		//Limpiamos en contexto de seguridad para resetear el login del usuario
		SecurityContextHolder.clearContext();
		
		// Guardo en session el atributo logoutManual para preguntar por él en el MySessionListener, que se encarga de gestionar los cierres de sessiones
//		FacesContext context = FacesContext.getCurrentInstance();
//		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
//		HttpSession httpSession = request.getSession(false);
//		httpSession.setAttribute("logoutManual", true);
		
		try{
				log.info("El usuario "+username + " abandona la aplicación");
			    
				authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(this.username, this.password));
				SecurityContextHolder.getContext().setAuthentication(authentication);
							 
			} catch (AuthenticationException e) {
		    	log.info("Logout error: " + e.getMessage());
		        return null;
			}

			//Guardamos la salida en la tabla de accesos
			User userLogado=(User) authentication.getPrincipal();
			logonService.createLogout(userLogado, LogonType.LOGOUT);
			
			//Guardamos el logout en la tabla de users
			LogonInfo logonInfo=logonService.findLastLoginByidUser(userLogado.getIdUser());
			userLogado.setFechaUltimoLogin(logonInfo.getFecha());
			userService.updateUser(userLogado);
		
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
