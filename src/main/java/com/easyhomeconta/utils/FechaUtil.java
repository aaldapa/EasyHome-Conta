package com.easyhomeconta.utils;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;


public class FechaUtil {

  	 private static String PATRON_DEFECTO = "dd/MM/yyyy";
  	 private static final String PATRON_DEFECTOddmmyyyy = "(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)";
  	 private Matcher matcher;
  	 private Pattern patternFecha;
  	 
  	 public FechaUtil() {   
		 patternFecha = Pattern.compile(PATRON_DEFECTOddmmyyyy);
		 
		 
	 }
     	
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
     
     /**
	   * Valida la fecha con una expresion regular
	   * @param date 
	   * @return true valido dateformat, false invalid0 dateformat
	   * Son validos los siguientes formatos:
	   * 1) "1-1-2010" , "01-01-2020"
	   * 2) "31-1-2010", "31-01-2020"
	   * 3) "29-2-2008", "29-02-2008"
	   * 4) "28-2-2009", "28-02-2009"
	   * 5) "31-3-2010", "31-03-2010"
	   * 6) "30-4-2010", "30-04-2010"
	   * 7) "31-5-2010", "31-05-2010"
	   * 8) "30-6-2010", "30-06-2010"
	   * 9) "31-7-2010", "31-07-2010"
	   * 10) "31-8-2010", "31-08-2010"
	   * 11) "30-9-2010", "30-09-2010"
	   * 12) "31-10-2010", "31-10-2010"
	   * 13) "30-11-2010", "30-11-2010"
	   * 14) "31-12-2010", "31-12-2010"
	   * NO SON VALIDOS
	   * 1)"32-1-2010" ,"32-01-2020" – day is out of range [1-31]
	   * 2)"1-13-2010","01-01-1820" – month is out of range [1-12], year is out of range [1900-2999]
	   * 3)"29-2-2007","29-02-2007" – 2007 is not leap year, only has 28 days
	   * 4)"30-2-2008","31-02-2008" – leap year in February has 29 days only
	   * 5)"29-a-2008","a-02-2008" – month is invalie, day is invalid
	   * 6)"333-2-2008","29-02-200a” – day is invalid, year is invalid
	   * 7)"31-4-20100","31-04-2010" – April has 30 days only
	   * 8 )"31-6-2010","31-06-2010" -June has 30 days only
	   * 9)"31-9-2010","31-09-2010" – September has 30 days only
	   * 10)"31-11-2010" – November has 30 days only
	   */
    
	  public boolean validarFecha(final String date){
		
		  matcher = patternFecha.matcher(date);
		  if(matcher.matches()){
			  matcher.reset();
			  if(matcher.find()){
				  String day = matcher.group(1);
				  String month = matcher.group(2);
				  int year = Integer.parseInt(matcher.group(3));
				  if (day.equals("31") &&  (month.equals("4") || month .equals("6") || month.equals("9") || month.equals("11") ||
								  month.equals("04") || month .equals("06") || month.equals("09")))
				  {
					  return false; // only 1,3,5,7,8,10,12 has 31 days
				  } else if (month.equals("2") || month.equals("02")) {
					  if(year % 4==0){ //leap year
						  if(day.equals("30") || day.equals("31")){
							  return false;
						  }else{
							  return true;
						  }
					  }else{
						  if(day.equals("29")||day.equals("30")||day.equals("31")){
							  return false;
						  }else{
							  return true;
						  }
					  }
				  }else{
					return true;

				  }
			  }else{
				  return false;
			  }
		  }else{
			  return false;
		  }

	  }

}
