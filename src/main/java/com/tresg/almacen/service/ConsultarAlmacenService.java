package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.interfaz.DetalleAlmacenDAO;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.jpa.ProductoJPA;

public class ConsultarAlmacenService implements ConsultarAlmacenBusinessService {

	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	DetalleAlmacenDAO iAlmacen = fabrica.getDetalleAlmacenDAO();

	@Override
	public List<DetalleAlmacenJPA> listaDetalleAlmacen() {
		return iAlmacen.listarDetalleAlmacen();
	}

	@Override
	public List<ProductoJPA> listaExistencia(String descripcion) {
		return iAlmacen.listarExistencias(descripcion);
	}

}
