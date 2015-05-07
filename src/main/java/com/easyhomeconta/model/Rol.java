/**
 * 
 */
package com.easyhomeconta.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author cuesta
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="ROLES")
public class Rol implements GrantedAuthority {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_rol", unique = true, nullable = false)
	private Integer idRol;
	   
	@ManyToMany(mappedBy="lstRoles")
	private List<User> lstUsuarios;
	
	@Column(name="role", unique=true, nullable=false)
	private String role;
	
	@Column(name="descripcion", length=255)
	private String descripcion;
	
	//Utilizo el atributo como un elemento de maniobra para la representacion del objeto en una jsf
	@Transient
	private Boolean selected;
	
	public Rol() {
		super();
	}

	public Rol(Integer idRol, String role,
			String descripcion) {
		super();
		this.idRol = idRol;
		this.role = role;
		this.descripcion = descripcion;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

	public List<User> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<User> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String getAuthority() {
		return this.getRole();
	}

	public String getRoleAbr() {
		String rolAbr=role.substring(5);
		return rolAbr;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
		result = prime * result
				+ ((lstUsuarios == null) ? 0 : lstUsuarios.hashCode());
		result = prime * result + ((role == null) ? 0 : role.hashCode());
		result = prime * result
				+ ((selected == null) ? 0 : selected.hashCode());
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
		Rol other = (Rol) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idRol == null) {
			if (other.idRol != null)
				return false;
		} else if (!idRol.equals(other.idRol))
			return false;
		if (lstUsuarios == null) {
			if (other.lstUsuarios != null)
				return false;
		} else if (!lstUsuarios.equals(other.lstUsuarios))
			return false;
		if (role == null) {
			if (other.role != null)
				return false;
		} else if (!role.equals(other.role))
			return false;
		if (selected == null) {
			if (other.selected != null)
				return false;
		} else if (!selected.equals(other.selected))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Rol [idRol=" + idRol + ", lstUsuarios=" + lstUsuarios
				+ ", role=" + role + ", descripcion=" + descripcion
				+ ", selected=" + selected + "]";
	}
	
	
	
}
