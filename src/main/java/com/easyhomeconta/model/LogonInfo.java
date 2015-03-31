/**
 * 
 */
package com.easyhomeconta.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="LOGONINFO")
public class LogonInfo implements Serializable {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idLogonInfo;
	
	@ManyToOne
	@JoinColumn(name="idUser", nullable = false)
	private User user;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
		
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition = "ENUM('LOGIN','LOGOUT', 'TIMEDOUT')")
	private Enumeraciones.LogonType logonType;
	
	public LogonInfo() {
		super();
	}
	
	public Long getIdLogonInfo() {
		return idLogonInfo;
	}
	public void setIdLogonInfo(Long idLogonInfo) {
		this.idLogonInfo = idLogonInfo;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Enumeraciones.LogonType getLogonType() {
		return logonType;
	}

	public void setLogonType(Enumeraciones.LogonType logonType) {
		this.logonType = logonType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result
				+ ((idLogonInfo == null) ? 0 : idLogonInfo.hashCode());
		result = prime * result
				+ ((logonType == null) ? 0 : logonType.hashCode());
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
		LogonInfo other = (LogonInfo) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (idLogonInfo == null) {
			if (other.idLogonInfo != null)
				return false;
		} else if (!idLogonInfo.equals(other.idLogonInfo))
			return false;
		if (logonType != other.logonType)
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
		return "LogonInfo [idLogonInfo=" + idLogonInfo + ", user=" + user
				+ ", fecha=" + fecha + ", logonType=" + logonType + "]";
	}
	
	
}
