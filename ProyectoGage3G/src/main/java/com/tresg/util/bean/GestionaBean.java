package com.tresg.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.tresg.almacen.service.AlmacenBusinessDelegate;
import com.tresg.almacen.service.ConsultarAlmacenBusinessService;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.EstadoJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.service.GestionarClienteService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.seguridad.jpa.UsuarioJPA;
import com.tresg.util.formato.Formateo;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.DetalleVentaJPAPK;
import com.tresg.ventas.jpa.GuiaRemisionJPA;
import com.tresg.ventas.jpa.VentaJPA;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean
@ApplicationScoped
public class GestionaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	Formateo formato = new Formateo();

	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();
	GestionarClienteService_I sCliente = IncluidoBusinessDelegate.getGestionarClienteService();
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ConsultarAlmacenBusinessService sConsultaAlmacen = AlmacenBusinessDelegate.getConsultarAlmacenService();

	// retorna el numero maximo + 1 del numero comprobante
	public int retornaNumeroComprobante(int comprobante) {
		return sVenta.listaVenta().stream().filter(v -> v.getComprobante().getCodComprobante() == comprobante)
				.mapToInt(v -> v.getNumComprobante()).max().orElse(0) + 1;
	}

	// metodo para mostrar lista de clientes dentro la generacion de una venta
	public List<ClienteJPA> listarCliente(String nombreCliente) {
		List<ClienteJPA> clientes = new ArrayList<>();
		sCliente.listaCliente().stream().filter(c->c.getNombre().toLowerCase().contains(nombreCliente.toLowerCase())).forEach(clientes::add);
		return clientes;
	}

	// metodo para mostrar lista de productos dentro la generacion de una venta
	public List<ProductoJPA> listarProducto(String descripcionProducto) {
		List<ProductoJPA> productos = new ArrayList<>();
		sConsultaAlmacen.listaExistencia(descripcionProducto).stream().forEach(productos::add);
		return productos;

	}

	// metodo tanto para el registro como la actualizacion de venta
	// se utiliza en la carga del producto a la lista de detallle
	public DetalleVentaJPA retornarProductoVenta(AtributoBean atributo) {

		DetalleVentaJPAPK pk = new DetalleVentaJPAPK();
		pk.setCodProducto(atributo.getCodigoProducto());
		pk.setNumComprobante(
				formato.obtenerTalonario(atributo.getCodigoComprobante(), atributo.getNumeroComprobante()));

		DetalleVentaJPA objDetalle = new DetalleVentaJPA();
		objDetalle.setDescripcionProducto(atributo.getDescripcionProducto());
		objDetalle.setUnidadMedida(atributo.getUnidad());
		objDetalle.setCantidad(atributo.getCantidad());
		objDetalle.setPrecio(atributo.getPrecio());
		objDetalle.setId(pk);

		return objDetalle;
	}

	// metodo tanto para el registro como la actualizacion de venta
	// se utiliza cuando se graba la operacion
	public VentaJPA retornarVenta(AtributoBean atributo, List<DetalleVentaJPA> temporales, int usuario) {

		ComprobanteJPA objComprobante = new ComprobanteJPA();
		objComprobante.setCodComprobante(atributo.getCodigoComprobante());

		MediosPagoJPA objPago = new MediosPagoJPA();
		objPago.setCodPago(atributo.getCodigoPago());

		ClienteJPA objCliente = tipoCliente(atributo);

		EstadoJPA objEstado = new EstadoJPA();
		objEstado.setCodEstado(estadoVenta(atributo.getCodigoPago()));

		UsuarioJPA objUsuario = new UsuarioJPA();
		objUsuario.setCodUsuario(usuario);
				
		
		VentaJPA objVenta = new VentaJPA();		
		objVenta.setComprobante(objComprobante);
		objVenta.setCliente(objCliente);
		objVenta.setEstado(objEstado);
		objVenta.setPago(objPago);
		objVenta.setNumComprobante(
				formato.obtenerTalonario(atributo.getCodigoComprobante(), atributo.getNumeroComprobante()));
		objVenta.setSerie(atributo.getNumeroSerie());
		objVenta.setMonto(atributo.getTotal());
		objVenta.setFecha(atributo.getFecha());
		objVenta.setFecVence(formato.obtenerFecha(atributo.getFechaVence()));
		objVenta.setHora(formato.obtenerHora());
		objVenta.setObservacion(atributo.getObservacion());
		objVenta.setUsuario(objUsuario);	
		objVenta.setDetalles(temporales);
		
		if(atributo.getGuiaNumero()!=0) {
			GuiaRemisionJPA objGuia=new GuiaRemisionJPA();
			objGuia.setNumGuia(atributo.getGuiaNumero());
			objGuia.setFecha(atributo.getFecha());
			objGuia.setVenta(objVenta);
			objVenta.setGuiaRemision(objGuia);
		}

		return objVenta;

	}

	// metodo de la propia clase
	ClienteJPA tipoCliente(AtributoBean atributo) {

		ClienteJPA objCliente = new ClienteJPA();
		if (atributo.getCliente().getCodCliente() == 0) {
			objCliente.setCodCliente(1);
		} else {
			objCliente.setCodCliente(atributo.getCliente().getCodCliente());
		}
		return objCliente;
	}

	// metodo de la propia clase
	int estadoVenta(int formaPago) {
		int estado = 0;
		if (formaPago == 1 || formaPago == 3) {
			estado = 1;// pagado
		} else if (formaPago == 2) {
			estado = 2;// adeudado
		}
		return estado;
	}

	public String iterarListaVenta(List<DetalleVentaJPA> temporales, AtributoBean atributo) {

		String mensaje = "";
		// Crea una lista auxiliar
		List<Integer> auxLista1 = new ArrayList<>();
		BigDecimal auxSubtotal = new java.math.BigDecimal("0.00");

		// Recorre la lista principal
		Iterator<DetalleVentaJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleVentaJPA detalleJPA = it.next();
			auxSubtotal = detalleJPA.getPrecio().multiply(detalleJPA.getCantidad());

			// Recorre la lista auxiliar
			Iterator<Integer> itaux1 = auxLista1.iterator();
			while (itaux1.hasNext()) {
				int aux1 = (int) itaux1.next();
				// Realiza la comparacion de listas
				if (detalleJPA.getId().getCodProducto() == aux1) {
					it.remove();
					mensaje = "Ya ingreso dicho producto";
					auxSubtotal = new java.math.BigDecimal("0.00");
				}
			}
			// Almacena en una lista auxiliar los codigos de los productos
			// ingresados para su posterior comparacion con la lista
			// principal
			auxLista1.add(detalleJPA.getId().getCodProducto());

		}

		atributo.setTotal(atributo.getTotal().add(auxSubtotal).setScale(2, RoundingMode.HALF_EVEN));
		atributo.setSubtotal(atributo.getTotal().divide(atributo.getValor(), 2, RoundingMode.HALF_EVEN));
		atributo.setIgv(atributo.getTotal().subtract(atributo.getSubtotal()));

		return mensaje;
	}

	// metodo para limpiar los componentes xhtml
	public void limpiarProductoVenta(AtributoBean atributo) {

		atributo.setCodigoProducto(0);
		atributo.setDescripcionProducto("");
		atributo.setPrecio(new java.math.BigDecimal("0.00"));
		atributo.setCantidad(new java.math.BigDecimal("0.00"));
	}

	// metodo modificar la venta se elimina un item de la lista detalle
	public void quitarListaProductoModificado(int codigo, List<DetalleVentaJPA> temporales, AtributoBean atributo) {

		BigDecimal monto = new java.math.BigDecimal("0.00");

		Iterator<DetalleVentaJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleVentaJPA detalleJPA = it.next();

			if ((detalleJPA.getId().getCodProducto()) == codigo) {
				monto = detalleJPA.getPrecio().multiply(detalleJPA.getCantidad());
				it.remove();
				// servicio que actualiza el stock en almacen
				sVenta.actualizaItemVentaEliminada(detalleJPA);
			}

		}
		atributo.setTotal(atributo.getTotal().subtract(monto));
		atributo.setSubtotal(atributo.getTotal().divide(new java.math.BigDecimal("1.18"), 2, RoundingMode.HALF_EVEN));
		atributo.setIgv(atributo.getTotal().subtract(atributo.getSubtotal()));

	}

	// metodo que elimina un item de la lista detalle
	public void quitarListaProductoVenta(int codigo, List<DetalleVentaJPA> temporales, AtributoBean atributo) {

		BigDecimal monto = new java.math.BigDecimal("0.00");

		Iterator<DetalleVentaJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleVentaJPA detalleJPA = it.next();

			if ((detalleJPA.getId().getCodProducto()) == codigo) {
				monto = detalleJPA.getPrecio().multiply(detalleJPA.getCantidad());
				it.remove();
			}
		}
		atributo.setTotal(atributo.getTotal().subtract(monto));
		atributo.setSubtotal(atributo.getTotal().divide(atributo.getValor(), 2, RoundingMode.HALF_EVEN));
		atributo.setIgv(atributo.getTotal().subtract(atributo.getSubtotal()));

	}

	public void editarProductoVenta(int codigoProducto, List<DetalleVentaJPA> temporales, AtributoBean atributo) {

		ProductoJPA objProducto = sProducto.buscaProductoPorCodigo(codigoProducto);
		BigDecimal monto = new java.math.BigDecimal("0.00");

		atributo.setCodigoTipo(objProducto.getTipo().getCodTipo());
		atributo.setCodigoProducto(objProducto.getCodProducto());
		atributo.setDescripcionProducto(objProducto.getDescripcion().concat(objProducto.getTipo().getDescripcion()));
		atributo.setPrecio(objProducto.getPrecioVenta());
		// quita de la lista
		Iterator<DetalleVentaJPA> it = temporales.iterator();
		while (it.hasNext()) {
			DetalleVentaJPA detalleJPA = it.next();
			if ((detalleJPA.getId().getCodProducto()) == codigoProducto) {
				monto = detalleJPA.getPrecio().multiply(detalleJPA.getCantidad());
				it.remove();
			}
		}
		atributo.setTotal(atributo.getTotal().subtract(monto));
		atributo.setSubtotal(atributo.getTotal().divide(new java.math.BigDecimal("1.18"), 2, RoundingMode.HALF_EVEN));
		atributo.setIgv(atributo.getTotal().subtract(atributo.getSubtotal()));

	}

}
