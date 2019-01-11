package com.tresg.ventas.jsf;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.xml.sax.SAXException;

import com.google.zxing.WriterException;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.util.bean.AtributoBean;
import com.tresg.util.bean.GestionaBean;
import com.tresg.util.bean.ListaConsultaBean;
import com.tresg.util.bean.MensajeBean;
import com.tresg.util.correo.Mensajeria;
import com.tresg.util.formato.Formateo;
import com.tresg.util.formato.MontoEnLetras;
import com.tresg.util.impresion.Impresora;
import com.tresg.util.optional.ValoresNulos;
import com.tresg.util.sunat.Sunat;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

import net.sf.jasperreports.engine.JRException;

@ManagedBean(name = "ventaActualizaBean")
@SessionScoped
public class ActualizaVentaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// para limpiar el filtro de una datatable
	DataTable tbVenta;

	private List<VentaJPA> ventas;
	private List<DetalleVentaJPA> detalles;

	// atributos para el rowSelect
	private ClienteJPA clienteSeleccionado;
	private ProductoJPA productoSeleccionado;

	// Para anadir elementos a la datatable
	private List<DetalleVentaJPA> temporales;

	@ManagedProperty(value = "#{usuario.sesionCodigoUsuario}")
	private int usuario;

	AtributoBean atributoUtil = new AtributoBean();
	ListaConsultaBean listaUtil = new ListaConsultaBean();
	GestionaBean gestionUtil = new GestionaBean();
	MensajeBean mensajeUtil = new MensajeBean();
	Sunat sunatUtil = new Sunat();
	Mensajeria correoUtil = new Mensajeria();
	Formateo formateo = new Formateo();
	ValoresNulos optionalUtil = new ValoresNulos();

	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public ActualizaVentaBean() {
		temporales = new ArrayList<>();
	}

	public void listarVentaXFecha() {
		ventas = listaUtil.listarVentaPorFecha(atributoUtil.getFecha());
	}

	public void listarVentaRangoFecha() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (atributoUtil.getFechaFin().before(atributoUtil.getFechaIni())) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		}
		ventas = listaUtil.listarVentaPorRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
	}

	public void listarVentaXCliente() {
		ventas = listaUtil.listarVentaPorCliente(atributoUtil.getCliente().getNombre());
	}

	public void listarVentaXNumero() {
		ventas = listaUtil.listarVentaPorNumero(String.valueOf(atributoUtil.getNumeroComprobante()));
	}

	public void mostrarDetalleVenta(ActionEvent e) {
		int numero = (int) e.getComponent().getAttributes().get("numeroDetalle");
		detalles = listaUtil.mostrarDetalleVenta(numero);
	}

	public void cargarVenta(ActionEvent e) {

		int numero = (int) e.getComponent().getAttributes().get("numeroEdicion");

		VentaJPA objVenta = sVenta.obtieneVenta(numero);
		atributoUtil.setNumeroComprobante(objVenta.getNumComprobante() % 10000000);
		atributoUtil.setNumeroSerie(objVenta.getSerie());
		atributoUtil.getCliente().setCodCliente(objVenta.getCliente().getCodCliente());
		atributoUtil.getCliente().setDireccion(objVenta.getCliente().getDireccion());
		atributoUtil.getCliente().setCodigoDocumento(objVenta.getCliente().getDocumento().getCodDocumento());
		atributoUtil.getCliente().setNroDocumento(objVenta.getCliente().getNroDocumento());
		atributoUtil.getCliente().setNombre(objVenta.getCliente().getNombre());
		atributoUtil.setCodigoComprobante(objVenta.getComprobante().getCodComprobante());
		atributoUtil.setCodigoPago(objVenta.getPago().getCodPago());

		if (objVenta.getGuiaRemision() == null) {
			atributoUtil.setGuiaNumero(0);
		} else {
			atributoUtil.setGuiaNumero(objVenta.getGuiaRemision().getNumGuia());
			atributoUtil.setGuiaSerie("T001");
			atributoUtil.setGuiaVenta(true);
		}

		atributoUtil.setCodigoOperacion("0101");
		for (DetalleVentaJPA d : objVenta.getDetalles()) {
			d.setDescripcionProducto(d.getProducto().getDescripcion());
			d.setUnidadMedida(d.getProducto().getMedida().getAbreviatura());
			temporales.add(d);

			atributoUtil.setTotal(atributoUtil.getTotal().add(d.getPrecio().multiply(d.getCantidad())));
			atributoUtil.setSubtotal(atributoUtil.getTotal().divide(atributoUtil.getValor(), 2, RoundingMode.CEILING));
			atributoUtil.setIgv(atributoUtil.getTotal().subtract(atributoUtil.getSubtotal()));
		}
	}

	// Metodo donde se agrega los atributos del cliente en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarCliente(SelectEvent event) {
		clienteSeleccionado = (ClienteJPA) event.getObject();
		atributoUtil.getCliente().setCodCliente(clienteSeleccionado.getCodCliente());
		atributoUtil.getCliente().setNombre(clienteSeleccionado.getNombre());
		atributoUtil.getCliente().setDireccion(clienteSeleccionado.getDireccion());
		atributoUtil.getCliente().setNroDocumento(clienteSeleccionado.getNroDocumento());
	}

	public void listarCliente() {
		atributoUtil.setClientes(gestionUtil.listarCliente(atributoUtil.getCliente().getNombre()));
	}

	// Metodo donde se agrega los atributos del producto en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarProducto(SelectEvent event) {
		productoSeleccionado = (ProductoJPA) event.getObject();
		atributoUtil.setCodigoProducto(productoSeleccionado.getCodProducto());
		atributoUtil.setDescripcionProducto(
				productoSeleccionado.getDescripcion().concat(" ").concat(productoSeleccionado.getDescripcionTipo()));
		atributoUtil.setPrecio(productoSeleccionado.getPrecioVenta());

		atributoUtil.getProductos().clear();

	}

	public void listarProducto() {
		atributoUtil.setProductos(gestionUtil.listarProducto(atributoUtil.getDescripcionProducto()));
	}

	// otra posibilidad se cargue codigo del producto
	public void cargarProductoCodigo() {

		FacesContext context = FacesContext.getCurrentInstance();
		ProductoJPA objProducto = sProducto.buscaProductoPorCodigo(atributoUtil.getCodigoProducto());
		if (objProducto == null) {
			context.addMessage("mensajeCodigo",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe producto con dicho codigo", null));
		} else {
			atributoUtil.setCodigoProducto(objProducto.getCodProducto());
			atributoUtil.setCodigoTipo(objProducto.getTipo().getCodTipo());
			atributoUtil.setDescripcionProducto(
					objProducto.getDescripcion().concat(" ").concat(objProducto.getTipo().getDescripcion()));
			atributoUtil.setUnidad(objProducto.getMedida().getAbreviatura());
			atributoUtil.setPrecio(objProducto.getPrecioVenta());
		}
	}

	// Metodo para añadir elementos al datatable de detalle venta
	public void agregarProductoVenta() {

		FacesContext context = FacesContext.getCurrentInstance();
		String mensajeAgregar = mensajeUtil.mostrarMensajeAgregarVenta(atributoUtil, temporales);

		if (!"".equals(mensajeAgregar)) {
			context.addMessage(mensajeAgregar, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensajeUtil.mostrarMensajeError(mensajeAgregar, atributoUtil), null));
		} else {
			temporales.add(gestionUtil.retornarProductoVenta(atributoUtil));
			String mensajeRepetido = gestionUtil.iterarListaVenta(temporales, atributoUtil);
			if (!"".equals(mensajeRepetido)) {
				context.addMessage("mensajeProductoRepetido",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeRepetido, null));
			}
			gestionUtil.limpiarProductoVenta(atributoUtil);
		}

	}

	public void quitarListaProducto(ActionEvent e) {
		int codigo = (int) e.getComponent().getAttributes().get("codigo");

		gestionUtil.quitarListaProductoModificado(codigo, temporales, atributoUtil);

	}

	public void editarProducto(ActionEvent e) {
		int codigoProducto = (int) e.getComponent().getAttributes().get("codigo");

		gestionUtil.editarProductoVenta(codigoProducto, temporales, atributoUtil);

	}

	public void cargarGuia() {

		if (atributoUtil.isGuiaVenta()) {
			atributoUtil.setGuiaSerie("T001");
		} else {
			atributoUtil.setGuiaSerie("");
			atributoUtil.setGuiaNumero(0);
		}
	}

	@SuppressWarnings("deprecation")
	public void actualizarVenta() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		String mensajeVenta = mensajeUtil.mostrarMensajeGrabarVenta(atributoUtil, temporales);

		if (!"".equals(mensajeVenta)) {
			context.addMessage(mensajeVenta, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensajeUtil.mostrarMensajeError(mensajeVenta, atributoUtil), null));
		} else {
			// factura o boleta electronica
			if (atributoUtil.getCodigoComprobante() != 2) {
				// generar el archivo plano para facturador sunat
				sunatUtil.generarCabeceraSunat(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobante(),
						atributoUtil.getNumeroSerie(), atributoUtil.getNumeroComprobante(), cadenaSunatCabecera(),
						cadenaSunatDetalle(), cadenaSunatTributo(), cadenaSunatLeyenda(),
						cadenaSunatDocumentoRelacionado());
				context.addMessage("mensajeActualizaVenta", new FacesMessage(FacesMessage.SEVERITY_INFO,
						sVenta.actualizaVenta(gestionUtil.retornarVenta(atributoUtil, temporales, usuario)), null));

				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
			} else {
				context.addMessage("mensajeActualizaVenta", new FacesMessage(FacesMessage.SEVERITY_INFO,
						sVenta.actualizaVenta(gestionUtil.retornarVenta(atributoUtil, temporales, usuario)), null));

				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");
			}

		}

	}

	public String retornar() {
		temporales.clear();
		atributoUtil.setTotal(new BigDecimal(0));
		atributoUtil.setSubtotal(new BigDecimal(0));
		atributoUtil.setIgv(new BigDecimal(0));
		return "consultaVentaModificada.xhtml";
	}

	// metodo para anular venta
	public void cargarNumeroAnulacion(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("numeroCargar");

		VentaJPA objVenta = sVenta.obtieneVenta(codigo);

		atributoUtil
				.setNumeroNota(gestionUtil.retornaNumeroComprobante(atributoUtil.getCodigoComprobante()) % 10000000);
		atributoUtil.setFacturaAnulada(objVenta.getComprobante().getCodComprobante());
		atributoUtil.setSerieAnulada(objVenta.getSerie());
		atributoUtil.setNroFacturaAnulada(codigo);

		atributoUtil.getCliente().setCodCliente(objVenta.getCliente().getCodCliente());
		atributoUtil.getCliente().setDireccion(objVenta.getCliente().getDireccion());
		atributoUtil.getCliente().setCodigoDocumento(objVenta.getCliente().getDocumento().getCodDocumento());
		atributoUtil.getCliente().setNroDocumento(objVenta.getCliente().getNroDocumento());
		atributoUtil.getCliente().setNombre(objVenta.getCliente().getNombre());
		
		atributoUtil.setCodigoPago(objVenta.getPago().getCodPago());

		atributoUtil.setCodigoOperacion("0101");

		if (objVenta.getGuiaRemision() == null) {
			atributoUtil.setGuiaNumero(0);
		} else {
			atributoUtil.setGuiaNumero(objVenta.getGuiaRemision().getNumGuia());
			atributoUtil.setGuiaSerie("T001");
			atributoUtil.setGuiaVenta(true);
		}

		for (DetalleVentaJPA d : objVenta.getDetalles()) {
			d.setDescripcionProducto(d.getProducto().getDescripcion());
			d.setUnidadMedida(d.getProducto().getMedida().getAbreviatura());
			temporales.add(d);

			atributoUtil.setTotal(atributoUtil.getTotal().add(d.getPrecio().multiply(d.getCantidad())));
			atributoUtil.setSubtotal(atributoUtil.getTotal().divide(atributoUtil.getValor(), 2, RoundingMode.CEILING));
			atributoUtil.setIgv(atributoUtil.getTotal().subtract(atributoUtil.getSubtotal()));
		}
	}

	public void anularVenta() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		// generar el archivo plano para facturador sunat
		if ("".equals(atributoUtil.getCodigoNota())) {
			context.addMessage("mensajeMotivoNota",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un motivo de nota", null));
		} else if("".equals(atributoUtil.getObservacion()) ){
			context.addMessage("mensajeMotivo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese un motivo de la anulacion", null));
		}else {
			// generar el archivo plano para facturador sunat
			sunatUtil.generarCabeceraSunat(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobanteNota(),
					"F001", atributoUtil.getNumeroNota(), cadenaSunatNota(),
					cadenaSunatDetalle(), cadenaSunatTributo(), cadenaSunatLeyenda(),
					cadenaSunatDocumentoRelacionado());
			
			sVenta.registraVenta(gestionUtil.retornarVentaAnulada(atributoUtil, usuario));
			context.addMessage("mensajeAnulacion", new FacesMessage(FacesMessage.SEVERITY_INFO,
					sVenta.anulaVenta(atributoUtil.getNroFacturaAnulada()), null));
		}

	}

	public void imprimirFactura(ActionEvent e) throws IOException, ClassNotFoundException, JRException, SQLException,
			ParserConfigurationException, SAXException, WriterException {

		int comprobante = (int) e.getComponent().getAttributes().get("comprobante");
		int numero = (int) e.getComponent().getAttributes().get("numeroFactura");
		BigDecimal monto = (BigDecimal) e.getComponent().getAttributes().get("monto");
		String serie = (String) e.getComponent().getAttributes().get("serie");
		String documento = (String) e.getComponent().getAttributes().get("documento");
		String numeroDocumento = (String) e.getComponent().getAttributes().get("numeroDocumento");

		String facturacionPDF = serie.concat("-").concat(formateo.obtenerFormatoNumeroComprobante(numero % 10000000));
		BigDecimal valor = new BigDecimal("1.18");

		File xml = new File(Sunat.RUTA_FIRMA
				.concat(sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000))
				.concat(".xml"));

		sunatUtil.leerNodosXml(xml);
		// generar el codigo de barra sin hash y sin firma digital, estos se
		// añaden em la clase sunat
		sunatUtil.generarCodigoBarra(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000,
				monto.subtract(monto.divide(valor, 2, RoundingMode.HALF_EVEN)).toString(), monto.toString(),
				atributoUtil.getFecha().toString(), documento, numeroDocumento);
		// El monto de la venta en letras
		MontoEnLetras numeroLetra = new MontoEnLetras();
		String leyenda = numeroLetra.convertir(monto.toString(), true);
		Impresora i = Impresora.getImpresora();
		i.imprimirVenta(facturacionPDF, numero, leyenda,
				Sunat.RUTA_IMAGEN.concat(
						sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000))
						.concat(".png"),
				sunatUtil.getDigestTexto());

	}

	String cadenaSunatCabecera() {

		return atributoUtil.getCodigoOperacion().concat("|").concat(formateo.obtenerFecha(atributoUtil.getFecha()))
				.concat("|").concat(formateo.obtenerHora()).concat("|")
				.concat(formateo.obtenerFecha(atributoUtil.getFechaVence())).concat("|")
				.concat(AtributoBean.CODIGO_DOMICILIO_FISCAL).concat("|")
				.concat(atributoUtil.getCliente().getCodigoDocumento()).concat("|")
				.concat(atributoUtil.getCliente().getNroDocumento()).concat("|")
				.concat(atributoUtil.getCliente().getNombre()).concat("|").concat(AtributoBean.CODIGO_MONEDA)
				.concat("|")
				// sumatoria de tributos
				.concat(atributoUtil.getIgv().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				// total valor de venta
				.concat(atributoUtil.getSubtotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				// total precio de venta
				.concat(atributoUtil.getTotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				.concat(AtributoBean.TOTAL_DESCUENTOS).concat("|").concat(AtributoBean.SUMATORIA_OTROS_CARGOS)
				.concat("|").concat(AtributoBean.TOTAL_ANTICIPOS).concat("|")
				// importe total de venta
				.concat(atributoUtil.getTotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				.concat(AtributoBean.VERSION_UBL).concat("|").concat(AtributoBean.CUSTOMIZACION_DOCUMENTO);
	}

	String cadenaSunatNota() {

		return atributoUtil.getCodigoOperacion().concat("|")
				.concat(formateo.obtenerFecha(atributoUtil.getFecha())).concat("|")
				.concat(formateo.obtenerHora()).concat("|")
				.concat(AtributoBean.CODIGO_DOMICILIO_FISCAL).concat("|")
				.concat(atributoUtil.getCliente().getCodigoDocumento()).concat("|")
				.concat(atributoUtil.getCliente().getNroDocumento()).concat("|")
				.concat(atributoUtil.getCliente().getNombre()).concat("|")
				.concat(AtributoBean.CODIGO_MONEDA).concat("|")
				.concat(atributoUtil.getCodigoNota()).concat("|")
				.concat(atributoUtil.getObservacion()).concat("|")
				.concat(formateo.obtenerFormatoComprobante(atributoUtil.getFacturaAnulada())).concat("|")
				.concat(atributoUtil.getSerieAnulada().concat("-")
						.concat(formateo.obtenerFormatoNumeroComprobante(atributoUtil.getNroFacturaAnulada()%10000000))).concat("|")
				// sumatoria de tributos
				.concat(atributoUtil.getIgv().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				// total valor de venta
				.concat(atributoUtil.getSubtotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				// total precio de venta
				.concat(atributoUtil.getTotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				.concat(AtributoBean.TOTAL_DESCUENTOS).concat("|").concat(AtributoBean.SUMATORIA_OTROS_CARGOS)
				.concat("|").concat(AtributoBean.TOTAL_ANTICIPOS).concat("|")
				// importe total de venta
				.concat(atributoUtil.getTotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				.concat(AtributoBean.VERSION_UBL).concat("|").concat(AtributoBean.CUSTOMIZACION_DOCUMENTO);
	}

	List<String> cadenaSunatDetalle() {

		List<String> cadenas = new ArrayList<>();
		BigDecimal valorUnitario;
		BigDecimal precioUnitario;
		BigDecimal valorVenta;

		for (DetalleVentaJPA s : temporales) {

			precioUnitario = s.getPrecio().multiply(s.getCantidad()).setScale(2, RoundingMode.HALF_UP);
			valorUnitario = s.getPrecio().divide(atributoUtil.getValor(), 10, RoundingMode.HALF_DOWN);
			valorVenta = valorUnitario.multiply(s.getCantidad()).setScale(2, RoundingMode.HALF_UP);

			cadenas.add(s.getUnidadMedida().concat("|").concat(s.getCantidad().toString()).concat("|")
					.concat(String.valueOf(s.getId().getCodProducto())).concat("|")
					.concat(AtributoBean.CODIGO_PRODUCTO_SUNAT).concat("|").concat(s.getDescripcionProducto())
					.concat("|")
					// valor unitario por item (sin igv)
					.concat(valorUnitario.toString()).concat("|")
					// sumatoria de tributos por item
					.concat(precioUnitario.subtract(valorVenta).toString()).concat("|").concat(AtributoBean.CODIGO_IGV)
					.concat("|")
					// monto de igv por item
					.concat(precioUnitario.subtract(valorVenta).toString()).concat("|")
					// base imponible igv
					.concat(valorVenta.toString()).concat("|").concat(AtributoBean.NOMBRE_TRIBUTO_ITEM_IGV).concat("|")
					.concat(AtributoBean.CODIGO_TIPO_TRIBUTO_IGV).concat("|").concat(AtributoBean.AFECTACION_IGV_ITEM)
					.concat("|").concat(AtributoBean.PORCENTAJE_IGV).concat("|")

					.concat(AtributoBean.CODIGO_ISC).concat("|").concat(AtributoBean.MONTO_ISC).concat("|")
					.concat(AtributoBean.BASE_IMPONIBLE_ISC_ITEM).concat("|")
					.concat(AtributoBean.NOMBRE_TRIBUTO_ITEM_ISC).concat("|")
					.concat(AtributoBean.CODIGO_TIPO_TRIBUTO_ISC).concat("|").concat(AtributoBean.TIPO_SISTEMA_ISC)
					.concat("|").concat(AtributoBean.PORCENTAJE_ISC).concat("|")

					// otros tributos, se copia igual que el isc
					.concat(AtributoBean.CODIGO_ISC).concat("|").concat(AtributoBean.MONTO_ISC).concat("|")
					.concat(AtributoBean.BASE_IMPONIBLE_ISC_ITEM).concat("|")
					.concat(AtributoBean.NOMBRE_TRIBUTO_ITEM_ISC).concat("|")
					.concat(AtributoBean.CODIGO_TIPO_TRIBUTO_ISC).concat("|").concat(AtributoBean.PORCENTAJE_ISC)
					.concat("|")

					// precio venta unitario
					.concat(precioUnitario.toString()).concat("|")
					// valor de venta por item
					.concat(valorVenta.toString()).concat("|").concat(AtributoBean.VALOR_REFERENCIAL).concat("\r\n"));
		}
		return cadenas;

	}

	String cadenaSunatTributo() {
		return AtributoBean.CODIGO_IGV.concat("|").concat(AtributoBean.NOMBRE_TRIBUTO_ITEM_IGV).concat("|")
				.concat(AtributoBean.CODIGO_TIPO_TRIBUTO_IGV).concat("|")
				.concat(atributoUtil.getSubtotal().setScale(2, RoundingMode.HALF_UP).toString()).concat("|")
				.concat(atributoUtil.getIgv().setScale(2, RoundingMode.HALF_UP).toString());
	}

	String cadenaSunatLeyenda() {
		MontoEnLetras numeroLetra = new MontoEnLetras();
		// 1000 monto en letras
		return "1000".concat("|").concat(
				numeroLetra.convertir(atributoUtil.getTotal().setScale(2, RoundingMode.HALF_UP).toString(), true));
	}

	String cadenaSunatDocumentoRelacionado() {
		String cadena = "";
		// 1 guia
		if (atributoUtil.isGuiaVenta()) {
			cadena = "1".concat("|").concat("-").concat("|")
					.concat(formateo.obtenerFormatoComprobante(atributoUtil.getCodigoComprobante())).concat("|")
					.concat(atributoUtil.getGuiaSerie()).concat("|")
					.concat(String.valueOf(atributoUtil.getGuiaNumero())).concat("|").concat("6").concat("|")
					.concat(AtributoBean.RUC_EMISOR).concat("|").concat(atributoUtil.getTotal().toString());
		} else {
			cadena = "";
		}

		return cadena;
	}

	// metodo para enviar firma venta
	public void cargarFirma(ActionEvent e) {

		ClienteJPA objCliente = new ClienteJPA();

		int numero = (Integer) e.getComponent().getAttributes().get("numeroCargar");
		atributoUtil.setNumeroComprobante(numero);

		String cliente = (String) e.getComponent().getAttributes().get("clienteCargar");
		objCliente.setNombre(cliente);

		String destinatario = (String) e.getComponent().getAttributes().get("correoCargar");
		objCliente.setCorreo(destinatario);

		atributoUtil.setCliente(objCliente);

		int comprobante = (Integer) e.getComponent().getAttributes().get("comprobanteCargar");
		atributoUtil.setCodigoComprobante(comprobante);

		String serie = (String) e.getComponent().getAttributes().get("serieCargar");
		atributoUtil.setNumeroSerie(serie);

		Date fecha = (Date) e.getComponent().getAttributes().get("fechaCargar");
		atributoUtil.setFecha(fecha);

		BigDecimal monto = (BigDecimal) e.getComponent().getAttributes().get("montoCargar");
		atributoUtil.setTotal(monto);

	}

	public void enviarFirma() throws Exception {

		FacesContext context = FacesContext.getCurrentInstance();

		String archivo = sunatUtil.verificarExistenciaArchivo(AtributoBean.RUC_EMISOR,
				atributoUtil.getCodigoComprobante(), atributoUtil.getNumeroSerie(),
				atributoUtil.getNumeroComprobante() % 10000000);
		String facturacion = atributoUtil.getNumeroSerie().concat("-")
				.concat(formateo.obtenerFormatoNumeroComprobante(atributoUtil.getNumeroComprobante() % 10000000));

		// validar existencia del archivo, informar generacion de xml
		if (!archivo.equals("")) {

			context.addMessage("mensajeArchivo", new FacesMessage(FacesMessage.SEVERITY_ERROR, archivo, null));
		} else {

			correoUtil.envioFirma(atributoUtil.getCliente().getNombre(), facturacion,
					formateo.obtenerFecha(atributoUtil.getFecha()), atributoUtil.getTotal().toString(),
					sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobante(),
							atributoUtil.getNumeroSerie(), atributoUtil.getNumeroComprobante() % 10000000),
					atributoUtil.getCliente().getCorreo());

			context.addMessage("mensajeFirma",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se envio la firma electronica", null));
		}
	}

	public DataTable getTbVenta() {
		return tbVenta;
	}

	public void setTbVenta(DataTable tbVenta) {
		this.tbVenta = tbVenta;
	}

	public List<VentaJPA> getVentas() {
		return ventas;
	}

	public void setVentas(List<VentaJPA> ventas) {
		this.ventas = ventas;
	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
	}

	public ClienteJPA getClienteSeleccionado() {
		return clienteSeleccionado;
	}

	public void setClienteSeleccionado(ClienteJPA clienteSeleccionado) {
		this.clienteSeleccionado = clienteSeleccionado;
	}

	public ProductoJPA getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(ProductoJPA productoSeleccionado) {
		this.productoSeleccionado = productoSeleccionado;
	}

	public List<DetalleVentaJPA> getTemporales() {
		return temporales;
	}

	public void setTemporales(List<DetalleVentaJPA> temporales) {
		this.temporales = temporales;
	}

	public AtributoBean getAtributoUtil() {
		return atributoUtil;
	}

	public void setAtributoUtil(AtributoBean atributoUtil) {
		this.atributoUtil = atributoUtil;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

}
