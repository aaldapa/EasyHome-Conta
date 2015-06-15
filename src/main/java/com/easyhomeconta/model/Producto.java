/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="PRODUCTOS")
public class Producto implements Serializable{

	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO) 
	@Column(name = "id_producto", unique = true, nullable = false)
	private Integer idProducto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo_producto", nullable = false)
	private TipoProducto tipoProducto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_banco", nullable = false)
	private Banco banco;
	
	@Column(nullable = false, length=40)
	private String nombre;
	
	private String descripcion;
	
	@Column(nullable = false, length=40)
	private String titular;
	
	@Column(nullable = true, length=40)
	private String cotitular;
	
	//Intereses, rendimiento en % TAE
	private Float rentabilidad;
	
	//Intereses, rendimiento en % TAE en caso de cancelacion anticipada
	private Float rentabilidadCancelacion;
	
	@Column(length=40)
	private String numero;
	
	@Column(name = "importe", precision = 10, scale=2)
	private BigDecimal importe;
	
	@Temporal(TemporalType.DATE)
	@Column(nullable=false)
	private Date fechaApertura;
	
	@Temporal(TemporalType.DATE)
	private Date fechaVencimiento;
	
	//ID del usuario prodietario o creador del producto
	@Column(nullable = false)
	private Integer idUserOwner;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('S','N') default 'N'", nullable=false, length=1) 
	private SiNo baja;

	//Mapeo NaN con usuarios
	@ManyToMany(cascade=CascadeType.ALL ,mappedBy="lstProductos")
	private List<User> lstUsuarios;

	@OneToMany( cascade = CascadeType.ALL, mappedBy = "producto")
	private List<Operacion> lstOperaciones;
	
	public Producto() {
		super();
	}

	public Producto(Integer idProducto, TipoProducto tipoProducto, Banco banco,
			String nombre, String descripcion, String titular,
			String cotitular, Float rentabilidad,
			Float rentabilidadCancelacion, String numero, BigDecimal importe,
			Date fechaApertura, Date fechaVencimiento, Integer idUserOwner, SiNo baja,
			List<User> lstUsuarios, List<Operacion> lstOperaciones) {
		super();
		this.idProducto = idProducto;
		this.tipoProducto = tipoProducto;
		this.banco = banco;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.titular = titular;
		this.cotitular = cotitular;
		this.rentabilidad = rentabilidad;
		this.rentabilidadCancelacion = rentabilidadCancelacion;
		this.numero = numero;
		this.importe = importe;
		this.fechaApertura = fechaApertura;
		this.fechaVencimiento = fechaVencimiento;
		this.idUserOwner=idUserOwner;
		this.baja = baja;
		this.lstUsuarios = lstUsuarios;
		this.lstOperaciones = lstOperaciones;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}

	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}

	public Banco getBanco() {
		return banco;
	}

	public void setBanco(Banco banco) {
		this.banco = banco;
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

	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public String getCotitular() {
		return cotitular;
	}

	public void setCotitular(String cotitular) {
		this.cotitular = cotitular;
	}

	public Float getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(Float rentabilidad) {
		this.rentabilidad = rentabilidad;
	}

	public Float getRentabilidadCancelacion() {
		return rentabilidadCancelacion;
	}

	public void setRentabilidadCancelacion(Float rentabilidadCancelacion) {
		this.rentabilidadCancelacion = rentabilidadCancelacion;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public Date getFechaApertura() {
		return fechaApertura;
	}

	public void setFechaApertura(Date fechaApertura) {
		this.fechaApertura = fechaApertura;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public Integer getIdUserOwner() {
		return idUserOwner;
	}

	public void setIdUserOwner(Integer idUserOwner) {
		this.idUserOwner = idUserOwner;
	}

	public SiNo getBaja() {
		return baja;
	}

	public void setBaja(SiNo baja) {
		this.baja = baja;
	}

	public List<User> getLstUsuarios() {
		return lstUsuarios;
	}

	public void setLstUsuarios(List<User> lstUsuarios) {
		this.lstUsuarios = lstUsuarios;
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
		result = prime * result + ((baja == null) ? 0 : baja.hashCode());
		result = prime * result + ((banco == null) ? 0 : banco.hashCode());
		result = prime * result
				+ ((cotitular == null) ? 0 : cotitular.hashCode());
		result = prime * result
				+ ((descripcion == null) ? 0 : descripcion.hashCode());
		result = prime * result
				+ ((fechaApertura == null) ? 0 : fechaApertura.hashCode());
		result = prime
				* result
				+ ((fechaVencimiento == null) ? 0 : fechaVencimiento.hashCode());
		result = prime * result
				+ ((idProducto == null) ? 0 : idProducto.hashCode());
		result = prime * result
				+ ((idUserOwner == null) ? 0 : idUserOwner.hashCode());
		result = prime * result + ((importe == null) ? 0 : importe.hashCode());
		result = prime * result
				+ ((lstOperaciones == null) ? 0 : lstOperaciones.hashCode());
		result = prime * result
				+ ((lstUsuarios == null) ? 0 : lstUsuarios.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((numero == null) ? 0 : numero.hashCode());
		result = prime * result
				+ ((rentabilidad == null) ? 0 : rentabilidad.hashCode());
		result = prime
				* result
				+ ((rentabilidadCancelacion == null) ? 0
						: rentabilidadCancelacion.hashCode());
		result = prime * result
				+ ((tipoProducto == null) ? 0 : tipoProducto.hashCode());
		result = prime * result + ((titular == null) ? 0 : titular.hashCode());
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
		Producto other = (Producto) obj;
		if (baja != other.baja)
			return false;
		if (banco == null) {
			if (other.banco != null)
				return false;
		} else if (!banco.equals(other.banco))
			return false;
		if (cotitular == null) {
			if (other.cotitular != null)
				return false;
		} else if (!cotitular.equals(other.cotitular))
			return false;
		if (descripcion == null) {
			if (other.descripcion != null)
				return false;
		} else if (!descripcion.equals(other.descripcion))
			return false;
		if (fechaApertura == null) {
			if (other.fechaApertura != null)
				return false;
		} else if (!fechaApertura.equals(other.fechaApertura))
			return false;
		if (fechaVencimiento == null) {
			if (other.fechaVencimiento != null)
				return false;
		} else if (!fechaVencimiento.equals(other.fechaVencimiento))
			return false;
		if (idProducto == null) {
			if (other.idProducto != null)
				return false;
		} else if (!idProducto.equals(other.idProducto))
			return false;
		if (idUserOwner == null) {
			if (other.idUserOwner != null)
				return false;
		} else if (!idUserOwner.equals(other.idUserOwner))
			return false;
		if (importe == null) {
			if (other.importe != null)
				return false;
		} else if (!importe.equals(other.importe))
			return false;
		if (lstOperaciones == null) {
			if (other.lstOperaciones != null)
				return false;
		} else if (!lstOperaciones.equals(other.lstOperaciones))
			return false;
		if (lstUsuarios == null) {
			if (other.lstUsuarios != null)
				return false;
		} else if (!lstUsuarios.equals(other.lstUsuarios))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (numero == null) {
			if (other.numero != null)
				return false;
		} else if (!numero.equals(other.numero))
			return false;
		if (rentabilidad == null) {
			if (other.rentabilidad != null)
				return false;
		} else if (!rentabilidad.equals(other.rentabilidad))
			return false;
		if (rentabilidadCancelacion == null) {
			if (other.rentabilidadCancelacion != null)
				return false;
		} else if (!rentabilidadCancelacion
				.equals(other.rentabilidadCancelacion))
			return false;
		if (tipoProducto == null) {
			if (other.tipoProducto != null)
				return false;
		} else if (!tipoProducto.equals(other.tipoProducto))
			return false;
		if (titular == null) {
			if (other.titular != null)
				return false;
		} else if (!titular.equals(other.titular))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Producto [idProducto=" + idProducto + ", tipoProducto="
				+ tipoProducto + ", banco=" + banco + ", nombre=" + nombre
				+ ", descripcion=" + descripcion + ", titular=" + titular
				+ ", cotitular=" + cotitular + ", rentabilidad=" + rentabilidad
				+ ", rentabilidadCancelacion=" + rentabilidadCancelacion
				+ ", numero=" + numero + ", importe=" + importe
				+ ", fechaApertura=" + fechaApertura + ", fechaVencimiento="
				+ fechaVencimiento + ", idUserOwner=" + idUserOwner + ", baja="
				+ baja + ", lstUsuarios=" + lstUsuarios + ", lstOperaciones="
				+ lstOperaciones + "]";
	}

}
