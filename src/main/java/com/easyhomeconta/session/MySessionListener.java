/**
 * 
 */
package com.easyhomeconta.session;

import java.util.Date;

import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.easyhomeconta.model.User;
import com.easyhomeconta.service.LogonService;
import com.easyhomeconta.service.UserService;
import com.easyhomeconta.utils.Enumeraciones.LogonType;

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
	
	private LogonService logonService;
	private UserService userService;
	
   @Override
   public void sessionCreated(HttpSessionEvent arg0) {
	   
	   log.info("Una nueva sesión ha sido creada");
         
   }

   @Override
   public void sessionDestroyed(HttpSessionEvent arg0) {
	   User user=prepareLogoutActiveUser(arg0.getSession());
	   if (user!=null)
		   log.info("La sesion ha sido destruida");
	   else
		   log.info("Se ha destruido una sesion sin usuario logado");
   }

   /**
    * Obtengo el usuario logado del SecurityContext de Spring Security y realizo las operaciones necesarias. 
    */
   private User prepareLogoutActiveUser (HttpSession httpSession) {

	   SecurityContextImpl securityCtx= (SecurityContextImpl) httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
	   	
	   	//Si el usuario no se ha logado en la aplicacion solo invalido la session.
	    if (securityCtx != null) {
	     
		     //Cargo el service de user y guardo la fecha del ultimo login en la tabla users
		     userService=getUserService(httpSession.getServletContext());

	    	// Obtengo la autenticacion del contexto seguro para capturar el id del usuario logado.
		     Authentication authentication = securityCtx.getAuthentication();
		     Integer idUsuarioLogado=((User) authentication.getPrincipal()).getIdUser();
		     User user=userService.getUserById(idUsuarioLogado);
		    
		     // Cargo el service y guardo el cierre de session en base de datos.
		     logonService=getLogonService(httpSession.getServletContext());
		     logonService.createLogout(user,  LogonType.TIMEDOUT);
		     
		     Date fechaUltimoLogin=logonService.findLastLoginByidUser(user.getIdUser()).getFecha();
		     user.setFechaUltimoLogin(fechaUltimoLogin);
		     userService.updateUser(user);
		     
		     return user;
	    } 
	    
	    return null;
   }
   
   //Cargo el bean de forma manual para poder usar la implementacion
   private LogonService getLogonService(ServletContext sc) {
	   if (logonService == null) 
		   logonService = (LogonService) WebApplicationContextUtils.getRequiredWebApplicationContext(sc)
		        .getBean("logonServiceImpl");
	   
	   return logonService;
   }
   
   //Cargo el bean de forma manual para poder usar la implementacion
   private UserService getUserService(ServletContext sc) {
	   if (userService == null) 
		   userService = (UserService) WebApplicationContextUtils.getRequiredWebApplicationContext(sc)
		        .getBean("userServiceImpl");
	   
	   return userService;
   }
   
}
