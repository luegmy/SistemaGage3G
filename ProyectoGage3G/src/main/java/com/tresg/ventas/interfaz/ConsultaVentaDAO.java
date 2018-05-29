package com.tresg.ventas.interfaz;

import java.util.Date;
import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface ConsultaVentaDAO {

	// CU consultar ventas
	List<VentaJPA> listarVenta();
	
	List<VentaJPA> consultarVentaPorCliente(String cliente);

	// mostrar el detalle de la VENTA
	VentaJPA mostrarDetalleVenta(int comprobante);

	// CU consultar productos por venta
	List<DetalleVentaJPA> consultarDetalleProductoPorVenta(Date fechaIni, Date fechaFin);

		
	// CU consultar productos por cliente
	List<DetalleVentaJPA> consultarDetalleProductoPorCliente(String cliente);

}
