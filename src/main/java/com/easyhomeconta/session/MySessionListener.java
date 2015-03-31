/**
 * 
 */
package com.easyhomeconta.session;

import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.easyhomeconta.model.Enumeraciones.LogonType;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.LogonService;

/**
 * HttpSessionListener es un listener que se ejecuta cada vez que ocurre algo referente a la session.
 * Implementandolo se puede añadir logica de negocio cada vez que se cree o destruya una session.
 * Para que se ejecute hay que registrarlo en el web.xml
 * 
 * @author Alberto
 *
 */
@Named
public class MySessionListener implements HttpSessionListener {

	private final Logger log = Logger.getLogger(MySessionListener.class);
	
	LogonService logonService;
	
   @Override
   public void sessionCreated(HttpSessionEvent arg0) {
	   
	   log.info("Sesión creada");
         
   }

   @Override
   public void sessionDestroyed(HttpSessionEvent arg0) {
	   prepareLogoutActiveUser(arg0.getSession());
	   log.info("Sesion destruida");
   }

   /**
    * Obtengo el usuario logado del SecurityContext de Spring Security and realizo las operaciones necesarias. 
    */
   private void prepareLogoutActiveUser (HttpSession httpSession) {

	   SecurityContextImpl securityCtx= (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
	   	
	   	//Si el usuario no se ha logado en la aplicacion solo invalido la session.
	    if (securityCtx != null) {
	     
		     // Obtengo la autenticacion del contexto seguro para capturar al usuario logado 
		     Authentication authentication = securityCtx.getAuthentication();
		     User user=(User) authentication.getPrincipal();
		   
		     // Llamo al service que se encarga de guardar el cierre de session en base de datos.
		     getLogonService(httpSession.getServletContext()).createLogout(user,  LogonType.TIMEDOUT);
	    } 
	    
   }
   
   //Cargo el bean de forma manual para poder usar la implementacion
   private LogonService getLogonService(ServletContext sc) {
	   if (logonService == null) 
		   logonService = (LogonService) WebApplicationContextUtils.getRequiredWebApplicationContext(sc)
		        .getBean("logonServiceImpl");
	   
	   return logonService;
   }
   
}
