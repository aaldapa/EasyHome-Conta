/**
 * 
 */
package com.easyhomeconta.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

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
	private List<User> lstUsuarios= new ArrayList<User>();
	
	@Column(name="role", unique=true, nullable=false)
	private String role;
	
	@Column(name="descripcion", length=255)
	private String descripcion;
	
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
	
}
