/**
 * 
 */
package com.easyhomeconta.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.primefaces.model.DefaultStreamedContent;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Usuarios con acceso a la aplicacion
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_familia", nullable = true)
	private Familia familia;

	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaCreacion;
	
	@Column(nullable=false, length=40)
	private String nombre;
	
	@Column(nullable=false, length=40)
	private String apellido1;
	
	@Column(length=40)
	private String apellido2;
	
	@Column(nullable=false, unique=true, length=20)
	private String username;
	
	@Column(nullable=false, length=20)
	private String password;
	
	private Date fechaUltimoLogin;
	
	@Lob
	private byte[] photo;
	
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
	
	//Mapeo para NaN con Roles (Usamos EAGER para que los roles se carguen por defecto al hacer la query de usuarios y no tengamos que hacer una join en la select)
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
	      name="USER_ROLES",
	      joinColumns={@JoinColumn(name="id_user")},
	      inverseJoinColumns={@JoinColumn(name="id_rol")})
	private List<Rol> lstRoles;
	
	//Mapeo para NaN con Roles (Usamos EAGER para que los roles se carguen por defecto al hacer la query de usuarios y no tengamos que hacer una join en la select)
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
	      name="USER_PRODUCTOS",
	      joinColumns={@JoinColumn(name="id_user")},
	      inverseJoinColumns={@JoinColumn(name="id_producto")})
	private List<Producto> lstProductos;
	
	//Mapeo con los accesos
	@OneToMany(mappedBy="user",cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LogonInfo> lstAccess=new ArrayList<LogonInfo>();
	
	//Mapeo con las categorias
	@OneToMany(mappedBy="user",cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Categoria> lstCategorias=new ArrayList<Categoria>();
	
	public User() {
		super();
	}

	public User(Integer idUser, Familia familia, Date fechaCreacion,
			String nombre, String apellido1, String apellido2, String username,
			String password, Date fechaUltimoLogin, byte[] photo,
			String userNameForSession, Boolean accountNonExpired,
			Boolean accountNonLocked, Boolean credentialsNonExpired,
			Boolean enabled, List<Rol> lstRoles, List<Producto> lstProductos,
			List<LogonInfo> lstAccess, List<Categoria> lstCategorias) {
		super();
		this.idUser = idUser;
		this.familia = familia;
		this.fechaCreacion = fechaCreacion;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.username = username;
		this.password = password;
		this.fechaUltimoLogin = fechaUltimoLogin;
		this.photo = photo;
		this.userNameForSession = userNameForSession;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.lstRoles = lstRoles;
		this.lstProductos = lstProductos;
		this.lstAccess = lstAccess;
		this.lstCategorias = lstCategorias;
	}

	/**
	 * Metodo utilizado para obtener el nombre completo de un usuario. Util por ejempli para mostrar
	 * el nombre de usuario que inicia session.  
	 * @return
	 */

	public String getUserNameForSession(){
		StringBuffer nombreSesion=new StringBuffer(this.nombre);
		nombreSesion.append(" ");
		nombreSesion.append(this.apellido1);
		return nombreSesion.toString();
	}	
	
	/**
	 * Metodo para mostrar la imagen guardada en photo en la bd
	 * @return
	 */
	public DefaultStreamedContent getPhotoDSContent(){
		if (this.getPhoto()!=null){
			InputStream is = new ByteArrayInputStream(this.getPhoto());
			return new DefaultStreamedContent(is);
		}
			return null;
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return lstRoles;
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
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
	
	public List<LogonInfo> getLstAccess() {
		return lstAccess;
	}

	public void setLstAccess(List<LogonInfo> lstAccess) {
		this.lstAccess = lstAccess;
	}

	public Date getFechaUltimoLogin() {
		return fechaUltimoLogin;
	}

	public void setFechaUltimoLogin(Date fechaUltimoLogin) {
		this.fechaUltimoLogin = fechaUltimoLogin;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

	public List<Producto> getLstProductos() {
		return lstProductos;
	}

	public void setLstProductos(List<Producto> lstProductos) {
		this.lstProductos = lstProductos;
	}

	public List<Categoria> getLstCategorias() {
		return lstCategorias;
	}

	public void setLstCategorias(List<Categoria> lstCategorias) {
		this.lstCategorias = lstCategorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((accountNonExpired == null) ? 0 : accountNonExpired
						.hashCode());
		result = prime
				* result
				+ ((accountNonLocked == null) ? 0 : accountNonLocked.hashCode());
		result = prime * result
				+ ((apellido1 == null) ? 0 : apellido1.hashCode());
		result = prime * result
				+ ((apellido2 == null) ? 0 : apellido2.hashCode());
		result = prime
				* result
				+ ((credentialsNonExpired == null) ? 0 : credentialsNonExpired
						.hashCode());
		result = prime * result + ((enabled == null) ? 0 : enabled.hashCode());
		result = prime * result + ((familia == null) ? 0 : familia.hashCode());
		result = prime * result
				+ ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime
				* result
				+ ((fechaUltimoLogin == null) ? 0 : fechaUltimoLogin.hashCode());
		result = prime * result + ((idUser == null) ? 0 : idUser.hashCode());
		result = prime * result
				+ ((lstAccess == null) ? 0 : lstAccess.hashCode());
		result = prime * result
				+ ((lstCategorias == null) ? 0 : lstCategorias.hashCode());
		result = prime * result
				+ ((lstProductos == null) ? 0 : lstProductos.hashCode());
		result = prime * result
				+ ((lstRoles == null) ? 0 : lstRoles.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + Arrays.hashCode(photo);
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
		if (accountNonExpired == null) {
			if (other.accountNonExpired != null)
				return false;
		} else if (!accountNonExpired.equals(other.accountNonExpired))
			return false;
		if (accountNonLocked == null) {
			if (other.accountNonLocked != null)
				return false;
		} else if (!accountNonLocked.equals(other.accountNonLocked))
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
		if (credentialsNonExpired == null) {
			if (other.credentialsNonExpired != null)
				return false;
		} else if (!credentialsNonExpired.equals(other.credentialsNonExpired))
			return false;
		if (enabled == null) {
			if (other.enabled != null)
				return false;
		} else if (!enabled.equals(other.enabled))
			return false;
		if (familia == null) {
			if (other.familia != null)
				return false;
		} else if (!familia.equals(other.familia))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (fechaUltimoLogin == null) {
			if (other.fechaUltimoLogin != null)
				return false;
		} else if (!fechaUltimoLogin.equals(other.fechaUltimoLogin))
			return false;
		if (idUser == null) {
			if (other.idUser != null)
				return false;
		} else if (!idUser.equals(other.idUser))
			return false;
		if (lstAccess == null) {
			if (other.lstAccess != null)
				return false;
		} else if (!lstAccess.equals(other.lstAccess))
			return false;
		if (lstCategorias == null) {
			if (other.lstCategorias != null)
				return false;
		} else if (!lstCategorias.equals(other.lstCategorias))
			return false;
		if (lstProductos == null) {
			if (other.lstProductos != null)
				return false;
		} else if (!lstProductos.equals(other.lstProductos))
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
		if (!Arrays.equals(photo, other.photo))
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

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", familia=" + familia
				+ ", fechaCreacion=" + fechaCreacion + ", nombre=" + nombre
				+ ", apellido1=" + apellido1 + ", apellido2=" + apellido2
				+ ", username=" + username + ", password=" + password
				+ ", fechaUltimoLogin=" + fechaUltimoLogin + ", photo="
				+ Arrays.toString(photo) + ", userNameForSession="
				+ userNameForSession + ", accountNonExpired="
				+ accountNonExpired + ", accountNonLocked=" + accountNonLocked
				+ ", credentialsNonExpired=" + credentialsNonExpired
				+ ", enabled=" + enabled + ", lstRoles=" + lstRoles
				+ ", lstProductos=" + lstProductos + ", lstAccess=" + lstAccess
				+ ", lstCategorias=" + lstCategorias + "]";
	}
	
	

}
