/**
 * 
 */
package com.easyhomeconta.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * @author Alberto
 *
 */
public class BancoBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idBanco;
	private String nombre;
	private byte []logoBanco;
	private UploadedFile imagen;
	
	@SuppressWarnings("unused")
	private DefaultStreamedContent photoDSContent; 
	
	public BancoBean() {
		super();
	}
	
	public BancoBean(Integer idBanco, String nombre, byte[] logoBanco) {
		super();
		this.idBanco = idBanco;
		this.nombre = nombre;
		this.logoBanco = logoBanco;
		if (this.getLogoBanco()!=null){
			InputStream is = new ByteArrayInputStream(this.getLogoBanco());
			this.photoDSContent=new DefaultStreamedContent(is);
		} 
	}

	/**
	 * Metodo para mostrar la imagen con primefaces 
	 * @return
	 */
	public DefaultStreamedContent getPhotoDSContent() {
		if (this.getLogoBanco()!=null){
			InputStream is = new ByteArrayInputStream(this.getLogoBanco());
			return new DefaultStreamedContent(is);
		}
		return null;
	}
	
	
	
	public void setPhotoDSContent(DefaultStreamedContent photoDSContent) {
		this.photoDSContent = photoDSContent;
	}

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getLogoBanco() {
		return logoBanco;
	}

	public void setLogoBanco(byte[] logoBanco) {
		this.logoBanco = logoBanco;
	}

	public UploadedFile getImagen() {
		return imagen;
	}

	public void setImagen(UploadedFile imagen) {
		this.imagen = imagen;
	}

	
	
}
