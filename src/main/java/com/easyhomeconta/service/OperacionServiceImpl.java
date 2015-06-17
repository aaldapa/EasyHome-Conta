/**
 * 
 */
package com.easyhomeconta.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import com.easyhomeconta.dao.BancoDao;
import com.easyhomeconta.dao.CategoriaDao;
import com.easyhomeconta.dao.OperacionDao;
import com.easyhomeconta.dao.ProductoDao;
import com.easyhomeconta.forms.OperacionForm;
import com.easyhomeconta.model.Banco;
import com.easyhomeconta.model.Categoria;
import com.easyhomeconta.model.Operacion;
import com.easyhomeconta.model.Producto;
import com.easyhomeconta.model.User;
import com.easyhomeconta.utils.FechaUtil;
import com.easyhomeconta.utils.MyUtils;

/**
 * @author Alberto
 *
 */
@Named
public class OperacionServiceImpl implements OperacionService {

	private final Logger log = Logger.getLogger(OperacionServiceImpl.class);
	
	@Inject
	ProductoDao productoDao;
	
	@Inject
	CategoriaDao categoriaDao;
	
	@Inject
	OperacionDao operacionDao;
	
	@Inject
	BancoDao bancoDao;
	
	@Inject
	UserService userService;
	
	@Inject
	CategoriaService categoriaService;
	
	@Override
	public List<SelectItem> getLstProductosOperables(Integer idUsuario) {

		List<Producto> lstProductos=productoDao.findProductosOperablesForUser(idUsuario);
		List<SelectItem> lstItems=new ArrayList<SelectItem>();
		for (Producto p:lstProductos)
			lstItems.add(new SelectItem(p.getIdProducto(), p.getNombre()));
		
		return lstItems;
	}
	
	@Override
	public List<SelectItem> getLstCategorias(Integer idUsuario) {
		List<Categoria>lstCategorias=categoriaDao.findCategoriaForUser(idUsuario);
		List<SelectItem> lstItems=new ArrayList<SelectItem>();
		for (Categoria c:lstCategorias)
			lstItems.add(new SelectItem(c.getIdCategoria(), c.getNombre()));
		
		return lstItems;
	}
	
	@Override
	@Transactional
	public ResultadoConsultaForm getLstOperacionesForm(Date fInicio, Date fFin, Integer idProducto,Integer idCategoria, String busqueda, Integer idUsuario) {
		
		ResultadoConsultaForm resultado= new ResultadoConsultaForm();
		
		List<Operacion> lstOperaciones=operacionDao.findOperacionesWithCategoria(fInicio, fFin, idProducto, idCategoria, busqueda, idUsuario);
		List<OperacionForm> lstOperacionesForm=new ArrayList<OperacionForm>();
		
		log.info("Operaciones "+lstOperaciones.size());
		//Casteo de operaciones
		for(Operacion op:lstOperaciones){
			OperacionForm opf=new OperacionForm();
			
			opf.setId(op.getIdOperacion());
			opf.setFecha(op.getFecha());
			opf.setConcepto(op.getConcepto());
			opf.setImporte(op.getImporte());
			opf.setNotas(op.getNotas());
			
			if (op.getLstCategorias()!=null && !op.getLstCategorias().isEmpty()){
				//Si la lista de categorias no es nula cargo la primera ya que solo deberia de haber una.
				opf.setIdCategoria(op.getLstCategorias().get(0).getIdCategoria());
				//Cargo el nombre para realizar la ordenacion alfabetica de categorias en la tabla
				opf.setNombreCategoria(op.getLstCategorias().get(0).getNombre());
			}
			
			lstOperacionesForm.add(opf);
		}
	
		BigDecimal balance=new BigDecimal("0");
		for (OperacionForm opf: lstOperacionesForm)
			balance=balance.add(opf.getImporte());

		setSaldosToLstOperacionesForm(lstOperacionesForm, idProducto, idCategoria);
		resultado.setLstOperacionesForm(lstOperacionesForm);
		resultado.setBalance(balance);
		
		return resultado;
	}
	
