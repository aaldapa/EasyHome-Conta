/**
 * 
 */
package com.easyhomeconta.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.dao.TipoProductoDao;
import com.easyhomeconta.dao.UserDao;
import com.easyhomeconta.forms.BancoForm;
import com.easyhomeconta.forms.ProductoForm;
import com.easyhomeconta.model.Banco;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.model.TipoProducto;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.Enumeraciones.SiNo;

/**
 * @author Alberto
 *
 */
@Named
public class ProductoServiceImpl implements ProductoService {

	
	@Inject
	ProductoDao productoDao; 
	
	@Inject
	UserDao userDao;
	
	@Inject
	BancoDao bancoDao;
	
	@Inject
	TipoProductoDao tProductoDao;
	
	
	@Override
	@Transactional
	public List<ProductoForm> getProductosForUser(Integer idUser) {
		List<Producto> lstProductos=productoDao.findProductosForUser(idUser);
		List<ProductoForm> lstBeans=new ArrayList<ProductoForm>();
		for (Producto producto:lstProductos)
			lstBeans.add(parseEntityToBean(producto, idUser));
		return lstBeans;
	}
	
	@Override
	@Transactional
	public ProductoForm saveProducto(ProductoForm bean, Integer idUser) {
		Producto producto=new Producto();
		List <User> lstUsuariosProducto=new ArrayList<User>();
		Banco banco=bancoDao.findById(bean.getIdBanco());
		TipoProducto tipo=tProductoDao.findById(bean.getIdTipoProducto());
		//Añado el producto al usuario logado y este al producto 
		User userLogado=userDao.findById(idUser);
		
		//Si es un producto NUEVO
		if (bean.getIdProducto()==null){
			producto.setIdUserOwner(userLogado.getIdUser());
			producto.setBanco(banco);
			producto.setTipoProducto(tipo);
			producto.setFechaApertura(bean.getFechaApertura());
			producto.setFechaVencimiento(bean.getFechaVencimiento());
			producto.setNombre(bean.getNombre());
			producto.setNumero(bean.getNumero());
			producto.setImporte(bean.getImporte());
			producto.setRentabilidad(bean.getRentabilidad());
			producto.setRentabilidadCancelacion(bean.getRentabilidadCancelacion());
			producto.setTitular(bean.getTitular());
			producto.setCotitular(bean.getCotitular());
			producto.setBaja(SiNo.N);
			
			//GUARDAR LOS USER_PRODUCTOS
			if (userLogado.getLstProductos()==null)
				userLogado.setLstProductos(new ArrayList<Producto>());
			else
				userLogado.getLstProductos().add(producto);
			lstUsuariosProducto.add(userLogado);
			
			//Recorro los usuarios seleccionado y les añado el producto para despues añadiles al producto
			if (bean.getIdsPermisos()!=null){
				for (int i=0;i<bean.getIdsPermisos().length;i++){
					User usuarioConPermiso=userDao.findById(new Integer(bean.getIdsPermisos()[i]));
					
					if (usuarioConPermiso.getLstProductos()==null)
						usuarioConPermiso.setLstProductos(new ArrayList<Producto>());
					else
						usuarioConPermiso.getLstProductos().add(producto);
					
					lstUsuariosProducto.add(usuarioConPermiso);
				}
			}
			producto.setLstUsuarios(lstUsuariosProducto);
			
			producto=productoDao.create(producto);
			
		}
		//Si es MODIFICACION
		else{
			producto=productoDao.findById(bean.getIdProducto());
			
			//Borro el producto a todos los usuarios
			for(User u:producto.getLstUsuarios())
				u.getLstProductos().remove(producto);
			
			//Borro a todos los usuarios del producto
			producto.getLstUsuarios().clear();
			
			//GUARDAR LOS USER_PRODUCTO
			//1º Añado al usuario logado
			userLogado.getLstProductos().add(producto);
			lstUsuariosProducto.add(userLogado);
			
			if (bean.getIdsPermisos()!=null){
				//Recorro los usuarios seleccionados y les añado el producto para despues añadirles al producto
				for (int i=0;i<bean.getIdsPermisos().length;i++){
					User usuarioConPermiso=userDao.findById(new Integer(bean.getIdsPermisos()[i]));
					
					if (usuarioConPermiso.getLstProductos()==null)
						usuarioConPermiso.setLstProductos(new ArrayList<Producto>());
					else
						usuarioConPermiso.getLstProductos().add(producto);
					
					lstUsuariosProducto.add(usuarioConPermiso);
				}
			}
			producto.setLstUsuarios(lstUsuariosProducto);
			
			producto.setBanco(banco);
			producto.setTipoProducto(tipo);
			producto.setFechaApertura(bean.getFechaApertura());
			producto.setFechaVencimiento(bean.getFechaVencimiento());
			producto.setNombre(bean.getNombre());
			producto.setNumero(bean.getNumero());
			producto.setImporte(bean.getImporte());
			producto.setRentabilidad(bean.getRentabilidad());
			producto.setRentabilidadCancelacion(bean.getRentabilidadCancelacion());
			producto.setTitular(bean.getTitular());
			producto.setCotitular(bean.getCotitular());
//			producto.setBaja(SiNo.N);
			
			producto=productoDao.update(producto);
			
		}
		
		bean.setIdProducto(producto.getIdProducto());
		bean.setNombreBanco(banco.getNombre());
		bean.setNombreTProducto(tipo.getNombre());
		
		return bean;
	}
	
