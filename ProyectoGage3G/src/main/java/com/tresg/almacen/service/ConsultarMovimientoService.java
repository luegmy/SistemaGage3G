package com.tresg.almacen.service;

import java.util.List;

import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.almacen.interfaz.MovimientoDAO;
import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.factoria.DAOFactory;

public class ConsultarMovimientoService implements ConsultarMovimientoBusinessService {

	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	DetalleMovimientoDAO iDetalleMovimiento = fabrica.getDetalleMovimientoDAO();
	MovimientoDAO iMovimiento=fabrica.getMovimientoDAO();
	
	@Override
	public List<DetalleMovimientoJPA> listaDetalleMovimiento() {
		return iDetalleMovimiento.listarDetalleMovimiento();
	}


}
