/**
 * 
 */
package com.easyhomeconta.beans;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.easyhomeconta.utils.NumeroUtil;

/**
 * @author Alberto
 *
 */
public class BancoForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idBanco;
	private String nombre;
	private byte []logoBanco;
	private UploadedFile imagen;
	
	//Campos exclusivos para ser utilizados en la renderizacion de listados de posicion global
	private BigDecimal balance;
	private List<ProductoForm> lstProductosForm;
	
	@SuppressWarnings("unused")
	private DefaultStreamedContent photoDSContent; 
	
	public BancoForm() {
		super();
	}
	
	public BancoForm(Integer idBanco, String nombre, byte[] logoBanco) {
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

	public BigDecimal getBalance() {
		return balance;
	}
	
	public String getBalanceStr(){
		return NumeroUtil.formatearACastellano(this.getBalance());
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<ProductoForm> getLstProductosForm() {
		return lstProductosForm;
	}

	public void setLstProductosForm(List<ProductoForm> lstProductosForm) {
		this.lstProductosForm = lstProductosForm;
	}

	
	
}
