/**
 * 
 */
package com.easyhomeconta.service;

import java.util.List;

import javax.faces.model.SelectItem;

import com.easyhomeconta.forms.BancoForm;
import com.easyhomeconta.model.Banco;

/**
 * @author Alberto
 *
 */
public interface BancoService {

	/**
	 * Obtiene la entidad por id
	 * @param id
	 * @return
	 */
	public Banco getBancoById(Integer id);
	
	/**
	 * Devuelve una lista de bancos activos (baja N) preparada para ser renderizada en un combo
	 * @return
	 */
	public List<SelectItem> getLstBancosActivosForCombo();
	
	
	/**
	 * Devuelve una lista de bancos activos (baja N) volcada a un bean para renderizarle 
	 * @return
	 */
	public List<BancoForm> getLstBancosActivos();
	
	/**
	 * Modifica o crea un nuevo banco en base de datos para el usuario logado en funcion de si
	 * el id del banco esta cargado o es nulo.
	 * @param banco
	 * @param cambiarFoto.Flag que indica si se ha pulsado el cambio de foto en la vista.
	 * @return
	 */
	public BancoForm saveBanco(BancoForm banco,Boolean cambiarFoto);
	
	
	/**
	 * Da de baja logica el banco con el id pasado como parametro
	 * @param idBanco
	 */
	public void deleteBanco(Integer idBanco);
	
	/**
	 * Devuelve una lista de bancos form con cargado con el tipo de productos pasado como parametro
	 * En el caso de que idProducto sea null se cargara con todos los productos del banco.
	 * Tambien se cargan aquellos productos que esten dados de baja logica.
	 * @param idUser
	 * @param idTroducto
	 * @return
	 */
	public List<BancoForm> findAllForUser(Integer idUser, Integer idTroducto);
}
