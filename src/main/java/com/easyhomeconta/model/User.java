/**
 * 
 */
package com.easyhomeconta.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="USERS")
public class User implements UserDetails{
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_user", unique = true, nullable = false)
	private Integer idUser;
	
	//Mapeo para NaN con Roles
	@ManyToMany
	  @JoinTable(
	      name="USER_ROLES",
	      joinColumns={@JoinColumn(name="id_user", referencedColumnName="id_user")},
	      inverseJoinColumns={@JoinColumn(name="id_rol", referencedColumnName="id_rol")})
	private List<Rol> lstRoles= new ArrayList<Rol>();
	
	private String nombre;
	private String apellido1;
	private String apellido2;
	private String username;
	private String password;
	
	@Transient
	private String userNameForSession;
	
	/*
	 * Campos necesarios para la implementacion del la seguridad desde el login
	 */
	@Column(name="cuenta_No_Expirada", nullable=false)
	private boolean accountNonExpired;
	@Column(name="cuenta_No_bloqueada", nullable=false)
	private boolean accountNonLocked;
	@Column(name="credencial_No_Expirada", nullable=false)
	private boolean credentialsNonExpired;
	@Column(name="activo", nullable=false)
	private boolean enabled;
	
	
	public User() {
		super();
	}

	public User(Integer idUser, List<Rol> lstRoles, String nombre,
			String apellido1, String apellido2, String username,
			String password, boolean accountNonExpired,
			boolean accountNonLocked, boolean credentialsNonExpired,
			boolean enabled) {
		super();
		this.idUser = idUser;
		this.lstRoles = lstRoles;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.username = username;
		this.password = password;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
	}



	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", lstRoles=" + lstRoles
				+ ", nombre=" + nombre + ", apellido1=" + apellido1
				+ ", apellido2=" + apellido2 + ", username=" + username
				+ ", password=" + password + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return lstRoles;
	}

	public List<Rol> getLstRoles() {
		return lstRoles;
	}

	public void setLstRoles(List<Rol> lstRoles) {
		this.lstRoles = lstRoles;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Metodo utilizado para obtener el nombre del usuario logado en la aplicacion.  
	 * @return
	 */

	public String getUserNameForSession(){
		StringBuffer nombreSesion=new StringBuffer(this.nombre);
		nombreSesion.append(" ");
		nombreSesion.append(this.apellido1);
		return nombreSesion.toString();
	}
	
	
}
