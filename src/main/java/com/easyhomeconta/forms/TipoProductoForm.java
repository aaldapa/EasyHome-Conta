/**
 * 
 */
package com.easyhomeconta.forms;

import java.io.Serializable;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
public class TipoProductoForm implements Serializable {

	private Integer idTipoProducto;
	private String nombre;
	private Boolean operable;
	private String notas;
	
	public TipoProductoForm() {
		super();
	}

	public TipoProductoForm(Integer idProducto, String nombre, String notas, Boolean operable) {
		super();
		this.idTipoProducto = idProducto;
		this.nombre = nombre;
		this.notas = notas;
		this.operable = operable;
	}

	public Integer getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(Integer idProducto) {
		this.idTipoProducto = idProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Boolean getOperable() {
		return operable;
	}

	public void setOperable(Boolean operable) {
		this.operable = operable;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

}
