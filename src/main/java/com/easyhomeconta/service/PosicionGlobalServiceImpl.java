/**
 * 
 */
package com.easyhomeconta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.easyhomeconta.dao.OperacionDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.dao.TipoProductoDao;
import com.easyhomeconta.model.OperacionView;
import com.easyhomeconta.model.TipoProducto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;
import com.easyhomeconta.utils.Enumeraciones.TipoOperacion;
import com.easyhomeconta.utils.MyUtils;

/**
 * @author Alberto
 *
 */

@Named
public class PosicionGlobalServiceImpl implements PosicionGlobalService {

	@Inject
	TipoProductoDao tProductoDao;
	
	@Inject
	ProductoDao productoDao;
	
	@Inject
	OperacionDao operacionDao;
	
	@Override
	public BigDecimal getBalanceTotalByTProducto(Integer idTProducto) {
		BigDecimal balanceTotal=new BigDecimal(0);
		BigDecimal sumatorioOperaciones=new BigDecimal(0);
		BigDecimal sumatorioProductos=new BigDecimal(0);
		
		if (idTProducto!=null){
			TipoProducto tipoProducto=tProductoDao.findById(idTProducto);
			//Si el tipo de producto es operable obtengo el sumatorio
			if (tipoProducto.getOperable().compareTo(SiNo.S)==0)
				sumatorioOperaciones=tProductoDao.getSumatorioOperacionesByTProducto(idTProducto);
			
			sumatorioProductos=tProductoDao.getSumatorioProductosByTProducto(idTProducto);
		} 
		else{
			sumatorioOperaciones=operacionDao.sumatorioOperaciones(null,null,null);
			sumatorioProductos=productoDao.sumatorioProductos();
		}
		
		
		balanceTotal=sumatorioProductos.add(sumatorioOperaciones);
		
		return balanceTotal;
	}

	@Override
	public BigDecimal getSumatorioMensual(TipoOperacion tipoOperacion) {
		DateTime startOfThisMonth = new DateTime().dayOfMonth().withMinimumValue().withTimeAtStartOfDay();
		DateTime endOfThisMonth = new DateTime().dayOfMonth().withMaximumValue().withTimeAtStartOfDay();

		BigDecimal sumatorioOperaciones=operacionDao.sumatorioOperaciones(tipoOperacion,startOfThisMonth.toDate(),endOfThisMonth.toDate());
		
		return sumatorioOperaciones;
	}

	@Override
	public BarChartModel getBarChartModel(){
		
		Integer year=new Integer(new DateTime().getYear());
		List<OperacionView> lstOperacionesView=operacionDao.getVistaBalancesMes(year);
		BarChartModel model = new BarChartModel();
		
		if (!lstOperacionesView.isEmpty()){
			
			ChartSeries ingresos = new ChartSeries();
	        ChartSeries gastos = new ChartSeries();
	        ChartSeries diferencias = new ChartSeries();
	        
	        List<BigDecimal> importes=new ArrayList<BigDecimal>();
	        List<BigDecimal> difImportes=new ArrayList<BigDecimal>();

	        
	        ingresos.setLabel("Ingresos");
	        gastos.setLabel("Gastos");
	        diferencias.setLabel("Balance");
	        
	        
	        for (OperacionView vista:lstOperacionesView){
	        	
	        	ingresos.set(MyUtils.getStringFromBundle("mes."+vista.getMesNumero()+".abr"), vista.getIngresos());	
	        	gastos.set(MyUtils.getStringFromBundle("mes."+vista.getMesNumero()+".abr"), vista.getGastos().negate());
	        	diferencias.set(MyUtils.getStringFromBundle("mes."+vista.getMesNumero()+".abr"), vista.getBalance());
	        	
	        	
	        	importes.add(vista.getIngresos());
	        	importes.add(vista.getGastos().negate());
	        	difImportes.add(vista.getBalance());
			}
	        
	        model.addSeries(ingresos);
	        model.addSeries(gastos);
	        model.addSeries(diferencias);
	        
	        model.setTitle("Año "+year);
	        model.setLegendPosition("ne");
	        model.setAnimate(true);
	        model.setShadow(false);
	        model.setExtender("chartExtender");
	         
	        Axis xAxis = model.getAxis(AxisType.X);
	        xAxis.setLabel("Meses");

	         
	        Axis yAxis = model.getAxis(AxisType.Y);
	        yAxis.setLabel("Euros");
	        yAxis.setTickFormat("%'.2f €");

	      
	        
	        //Ordeno la lista de diferencias para quedarme con el valor mas bajo 
	        Collections.sort(difImportes);
	        //Calculo el valor de miles inferior mas cercano del valor minimo. Ejemplo -1005,54 ->2000
	        BigDecimal min=difImportes.get(0).divide(new BigDecimal(1000),0,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(-1)).multiply(new BigDecimal(1000));
	        yAxis.setMin(min);
	        //Ordeno la lista de importes para quedarme con el valor mas alto
	        Collections.sort(importes ,Collections.reverseOrder());
	      //Calculo el valor de miles superior mas cercano del valor maximo. Ejemplo 4659,78 ->5000
	        BigDecimal max=importes.get(0).divide(new BigDecimal(1000),0,BigDecimal.ROUND_HALF_UP).add(new BigDecimal(1)).multiply(new BigDecimal(1000));
	        yAxis.setMax(max);
		}
		
		
         
        
        
        return model;
        
	}
	
}
