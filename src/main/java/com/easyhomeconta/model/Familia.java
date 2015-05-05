/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Agrupacion de usuarios que tienen visibilidad entre si.
 * @author Alberto
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="FAMILIAS")
public class Familia implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_familia", unique = true, nullable = false)
	private Integer idFamilia;
	
	@Column(nullable = false, length=40)
	private String nombre;

	//Lista de usuarios que componen la familia
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "familia")
	private List<User> lstUsers= new ArrayList<User>();
	
	public Familia() {
		super();
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

	public List<User> getLstUsers() {
		return lstUsers;
	}

	public void setLstUsers(List<User> lstUsers) {
		this.lstUsers = lstUsers;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idFamilia == null) ? 0 : idFamilia.hashCode());
		result = prime * result
				+ ((lstUsers == null) ? 0 : lstUsers.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Familia other = (Familia) obj;
		if (idFamilia == null) {
			if (other.idFamilia != null)
				return false;
		} else if (!idFamilia.equals(other.idFamilia))
			return false;
		if (lstUsers == null) {
			if (other.lstUsers != null)
				return false;
		} else if (!lstUsers.equals(other.lstUsers))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Familia [idFamilia=" + idFamilia + ", nombre=" + nombre
				+ ", lstUsers=" + lstUsers + "]";
	}

}
