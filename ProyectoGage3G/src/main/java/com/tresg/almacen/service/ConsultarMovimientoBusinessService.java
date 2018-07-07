package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.almacen.jpa.MovimientoJPA;

public interface ConsultarMovimientoBusinessService {

	public List<DetalleMovimientoJPA> listaDetalleMovimiento();
	public List<MovimientoJPA> listaMovimientos();

}