	private void setSaldosToLstOperacionesForm(List<OperacionForm>lstOperacionesForm, Integer idProducto, Integer idCategoria){
		log.info("Inicio");
		BigDecimal saldo=new BigDecimal("0");
		if (idProducto!=null && idCategoria==null){
			BigDecimal saldoInicial=productoDao.findById(idProducto).getImporte();
			saldo=saldo.add(saldoInicial!=null?saldoInicial:new BigDecimal(0));
			
			//Obtengo todos los productos
			List<Operacion>lstOperacionesAll=operacionDao.findAll();
			
			for (Operacion op:lstOperacionesAll){
				
				//Sumatorio para el saldo para cada operacion
				saldo=saldo.add(op.getImporte());
				
				for (OperacionForm opForm:lstOperacionesForm){
					if (opForm.getId().compareTo(op.getIdOperacion())==0){
						opForm.setSaldo(saldo);
					}
				}
			}
							
		}
		log.info("fin");
	}
	
	
	@Override
	public List<OperacionForm> getLstOperacionesFormXLS(InputStream file, Integer idProducto) {
		
		List<List<HSSFCell>> cellDataList=new ArrayList<List<HSSFCell>>();
		
		try {
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			cellDataList=getRows(workbook);
			
		} catch (IOException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error reading file" + e, null));
		}
		
		
		/**
		* Call the printToConsole method to print the cell data in the
		* console.
		*/
		//printToConsole(cellDataList);
		
		List<OperacionForm> lstOperacionesForm=loadLstOperacionesForm(cellDataList, idProducto);
		
		return lstOperacionesForm;
    }	

	
	@Override
	@Transactional
	public void saveOperaciones(List<OperacionForm> lstOperacionesForm,	Integer idProducto, String accion) {

		for (OperacionForm opForm: lstOperacionesForm){
			//Si desde la importacion notas esta vacio inserto literal
			if (accion.equalsIgnoreCase("IMPORT")){
				//Elimino el id que le he dado con la posicion en la tabla porque el componente primefaces lo necesita
				opForm.setId(null);
				
				String notas=MyUtils.getStringFromBundle("operacion.form.importar.notas.text.default");
				
				if (opForm.getNotas()==null || opForm.getNotas().isEmpty())
					opForm.setNotas(notas);
				else
					opForm.getNotas().concat(" ."+notas);
			}
			opForm.setIdProducto(idProducto);	
			this.saveOperacion(opForm);
		}	
	}

	
	@Override
	@Transactional
	public void saveOperacion(OperacionForm opForm) {
		
		Operacion op=new Operacion();
		
		op.setIdOperacion(opForm.getId());	
		op.setFecha(opForm.getFecha());
		
		//Si el idProducto es null (por que no se ha seleccionado del combo) el producto se recoge desde la operacion
		if (opForm.getIdProducto()==null)
			op.setProducto(operacionDao.getProductoByIdOperacion(opForm.getId()));
		else
			op.setProducto(productoDao.findById(opForm.getIdProducto()));
			
		op.setNotas(opForm.getNotas());
		op.setConcepto(opForm.getConcepto());
		op.setImporte(opForm.getImporte());
		
		//GESTION CATEGORIA
		this.gestionarCategoria(opForm, op);
		
		//En funcion del id guardo o modifico
		if (opForm.getId()!=null){
			operacionDao.update(op);
			log.info("operacion "+op.getIdOperacion()+" guardada con categoria "+opForm.getIdCategoria());
		}
		else
			operacionDao.create(op);
		
	}
	
