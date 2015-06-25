/**
 * 
 */
package com.easyhomeconta.forms;

import java.io.Serializable;
import java.math.BigDecimal;

import com.easyhomeconta.utils.NumeroUtil;

/**
 * @author Alberto
 *
 */
public class PosicionGlobalForm implements Serializable {

	private static final long serialVersionUID = 1L;
	//Totales
	public BigDecimal totalCuentas, totalDepositos, totalOtros, total;
	public String totalCuentasStr, totalDepositosStr, totalOtrosStr, totalStr;
	public Float percentCuentas, percentDepositos, percentOtros;
	public String percentCuentasStr, percentDepositosStr, percentOtrosStr;
	//Mes
	public BigDecimal totalMes, totalIngresosMes, totalGastosMes;
	public String totalMesStr, totalIngresosMesStr, totalGastosMesStr;

	public PosicionGlobalForm() {
		super();
	}

	public BigDecimal getTotalCuentas() {
		return totalCuentas;
	}
	public void setTotalCuentas(BigDecimal totalCuentas) {
		this.totalCuentas = totalCuentas;
	}
	public BigDecimal getTotalDepositos() {
		return totalDepositos;
	}
	public void setTotalDepositos(BigDecimal totalDepositos) {
		this.totalDepositos = totalDepositos;
	}
	public BigDecimal getTotalOtros() {
		return totalOtros;
	}
	public void setTotalOtros(BigDecimal totalOtros) {
		this.totalOtros = totalOtros;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	public String getTotalCuentasStr() {
		return NumeroUtil.formatearACastellano(this.getTotalCuentas());
	}
	public void setTotalCuentasStr(String totalCuentasStr) {
		this.totalCuentasStr = totalCuentasStr;
	}
	public String getTotalDepositosStr() {
		return NumeroUtil.formatearACastellano(this.getTotalDepositos());
	}
	public void setTotalDepositosStr(String totalDepositosStr) {
		this.totalDepositosStr = totalDepositosStr;
	}
	public String getTotalOtrosStr() {
		return NumeroUtil.formatearACastellano(this.getTotalOtros());
	}
	public void setTotalOtrosStr(String totalOtrosStr) {
		this.totalOtrosStr = totalOtrosStr;
	}
	public String getTotalStr() {
		return NumeroUtil.formatearACastellano(this.getTotal());
	}
	public void setTotalStr(String totalStr) {
		this.totalStr = totalStr;
	}

	public Float getPercentCuentas() {
		return percentCuentas;
	}

	public void setPercentCuentas(Float percentCuentas) {
		this.percentCuentas = percentCuentas;
	}

	public Float getPercentDepositos() {
		return percentDepositos;
	}

	public void setPercentDepositos(Float percentDepositos) {
		this.percentDepositos = percentDepositos;
	}

	public Float getPercentOtros() {
		return percentOtros;
	}

	public void setPercentOtros(Float percentOtros) {
		this.percentOtros = percentOtros;
	}

	public String getPercentCuentasStr() {
		BigDecimal percent=totalCuentas.multiply(new BigDecimal(100)).divide(total,2,BigDecimal.ROUND_HALF_UP);
		return NumeroUtil.formatearACastellano(percent);
		
	}

	public void setPercentCuentasStr(String percentCuentasStr) {
		this.percentCuentasStr = percentCuentasStr;
	}

	public String getPercentDepositosStr() {
		BigDecimal percent=totalDepositos.multiply(new BigDecimal(100)).divide(total,2,BigDecimal.ROUND_HALF_UP);
		return NumeroUtil.formatearACastellano(percent);
	}

	public void setPercentDepositosStr(String percentDepositosStr) {
		this.percentDepositosStr = percentDepositosStr;
	}

	public String getPercentOtrosStr() {
		BigDecimal percent=totalOtros.multiply(new BigDecimal(100)).divide(total,2,BigDecimal.ROUND_HALF_UP);
		return NumeroUtil.formatearACastellano(percent);
		
	}

	public void setPercentOtrosStr(String percentOtrosStr) {
		this.percentOtrosStr = percentOtrosStr;
	}

	public BigDecimal getTotalMes() {
		return totalMes;
	}

	public void setTotalMes(BigDecimal totalMes) {
		this.totalMes = totalMes;
	}

	public BigDecimal getTotalIngresosMes() {
		return totalIngresosMes;
	}

	public void setTotalIngresosMes(BigDecimal totalIngresosMes) {
		this.totalIngresosMes = totalIngresosMes;
	}

	public BigDecimal getTotalGastosMes() {
		return totalGastosMes;
	}

	public void setTotalGastosMes(BigDecimal totalGastosMes) {
		this.totalGastosMes = totalGastosMes;
	}

	public String getTotalMesStr() {
		return NumeroUtil.formatearACastellano(this.getTotalMes());
	}

	public void setTotalMesStr(String totalMesStr) {
		this.totalMesStr = totalMesStr;
	}

	public String getTotalIngresosMesStr() {
		return NumeroUtil.formatearACastellano(this.getTotalIngresosMes());
	}

	public void setTotalIngresosMesStr(String totalIngresosMesStr) {
		this.totalIngresosMesStr = totalIngresosMesStr;
	}

	public String getTotalGastosMesStr() {
		return NumeroUtil.formatearACastellano(this.getTotalGastosMes());
	}

	public void setTotalGastosMesStr(String totalGastosMesStr) {
		this.totalGastosMesStr = totalGastosMesStr;
	}
	
}
