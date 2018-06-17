package com.tresg.almacen.service;


import java.util.List;

import com.tresg.almacen.interfaz.DetalleAlmacenDAO;
import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.factoria.DAOFactory;

public class ConsultarAlmacenService implements ConsultarAlmacenBusinessService {

	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	DetalleAlmacenDAO iDetalleAlmacen = fabrica.getDetalleAlmacenDAO();

	
	@Override
	public List<DetalleAlmacenJPA> listaProductoPorAlmacen(String descripcion) {
		return iDetalleAlmacen.listarProductoPorAlmacen(descripcion);
	}

}
