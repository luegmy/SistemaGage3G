package com.tresg.almacen.interfaz;


import com.tresg.almacen.jpa.MovimientoJPA;

public interface MovimientoDAO {

	// CU Registrar ingreso o salida almacen
	
	// obtener el numero del movimiento
	int obtenerNumeroMovimiento();

	// generar la numeracion de la nota de movimiento
	int generarNumeroNota();

	String registrarMovimiento(MovimientoJPA movimiento);


}
