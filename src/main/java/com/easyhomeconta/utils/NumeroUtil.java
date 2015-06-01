package com.easyhomeconta.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;

public class NumeroUtil {

	private static String PATRON_DEFECTO = "0.00";
    
	/*
	 * Formatea un string con coma a un Float con punto
	 */
	
	public static Float formatearImporteComaAPunto(String importeStr){
		if (importeStr.contains("."))
			importeStr=importeStr.replace(".", "");
		DecimalFormat formateador = new DecimalFormat(PATRON_DEFECTO);
		Float importe=null;
		try{	
			importe = formateador.parse(importeStr).floatValue();
		}
		catch (ParseException e){System.out.println("Error al formatear importe:"+ e);}
		
		return importe;
	} 
	
	public static BigDecimal formatearImporteComaAPuntoBD(String importeStr){
		if (importeStr.contains("."))
			importeStr=importeStr.replace(".", "");
		DecimalFormat formateador = new DecimalFormat(PATRON_DEFECTO);
		BigDecimal importe=null;
		try{	
			importe = new BigDecimal(formateador.parse(importeStr).toString());
		}
		catch (ParseException e){System.out.println("Error al formatear importe:"+ e);}
		
		return importe;
	} 
	
	public static String formatearACastellano(BigDecimal importe){
	    if (importe != null){ 
			return customFormat("###,##0.00", importe.doubleValue()); 
		}
				
		return "";
    }
	
	static public String customFormat(String pattern, double value) {
	    DecimalFormat myFormatter = new DecimalFormat(pattern);
	    String output = myFormatter.format(value);
	    //System.out.println(value + "  " + pattern + "  " + output);
	    return output;
	}

	
}