	@Override
	@Transactional
	public void deleteProducto(Integer idProducto) {
		Producto producto=productoDao.findById(idProducto);
		//Baja logica para no eliminar el producto de forma permanente
		producto.setBaja(SiNo.S);
		productoDao.update(producto);
	}
	
	@Override
	@Transactional
	public List<BancoForm> findAllByTypeForUser(Integer idUser, Integer idTProducto) {
		
		List<Banco> lstBancos=bancoDao.findAllCompleteByTipoForUser(idUser, idTProducto);
		List<BancoForm> lstBancosForm=new ArrayList<BancoForm>();
		
		for(Banco b:lstBancos){
			List<ProductoForm> lstProductosForm=new ArrayList<ProductoForm>();
			BancoForm bForm=new BancoForm();
			bForm.setIdBanco(b.getIdBanco());
			bForm.setNombre(b.getNombre());
			BigDecimal balanceTotal=new BigDecimal(0);
			
			for (Producto p:b.getLstProductos()){
				ProductoForm newProducto=new ProductoForm();
				newProducto.setIdProducto(p.getIdProducto());
				
				newProducto.setNombre(p.getNombre());
				newProducto.setNombreBanco(p.getBanco().getNombre());
				newProducto.setRentabilidad(p.getRentabilidad());
				newProducto.setFechaVencimiento(p.getFechaVencimiento());
				
				//Si el tipo de producto es operable obtenemos la fecha del ultimo movimiento y el balance
				if (p.getTipoProducto().getOperable().compareTo(SiNo.S)==0){
					newProducto.setBalance(productoDao.getBalance(p.getIdProducto()));
					newProducto.setFechaUltOperacion(productoDao.getFechaUltimaOperacion(p.getIdProducto()));
				}
				else
					newProducto.setBalance(p.getImporte());
								
				newProducto.setActivo(p.getBaja().equals(SiNo.N)?true:false);
				//Sumo el balance de la cuenta al del banco para obtener el total de las cuentas
				balanceTotal=balanceTotal.add(newProducto.getBalance());
				lstProductosForm.add(newProducto);	
			}
			bForm.setBalance(balanceTotal);
			bForm.setLstProductosForm(lstProductosForm);
			lstBancosForm.add(bForm);
		}
		return lstBancosForm;
	}
	
	/**
	 * Parsea de entidad al bean de maniobra para trabajar en la capa de presentacion
	 * @param tipoProducto
	 * * @param idUser logado
	 * @return
	 */
	private ProductoForm parseEntityToBean(Producto producto, Integer IdUser){
				
		ProductoForm bean=new ProductoForm();
		bean.setIdProducto(producto.getIdProducto());
		bean.setIdBanco(producto.getBanco().getIdBanco());
		bean.setNombreBanco(producto.getBanco().getNombre());
		bean.setIdTipoProducto(producto.getTipoProducto().getIdTipoProducto());
		bean.setNombreTProducto(producto.getTipoProducto().getNombre());
		bean.setFechaApertura(producto.getFechaApertura());
		bean.setFechaVencimiento(producto.getFechaVencimiento());
		bean.setNombre(producto.getNombre());
		bean.setTitular(producto.getTitular());
		bean.setCotitular(producto.getCotitular());
		bean.setNumero(producto.getNumero());
		bean.setImporte(producto.getImporte());
		bean.setRentabilidad(producto.getRentabilidad());
		bean.setRentabilidadCancelacion(producto.getRentabilidadCancelacion());
		bean.setDescripcion(producto.getDescripcion());
		bean.setOwner(producto.getIdUserOwner().compareTo(IdUser)==0?true:false);
		
		
		List<User>lstUsuarios=userDao.findUsersByProducto(producto.getIdProducto());
		
		if (lstUsuarios.size()>1){
			//Inicializo el array del bean
			bean.setIdsPermisos(new String[lstUsuarios.size()-1]);

			//Contador de posiciones para el array de ids de familiares en el bean
			int cont=0;
			for (User fam: lstUsuarios){	
				if (fam.getIdUser().compareTo(IdUser)!=0){
					bean.getIdsPermisos()[cont]=fam.getIdUser().toString();
					cont++;
				}
			} 
		}
		return bean;
		
	}

}
