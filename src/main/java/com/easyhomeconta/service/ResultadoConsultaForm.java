/**
 * 
 */
package com.easyhomeconta.service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.easyhomeconta.forms.OperacionForm;
import com.easyhomeconta.utils.NumeroUtil;

/**
 * @author Alberto
 *
 */
public class ResultadoConsultaForm implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal balance, totalIngresos, totalGastos;
	private List<OperacionForm> lstOperacionesForm;
	private Integer nRegistros;
	
	public ResultadoConsultaForm() {
		super();
	}

	public BigDecimal getBalance() {
		this.balance=this.totalIngresos.add(this.totalGastos);
		return balance;
	}

	public String getBalanceStr(){
		return NumeroUtil.formatearACastellano(this.getBalance());
	};
	
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
	public List<OperacionForm> getLstOperacionesForm() {
		return lstOperacionesForm;
	}

	public void setLstOperacionesForm(List<OperacionForm> lstOperacionesForm) {
		this.lstOperacionesForm = lstOperacionesForm;
	}

	public Integer getnRegistros() {
		if (lstOperacionesForm!=null)
			nRegistros=lstOperacionesForm.size();
		else
			nRegistros=new Integer(0);
		return nRegistros;
	}

	public void setnRegistros(Integer nRegistros) {
		this.nRegistros = nRegistros;
	}

	/**
	 * @return the totalGastos
	 */
	public BigDecimal getTotalGastos() {
		return totalGastos;
	}
	
	public String getTotalGastosStr(){
		return NumeroUtil.formatearACastellano(this.getTotalGastos());
	}

	/**
	 * @param totalGastos the totalGastos to set
	 */
	public void setTotalGastos(BigDecimal totalGastos) {
		this.totalGastos = totalGastos;
	}

	/**
	 * @return the totalIngresos
	 */
	public BigDecimal getTotalIngresos() {
		return totalIngresos;
	}

	
	public String getTotalIngresosStr(){
		return NumeroUtil.formatearACastellano(this.getTotalIngresos());
	}
	/**
	 * @param totalIngresos the totalIngresos to set
	 */
	public void setTotalIngresos(BigDecimal totalIngresos) {
		this.totalIngresos = totalIngresos;
	}
	
}
