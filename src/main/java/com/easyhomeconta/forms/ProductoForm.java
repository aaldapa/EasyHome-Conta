/**
 * 
 */
package com.easyhomeconta.forms;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.primefaces.model.DefaultStreamedContent;

import com.easyhomeconta.utils.NumeroUtil;

/**
 * @author Alberto
 *
 */
public class ProductoForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idProducto;
	private Integer idTipoProducto;
	private String nombreTProducto;
	private Integer idBanco;
	private byte []logoBanco;
	private String nombreBanco;
	private String nombre;
	private String descripcion;
	private String titular;
	private String cotitular;
	private Float rentabilidad;
	private Float rentabilidadCancelacion;
	private String numero;
	private BigDecimal importe;
	private Date fechaApertura;
	private Date fechaVencimiento;
	private String[] idsPermisos;
	private Boolean owner;

	//Campos exclusivos para ser utilizados en la renderizacion de listados de posicion global
	private Date fechaUltOperacion;
	private BigDecimal balance;
	private List<OperacionForm> lstOperacionesForm;
	private Boolean activo;
	
	public String[] getIdsPermisos() {
		return idsPermisos;
	}

	public void setIdsPermisos(String[] idsPermisos) {
		this.idsPermisos = idsPermisos;
	}

	public ProductoForm() {
		super();
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(Integer idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCotitular() {
		return cotitular;
	}

	public void setCotitular(String cotitular) {
		this.cotitular = cotitular;
	}

	public Float getRentabilidad() {
		return rentabilidad;
	}

	public String getRentabilidadStr(){
		return NumeroUtil.formatearACastellano(new BigDecimal(getRentabilidad()));	
	}
	
	public void setRentabilidad(Float rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public Float getRentabilidadCancelacion() {
		return rentabilidadCancelacion;
	}

	public void setRentabilidadCancelacion(Float rentabilidadCancelacion) {
		this.rentabilidadCancelacion = rentabilidadCancelacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getNombreTProducto() {
		return nombreTProducto;
	}

	public void setNombreTProducto(String nombreProducto) {
		this.nombreTProducto = nombreProducto;
	}

	public String getNombreBanco() {
		return nombreBanco;
	}

	public void setNombreBanco(String nombreBanco) {
		this.nombreBanco = nombreBanco;
	}

	public byte [] getLogoBanco() {
		return logoBanco;
	}

	public void setLogoBanco(byte [] logoBanco) {
		this.logoBanco = logoBanco;
	}

	/**
	 * Metodo para mostrar la imagen con primefaces 
	 * @return
	 */
	public DefaultStreamedContent getPhotoDSContent(){
		if (this.getLogoBanco()!=null){
			InputStream is = new ByteArrayInputStream(this.getLogoBanco());
			return new DefaultStreamedContent(is);
		}
			return null;
	}

	public Boolean getOwner() {
		return owner;
	}

	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	public Date getFechaUltOperacion() {
		return fechaUltOperacion;
	}

	public void setFechaUltOperacion(Date fechaUltOperacion) {
		this.fechaUltOperacion = fechaUltOperacion;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public String getBalanceStr(){
		return NumeroUtil.formatearACastellano(this.getBalance());
	}

	public List<OperacionForm> getLstOperacionesForm() {
		return lstOperacionesForm;
	}

	public void setLstOperacionesForm(List<OperacionForm> lstOperacionesForm) {
		this.lstOperacionesForm = lstOperacionesForm;
	}

	/**
	 * @return the activo
	 */
	public Boolean getActivo() {
		return activo;
	}

	/**
	 * @param activo the activo to set
	 */
	public void setActivo(Boolean activo) {
		this.activo = activo;
	}
	
}
