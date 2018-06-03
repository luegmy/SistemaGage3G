package com.tresg.ventas.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.xml.parsers.ParserConfigurationException;

import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.xml.sax.SAXException;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.util.bean.AtributoBean;
import com.tresg.util.bean.GestionaBean;
import com.tresg.util.bean.ListaConsultaBean;
import com.tresg.util.bean.MensajeBean;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

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
	private DetalleAlmacenJPA productoSeleccionado;

	// Para anadir elementos a la datatable
	private List<DetalleVentaJPA> temporales;

	AtributoBean atributoUtil = new AtributoBean();
	ListaConsultaBean listaUtil = new ListaConsultaBean();
	GestionaBean gestionUtil = new GestionaBean();
	MensajeBean mensajeUtil = new MensajeBean();

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
		
		ventas = listaUtil.listarPorCliente(atributoUtil.getCliente().getNombre());

	}

	public void mostrarDetalleVenta(ActionEvent e) {
		int numero = (int) e.getComponent().getAttributes().get("numeroDetalle");
		detalles = listaUtil.mostrarDetalleVenta(numero);

	}

	public void cargarVenta(ActionEvent e) {

		int numero = (int) e.getComponent().getAttributes().get("numeroEdicion");
		ProductoJPA objProducto;
		
		VentaJPA objVenta = sVenta.muestraDetalleVenta(numero);
		atributoUtil.setNumeroComprobante(objVenta.getNumComprobante() % 10000000);
		atributoUtil.getCliente().setCodCliente(objVenta.getCliente().getCodCliente());
		atributoUtil.getCliente().setDireccion(objVenta.getCliente().getDireccion());
		atributoUtil.getCliente().setNroDocumento(objVenta.getCliente().getNroDocumento());
		atributoUtil.getCliente().setNombre(objVenta.getCliente().getNombre());
		atributoUtil.setCodigoComprobante(objVenta.getComprobante().getCodComprobante());
		atributoUtil.setNumeroSerie(objVenta.getSerie().getNroSerie());
		atributoUtil.setCodigoPago(objVenta.getPago().getCodPago());

		for (DetalleVentaJPA d : objVenta.getDetalles()) {
			objProducto = sProducto.buscaProductoPorCodigo(d.getId().getPolvo());
			if (objProducto != null) {
				d.setDescripcion(d.getProducto().getDescripcion().concat(" ").concat
						 (d.getProducto().getTipo().getDescripcion()).concat(" ").concat
						 (objProducto.getTipo().getDescripcion()).concat(" ").concat
						 (objProducto.getDescripcion()));				
						
			} else {
				d.setDescripcion(d.getProducto().getDescripcion().concat(" ").concat
						 (d.getProducto().getTipo().getDescripcion()));
			}
				
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
			productoSeleccionado = (DetalleAlmacenJPA) event.getObject();
			atributoUtil.setCodigoProducto(productoSeleccionado.getId().getCodProducto());
			atributoUtil.setCodigoTipo(productoSeleccionado.getProducto().getTipo().getCodTipo());
			atributoUtil.setDescripcionProducto(productoSeleccionado.getProducto().getDescripcion().concat(" ")
					.concat(productoSeleccionado.getProducto().getTipo().getDescripcion()));
			atributoUtil.setPrecio(productoSeleccionado.getProducto().getPrecioVenta());

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
				gestionUtil.iterarListaVenta(temporales, atributoUtil);
				gestionUtil.limpiarProductoVenta(atributoUtil);
			}

		}

		public void quitarListaProducto(ActionEvent e) {
			int codigo = (int) e.getComponent().getAttributes().get("codigo");
			int codigoPolvo = (int) e.getComponent().getAttributes().get("codigoPolvo");
			BigDecimal cantidad = (BigDecimal) e.getComponent().getAttributes().get("cantidad");

			gestionUtil.quitarListaProductoModificado(codigo, codigoPolvo, cantidad, temporales, atributoUtil);

		}

		public void editarProducto(ActionEvent e) {
			int codigoProducto = (int) e.getComponent().getAttributes().get("codigo");

			gestionUtil.editarProductoVenta(codigoProducto, temporales, atributoUtil);

		}

		public String actualizarVenta() {
			FacesContext context = FacesContext.getCurrentInstance();
			String mensajeVenta = mensajeUtil.mostrarMensajeGrabarVenta(atributoUtil, temporales);

			if (!"".equals(mensajeVenta)) {
				context.addMessage(mensajeVenta, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						mensajeUtil.mostrarMensajeError(mensajeVenta, atributoUtil), null));
			} else {
				
				context.addMessage("mensajeActualizaVenta", new FacesMessage(FacesMessage.SEVERITY_INFO,
						sVenta.actualizaVenta(gestionUtil.retornarVenta(atributoUtil, temporales)), null));
				RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");

			}
			return "consultaVentaModificada.xhtml";
		}

		public String cancelarVenta() {
			temporales.clear();
			FacesContext context = FacesContext.getCurrentInstance();
			context.getExternalContext().getSessionMap().remove("ventaActualizaBean");
			return "consultaVentaModificada.xhtml";
		}

		// metodo para anular venta
		public void cargarNumeroComprobante(ActionEvent e) {
			int codigo = (Integer) e.getComponent().getAttributes().get("numeroCargar");
			atributoUtil.setNumeroComprobante(codigo);
		}

		public void anularVenta() {
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage("mensajeAnulacion", new FacesMessage(FacesMessage.SEVERITY_INFO,
					sVenta.anulaVenta(atributoUtil.getNumeroComprobante()), null));
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

	public DetalleAlmacenJPA getProductoSeleccionado() {
		return productoSeleccionado;
	}

	public void setProductoSeleccionado(DetalleAlmacenJPA productoSeleccionado) {
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

}
