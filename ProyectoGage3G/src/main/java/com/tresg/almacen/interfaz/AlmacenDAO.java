package com.tresg.almacen.interfaz;


import java.util.List;

import com.tresg.almacen.jpa.MovimientoJPA;

public interface AlmacenDAO {

	// CU Registrar ingreso o salida almacen
	// obtener el numero del movimiento
	int obtenerNumero();

	// generar la numeracion de la nota de movimiento
	int generarNumeroNota();

	String registrarMovimiento(MovimientoJPA movimiento, int destino);
	
	// CU Consultar kardex
	List<MovimientoJPA> listarMovimiento() ;

}
