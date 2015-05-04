/**
 * 
 */
package com.easyhomeconta.model;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
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
	
	//Mapeo para NaN con Roles (Usamos EAGER para que los roles se carguen por defecto al hacer la query de usuarios y no tengamos que hacer una join en la select)
	@ManyToMany(fetch=FetchType.EAGER)
	  @JoinTable(
	      name="USER_ROLES",
	      joinColumns={@JoinColumn(name="id_user")},
	      inverseJoinColumns={@JoinColumn(name="id_rol")})
	private List<Rol> lstRoles;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaCreacion;
	
	@OneToMany(mappedBy="user",cascade= CascadeType.ALL, fetch = FetchType.LAZY)
	private List<LogonInfo> lstAccess=new ArrayList<LogonInfo>();
	
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
		
	public User() {
		super();
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

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	public DefaultStreamedContent getPhotoDSContent(){
		if (this.getPhoto()!=null){
			InputStream is = new ByteArrayInputStream(this.getPhoto());
			return new DefaultStreamedContent(is);
		}
			return null;
	}
	
	public Familia getFamilia() {
		return familia;
	}

	public void setFamilia(Familia familia) {
		this.familia = familia;
	}

}
