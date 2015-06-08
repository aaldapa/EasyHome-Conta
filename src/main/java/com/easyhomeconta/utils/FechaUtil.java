package com.easyhomeconta.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class FechaUtil {

  	 private static String PATRON_DEFECTO = "dd/MM/yyyy";
     	
	 public FechaUtil() {   
		 super();
	 }

     /* Entre nuestro Hosting en Espa�a y el Hosting en EEUU hay una diferencia de 
     * 9 Horas por tanto la hora se ha de obtener desde este Lugar */     
     public static Date getDate(){
    	DateTime horaActual = new DateTime();
    	return horaActual.toDate();
     }
     
     /**
      * Recibe una fecha en String y la formatea a Date 
      * @param fechaStr
      * @return
      */
     public static Date formatearADate(String fechaStr){
    	 DateTimeFormatter df =DateTimeFormat.forPattern(PATRON_DEFECTO);
         long millis = df.parseMillis(fechaStr);
         Date date  = new Date(millis);
         
         return date;
     }
     
}
