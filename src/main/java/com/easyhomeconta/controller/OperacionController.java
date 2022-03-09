/**
 * 
 */
package com.easyhomeconta.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.context.SecurityContextHolder;

import com.easyhomeconta.forms.OperacionForm;
import com.easyhomeconta.model.User;
import com.easyhomeconta.service.CategoriaService;
import com.easyhomeconta.service.OperacionService;
import com.easyhomeconta.service.ProductoService;
import com.easyhomeconta.service.ResultadoConsultaForm;
import com.easyhomeconta.service.UserService;
import com.easyhomeconta.utils.Constantes;
import com.easyhomeconta.utils.FechaUtil;
import com.easyhomeconta.utils.MyUtils;
import com.sun.faces.context.flash.ELFlash;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * @author Alberto
 *
 */
@SuppressWarnings("serial")
@Named(value = "operacionBean")
@Scope("view")
public class OperacionController implements Serializable {

	private final Logger log = Logger.getLogger(OperacionController.class);
	
	@Inject
	OperacionService operacionService;

	@Inject
	UserService userService;
	
	@Inject
	CategoriaService categoriaService;

	@Inject
	ProductoService productoService;

	private ResultadoConsultaForm resultadoConsulta;

	private Date fechaInicio;
	private Date fechaFin;
	private Integer idProducto, idProductoReorganizar;
	private int nRangodias;
	private String busqueda;
	private Integer idCategoria, idCategoriaReorganizar;

	private UploadedFile archivo;
	private List<SelectItem> lstProductos;
	private List<SelectItem> lstCategorias;
	private List<OperacionForm> lstOperacionesForm;

	private OperacionForm operacionForm = new OperacionForm();

	private List<OperacionForm> selectedOperacionesForm;

