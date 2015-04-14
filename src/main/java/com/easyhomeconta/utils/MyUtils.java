/**
 * 
 */
package com.easyhomeconta.utils;

import javax.servlet.http.Part;

/**
 * Clase de utilidades
 * @author Alberto
 *
 */
public class MyUtils {

	/**
	 * Metodo que extrae el nombre del archivo de un objeto Part.
	 * @param part
	 * @return
	 */
	public static String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1)
                        .trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
	}

	
}
