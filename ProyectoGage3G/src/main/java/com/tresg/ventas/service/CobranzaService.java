package com.tresg.ventas.service;

import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.ventas.interfaz.CobranzaDAO;
import com.tresg.ventas.jpa.CobranzaJPA;

public class CobranzaService implements CobranzaBusinessService{
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	CobranzaDAO iCobranzaDAO=fabrica.getCobranzaDAO();
	

	@Override
	public List<CobranzaJPA> listaFacturasPorCobrar()
			 {
		return iCobranzaDAO.listarFacturasPorCobrar();
	}
	@Override
	public String actualizaFacturaCredito(CobranzaJPA cobranza)
			 {
		return iCobranzaDAO.actualizarFacturaCredito(cobranza);
	}
}
