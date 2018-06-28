package com.tresg.almacen.interfaz;

import java.util.List;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;

public interface DetalleMovimientoDAO {

	//CU consultar movimientos
	List<DetalleMovimientoJPA> listarDetalleMovimiento();


}
