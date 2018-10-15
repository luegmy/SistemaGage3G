package com.tresg.almacen.jsf;

import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.context.RequestContext;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.almacen.jpa.DetalleMovimientoJPAPK;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.almacen.jpa.TipoMovimientoJPA;
import com.tresg.almacen.service.AlmacenBusinessDelegate;
import com.tresg.almacen.service.RegistrarMovimientoBusinessService;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.ProveedorJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.GestionarProveedorService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.seguridad.jpa.UsuarioJPA;
import com.tresg.util.formato.Formateo;

@ManagedBean(name = "almacenBean")
@SessionScoped
public class RegistroMovimientoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> tipoMovimientos;
	private int codigoTipoMovimiento;

	private List<SelectItem> almacenes;
	private int codigoOrigen;
	private int codigoDestino;

	private List<SelectItem> comprobantes;
	private int codigoComprobante;
	private int numeroComprobante;

	private List<SelectItem> proveedores;
	private int codigoProveedor;

	private Date fecha = new Date();
	private String observacion;

	private List<SelectItem> productos;
	private List<SelectItem> tiposProductos;
	private int codigoTipoProducto;

	@ManagedProperty(value = "#{usuario.sesionCodigoUsuario}")
	private int usuario;

	// Para anadir elementos a la datatable
	private List<DetalleMovimientoJPA> temporales;
	// Atributo de la tabla detalle_almacen
	private int codigoProducto;
	private int cantidad;

	private List<DetalleMovimientoJPA> transferencias;

	// Instanciar los services
	RegistrarMovimientoBusinessService sAlmacen = AlmacenBusinessDelegate.getRegistrarAlmacenService();
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	GestionarProveedorService_I sProveedor=IncluidoBusinessDelegate.getGestionarProveedorService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public RegistroMovimientoBean() {
		temporales = new ArrayList<>();
		transferencias = new ArrayList<>();
	}

	public void cargarComprobante() {
		if (codigoComprobante == 2) {
			setNumeroComprobante(sAlmacen.generaNumeroNota());
		} else {
			setNumeroComprobante(0);
		}
	}

	public void agregarProductoAlmacen() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (cantidad == 0) {
			context.addMessage("mensajeAgregarCantidad",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese una cantidad mayor a 0", null));
		} else if (codigoTipoProducto == 0) {
			context.addMessage("mensajeTipo",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un tipo de producto", null));
		} else if (codigoProducto == 0) {
			context.addMessage("mensajeProducto",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un producto", null));
		}

		else {

			ProductoJPA producto = sProducto.buscaProductoPorCodigo(codigoProducto);
			if (codigoDestino != 0) {

				DetalleMovimientoJPAPK dmpk2 = new DetalleMovimientoJPAPK();
				dmpk2.setCodAlmacen(codigoDestino);
				dmpk2.setCodProducto(codigoProducto);
				dmpk2.setNroMovimiento(getNumeroDocumento() + 1);

				DetalleMovimientoJPA objDetalle2 = new DetalleMovimientoJPA();
				objDetalle2.setDescripcionProducto(
						producto.getDescripcion().concat(" ").concat(producto.getTipo().getDescripcion()));
				objDetalle2.setCantidad(cantidad);
				objDetalle2.setId(dmpk2);

				transferencias.add(objDetalle2);
			}

			DetalleMovimientoJPAPK dmpk = new DetalleMovimientoJPAPK();
			dmpk.setCodAlmacen(codigoOrigen);
			dmpk.setCodProducto(codigoProducto);
			dmpk.setNroMovimiento(getNumeroDocumento());

			DetalleMovimientoJPA objDetalle = new DetalleMovimientoJPA();
			objDetalle.setCantidad(cantidad);
			objDetalle.setDescripcionProducto(
					producto.getDescripcion().concat(" ").concat(producto.getTipo().getDescripcion()));
			objDetalle.setId(dmpk);

			temporales.add(objDetalle);

		}

		// Crear una lista auxiliar
		List<String> auxLista = new ArrayList<>();

		// Recorrer la lista principal
		Iterator<DetalleMovimientoJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleMovimientoJPA detalleJPA = it.next();

			// Recorrer la lista principal auxiliar
			Iterator<String> itaux = auxLista.iterator();
			while (itaux.hasNext()) {
				String aux = (String) itaux.next();
				// Realizar la compracion de listas
				if (aux.equals(String.valueOf(detalleJPA.getId().getCodProducto())
						.concat(String.valueOf(detalleJPA.getId().getCodAlmacen())))) {
					it.remove();
					context.addMessage("mensajeLista",
							new FacesMessage(FacesMessage.SEVERITY_ERROR,
									"Ya ingreso ".concat(detalleJPA.getDescripcionProducto()).concat(" ")
											.concat("en almacen").concat(" ")
											.concat(String.valueOf(detalleJPA.getId().getCodAlmacen())),
									null));
				}
			}
			// Almacenar en una lista auxiliar los codigos ingresados
			// para su posterior comparacion con la lista principal
			auxLista.add(String.valueOf(detalleJPA.getId().getCodProducto())
					.concat(String.valueOf(detalleJPA.getId().getCodAlmacen())));
		}

		limpiar();

	}

	public void limpiar() {
		cantidad = 0;
	}

	public void quitarListaProducto(ActionEvent e) {

		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		Iterator<DetalleMovimientoJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleMovimientoJPA detalleJPA = it.next();
			if ((detalleJPA.getId().getCodProducto()) == codigo) {
				it.remove();
			}
		}
	}

	// Para grabar ingreso/salida del producto
	@SuppressWarnings("deprecation")
	public String grabarAlmacen() {
		FacesContext context = FacesContext.getCurrentInstance();

		TipoMovimientoJPA objTipoMovimiento = new TipoMovimientoJPA();
		objTipoMovimiento.setCodMovimiento(codigoTipoMovimiento);

		UsuarioJPA objUsuario = new UsuarioJPA();
		objUsuario.setCodUsuario(usuario);

		ProveedorJPA objProveedor = new ProveedorJPA();
		objProveedor.setCodProveedor(codigoProveedor);

		ComprobanteJPA objComprobante = new ComprobanteJPA();
		objComprobante.setCodComprobante(codigoComprobante);

		Formateo formatoHora = new Formateo();

		MovimientoJPA objMovimiento = new MovimientoJPA();
		objMovimiento.setNroMovimiento(getNumeroDocumento());
		objMovimiento.setComprobante(objComprobante);
		objMovimiento.setNumComprobante(numeroComprobante);
		objMovimiento.setFecha(fecha);
		objMovimiento.setHora(formatoHora.obtenerHora());
		objMovimiento.setTipoMovimiento(objTipoMovimiento);
		objMovimiento.setUsuario(objUsuario);
		objMovimiento.setProveedor(objProveedor);
		objMovimiento.setObservacion(observacion);
		objMovimiento.setDetalles(temporales);

		if (codigoTipoMovimiento == 0) {
			context.addMessage("mensajeTipoMovimiento",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el tipo movimiento", null));
		} else if (codigoTipoMovimiento > 0 && codigoOrigen == 0) {
			context.addMessage("mensajeAlmacenOrigen",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el almacen", null));
		} else if (codigoTipoMovimiento == 3 && codigoDestino == 0) {
			context.addMessage("mensajeAlmacenTransferencia",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el almacen destino", null));
		} else if ((codigoOrigen == codigoDestino) && (codigoOrigen != 0 || codigoDestino != 0)) {
			context.addMessage("mensajeAlmacenIgual",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione otro almacen diferente al origen", null));
		} else if (codigoComprobante == 0) {
			context.addMessage("mensajeComprobante",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el tipo de comprobante", null));
		} else if (codigoProveedor == 0) {
			context.addMessage("mensajeProveedor",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el proveedor", null));
		}else if (numeroComprobante == 0 && codigoComprobante != 2) {
			context.addMessage("mensajeNumeroComprobante",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el numero de comprobante", null));
		} else if (temporales.isEmpty()) {
			context.addMessage("mensajeLista", new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Agregue un producto a la lista, pulse el boton AGREGAR", null));
		} else {
			context.addMessage("mensajeRegistroAlmacen",
					new FacesMessage(FacesMessage.SEVERITY_INFO, sAlmacen.registraMovimiento(objMovimiento), null));

			RequestContext.getCurrentInstance().execute("PF('dlgMensaje').show();");

		}
		return "registroAlmacen";
	}

	public String grabarTransferencia() {

		TipoMovimientoJPA objTipoMovimiento = new TipoMovimientoJPA();
		objTipoMovimiento.setCodMovimiento(2);

		UsuarioJPA objUsuario = new UsuarioJPA();
		objUsuario.setCodUsuario(usuario);

		ComprobanteJPA objComprobante = new ComprobanteJPA();
		objComprobante.setCodComprobante(codigoComprobante);

		Formateo formatoHora = new Formateo();

		MovimientoJPA objMovimiento = new MovimientoJPA();
		objMovimiento.setNroMovimiento(getNumeroDocumento());
		objMovimiento.setComprobante(objComprobante);
		objMovimiento.setNumComprobante(numeroComprobante);
		objMovimiento.setFecha(fecha);
		objMovimiento.setHora(formatoHora.obtenerHora());
		objMovimiento.setTipoMovimiento(objTipoMovimiento);
		objMovimiento.setUsuario(objUsuario);
		objMovimiento.setObservacion(observacion);
		objMovimiento.setDetalles(transferencias);

		sAlmacen.registraMovimiento(objMovimiento);
		return cancelarAlmacen();

	}

	public String cancelarAlmacen() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getSessionMap().remove("almacenBean");
		return "registroAlmacen";

	}

	// lista despegable tipo de productos
	public List<SelectItem> getTiposProductos() {
		tiposProductos = new ArrayList<>();
		sCombo.comboTipoProducto().forEach(p -> tiposProductos.add(new SelectItem(p.getCodTipo(), p.getDescripcion())));
		return tiposProductos;
	}

	public void setTiposProductos(List<SelectItem> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}

	// lista despegable de productos
	public List<SelectItem> getProductos() {
		productos = new ArrayList<>();
		sProducto.listaProductoPorDescripcion("").stream().forEach(p -> productos.add(new SelectItem(p.getCodProducto(), p.getDescripcion())));
		return productos;
	}

	public void setProductos(List<SelectItem> productos) {
		this.productos = productos;
	}

	// lista despegable de movimientos
	public List<SelectItem> getTipoMovimientos() {
		tipoMovimientos = new ArrayList<>();
		sCombo.comboTipoMovimiento()
				.forEach(a -> tipoMovimientos.add(new SelectItem(a.getCodMovimiento(), a.getDescripcion())));
		return tipoMovimientos;
	}

	public void setTipoMovimientos(List<SelectItem> tipoMovimientos) {
		this.tipoMovimientos = tipoMovimientos;
	}

	// lista despegable de almacenes
	public List<SelectItem> getAlmacenes() {
		almacenes = new ArrayList<>();
		sCombo.comboAlamcen().forEach(a -> almacenes.add(new SelectItem(a.getCodAlmacen(), a.getDescripcion())));
		return almacenes;
	}

	public void setAlmacenes(List<SelectItem> almacenes) {
		this.almacenes = almacenes;
	}

	public int getCodigoTipoMovimiento() {
		return codigoTipoMovimiento;
	}

	public void setCodigoTipoMovimiento(int codigoTipoMovimiento) {
		this.codigoTipoMovimiento = codigoTipoMovimiento;
	}

	public List<SelectItem> getComprobantes() {
		comprobantes = new ArrayList<>();
		sCombo.comboComprobante().stream().filter(c -> (c.getCodComprobante() != 7 && c.getCodComprobante() != 8))
				.forEach(a -> comprobantes.add(new SelectItem(a.getCodComprobante(), a.getDescripcion())));
		return comprobantes;
	}

	public void setComprobantes(List<SelectItem> comprobantes) {
		this.comprobantes = comprobantes;
	}

	public int getCodigoComprobante() {
		return codigoComprobante;
	}

	public void setCodigoComprobante(int codigoComprobante) {
		this.codigoComprobante = codigoComprobante;
	}

	public int getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(int numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public int getNumeroDocumento() {
		return sAlmacen.obtieneNumeroMovimiento();
	}

	public List<SelectItem> getProveedores() {
		proveedores=new ArrayList<>();
		sProveedor.listaProveedor().forEach(a -> proveedores.add(new SelectItem(a.getCodProveedor(), a.getNombre())));
		return proveedores;
	}

	public void setProveedores(List<SelectItem> proveedores) {
		this.proveedores = proveedores;
	}

	public int getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(int codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getCodigoOrigen() {
		return codigoOrigen;
	}

	public void setCodigoOrigen(int codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}

	public int getCodigoDestino() {
		return codigoDestino;
	}

	public void setCodigoDestino(int codigoDestino) {
		this.codigoDestino = codigoDestino;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public int getCodigoTipoProducto() {
		return codigoTipoProducto;
	}

	public void setCodigoTipoProducto(int codigoTipoProducto) {
		this.codigoTipoProducto = codigoTipoProducto;
	}

	public int getUsuario() {
		return usuario;
	}

	public void setUsuario(int usuario) {
		this.usuario = usuario;
	}

	public List<DetalleMovimientoJPA> getTemporales() {
		return temporales;
	}

	public void setTemporales(List<DetalleMovimientoJPA> temporales) {
		this.temporales = temporales;
	}

	public List<DetalleMovimientoJPA> getTransferencias() {

		return transferencias;
	}

	public void setTransferencias(List<DetalleMovimientoJPA> transferencias) {
		this.transferencias = transferencias;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
