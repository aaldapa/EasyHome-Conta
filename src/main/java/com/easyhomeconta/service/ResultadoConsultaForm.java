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

	private BigDecimal balance;
	private List<OperacionForm> lstOperacionesForm;
	private Integer nRegistros;
	
	public ResultadoConsultaForm() {
		super();
	}

	public BigDecimal getBalance() {
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
	
}
