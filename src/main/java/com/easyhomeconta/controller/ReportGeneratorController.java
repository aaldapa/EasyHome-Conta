/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.springframework.context.annotation.Scope;

/**
 * @author Alberto
 *
 */
@Named(value="reportBean")
@Scope("request")
public class ReportGeneratorController implements Serializable {

	 public void generateReport()  throws ClassNotFoundException, SQLException, IOException,  
			   JRException { 
			 

			//Parametros
//			Map parameterMap = new HashMap();
//
//	        FacesContext ctx = FacesContext.getCurrentInstance();
//	        HttpServletResponse response = (HttpServletResponse) ctx.getExternalContext().getResponse();
//	        InputStream reportStream = ctx.getExternalContext().getResourceAsStream("/reports/prueba.jasper");
//
//	        ServletOutputStream servletOutputStream = response.getOutputStream();
//	        servletOutputStream.flush();
//
//	        //response.setContentType("application/pdf");
//	        response.addHeader("Content-disposition", "attachment; filename=report.pdf");
//	        JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parameterMap,new JREmptyDataSource() );
//
//	        servletOutputStream.flush();
//	        servletOutputStream.close();
//	        ctx.responseComplete();
		 
		 	FacesContext ctx = FacesContext.getCurrentInstance();
		 
		 	String  reportPath= "/reports/prueba.jrxml";
	        InputStream jasperTemplate = ctx.getExternalContext().getResourceAsStream(reportPath);
	 
	        JasperReport jasperReport = JasperCompileManager
	            .compileReport(jasperTemplate);
	 
	        Map parameters = new HashMap();
	 
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
	            parameters, new JREmptyDataSource());
	 
	        HttpServletResponse response = (HttpServletResponse) FacesContext
	            .getCurrentInstance().getExternalContext().getResponse();
	 
	        response.setContentType("application/pdf");
	        response.setHeader("Content-Disposition",
	            "attachment; filename=\"report.pdf\"");
	         
	        ServletOutputStream outputStream = response.getOutputStream();
	        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
	 
	        outputStream.flush();
	        outputStream.close();
	        ctx.renderResponse();
	        ctx.responseComplete();
	    
		}
	
}