	/**
	 * Gestiona el guardado de la categoria en caso necesario
	 * @param opForm
	 * @param op
	 */
	private void gestionarCategoria(OperacionForm opForm, Operacion op){
	
		//MODIFICACION DE OPERACION
		if (opForm.getId()!=null){
			//Obtengo el usuario logado
			User userLogado = userService.getUserById(((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser());

			//Obtengo la operacion a modificar con todas las categorizaciones
			Operacion opAntAllCat=operacionDao.findById(opForm.getId());
			
			//Obtengo la categorizacion anterior del usuario
			Categoria catAntUser=categoriaDao.findCategorizacionUsuarioByIdOperacion(opForm.getId(), userLogado.getIdUser());

			
			//Si antes no tenia y ahora si tiene
			if (catAntUser==null){
				if (opForm.getIdCategoria()!=null)
					crearCategorizacion(opForm.getIdCategoria(), op);
			}
			//Si antes tenia categoria
			else{
				//Si ahora no tiene categoria
				if (opForm.getIdCategoria()==null)
					borrarCategorizacionAnterior(catAntUser, opAntAllCat);				
				//Si ahora tiene una categoria diferente que antes
				else if(opForm.getIdCategoria().compareTo(catAntUser.getIdCategoria())!=0){
					borrarCategorizacionAnterior(catAntUser, opAntAllCat);
					crearCategorizacion(opForm.getIdCategoria(), op);
				}
			}
		}
		//CREAR NUEVA OPERACION
		else{
			//Si el usuario ha seleccionado una categoria para la operacion 
			if (opForm.getIdCategoria()!=null)
				crearCategorizacion(opForm.getIdCategoria(), op);
		}
	}
	
	@Override
	@Transactional
	public void deleteOperaciones(List<OperacionForm> lstOperacionesForm) {

		for (OperacionForm opForm: lstOperacionesForm){
			Operacion op=operacionDao.findById(opForm.getId());
			this.deleteOperacion(op);
		}
	}
	
	/**
	 * Borra todas las categorias de la operacion y la operacion 
	 * @param op
	 */
	private void deleteOperacion(Operacion op){
		
		for(Categoria cat:op.getLstCategorias())
			cat.getLstOperaciones().remove(op);
		
		operacionDao.delete(op.getIdOperacion());
	}
	
	/**
	 * 
	 * @param catAntUser Categorizacion del usuario anterior a la modificacion de la operacion. 
	 * @param opAntAllCat Operacion anterior a la modificacion.
	 */
	private void borrarCategorizacionAnterior(Categoria catAntUser, Operacion opAntAllCat){
		catAntUser.getLstOperaciones().remove(opAntAllCat);
		opAntAllCat.getLstCategorias().remove(catAntUser);
		operacionDao.update(opAntAllCat);
	}
	/**
	 * 
	 * @param idCategoria Id de la categoria seleccionada desde el formulario
	 * @param op Nueva operacion creada. 
	 */
	private void crearCategorizacion(Integer idCategoria, Operacion op){
		Categoria newCategorizacion=categoriaDao.findById(idCategoria);
		newCategorizacion.getLstOperaciones().add(op);
		op.setLstCategorias(new ArrayList<Categoria>());
		op.getLstCategorias().add(newCategorizacion);	
	}
	
	/**
	 * Itera las filas y sus celdas para obtener los datos de la hoja de calculo.
	 * @param workbook
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<List<HSSFCell>> getRows(HSSFWorkbook workbook) {
		/**
		 * Create a new instance for cellDataList
		 */
		List cellDataList = new ArrayList();

		/*
		 * Create a new instance for HSSFWorkBook Class
		 */
		HSSFSheet hssfSheet = workbook.getSheetAt(0);
		/**
		 * Iterate the rows and cells of the spreadsheet to get all the datas.
		 */
		Iterator rowIterator = hssfSheet.rowIterator();
		while (rowIterator.hasNext()) {
			HSSFRow hssfRow = (HSSFRow) rowIterator.next();
			Iterator iterator = hssfRow.cellIterator();
			List cellTempList = new ArrayList();
			while (iterator.hasNext()) {
				HSSFCell hssfCell = (HSSFCell) iterator.next();
				cellTempList.add(hssfCell);
			}
			cellDataList.add(cellTempList);
		}
		return cellDataList;
	}
	
	/**
	 * Cada banco tiene su formato de archivo excel, para saber la posicion en la que se encuentra el dato,
	 * indicamos el id del banco.
	 * @param cellDataList
	 * @return
	 */
	@Transactional
	private List<OperacionForm> loadLstOperacionesForm(List<List<HSSFCell>> cellDataList, Integer idProducto){
		List<OperacionForm> lstOperacionesForm= new ArrayList<OperacionForm>();
		/*------------- Kutxabank --------------
		parametro[0] = fecha 		parametro[1] = concepto	
	 	parametro[2] = fecha valor 	parametro[3] = importe	
	 	parametro[4] = saldo
		 */
		
		//Obtengo el banco para ver en que posiciones debe de obtener los datos de la hoja de calculo
		Banco banco=productoDao.findWithBancoById(idProducto).getBanco();
		
		
		for (int i = banco.getFilaInicio(); i < cellDataList.size(); i++){
			List<HSSFCell> cellTempList = (List<HSSFCell>) cellDataList.get(i);
			OperacionForm operacion=new OperacionForm();
			Boolean pintarlinea=true;
			for (int j = 0; j < cellTempList.size(); j++){
				HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
				String stringCellValue = hssfCell.toString();
				if(stringCellValue.isEmpty()){
					pintarlinea=false;
					break;
				}
			 
				else{
					//Fecha
					if (j==banco.getColumnaFecha()){
						operacion.setFecha(FechaUtil.formatearADate(stringCellValue));
						log.info("Fecha: "+stringCellValue);
					}
					//Concepto
					else if (j==banco.getColumnaConcepto()){
						operacion.setConcepto(stringCellValue);
						log.info("Concepto: "+stringCellValue);
					}
					//Importe
					else if (j==banco.getColumnaImporte()){
						operacion.setImporte(new BigDecimal(stringCellValue));
						log.info("Importe: "+stringCellValue);
					}
				}
			}
			if (pintarlinea)
				lstOperacionesForm.add(operacion);
		}
		
		return lstOperacionesForm;
	}

	/**
	* This method is used to print the cell data to the console.
	* @param cellDataList - List of the data's in the spreadsheet.
	*/
	@SuppressWarnings({ "rawtypes", "unused" })
	private void printToConsole(List cellDataList) {
		for (int i = 0; i < cellDataList.size(); i++) {
			List cellTempList = (List) cellDataList.get(i);
			for (int j = 0; j < cellTempList.size(); j++) {
				HSSFCell hssfCell = (HSSFCell) cellTempList.get(j);
				String stringCellValue = hssfCell.toString();
				System.out.print(stringCellValue + "\t");
			}
			System.out.println();
		}
	}
}
