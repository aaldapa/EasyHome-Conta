/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Alberto
 *
 */
@SuppressWarnings(value = "serial")
@Entity
@Table(name="CATEGORIAS")
public class Categoria implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_categoria", unique = true, nullable = false)
	private Integer idCategoria;
	
	@ManyToOne
	@JoinColumn(name="idUser", nullable = false)
	private User user;
	
	@Column(length=40, nullable=false)
	private String nombre;
	
	private String descripcion;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(name="CATEGORIA_OPERACIONES",
			joinColumns={@JoinColumn(name="id_categoria")},
			inverseJoinColumns={@JoinColumn(name="id_operacion")})
	private List<Operacion> lstOperaciones;
	
	public Categoria() {
		super();
	}

	public Categoria(Integer idCategoria, User user, String nombre,
			String descripcion, List<Operacion> lstOperaciones) {
		super();
		this.idCategoria = idCategoria;
		this.user = user;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lstOperaciones = lstOperaciones;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Operacion> getLstOperaciones() {
		return lstOperaciones;
	}

	public void setLstOperaciones(List<Operacion> lstOperaciones) {
		this.lstOperaciones = lstOperaciones;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((idCategoria == null) ? 0 : idCategoria.hashCode());
		result = prime * result
				+ ((lstOperaciones == null) ? 0 : lstOperaciones.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Categoria other = (Categoria) obj;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (idCategoria == null) {
			if (other.idCategoria != null)
				return false;
		} else if (!idCategoria.equals(other.idCategoria))
			return false;
		if (lstOperaciones == null) {
			if (other.lstOperaciones != null)
				return false;
		} else if (!lstOperaciones.equals(other.lstOperaciones))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", user=" + user
				+ ", nombre=" + nombre + ", descripcion=" + descripcion
				+ ", lstOperaciones=" + lstOperaciones + "]";
	}

}
