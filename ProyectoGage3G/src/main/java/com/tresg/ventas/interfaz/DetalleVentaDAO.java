package com.tresg.ventas.interfaz;

import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;

public interface DetalleVentaDAO {
	
	// CU consultar ventas
	List<DetalleVentaJPA> listarDetalleVenta();

	// CU consultar cantidad vendida por producto
	List<DetalleVentaJPA> consultarDetalleProductoPorVenta();


}
