package com.tresg.ventas.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "ventaBean")
@ViewScoped
public class RegistroVentaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// atributo para el rowSelect
	private ClienteJPA clienteSeleccionado;
	private ProductoJPA productoSeleccionado;

	// Para anadir elementos a la datatable
	private List<DetalleVentaJPA> temporales;

	AtributoBean atributoUtil = new AtributoBean();
	GestionaBean gestionUtil = new GestionaBean();
	MensajeBean mensajeUtil = new MensajeBean();
	Formateo talonarioUtil;

	// Instanciar los services
	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();
	GestionarClienteService_I sCliente = IncluidoBusinessDelegate.getGestionarClienteService();
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public RegistroVentaBean() {
		temporales = new ArrayList<>();
	}

	public void cargarComprobante() {
		atributoUtil.setNumeroComprobante(
				gestionUtil.retornaNumeroComprobante(atributoUtil.getCodigoComprobante()) % 10000000);
	}

	public void listarCliente() {
		atributoUtil.setClientes(gestionUtil.listarCliente(atributoUtil.getCliente().getNombre()));
	}

	// Metodo donde se agrega los atributos del cliente en las respectivas
	// cajas de texto del formulario venta
	public void seleccionarCliente(SelectEvent event) {

		clienteSeleccionado = (ClienteJPA) event.getObject();
		atributoUtil.getCliente().setCodCliente(clienteSeleccionado.getCodCliente());
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
		atributoUtil.setCodigoTipo(productoSeleccionado.getTipo().getCodTipo());
		atributoUtil.setDescripcionProducto(productoSeleccionado.getDescripcion().concat(" ")
				.concat(productoSeleccionado.getTipo().getDescripcion()));
		atributoUtil.setPrecio(productoSeleccionado.getPrecioVenta());
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
			gestionUtil.iterarListaVenta(temporales, atributoUtil);
			gestionUtil.limpiarProductoVenta(atributoUtil);
		}

	}

	public void grabarVenta() {

		FacesContext context = FacesContext.getCurrentInstance();
		String mensajeVenta = mensajeUtil.mostrarMensajeGrabarVenta(atributoUtil, temporales);

		if (!"".equals(mensajeVenta)) {
			context.addMessage(mensajeVenta, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					mensajeUtil.mostrarMensajeError(mensajeVenta, atributoUtil), null));
		} else {

			context.addMessage("mensajeRegistroVenta", new FacesMessage(FacesMessage.SEVERITY_INFO,
					sVenta.registraVenta(gestionUtil.retornarVenta(atributoUtil, temporales)), null));
			RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");

		}
	}

	public void editaProducto(ActionEvent e) {

		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		gestionUtil.editarProductoVenta(codigo, temporales, atributoUtil);
	}

	public void quitarListaProducto(ActionEvent e) {

		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		BigDecimal cantidad = (BigDecimal) e.getComponent().getAttributes().get("cantidad");

		gestionUtil.quitarListaProductoVenta(codigo, cantidad, temporales, atributoUtil);
	}

	public String cancelarVenta() {

		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("ventaBean");
		return atributoUtil.getAgregarVenta();
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

}
