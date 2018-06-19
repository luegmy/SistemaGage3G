package com.tresg.almacen.service;

import com.tresg.almacen.jpa.MovimientoJPA;

public interface RegistrarAlmacenBusinessService {

	public int obtieneNumero();
	public int generaNumeroNota();
	public String registraMovimiento(MovimientoJPA movimiento, int destino);

}
