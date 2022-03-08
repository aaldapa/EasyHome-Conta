package com.easyhomeconta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.forms.BancoForm;
import com.easyhomeconta.forms.ProductoForm;
import com.easyhomeconta.model.Banco;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

@Named
public class BancoServiceImpl implements BancoService {

	private final Logger log = Logger.getLogger(BancoServiceImpl.class);

	@Inject
	BancoDao bancoDao;

	@Inject
	ProductoDao productoDao;

	public Banco getBancoById(Integer id) {
		Banco banco = bancoDao.findById(id);
		return banco;
	}

	@Override
	public List<SelectItem> getLstBancosActivosForCombo() {
		List<Banco> lstBancos = bancoDao.findLstBancosActivos();
		List<SelectItem> lstItems = new ArrayList<SelectItem>();

		for (Banco banco : lstBancos)
			lstItems.add(new SelectItem(banco.getIdBanco(), banco.getNombre()));

		return lstItems;
	}

	@Override
	public List<BancoForm> getLstBancosActivos() {
		List<Banco> lstBancos = bancoDao.findLstBancosActivos();
		List<BancoForm> lstBeans = new ArrayList<BancoForm>();

		for (Banco banco : lstBancos)
			lstBeans.add(new BancoForm(banco.getIdBanco(), banco.getNombre(), banco.getFilaInicio(),
					banco.getColumnaFecha(), banco.getColumnaConcepto(), banco.getColumnaImporte(), banco.getLogo()));

		return lstBeans;
	}

	@Override
	@Transactional
	public BancoForm saveBanco(BancoForm bean, Boolean cambiarFoto) {

		if (null == bean.getIdBanco() || new Integer("0").compareTo(bean.getIdBanco()) == 0) {
			Banco newBanco = new Banco(null, bean.getNombre(), SiNo.N, parseUploadedFileToArrayByte(bean.getImagen()));
			newBanco = bancoDao.create(newBanco);
			// Guardo el id del nuevo banco para mostrarlo en el listado de
			// bancos
			bean.setIdBanco(newBanco.getIdBanco());
		} else {
			Banco banco = bancoDao.findById(bean.getIdBanco());
			banco.setNombre(bean.getNombre());
			banco.setFilaInicio(bean.getFilaInicio());
			banco.setColumnaFecha(bean.getColumnaFecha());
			banco.setColumnaConcepto(bean.getColumnaConcepto());
			banco.setColumnaImporte(bean.getColumnaImporte());

			if (cambiarFoto) {
				banco.setLogo(parseUploadedFileToArrayByte(bean.getImagen()));
				bean.setLogoBanco(banco.getLogo());
			}

			bancoDao.update(banco);
		}

		return bean;
	}

	@Override
	@Transactional
	public void deleteBanco(Integer idBanco) {
		Banco banco = bancoDao.findById(idBanco);
		banco.setBaja(SiNo.S);
		// Baja logica
		bancoDao.update(banco);
	}

	@Override
	public List<BancoForm> findAllForUser(Integer idUser, Integer idTroducto) {

		List<Banco> lstBancos = bancoDao.findAllCompleteByTipoForUser(idUser, idTroducto);
		List<BancoForm> lstBancosForm = new ArrayList<BancoForm>();

		for (Banco b : lstBancos) {
			List<ProductoForm> lstCuentasForm = new ArrayList<ProductoForm>();
			BancoForm bForm = new BancoForm();
			bForm.setIdBanco(b.getIdBanco());
			bForm.setNombre(b.getNombre());
			BigDecimal balanceTotal = new BigDecimal(0);

			for (Producto p : b.getLstProductos()) {
				ProductoForm newCuenta = new ProductoForm();
				newCuenta.setIdProducto(p.getIdProducto());
				newCuenta.setBalance(productoDao.getBalance(p.getIdProducto()));
				newCuenta.setNombre(p.getNombre());
				newCuenta.setNombreBanco(p.getBanco().getNombre());
				newCuenta.setFechaUltOperacion(productoDao.getFechaUltimaOperacion(p.getIdProducto()));
				newCuenta.setActivo(p.getBaja().equals(SiNo.S) ? true : false);
				// Sumo el balance de la cuenta al del banco para obtener el
				// total de las cuentas
				balanceTotal = balanceTotal.add(newCuenta.getBalance());
				lstCuentasForm.add(newCuenta);
			}
			bForm.setBalance(balanceTotal);
			bForm.setLstProductosForm(lstCuentasForm);
			lstBancosForm.add(bForm);
		}
		return lstBancosForm;
	}

	/**
	 * Convierte la imagen seleccionada en el formulario a un array de byte para
	 * ser guardado en bd
	 * 
	 * @param img
	 * @return
	 */
	private byte[] parseUploadedFileToArrayByte(UploadedFile img) {

		byte[] logo = null;
		if (img != null && img.getSize() > 0) {
			try {
				logo = IOUtils.toByteArray(img.getInputstream());
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		return logo;
	}

}
