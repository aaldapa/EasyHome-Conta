/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BANCOS")
public class Banco implements Serializable{

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_banco", unique = true, nullable = false)
	private Integer idBanco;
	
	@Column(nullable = false, length=40)
	private String nombre;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('S','N') default 'N'", nullable=false, length=1) 
	private SiNo baja;
	
	@Lob
	private byte[] logo;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "banco")
	private List<Producto> lstProductos=new ArrayList<Producto>();
	
	public Banco()  {
		super();
	}

	public Banco(Integer idBanco, String nombre, SiNo baja, byte[] logo) {
		super();
		this.idBanco = idBanco;
		this.nombre = nombre;
		this.baja = baja;
		this.logo = logo;
	}

	public Integer getIdBanco() {
		return idBanco;
	}

	public void setIdBanco(Integer idBanco) {
		this.idBanco = idBanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public SiNo getBaja() {
		return baja;
	}

	public void setBaja(SiNo baja) {
		this.baja = baja;
	}

	public byte[] getLogo() {
		return logo;
	}

	public void setLogo(byte[] logo) {
		this.logo = logo;
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
		result = prime * result + ((idBanco == null) ? 0 : idBanco.hashCode());
		result = prime * result + Arrays.hashCode(logo);
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
		Banco other = (Banco) obj;
		if (baja != other.baja)
			return false;
		if (idBanco == null) {
			if (other.idBanco != null)
				return false;
		} else if (!idBanco.equals(other.idBanco))
			return false;
		if (!Arrays.equals(logo, other.logo))
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
		return "Banco [idBanco=" + idBanco + ", nombre=" + nombre + ", baja="
				+ baja + ", logo=" + Arrays.toString(logo) + "]";
	}

}
