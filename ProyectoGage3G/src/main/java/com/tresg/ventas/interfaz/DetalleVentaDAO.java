package com.tresg.ventas.interfaz;

import java.util.Date;
import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;

public interface DetalleVentaDAO {
	
	// CU consultar productos por cliente
	List<DetalleVentaJPA> listarDetalleVenta();

	// CU consultar cantidad vendida por producto
	List<DetalleVentaJPA> consultarDetalleProductoPorVenta(Date fechaIni, Date fechaFin);


}
