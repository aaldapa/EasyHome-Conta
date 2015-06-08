/**
 * 
 */
package com.easyhomeconta.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.easyhomeconta.utils.FechaUtil;

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
	public void saveOperaciones(List<OperacionForm> lstOperacionesForm,	Integer idProducto) {

		for (OperacionForm opForm: lstOperacionesForm){
			Operacion op=new Operacion();
			op.setFecha(opForm.getFecha());
			op.setProducto(productoDao.findById(idProducto));
			op.setNotas("Importado desde fichero");
			op.setConcepto(opForm.getConcepto());
			op.setImporte(opForm.getImporte());
			
			//Si el usuario ha seleccionado una categoria para la operacion 
			if (opForm.getIdCategoria()!=null){
				Categoria categoria=categoriaDao.findById(opForm.getIdCategoria());
				//Inserto la operacion en la categoria
				categoria.getLstOperaciones().add(op);

				//Inserto la categoria en la operacion
				op.setLstCategorias(new ArrayList<Categoria>());
				op.getLstCategorias().add(categoria);
			}
			operacionDao.create(op);
			
		}
		
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
	private List<OperacionForm> loadLstOperacionesForm(List<List<HSSFCell>> cellDataList, Integer idProducto){
		List<OperacionForm> lstOperacionesForm= new ArrayList<OperacionForm>();
		/*------------- Kutxabank --------------
		parametro[0] = fecha 		parametro[1] = concepto	
	 	parametro[2] = fecha valor 	parametro[3] = importe	
	 	parametro[4] = saldo
		 */
		
		//Obtengo el banco para ver en que posiciones debe de obtener los datos de la hoja de calculo
		Banco banco=productoDao.findById(idProducto).getBanco();
		
		
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
