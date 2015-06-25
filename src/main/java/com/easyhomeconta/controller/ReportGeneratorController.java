/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.context.annotation.Scope;

import com.easyhomeconta.model.User;

/**
 * @author Alberto
 *
 */
@Named(value="reportBean")
@Scope("request")
public class ReportGeneratorController implements Serializable {

	 public void generateReport()  throws ClassNotFoundException, SQLException, IOException,  
			   JRException {  
		 
			 JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(new ArrayList<User>());  
			 String  reportPath=  FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/prueba.jasper");
			 JasperPrint jasperPrint=JasperFillManager.fillReport(reportPath, new HashMap(),beanCollectionDataSource);  
			 HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
			 httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
			 ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream();  
			 JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
			 FacesContext.getCurrentInstance().responseComplete();  
		}
	
}
