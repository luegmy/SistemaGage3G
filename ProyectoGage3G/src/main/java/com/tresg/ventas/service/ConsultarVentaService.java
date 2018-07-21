package com.tresg.ventas.service;

import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.ventas.interfaz.DetalleVentaDAO;
import com.tresg.ventas.jpa.DetalleVentaJPA;



public class ConsultarVentaService implements ConsultarVentaBusinessService {
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	DetalleVentaDAO iDetalleVentaDAO = fabrica.getDetalleVentaDAO();
	
	@Override
	public List<DetalleVentaJPA> listaDetalleVenta() {
		return iDetalleVentaDAO.listarDetalleVenta();
	}

	@Override
	public List<DetalleVentaJPA> consultaDetalleProductoPorVenta() {
		return iDetalleVentaDAO.consultarDetalleProductoPorVenta();
	}


}
