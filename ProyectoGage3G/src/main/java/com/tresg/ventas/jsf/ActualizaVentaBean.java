package com.tresg.ventas.jsf;

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
import com.tresg.incluido.jsf.AtributoBean;
import com.tresg.incluido.jsf.ClienteBean;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
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
	ClienteBean clienteBean = new ClienteBean();
	ValoresNulos optionalUtil=new ValoresNulos();

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
		atributoUtil.getCliente().setNroDocumento(objVenta.getCliente().getNroDocumento());
		atributoUtil.getCliente().setNombre(objVenta.getCliente().getNombre());
		atributoUtil.setCodigoComprobante(objVenta.getComprobante().getCodComprobante());
		atributoUtil.setCodigoPago(objVenta.getPago().getCodPago());
		
		if(objVenta.getGuiaRemision()==null) {
			atributoUtil.setGuiaNumero(0);
		}else {
			atributoUtil.setGuiaNumero(objVenta.getGuiaRemision().getNumGuia());
			atributoUtil.setGuiaSerie("T001");
			atributoUtil.setGuiaVenta(true);
		}

		atributoUtil.setCodigoOperacion("0101");
		for (DetalleVentaJPA d : objVenta.getDetalles()) {
			d.setDescripcionProducto(d.getProducto().getDescripcion());
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
		atributoUtil.setClientes(clienteBean.getClientes());
	}

	// Metodo donde se agrega los atributos del producto en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarProducto(SelectEvent event) {
		productoSeleccionado = (ProductoJPA) event.getObject();
		atributoUtil.setCodigoProducto(productoSeleccionado.getCodProducto());
		atributoUtil.setDescripcionProducto(productoSeleccionado.getDescripcion().concat(" ")
				.concat(productoSeleccionado.getTipo().getDescripcion()));
		atributoUtil.setPrecio(productoSeleccionado.getPrecioVenta());

		atributoUtil.getProductos().clear();

	}

	public void listarProducto() {
		atributoUtil.setProductos(gestionUtil.listarProducto(atributoUtil.getDescripcionProducto()));
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
	public String actualizarVenta() {
		FacesContext context = FacesContext.getCurrentInstance();
		String mensajeVenta = mensajeUtil.mostrarMensajeGrabarVenta(atributoUtil, temporales);

		if (!"".equals(mensajeVenta)) {
			context.addMessage(mensajeVenta, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensajeUtil.mostrarMensajeError(mensajeVenta, atributoUtil), null));
		} else {

			context.addMessage("mensajeActualizaVenta", new FacesMessage(FacesMessage.SEVERITY_INFO,
					sVenta.actualizaVenta(gestionUtil.retornarVenta(atributoUtil, temporales, usuario)), null));

			RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");

		}
		return "consultaVentaModificada.xhtml";
	}

	public String cancelarVenta() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("ventaActualizaBean");
		return "consultaVentaModificada.xhtml";
	}

	public String retornar() {
		temporales.clear();
		atributoUtil.setTotal(new BigDecimal(0));
		atributoUtil.setSubtotal(new BigDecimal(0));
		atributoUtil.setIgv(new BigDecimal(0));
		return "consultaVentaModificada.xhtml";
	}

	// metodo para anular venta
	public void cargarNumeroComprobante(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("numeroCargar");
		atributoUtil.setNumeroComprobante(codigo);
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

		sunatUtil.leerNodosXml(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000);
		// generar el codigo de barra sin hash y sin firma digital, estos se
		// añaden em la clase sunat
		sunatUtil.generarCodigoBarra(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000,
				monto.subtract(monto.divide(valor, 2, RoundingMode.HALF_EVEN)).toString(), monto.toString(),
				atributoUtil.getFecha().toString(), documento, numeroDocumento);

		Impresora i = Impresora.getImpresora();
		i.imprimirVenta(facturacionPDF, numero, cadenaSunatLeyenda(monto),
				Sunat.RUTA_IMAGEN.concat(
						sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, comprobante, serie, numero % 10000000))
						.concat(".png"),
				sunatUtil.getDigestTexto());

	}

	String cadenaSunatLeyenda(BigDecimal total) {
		MontoEnLetras numeroLetra = new MontoEnLetras();
		String letra = String.valueOf(total);

		return numeroLetra.convertir(letra, true);
	}

	public void anularVenta() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("mensajeAnulacion", new FacesMessage(FacesMessage.SEVERITY_INFO,
				sVenta.anulaVenta(atributoUtil.getNumeroComprobante()), null));
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

	public void enviarFirma(ActionEvent e) throws Exception {

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

	public ListaConsultaBean getListaUtil() {
		return listaUtil;
	}

	public void setListaUtil(ListaConsultaBean listaUtil) {
		this.listaUtil = listaUtil;
	}

	public GestionaBean getGestionUtil() {
		return gestionUtil;
	}

	public void setGestionUtil(GestionaBean gestionUtil) {
		this.gestionUtil = gestionUtil;
	}

	public MensajeBean getMensajeUtil() {
		return mensajeUtil;
	}

	public void setMensajeUtil(MensajeBean mensajeUtil) {
		this.mensajeUtil = mensajeUtil;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

}
