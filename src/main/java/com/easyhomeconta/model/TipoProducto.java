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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="TIPO_PRODUCTOS")
public class TipoProducto implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_tipo_producto", unique = true, nullable = false)
	private Integer idTipoProducto;
	
	@Column(nullable=true, length=40)
	private String nombre;

	//Campo utilizado para indicar si el producto tiene operaciones (movimientos)
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('S','N') default 'S'", nullable=false, length=1) 
	private SiNo operable;
	
	private String notas;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('S','N') default 'N'", nullable=false, length=1) 
	private SiNo baja;
	
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, mappedBy = "tipoProducto")
	private List<Producto> lstProductos=new ArrayList<Producto>();
	
	public TipoProducto() {
		super();
	}

	public TipoProducto(Integer idTipoProducto, String nombre, SiNo operable,
			String notas, SiNo baja) {
		super();
		this.idTipoProducto = idTipoProducto;
		this.nombre = nombre;
		this.operable = operable;
		this.notas = notas;
		this.baja = baja;
	}

	public Integer getIdTipoProducto() {
		return idTipoProducto;
	}

	public void setIdTipoProducto(Integer idTipoProducto) {
		this.idTipoProducto = idTipoProducto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SiNo getOperable() {
		return operable;
	}

	public void setOperable(SiNo operable) {
		this.operable = operable;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public SiNo getBaja() {
		return baja;
	}

	public void setBaja(SiNo baja) {
		this.baja = baja;
	}

	public List<Producto> getLstProductos() {
		return lstProductos;
	}

	public void setLstProductos(List<Producto> lstProductos) {
		this.lstProductos = lstProductos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baja == null) ? 0 : baja.hashCode());
		result = prime * result
				+ ((idTipoProducto == null) ? 0 : idTipoProducto.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((notas == null) ? 0 : notas.hashCode());
		result = prime * result
				+ ((operable == null) ? 0 : operable.hashCode());
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
		TipoProducto other = (TipoProducto) obj;
		if (baja != other.baja)
			return false;
		if (idTipoProducto == null) {
			if (other.idTipoProducto != null)
				return false;
		} else if (!idTipoProducto.equals(other.idTipoProducto))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (notas == null) {
			if (other.notas != null)
				return false;
		} else if (!notas.equals(other.notas))
			return false;
		if (operable != other.operable)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TIPO_PRODUCTO [idTipoProducto=" + idTipoProducto + ", nombre="
				+ nombre + ", operable=" + operable + ", notas=" + notas
				+ ", baja=" + baja + "]";
	}
	
	

}
