package com.tresg.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.tresg.incluido.jsf.AtributoBean;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean
@ApplicationScoped
public class MensajeBean implements Serializable {

	private static final long serialVersionUID = 1L;
	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();

	public String mostrarMensajeAgregarVenta(AtributoBean atributo, List<DetalleVentaJPA> temporales) {
		String mensaje = "";
		int stock = sVenta.compruebaExistenciaProducto(atributo.getCodigoProducto());

		if (atributo.getDescripcionProducto().isEmpty() || atributo.getPrecio().compareTo(BigDecimal.ZERO) < 0.00) {
			mensaje = "mensajeAgregarProducto";
		} else if (atributo.getCantidad().intValue() == 0) {
			mensaje = "mensajeAgregarCantidad";
		} else if (stock < atributo.getCantidad().intValue()) {
			mensaje = "mensajeStock";
		} 
		return mensaje;
	}

	public String mostrarMensajeGrabarVenta(AtributoBean atributo, List<DetalleVentaJPA> temporales) {
		String mensaje = "";

		if ("".equals(atributo.getCodigoOperacion())) {
			mensaje = "mensajeOperacion";
		} else if (atributo.getCodigoComprobante() == 0) {
			mensaje = "mensajeComprobante";
		}else if (atributo.getCodigoPago() == 0) {
			mensaje = "mensajePago";
		} else if (atributo.getNumeroComprobante() == 0) {
			mensaje = "mensajeCargarNumeroComprobante";
		} else if ((atributo.getCliente().getNombre().isEmpty() || atributo.getCliente().getDireccion().isEmpty()
				|| atributo.getCliente().getNroDocumento().isEmpty()) && atributo.getCodigoComprobante() == 1) {
			mensaje = "mensajeCliente";
		} else if ((atributo.getCliente().getNombre().isEmpty() || atributo.getCliente().getDireccion().isEmpty()
				|| atributo.getCliente().getNroDocumento().isEmpty()) && atributo.getCodigoPago() == 2) {
			mensaje = "mensajeCredito";
		} else if (temporales.isEmpty()) {
			mensaje = "mensajeProductoLista";
		}
		return mensaje;

	}

	public String mostrarMensajeError(String mensaje, AtributoBean atributo) {
		String error;
		switch (mensaje) {
		case "mensajeAgregarProducto":
			error = "Seleccione un producto de la lista";
			break;
		case "mensajeAgregarCantidad":
			error = "Ingrese cantidad mayor a 0";
			break;
		case "mensajeStock":
			error = "No cuenta con ".concat(atributo.getCantidad().toString()).concat(" ").concat("unidades de ")
					.concat(atributo.getDescripcionProducto())
					.concat(" en stock, solicitar pedido para dicho punto de venta");
			break;
		case "mensajeOperacion":
			error = "Seleccione una operacion";
			break;
		case "mensajeComprobante":
			error = "Seleccione un Comprobante";
			break;
		case "mensajePago":
			error = "Seleccione la forma de pago";
			break;
		case "mensajeCargarNumeroComprobante":
			error = "Ingrese la numeracion inicial del comprobante";
			break;
		case "mensajeCliente":
			error = "Ingrese el cliente, por ser una venta con factura";
			break;
		case "mensajeCredito":
			error = "Ingresar cliente, por ser una venta a credito";
			break;
		case "mensajeProductoLista":
			error = "Ud. debe ingresar por lo menos un producto a la lista, click en AGREGAR";
			break;
		default:
			error = "";
			break;
		}
		return error;

	}

}
