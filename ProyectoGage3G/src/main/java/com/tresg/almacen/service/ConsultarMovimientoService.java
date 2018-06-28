package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.factoria.DAOFactory;

public class ConsultarMovimientoService implements ConsultarMovimientoBusinessService {

	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	DetalleMovimientoDAO iMovimiento = fabrica.getDetalleMovimientoDAO();
	
	@Override
	public List<DetalleMovimientoJPA> listaMovimiento() {
		return iMovimiento.listarDetalleMovimiento();
	}


}
