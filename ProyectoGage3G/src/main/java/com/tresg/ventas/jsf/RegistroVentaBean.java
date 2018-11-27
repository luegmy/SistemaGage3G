package com.tresg.ventas.jsf;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.xml.sax.SAXException;

import com.google.zxing.WriterException;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarClienteService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.util.bean.AtributoBean;
import com.tresg.util.bean.GestionaBean;
import com.tresg.util.bean.MensajeBean;
import com.tresg.util.formato.Formateo;
import com.tresg.util.formato.MontoEnLetras;
import com.tresg.util.impresion.Impresora;
import com.tresg.util.sunat.Sunat;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.RegistrarGuiaRemisionBusinessService;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

import net.sf.jasperreports.engine.JRException;

@ManagedBean(name = "ventaBean")
@SessionScoped
public class RegistroVentaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final String MENSAJE_REGISTRO = "mensajeRegistroVenta";
	private static final String MENSAJE_DIALOGO = "PF('dlgMensaje').show();";

	@ManagedProperty(value = "#{usuario.sesionCodigoUsuario}")
	private int usuario;

	// atributo para el rowSelect
	private ClienteJPA clienteSeleccionado;
	private ProductoJPA productoSeleccionado;

	// Para anadir elementos a la datatable
	private List<DetalleVentaJPA> temporales;
	
	private int dias;

	AtributoBean atributoUtil = new AtributoBean();
	GestionaBean gestionUtil = new GestionaBean();
	MensajeBean mensajeUtil = new MensajeBean();
	Sunat sunatUtil = new Sunat();
	Formateo talonarioUtil = new Formateo();

	// Instanciar los services
	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();
	GestionarClienteService_I sCliente = IncluidoBusinessDelegate.getGestionarClienteService();
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();
	RegistrarGuiaRemisionBusinessService sGuia = VentasBusinessDelegate.getRegistrarGuiaRemisionService();

	public RegistroVentaBean() {
		temporales = new ArrayList<>();
	}

	// Cargar la serie correspondiente al comprobante
	public void cargarSerie() {
		if (atributoUtil.getCodigoComprobante() == 1) {
			atributoUtil.setNumeroSerie("F001");
		} else if (atributoUtil.getCodigoComprobante() == 3) {
			atributoUtil.setNumeroSerie("B001");
		} else {
			atributoUtil.setNumeroSerie("N001");
		}

	}

	// Carga el numero correlativo
	public void cargarComprobante() {
		atributoUtil.setNumeroComprobante(
				gestionUtil.retornaNumeroComprobante(atributoUtil.getCodigoComprobante()) % 10000000);
	}
	
	//Cuando es credito se suma los dias a la fecha vencimiento
	
	public void obtenerFechaVencimiento() {


	}
	

	public void listarCliente() {
		atributoUtil.setClientes(gestionUtil.listarCliente(atributoUtil.getCliente().getNombre()));
	}

	// Metodo donde se agrega los atributos del cliente en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarCliente(SelectEvent event) {

		clienteSeleccionado = (ClienteJPA) event.getObject();
		atributoUtil.getCliente().setCodCliente(clienteSeleccionado.getCodCliente());
		atributoUtil.getCliente().setCodigoDocumento(clienteSeleccionado.getDocumento().getCodDocumento());
		atributoUtil.getCliente().setNombre(clienteSeleccionado.getNombre());
		atributoUtil.getCliente().setDireccion(clienteSeleccionado.getDireccion());
		atributoUtil.getCliente().setNroDocumento(clienteSeleccionado.getNroDocumento());

		atributoUtil.getClientes().clear();
	}

	// otra posibilidad se cargue ingresando el ruc del cliente
	public void cargarClienteRuc() {

		FacesContext context = FacesContext.getCurrentInstance();
		ClienteJPA objCliente = sCliente.buscaClientePorRuc(atributoUtil.getCliente().getNroDocumento());
		if (objCliente == null) {
			context.addMessage("mensajeRuc",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "No existe cliente con dicho ruc", null));
		} else {
			atributoUtil.getCliente().setCodCliente(objCliente.getCodCliente());
			atributoUtil.getCliente().setNombre(objCliente.getNombre());
			atributoUtil.getCliente().setDireccion(objCliente.getDireccion());
		}
	}

	public void listarProducto() {
		atributoUtil.setProductos(gestionUtil.listarProducto(atributoUtil.getDescripcionProducto()));
	}

	// Metodo donde se agrega los atributos del producto en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarProducto(SelectEvent event) {

		productoSeleccionado = (ProductoJPA) event.getObject();
		atributoUtil.setCodigoProducto(productoSeleccionado.getCodProducto());
		atributoUtil.setDescripcionProducto(
				productoSeleccionado.getDescripcion().concat(" ").concat(productoSeleccionado.getDescripcionTipo()));
		atributoUtil.setUnidad(productoSeleccionado.getDescripcionMedida());
		atributoUtil.setPrecio(productoSeleccionado.getPrecioVenta());

		atributoUtil.getProductos().clear();
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

		// Validacion de los componentes
		String mensajeAgregar = mensajeUtil.mostrarMensajeAgregarVenta(atributoUtil, temporales);

		if (!"".equals(mensajeAgregar)) {
			context.addMessage(mensajeAgregar, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensajeUtil.mostrarMensajeError(mensajeAgregar, atributoUtil), null));
		} else {
			temporales.add(gestionUtil.retornarProductoVenta(atributoUtil));

			// Iterar la lista para no ingresar producto duplicado
			String mensajeRepetido = gestionUtil.iterarListaVenta(temporales, atributoUtil);
			if (!"".equals(mensajeRepetido)) {
				context.addMessage("mensajeProductoRepetido",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, mensajeRepetido, null));
			}

			gestionUtil.limpiarProductoVenta(atributoUtil);
		}

	}

	// Venta adjunta una guia remision
	public void cargarGuia() {

		if (atributoUtil.isGuiaVenta()) {
			atributoUtil.setGuiaSerie("T001");
		} else {
			atributoUtil.setGuiaSerie("");
			atributoUtil.setGuiaNumero(0);
		}
	}

	@SuppressWarnings("deprecation")
	public void grabarVenta() throws IOException {

		FacesContext context = FacesContext.getCurrentInstance();

		// Validar los componentes
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

				context.addMessage(MENSAJE_REGISTRO, new FacesMessage(FacesMessage.SEVERITY_INFO,
						sVenta.registraVenta(gestionUtil.retornarVenta(atributoUtil, temporales, usuario)), null));
				RequestContext.getCurrentInstance().execute(MENSAJE_DIALOGO);
			} else {
				context.addMessage(MENSAJE_REGISTRO, new FacesMessage(FacesMessage.SEVERITY_INFO,
						sVenta.registraVenta(gestionUtil.retornarVenta(atributoUtil, temporales, usuario)), null));
				RequestContext.getCurrentInstance().execute(MENSAJE_DIALOGO);
			}
		}
	}

	String cadenaSunatCabecera() {

		return atributoUtil.getCodigoOperacion().concat("|").concat(talonarioUtil.obtenerFecha(atributoUtil.getFecha()))
				.concat("|").concat(talonarioUtil.obtenerHora()).concat("|")
				.concat(talonarioUtil.obtenerFecha(atributoUtil.getFechaVence())).concat("|")
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

	List<String> cadenaSunatDetalle() {

		List<String> cadenas = new ArrayList<>();
		BigDecimal valorUnitario;
		BigDecimal precioUnitario;
		BigDecimal valorVenta;

		for (DetalleVentaJPA s : temporales) {

			precioUnitario = s.getPrecio().multiply(s.getCantidad());
			valorUnitario = s.getPrecio().divide(atributoUtil.getValor(), 2, RoundingMode.HALF_DOWN);
			valorVenta = valorUnitario.multiply(s.getCantidad());

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
				.concat(AtributoBean.CODIGO_TIPO_TRIBUTO_IGV).concat("|").concat(atributoUtil.getSubtotal().toString())
				.concat("|").concat(atributoUtil.getIgv().toString());
	}

	String cadenaSunatLeyenda() {
		MontoEnLetras numeroLetra = new MontoEnLetras();
		String letra = String.valueOf(atributoUtil.getTotal());
		String leyenda = numeroLetra.convertir(letra, true);

		// 1000 monto en letras
		return "1000".concat("|").concat(leyenda);
	}

	String cadenaSunatDocumentoRelacionado() {
		String cadena = "";
		// 1 guia
		if (atributoUtil.isGuiaVenta()) {
			cadena = "1".concat("|").concat("-").concat("|")
					.concat(talonarioUtil.obtenerFormatoComprobante(atributoUtil.getCodigoComprobante())).concat("|")
					.concat(atributoUtil.getGuiaSerie()).concat("|")
					.concat(String.valueOf(atributoUtil.getGuiaNumero())).concat("|").concat("6").concat("|")
					.concat(AtributoBean.RUC_EMISOR).concat("|").concat(atributoUtil.getTotal().toString());
		} else {
			cadena = "";
		}

		return cadena;
	}

	public void editaProducto(ActionEvent e) {

		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		gestionUtil.editarProductoVenta(codigo, temporales, atributoUtil);
	}

	public void quitarListaProducto(ActionEvent e) {

		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		gestionUtil.quitarListaProductoVenta(codigo, temporales, atributoUtil);
	}

	public String cancelarVenta() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("ventaBean");
		return "registroVenta.xhtml";
	}

	public void imprimirVenta() throws IOException, ClassNotFoundException, JRException, SQLException,
			ParserConfigurationException, SAXException, WriterException {

		// Obtener la firma digital en XML
		File xml = new File(Sunat.RUTA_FIRMA
				.concat(sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobante(),
						atributoUtil.getNumeroSerie(), atributoUtil.getNumeroComprobante() % 10000000))
				.concat(".xml"));

		// leer archivo xml firma, para obtener el digestValue (hash o valor
		// resumen) y signatureValue (firma digital)
		sunatUtil.leerNodosXml(xml);

		// generar el codigo de barra sin hash y sin firma digital, estos se
		// añaden em la clase sunat
		sunatUtil.generarCodigoBarra(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobante(),
				atributoUtil.getNumeroSerie(), atributoUtil.getNumeroComprobante(), atributoUtil.getIgv().toString(),
				atributoUtil.getTotal().toString(), atributoUtil.getFecha().toString(),
				atributoUtil.getCliente().getCodigoDocumento(), atributoUtil.getCliente().getNroDocumento());

		// El monto de la venta en letras
		MontoEnLetras numeroLetra = new MontoEnLetras();
		String letra = String.valueOf(atributoUtil.getTotal());
		String leyenda = numeroLetra.convertir(letra, true);

		// Concatenar la serie y numero comprobante
		String facturacionPDF = atributoUtil.getNumeroSerie().concat("-")
				.concat(talonarioUtil.obtenerFormatoNumeroComprobante(atributoUtil.getNumeroComprobante() % 10000000));

		Impresora i = Impresora.getImpresora();
		i.imprimirVenta(facturacionPDF,
				talonarioUtil
						.obtenerTalonario(atributoUtil.getCodigoComprobante(), atributoUtil.getNumeroComprobante()),
				leyenda,
				Sunat.RUTA_IMAGEN.concat(
						sunatUtil.generarNombreArchivo(AtributoBean.RUC_EMISOR, atributoUtil.getCodigoComprobante(),
								atributoUtil.getNumeroSerie(), atributoUtil.getNumeroComprobante() % 10000000))
						.concat(".png"),
				sunatUtil.getDigestTexto());
	}

	public AtributoBean getAtributoUtil() {
		return atributoUtil;
	}

	public void setAtributoUtil(AtributoBean atributoUtil) {
		this.atributoUtil = atributoUtil;
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

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

}
