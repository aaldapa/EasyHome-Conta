/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="OPERACIONES")
public class Operacion implements Serializable {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_operacion", unique = true, nullable = false)
	private Long idOperacion;
	
	@ManyToOne
	@JoinColumn(name = "id_producto", nullable = true)
	private Producto producto;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('INGRESO','GASTO') default 'GASTO'", nullable=false) 
	private TipoOperacion tipoOperacion;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fecha;
	
	@Column(length=40, nullable=false)
	private String concepto;
	
	private String notas;
	
	@Column(name = "importe", precision = 10, scale=2, nullable=false)
	private BigDecimal importe;
	
	@ManyToMany(mappedBy="lstOperaciones")
	private List<Categoria> lstCategorias;
	
	public Operacion() {
		super();
	}
	
	public Operacion(Long idOperacion, Producto producto,
			TipoOperacion tipoOperacion, Date fecha, String concepto,
			String notas, BigDecimal importe, List<Categoria> lstCategorias) {
		super();
		this.idOperacion = idOperacion;
		this.producto = producto;
		this.tipoOperacion = tipoOperacion;
		this.fecha = fecha;
		this.concepto = concepto;
		this.notas = notas;
		this.importe = importe;
		this.lstCategorias = lstCategorias;
	}

	public Long getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public TipoOperacion getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacion tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
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
		result = prime * result
				+ ((concepto == null) ? 0 : concepto.hashCode());
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((idOperacion == null) ? 0 : idOperacion.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result
				+ ((lstCategorias == null) ? 0 : lstCategorias.hashCode());
		result = prime * result + ((notas == null) ? 0 : notas.hashCode());
		result = prime * result
				+ ((producto == null) ? 0 : producto.hashCode());
		result = prime * result
				+ ((tipoOperacion == null) ? 0 : tipoOperacion.hashCode());
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
		Operacion other = (Operacion) obj;
		if (concepto == null) {
			if (other.concepto != null)
				return false;
		} else if (!concepto.equals(other.concepto))
			return false;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idOperacion == null) {
			if (other.idOperacion != null)
				return false;
		} else if (!idOperacion.equals(other.idOperacion))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (lstCategorias == null) {
			if (other.lstCategorias != null)
				return false;
		} else if (!lstCategorias.equals(other.lstCategorias))
			return false;
		if (notas == null) {
			if (other.notas != null)
				return false;
		} else if (!notas.equals(other.notas))
			return false;
		if (producto == null) {
			if (other.producto != null)
				return false;
		} else if (!producto.equals(other.producto))
			return false;
		if (tipoOperacion != other.tipoOperacion)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Operacion [idOperacion=" + idOperacion + ", producto="
				+ producto + ", tipoOperacion=" + tipoOperacion + ", fecha="
				+ fecha + ", concepto=" + concepto + ", notas=" + notas
				+ ", importe=" + importe + ", lstCategorias=" + lstCategorias
				+ "]";
	}
	
}
