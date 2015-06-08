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
	
	private Integer filaInicio;
	private Integer columnaFecha;
	private Integer columnaImporte;
	private Integer columnaConcepto;
	
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

	public Integer getFilaInicio() {
		return filaInicio;
	}

	public void setFilaInicio(Integer filaInicio) {
		this.filaInicio = filaInicio;
	}

	public Integer getColumnaFecha() {
		return columnaFecha;
	}

	public void setColumnaFecha(Integer columnaFecha) {
		this.columnaFecha = columnaFecha;
	}

	public Integer getColumnaImporte() {
		return columnaImporte;
	}

	public void setColumnaImporte(Integer columnaImporte) {
		this.columnaImporte = columnaImporte;
	}

	public Integer getColumnaConcepto() {
		return columnaConcepto;
	}

	public void setColumnaConcepto(Integer columnaConcepto) {
		this.columnaConcepto = columnaConcepto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((baja == null) ? 0 : baja.hashCode());
		result = prime * result
				+ ((columnaConcepto == null) ? 0 : columnaConcepto.hashCode());
		result = prime * result
				+ ((columnaFecha == null) ? 0 : columnaFecha.hashCode());
		result = prime * result
				+ ((columnaImporte == null) ? 0 : columnaImporte.hashCode());
		result = prime * result
				+ ((filaInicio == null) ? 0 : filaInicio.hashCode());
		result = prime * result + ((idBanco == null) ? 0 : idBanco.hashCode());
		result = prime * result + Arrays.hashCode(logo);
		result = prime * result
				+ ((lstProductos == null) ? 0 : lstProductos.hashCode());
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
		if (columnaConcepto == null) {
			if (other.columnaConcepto != null)
				return false;
		} else if (!columnaConcepto.equals(other.columnaConcepto))
			return false;
		if (columnaFecha == null) {
			if (other.columnaFecha != null)
				return false;
		} else if (!columnaFecha.equals(other.columnaFecha))
			return false;
		if (columnaImporte == null) {
			if (other.columnaImporte != null)
				return false;
		} else if (!columnaImporte.equals(other.columnaImporte))
			return false;
		if (filaInicio == null) {
			if (other.filaInicio != null)
				return false;
		} else if (!filaInicio.equals(other.filaInicio))
			return false;
		if (idBanco == null) {
			if (other.idBanco != null)
				return false;
		} else if (!idBanco.equals(other.idBanco))
			return false;
		if (!Arrays.equals(logo, other.logo))
			return false;
		if (lstProductos == null) {
			if (other.lstProductos != null)
				return false;
		} else if (!lstProductos.equals(other.lstProductos))
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
		return "Banco [idBanco=" + idBanco + ", nombre=" + nombre
				+ ", filaInicio=" + filaInicio + ", columnaFecha="
				+ columnaFecha + ", columnaImporte=" + columnaImporte
				+ ", columnaConcepto=" + columnaConcepto + ", baja=" + baja
				+ ", logo=" + Arrays.toString(logo) + ", lstProductos="
				+ lstProductos + "]";
	}

	
}
