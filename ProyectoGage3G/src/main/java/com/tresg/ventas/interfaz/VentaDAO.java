
package com.tresg.ventas.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface VentaDAO {

	
	// CU consultar ventas
	List<VentaJPA> listarVenta();

	// nuevo requerimiento, validar numero ingresado diferente al mostrado por el sistema
	VentaJPA validarNumeroComprobantePago(int comprobante);

	int comprobarExistenciaProducto(int producto);

	// CU registrar venta
	String registrarVenta(VentaJPA venta);

	// CU actualizar venta
	String actualizarVenta(VentaJPA venta);

	// Actualiza stock en almacen cuando item de venta es retirada de la lista
	void actualizarItemVentaEliminada(ProductoJPA producto);

	// CU anular venta
	String anularVenta(int venta);

}
