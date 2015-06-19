package com.easyhomeconta.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * Entity implementation class for Entity: OperacionView
 *
 */

@Entity
@Immutable
@Table(name="vista_operaciones")
public class OperacionView implements Serializable {

	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id_vista")
	private Integer idVista;
	private BigDecimal ingresos;
	private BigDecimal gastos;
	private BigDecimal balance;
	private Integer mesNumero;
	private String mes;
	private Integer anio;
	private Integer idProducto;
	
	private static final long serialVersionUID = 1L;

	public OperacionView() {
		super();
	}   
	public Integer getIdVista() {
		return this.idVista;
	}

	public void setIdVista(Integer idVista) {
		this.idVista = idVista;
	}   
	public BigDecimal getIngresos() {
		return this.ingresos;
	}

	public void setIngresos(BigDecimal ingresos) {
		this.ingresos = ingresos;
	}   
	public BigDecimal getGastos() {
		return this.gastos;
	}

	public void setGastos(BigDecimal gastos) {
		this.gastos = gastos;
	}   
	public BigDecimal getBalance() {
		return this.balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}   
	public Integer getMesNumero() {
		return this.mesNumero;
	}

	public void setMesNumero(Integer mesNumero) {
		this.mesNumero = mesNumero;
	}   
	public String getMes() {
		return this.mes;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}   
	public Integer getAnio() {
		return this.anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}   
	public Integer getIdProducto() {
		return this.idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
   
}
