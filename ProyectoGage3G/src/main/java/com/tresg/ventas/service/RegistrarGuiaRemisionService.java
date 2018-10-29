package com.tresg.ventas.service;

import com.tresg.factoria.DAOFactory;
import com.tresg.ventas.interfaz.GuiaRemisionDAO;
import com.tresg.ventas.jpa.GuiaRemisionJPA;

public class RegistrarGuiaRemisionService implements RegistrarGuiaRemisionBusinessService {
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	GuiaRemisionDAO iGuiaRemisionDAO = fabrica.getGuiaRemisionDAO();

	@Override
	public String registraGuia(GuiaRemisionJPA guia) {
		return iGuiaRemisionDAO.registrarGuia(guia);
	}

}
