/**
 * 
 */
package com.easyhomeconta.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;

/**
 * @author Alberto
 *
 */
public class OperacionForm implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer idCategoria;
	private Integer idProducto; 
	private TipoOperacion tipoOperacion;
	private Date fecha;
	private String concepto;
	private BigDecimal importe;
	private BigDecimal saldo;
	private String notas;
	
	public OperacionForm() {
		super();
	}

	public OperacionForm(Long id, Integer idCategoria, Integer idProducto,
			TipoOperacion tipoOperacion, Date fecha, String concepto,
			BigDecimal importe, BigDecimal saldo, String notas) {
		super();
		this.id = id;
		this.idCategoria = idCategoria;
		this.idProducto = idProducto;
		this.tipoOperacion = tipoOperacion;
		this.fecha = fecha;
		this.concepto = concepto;
		this.importe = importe;
		this.saldo = saldo;
		this.notas = notas;
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

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
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

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getSaldo() {
		return saldo;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
