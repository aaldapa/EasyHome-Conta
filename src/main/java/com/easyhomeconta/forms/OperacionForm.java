/**
 * 
 */
package com.easyhomeconta.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.easyhomeconta.utils.NumeroUtil;

/**
 * @author Alberto
 *
 */
public class OperacionForm implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idCategoria;
	private String nombreCategoria;
	private Integer idProducto; 
	private Date fecha;
	private Boolean traspaso;
	private String concepto;
	private BigDecimal importe;
	private BigDecimal saldo;
	private String notas;
	
	public OperacionForm() {
		super();
	}

	public OperacionForm(Date fecha, BigDecimal importe) {
		super();
		this.fecha = fecha;
		this.importe = importe;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getImporte() {
		return importe;
	}
	
	public String getImporteStr(){
		return NumeroUtil.formatearACastellano(this.getImporte());
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public String getSaldoStr(){
		return NumeroUtil.formatearACastellano(this.getSaldo());
	}
	
	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	/**
	 * @return the nombreCategoria
	 */
	public String getNombreCategoria() {
		return nombreCategoria;
	}

	/**
	 * @param nombreCategoria the nombreCategoria to set
	 */
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}

	/**
	 * @return the traspaso
	 */
	public Boolean getTraspaso() {
		return traspaso;
	}

	/**
	 * @param traspaso the traspaso to set
	 */
	public void setTraspaso(Boolean traspaso) {
		this.traspaso = traspaso;
	}

}
