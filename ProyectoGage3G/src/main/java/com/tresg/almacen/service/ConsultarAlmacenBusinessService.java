package com.tresg.almacen.service;


import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.almacen.jpa.MovimientoJPA;

public interface ConsultarAlmacenBusinessService {


	public List<DetalleAlmacenJPA> listaProductoPorAlmacen(String descripcion);
}
