package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.incluido.jpa.ProductoJPA;

public interface ConsultarAlmacenBusinessService {

	public List<DetalleAlmacenJPA> listaDetalleAlmacen();
	
	public List<ProductoJPA> listaExistencia(String descripcion);

}
