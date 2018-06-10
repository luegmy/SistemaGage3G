package com.tresg.ventas.interfaz;

import java.util.Date;
import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface DetalleVentaDAO {
	
	// CU consultar productos por cliente
	List<DetalleVentaJPA> listarDetalleVenta();

	// CU consultar productos por venta
	List<DetalleVentaJPA> consultarDetalleProductoPorVenta(Date fechaIni, Date fechaFin);


}
