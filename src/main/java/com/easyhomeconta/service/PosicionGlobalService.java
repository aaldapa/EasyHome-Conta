/**
 * 
 */
package com.easyhomeconta.service;

import java.math.BigDecimal;

import org.primefaces.model.chart.BarChartModel;

import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;

/**
 * @author Alberto
 *
 */
public interface PosicionGlobalService {

	/**
	 * Devuelve el balance total de productos para el tipo de producto del id pasado como parametro 
	 * @param idTProducto
	 * @return
	 */
	public BigDecimal getBalanceTotalByTProducto(Integer idTProducto);
	
	/**
	 * Devuelve el sumatorio de gastos o ingresos para el mes indicado
	 * @param tipoOperacion (INGRESO o GASTO)
	 * @return
	 */
	public BigDecimal getSumatorioMensual(TipoOperacion tipoOperacion);
	
	/**
	 * Devuelve los valores necesarios para la renderizar la grafica de la vista posicion global
	 * @return
	 */
	public BarChartModel getBarChartModel();
	
}
