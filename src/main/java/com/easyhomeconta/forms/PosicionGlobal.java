/**
 * 
 */
package com.easyhomeconta.forms;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alberto
 *
 */
public class PosicionGlobal implements Serializable{

	private static final long serialVersionUID = 1L;

	private BigDecimal balanceGlobal;
	private List<BancoForm> lstBancosForm;
	
	public PosicionGlobal() {
		super();
	}

	public BigDecimal getBalanceGlobal() {
		return balanceGlobal;
	}

	public void setBalanceGlobal(BigDecimal balanceGlobal) {
		this.balanceGlobal = balanceGlobal;
	}

	public List<BancoForm> getLstBancosForm() {
		return lstBancosForm;
	}

	public void setLstBancosForm(List<BancoForm> lstBancosForm) {
		this.lstBancosForm = lstBancosForm;
	}

}
