package com.easyhomeconta.utils;

import java.util.Date;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class FechaUtil {

  	 private static String PATRON_DEFECTO = "dd/MM/yyyy";
     	
	 /**
	  * Devuelve la hora actual
	  * @return
	  */
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
     
     /**
      * Resta los dias indicados a la fecha indicada
      * @param fecha
      * @param dias
      * @return
      */
     public static Date restarDiasAFecha(Date fecha, int dias){
    	 DateTime dt= new DateTime(fecha).minusDays(dias);
    	 return dt.toDate();
     }
}
