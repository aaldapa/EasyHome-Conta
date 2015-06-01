/**
 * 
 */
package com.easyhomeconta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;

import com.easyhomeconta.beans.BancoForm;
import com.easyhomeconta.beans.ProductoForm;
import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.model.Banco;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@Named
public class CuentaServiceImpl implements CuentaService {

	@SuppressWarnings("unused")
	private final Logger log = Logger.getLogger(CuentaServiceImpl.class);
	
	@Inject
	ProductoDao productoDao;
	
	@Inject
	BancoDao bancoDao;
	
	@Override
	public List<BancoForm> findAllForUser(Integer idUser) {
		
		List<Banco> lstBancos=bancoDao.findAllCompleteByTipoForUser(idUser, new Integer(1));
		List<BancoForm> lstBancosForm=new ArrayList<BancoForm>();
		//List<Producto>lstCuentas=productoDao.findAllByTipoForUser(idUser, new Integer(1));
		
		
		for(Banco b:lstBancos){
			List<ProductoForm> lstCuentasForm=new ArrayList<ProductoForm>();
			BancoForm bForm=new BancoForm();
			bForm.setIdBanco(b.getIdBanco());
			bForm.setNombre(b.getNombre());
			BigDecimal balanceTotal=new BigDecimal(0);
			
			for (Producto p:b.getLstProductos()){
				ProductoForm newCuenta=new ProductoForm();
				newCuenta.setIdProducto(p.getIdProducto());
				newCuenta.setBalance(productoDao.getBalance(p.getIdProducto()));
				newCuenta.setNombre(p.getNombre());
				newCuenta.setNombreBanco(p.getBanco().getNombre());
				newCuenta.setFechaUltOperacion(productoDao.getFechaUltimaOperacion(p.getIdProducto()));
				newCuenta.setActivo(p.getBaja().equals(SiNo.S)?true:false);
				//Sumo el balance de la cuenta al del banco para obtener el total de las cuentas
				balanceTotal=balanceTotal.add(newCuenta.getBalance());
				lstCuentasForm.add(newCuenta);	
			}
			bForm.setBalance(balanceTotal);
			bForm.setLstProductosForm(lstCuentasForm);
			lstBancosForm.add(bForm);
		}
		return lstBancosForm;
	}

}
