/**
 * 
 */
package com.easyhomeconta.model;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
	
	//Mapeo para NaN con Roles (Usamos EAGER para que los roles se carguen por defecto al hacer la query de usuarios y no tengamos que hacer una join en la select)
	@ManyToMany(fetch=FetchType.EAGER)
	  @JoinTable(
	      name="USER_ROLES",
	      joinColumns={@JoinColumn(name="id_user")},
	      inverseJoinColumns={@JoinColumn(name="id_rol")})
	private List<Rol> lstRoles;
	
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
	private Boolean accountNonExpired=true;
	@Column(name="cuenta_No_bloqueada", nullable=false)
	private Boolean accountNonLocked=true;
	@Column(name="credencial_No_Expirada", nullable=false)
	private Boolean credentialsNonExpired;
	@Column(name="activo", nullable=false)
	private Boolean enabled=true;
	
	
	public User() {
		super();
	}

	public User(Integer idUser, List<Rol> lstRoles, String nombre,
			String apellido1, String apellido2, String username,
			String password, Boolean accountNonExpired,
			Boolean accountNonLocked, Boolean credentialsNonExpired,
			Boolean enabled) {
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

	public void setAccountNonExpired(Boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(Boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (accountNonExpired ? 1231 : 1237);
		result = prime * result + (accountNonLocked ? 1231 : 1237);
		result = prime * result
				+ ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result
				+ ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime * result + (credentialsNonExpired ? 1231 : 1237);
		result = prime * result + (enabled ? 1231 : 1237);
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result
				+ ((lstRoles == null) ? 0 : lstRoles.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime
				* result
				+ ((userNameForSession == null) ? 0 : userNameForSession
						.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if (accountNonExpired != other.accountNonExpired)
			return false;
		if (accountNonLocked != other.accountNonLocked)
			return false;
		if (apellido1 == null) {
			if (other.apellido1 != null)
				return false;
		} else if (!apellido1.equals(other.apellido1))
			return false;
		if (apellido2 == null) {
			if (other.apellido2 != null)
				return false;
		} else if (!apellido2.equals(other.apellido2))
			return false;
		if (credentialsNonExpired != other.credentialsNonExpired)
			return false;
		if (enabled != other.enabled)
			return false;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (lstRoles == null) {
			if (other.lstRoles != null)
				return false;
		} else if (!lstRoles.equals(other.lstRoles))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userNameForSession == null) {
			if (other.userNameForSession != null)
				return false;
		} else if (!userNameForSession.equals(other.userNameForSession))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}	
}
