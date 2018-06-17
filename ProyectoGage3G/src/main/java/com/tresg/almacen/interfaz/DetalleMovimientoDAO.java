package com.tresg.almacen.interfaz;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;

public interface DetalleMovimientoDAO {

	// CU Consultar productos por almacen
	AlmacenJPA consultarAlmacen(int almacen);

	
	
	//CU incluido para ventas
	List<DetalleAlmacenJPA> listarProductoPorAlmacen(String descripcion);


}
