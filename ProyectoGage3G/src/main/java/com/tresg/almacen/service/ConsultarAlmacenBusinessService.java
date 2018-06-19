package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;

public interface ConsultarAlmacenBusinessService {

	public List<DetalleAlmacenJPA> listaDetalleAlmacen();

}
