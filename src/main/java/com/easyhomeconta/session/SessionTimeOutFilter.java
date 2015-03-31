package com.easyhomeconta.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class SessionTimeOutFilter implements Filter {

	private final Logger log = Logger.getLogger(SessionTimeOutFilter.class);
	
	 // Esta es la pagina de session expirada.
	private String timeoutPage = "session-expired.xhtml";
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	@Override
	public void destroy() {	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException,ServletException {
		if ((request instanceof HttpServletRequest) && (response instanceof HttpServletResponse)) {
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			HttpServletResponse httpServletResponse = (HttpServletResponse) response;

			// is session expire control required for this request?
			if (isSessionControlRequiredForThisResource(httpServletRequest)) {	
			// is session invalid?
			
				if (isSessionInvalid(httpServletRequest)) {
					String timeoutUrl = httpServletRequest.getContextPath() + "/" + getTimeoutPage();
					log.info("Session invalida! redirigiendo a : " + timeoutUrl);
				
					httpServletResponse.sendRedirect(timeoutUrl);
					return;
				}
			}
		}
		filterChain.doFilter(request, response);
	}

	/*
	* La session no deberia de comprobarse para la pagina de timeout ya que con este filtro lo que se pretende 
	* es hacer una redireccion a esta pagina. De lo contrario, (si comprobasemos tambien esta pagina) 
	* el resultado seria un bucle infinito. Ademas, tampoco vamos a realizar comprobacion para la pagina de login
	*/
	private boolean isSessionControlRequiredForThisResource(HttpServletRequest httpServletRequest) {
		String requestPath = httpServletRequest.getRequestURI();
		
		//Si la pagina solicitada es la ruta raiz("/") O es la pagina de timeout
		if (requestPath.equals(httpServletRequest.getContextPath() + "/") || requestPath.equals(httpServletRequest.getContextPath() + "/" +getTimeoutPage()))
			return false;
		
		return true;
	}
	
	private boolean isSessionInvalid(HttpServletRequest httpServletRequest) {
		boolean sessionInValid = (httpServletRequest.getRequestedSessionId() != null)
		&& !httpServletRequest.isRequestedSessionIdValid();
		return sessionInValid;
	}
	
	public String getTimeoutPage() {
		return timeoutPage;
	}
	
	public void setTimeoutPage(String timeoutPage) {
		this.timeoutPage = timeoutPage;
	}

}