	/**
	 * Al entrar en una vista en la que se llama algun metodo del controllador,
	 * primero se llama al init. En esta ocasion, cargo el valor del idProducto
	 * que se ha introducido en el scope flash desde el controller CuentaBean.
	 * Lo hago asi porque con el scope vista no recoge parametros de otras vistas
	 * y no quiero usar el scope session.
	 */
	@PostConstruct
	public void init() {
		// cargo la lista de categorias que se utilizara en la vista de
		// consultas y la de importar
		setLstCategorias(operacionService.getLstCategorias(getUserLogado().getIdUser()));

		// Como el scope de este controller view, si deseo recibir el idProducto
		// desde otra vista o controller lo tendre que meter en el scope flash.
		this.idProducto = (Integer) ELFlash.getFlash().get("idProducto");

		// Capturo la url a la que se esta haciendo el request para saber a que
		// pagina se va a cargar
		String path = FacesContext.getCurrentInstance().getExternalContext().getRequestServletPath();

		// Si entro en la vista de consultas cargo la lista de operaciones por
		// defecto.
		if (path.contains("operacion-list.xhtml")) {
			this.fechaFin = new Date();
			this.fechaInicio = FechaUtil.restarDiasAFecha(fechaFin, getnRangodias());
			resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto, idCategoria,
					busqueda, getUserLogado().getIdUser());
		}
		// Inicializo operacionForm para que los campos required (fecha y
		// importe) pasen la validacion cuando se hace cualquier request.
		operacionForm = new OperacionForm(fechaFin, new BigDecimal("0"));
	}

	/**
	 * Carga la vista de consulta desde el menu
	 * 
	 * @return
	 */
	public String doListItems() {
		// Reseteo el idProducto del scope flash para que cada vez que se
		// realice la llamada desde el menu no se visualice ningún producto en
		// el combo
		ELFlash.getFlash().put("idProducto", null);
		return "operacionList";
	}

	/**
	 * Aprovechando el id del scope flash, cuando pulso el boton volver de la
	 * vista de importacion muestro las operaciones para el producto con el que
	 * se ha trabajado anteriormente.
	 * 
	 * @return
	 */
	public String doVolver() {
		ELFlash.getFlash().put("idProducto", idProducto);
		return "operacionList";
	}

	/**
	 * Caga los datos de la tabla de operaciones por medio de llamada asincrona
	 */
	public void doLoadDateTable() {
		resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto, idCategoria,
				busqueda, getUserLogado().getIdUser());
		log.info("Balance: " + resultadoConsulta.getBalance() + "€ -- Nº de registros: "
				+ resultadoConsulta.getnRegistros());
	}

	/**
	 * Elimina las operaciones de la base de datos
	 */
	public void doDeleteItems() {
		// log.info("Eliminar lista");

		operacionService.deleteOperaciones(selectedOperacionesForm);
		removeSelectedFromList(resultadoConsulta.getLstOperacionesForm(), true);
		// Cargo de nuevo los datos de resultado para obtener los calculos
		resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto, idCategoria,
				busqueda, getUserLogado().getIdUser());
	}

	/**
	 * Carga la vista del formulario de importacion
	 * 
	 * @param idProducto
	 * @return
	 */
	public String doLoadImportForm() {
		clearSessionObjects();
		return "operacionImportForm";
	}

	/**
	 * Utilizo este metodo para guardar las operaciones importadas del excel y
	 * para modificar las operaciones de base de datos.
	 * 
	 * @param accion.
	 *            Posibles valores {IMPORT, UPDATE}
	 */
	public void doSaveItems(String accion) {

		// Si la lista de operaciones seleccionadas no es nula ni esta vacia los
		// guardo en bd
		if (selectedOperacionesForm != null && !selectedOperacionesForm.isEmpty()) {
			log.info("Entro en guardar. idProducto: " + idProducto + " , accion: " + accion);

			// Validacion de idProducto
			if (idProducto == null && new Integer("0").compareTo(idProducto) > 0 && accion.equalsIgnoreCase("IMPORT"))
				MyUtils.addErrorMessage(MyUtils.getStringFromBundle("error"),
						MyUtils.getStringFromBundle("operacion.form.importar.guardar.error.detail"));
			else {
				operacionService.saveOperaciones(selectedOperacionesForm, idProducto, accion);
				MyUtils.addInfoMessage(MyUtils.getStringFromBundle("success"), selectedOperacionesForm.size() + " "
						+ MyUtils.getStringFromBundle("operacion.form.importar.guardar.detail"));
				// Si viene de un proceso de importacion elimino la lista
				if (accion.equalsIgnoreCase("IMPORT"))
					removeSelectedFromList(lstOperacionesForm, false);
				else
					// Recargo los calculos y la tabla
					resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto,
							idCategoria, busqueda, getUserLogado().getIdUser());
			}
		}
	}

	/**
	 * Crea nueva operacion
	 */
	public void doNewItem() {
		log.info("doNew");
		// Guardo la nueva operacion
		operacionService.saveOperacion(operacionForm);
		// Recargo los calculos y la tabla
		resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto, idCategoria,
				busqueda, getUserLogado().getIdUser());
		// Reseteo operacionForm para que los campos required (fecha y importe)
		// pasen la validacion cuando se hace cualquier request.
		operacionForm = new OperacionForm(fechaFin, new BigDecimal("0"));

		MyUtils.addInfoMessage(MyUtils.getStringFromBundle("success"),
				MyUtils.getStringFromBundle("operacion.form.guardar.detail"));
	}

	/**
	 * Eliminar la seleccion de la tabla
	 */
	public void doDeleteSelection() {
		// Si la lista de operaciones seleccionadas no es nula ni esta vacia los
		// borro de la tabla
		if (selectedOperacionesForm != null && !selectedOperacionesForm.isEmpty())
			removeSelectedFromList(lstOperacionesForm, true);
	}

	/**
	 * Reorganiza las operaciones con la categoria y el producto seleccionado en
	 * el popup
	 */
	public void doReorganizarSelection() {
		log.info("doReorganizar");
		// Si la lista de operaciones seleccionadas no es nula ni esta modifico
		// las operaciones
		if (selectedOperacionesForm != null && !selectedOperacionesForm.isEmpty()) {
			// Guardo reorganizacion
			operacionService.reorganizarOperaciones(selectedOperacionesForm, idCategoriaReorganizar,
					idProductoReorganizar);
			// Recargo los calculos y la tabla
			resultadoConsulta = operacionService.getLstOperacionesForm(fechaInicio, fechaFin, idProducto, idCategoria,
					busqueda, getUserLogado().getIdUser());
			// Reseteo formulario
			idCategoriaReorganizar = null;
			idProductoReorganizar = null;
			MyUtils.addInfoMessage(MyUtils.getStringFromBundle("success"), selectedOperacionesForm.size() + " "
					+ MyUtils.getStringFromBundle("operacion.form.importar.guardar.detail"));
		}
	}

	/**
	 * Elimina de la lista pasada como parametro, los objecto seleccionados en
	 * la tabla (tanto en la tabla importacion como en la de consultas)
	 * 
	 * @param listado
	 * @param message
	 *            true/false muestra mensaje con el numero de elementos
	 *            eliminados de las lista.
	 */
	private void removeSelectedFromList(List<OperacionForm> listado, Boolean message) {
		if (message)
			MyUtils.addInfoMessage(MyUtils.getStringFromBundle("success"), selectedOperacionesForm.size() + " "
					+ MyUtils.getStringFromBundle("operacion.form.importar.eliminar.detail"));
		// Elimino de la tabla los elementos seleccionados
		for (OperacionForm o : selectedOperacionesForm)
			listado.remove(o);

	}

	private void clearSessionObjects() {
		if (lstOperacionesForm != null)
			lstOperacionesForm.clear();
		if (selectedOperacionesForm != null)
			selectedOperacionesForm.clear();
		this.idProducto = null;
	}

	/**
	 * @return the idProducto
	 */
	public Integer getIdProducto() {
		return idProducto;
	}

	/**
	 * @param idProducto
	 *            the idProducto to set
	 */
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	/**
	 * Metodo que carga la tabla de operaciones a importar con los datos del
	 * excel seleccionado
	 * 
	 * @param event
	 */
	public void handleFileUpload(FileUploadEvent event) {

		if (!PhaseId.INVOKE_APPLICATION.equals(event.getPhaseId())) {
			event.setPhaseId(PhaseId.INVOKE_APPLICATION);
			event.queue();
		} else {
			log.info(idProducto);

			if (event.getFile().equals(null))
				MyUtils.addErrorMessage(MyUtils.getStringFromBundle("operacion.form.importar.cargar.error.summary"),
						MyUtils.getStringFromBundle("operacion.form.importar.cargar.error.detail"));

			try {
				lstOperacionesForm = operacionService.getLstOperacionesFormXLS(event.getFile().getInputstream(),
						idProducto);

				// Como la p:datetable necesita que los items tengan id, recorro
				// la lista añadiendole la posicion en el id
				for (int i = 0; i < lstOperacionesForm.size(); i++)
					lstOperacionesForm.get(i).setId(new Long(i + 1));

				// cargo la lista de categorias
				setLstCategorias(operacionService.getLstCategorias(getUserLogado().getIdUser()));

			} catch (Exception e) {
				MyUtils.addErrorMessage(MyUtils.getStringFromBundle("operacion.form.importar.cargar.error.summary"),
						MyUtils.getStringFromBundle("operacion.form.importar.cargar.error.detail"));
			}
		}

	}

	public void generateReport() throws ClassNotFoundException, SQLException, IOException, JRException {

		FacesContext ctx = FacesContext.getCurrentInstance();

		// Definimos cual sera nuestra fuente de datos
		JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(resultadoConsulta.getLstOperacionesForm());

		String reportPath = "/reports/prueba.jrxml";
		InputStream jasperTemplate = ctx.getExternalContext().getResourceAsStream(reportPath);
		// Compilamos el informe jrxml
		JasperReport jasperReport = JasperCompileManager.compileReport(jasperTemplate);

		String producto = idProducto != null ? productoService.getProductoById(idProducto).getNombre() : "TODOS";
		String categoria = "";
		if (idCategoria == null)
			categoria = "TODAS";
		else if (idCategoria.compareTo(new Integer(0)) == 0)
			categoria = "NINGUNA";
		else
			producto = categoriaService.getCategoriaById(idCategoria).getNombre();

		Map parameters = new HashMap();

		parameters.put("titulo", "Cuentas - Operaciones");
		parameters.put("tituloTabla", "Lista de operaciones");
		parameters.put("fechaInicio", fechaInicio);
		parameters.put("fechaFin", fechaFin);
		parameters.put("categoria", categoria);
		parameters.put("producto", producto);

		// Rellenamos el informe con la conexion creada y sus parametros
		// establecidos
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, ds);

		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
				.getResponse();

		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachment; filename=\"report.pdf\"");

		ServletOutputStream outputStream = response.getOutputStream();
		// Exportamos el informe a formato PDF
		JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

		outputStream.flush();
		outputStream.close();
		ctx.renderResponse();
		ctx.responseComplete();

	}

	/**
	 * @return the lstProductos
	 */
	public List<SelectItem> getLstProductos() {
		lstProductos = operacionService.getLstProductosOperables(getUserLogado().getIdUser());
		return lstProductos;
	}

	/**
	 * @param lstProductos
	 *            the lstProductos to set
	 */
	public void setLstProductos(List<SelectItem> lstProductos) {
		this.lstProductos = lstProductos;
	}

	/**
	 * Cargo al usuario logado existente en el Contexto de spring security para
	 * obtener su id y referencias
	 * 
	 * @return
	 */
	public User getUserLogado() {
		User userLogado = userService.getUserById(
				((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getIdUser());
		return userLogado;
	}

	/**
	 * @return the archivo
	 */
	public UploadedFile getArchivo() {
		return archivo;
	}

	/**
	 * @param archivo
	 *            the archivo to set
	 */
	public void setArchivo(UploadedFile archivo) {
		this.archivo = archivo;
	}

	public List<OperacionForm> getLstOperacionesForm() {
		return lstOperacionesForm;
	}

	public ResultadoConsultaForm getResultadoConsulta() {
		return resultadoConsulta;
	}

	public void setResultadoConsulta(ResultadoConsultaForm resultadoConsulta) {
		this.resultadoConsulta = resultadoConsulta;
	}

	public void setLstOperacionesForm(List<OperacionForm> lstOperacionesForm) {
		this.lstOperacionesForm = lstOperacionesForm;
	}

	/**
	 * @return the lstCategorias
	 */
	public List<SelectItem> getLstCategorias() {
		return lstCategorias;
	}

	/**
	 * @param lstCategorias
	 *            the lstCategorias to set
	 */
	public void setLstCategorias(List<SelectItem> lstCategorias) {
		this.lstCategorias = lstCategorias;
	}

	/**
	 * @return the selectedOperacionesForm
	 */
	public List<OperacionForm> getSelectedOperacionesForm() {
		return selectedOperacionesForm;
	}

	/**
	 * @param selectedOperacionesForm
	 *            the selectedOperacionesForm to set
	 */
	public void setSelectedOperacionesForm(List<OperacionForm> selectedOperacionesForm) {
		this.selectedOperacionesForm = selectedOperacionesForm;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	/**
	 * Utilizo esta prodiedad para recoger el numero de dias por defecto a
	 * restar a la fecha actual para establecer el rango de fechas para la
	 * busqueda de operaciones.
	 * 
	 * @return the nRangodias
	 */
	public int getnRangodias() {
		nRangodias = Constantes.RANGODIASDEFAULT;
		return nRangodias;
	}

	/**
	 * @param nRangodias
	 *            the nRangodias to set
	 */
	public void setnRangodias(int nRangodias) {
		this.nRangodias = nRangodias;
	}

	/**
	 * @return the idCategoria
	 */
	public Integer getIdCategoria() {
		return idCategoria;
	}

	/**
	 * @param idCategoria
	 *            the idCategoria to set
	 */
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * @return the operacionForm
	 */
	public OperacionForm getOperacionForm() {
		return operacionForm;
	}

	/**
	 * @param operacionForm
	 *            the operacionForm to set
	 */
	public void setOperacionForm(OperacionForm operacionForm) {
		this.operacionForm = operacionForm;
	}

	public Integer getIdProductoReorganizar() {
		return idProductoReorganizar;
	}

	public void setIdProductoReorganizar(Integer idProductoReorganizar) {
		this.idProductoReorganizar = idProductoReorganizar;
	}

	public Integer getIdCategoriaReorganizar() {
		return idCategoriaReorganizar;
	}

	public void setIdCategoriaReorganizar(Integer idCategoriaReorganizar) {
		this.idCategoriaReorganizar = idCategoriaReorganizar;
	}

}
