/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.BarChartModel;
import org.springframework.context.annotation.Scope;

import com.easyhomeconta.forms.PosicionGlobalForm;
import com.easyhomeconta.service.PosicionGlobalService;
import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;

/**
 * @author Alberto
 *
 */
@Named(value="pgBean")
@Scope("request")
public class PosicionGlobalController implements Serializable{


	private final Logger log = Logger.getLogger(PosicionGlobalController.class);
	private static final long serialVersionUID = 1L;
	
	
	@Inject
	PosicionGlobalService posicionService;
	
	 private BarChartModel barModel;
	
	PosicionGlobalForm pgf;
	
	
	@PostConstruct
	public void init(){
		pgf=new PosicionGlobalForm();
		pgf.setTotalCuentas(posicionService.getBalanceTotalByTProducto(new Integer(1)));
		pgf.setTotalDepositos(posicionService.getBalanceTotalByTProducto(new Integer (2)));
		pgf.setTotal(posicionService.getBalanceTotalByTProducto(null));
		//Para calcular el total para el resto de tipos de productos resto al total las las cuentas y los depositos
		BigDecimal cuentasYdepositos=pgf.getTotalCuentas().add(pgf.getTotalDepositos());
		pgf.setTotalOtros(pgf.getTotal().subtract(cuentasYdepositos));
		
		pgf.setTotalIngresosMes(posicionService.getSumatorioMensual(TipoOperacion.INGRESO));
		pgf.setTotalGastosMes(posicionService.getSumatorioMensual(TipoOperacion.GASTO));
		pgf.setTotalMes(pgf.totalIngresosMes.add(pgf.getTotalGastosMes()));
		
		log.info("Cuentas:"+ pgf.getTotalCuentas()+ "€ || Depositos: "+pgf.getTotalDepositos()+ "€ || Otros: "+pgf.getTotalOtros()+"€ || Balance: "+pgf.getTotal());
		
		barModel= posicionService.getBarChartModel();
		
	}
	
	public PosicionGlobalForm getPgf() {
		return pgf;
	}
	public void setPgf(PosicionGlobalForm pgf) {
		this.pgf = pgf;
	}
	
    public BarChartModel getBarModel() {
        return barModel;
    }

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}
	
}
