/**
 * 
 */
package com.easyhomeconta.beans;

import java.io.Serializable;

/**
 * @author Alberto
 *
 */
public class FamiliaForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idFamilia;
	private String nombre;
	
	public FamiliaForm() {
		super();
	}
	
	public FamiliaForm(Integer idFamilia, String nombre) {
		super();
		this.idFamilia = idFamilia;
		this.nombre = nombre;
	}

	public Integer getIdFamilia() {
		return idFamilia;
	}

	public void setIdFamilia(Integer idFamilia) {
		this.idFamilia = idFamilia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
