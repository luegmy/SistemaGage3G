package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;

public interface ConsultarMovimientoBusinessService {

	public List<DetalleMovimientoJPA> listaMovimiento();

}
