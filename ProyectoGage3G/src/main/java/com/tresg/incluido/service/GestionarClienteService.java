package com.tresg.incluido.service;

import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ClienteDAO;
import com.tresg.incluido.jpa.ClienteJPA;


public class GestionarClienteService implements GestionarClienteService_I{
	
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ClienteDAO iCliente=fabrica.getClienteDAO();

	@Override
	public List<ClienteJPA> listaCliente()
			 {
		return iCliente.listarCliente();
	}

	@Override
	public ClienteJPA buscaClientePorCodigo(int codigo)  {
		return iCliente.buscarClientePorCodigo(codigo);
	}

	@Override
	public String actualizaCliente(ClienteJPA cliente)  {
		return iCliente.actualizarCliente(cliente);
	}
	
	@Override
	public String obtieneRuc(String ruc)  {
		return iCliente.obtenerRuc(ruc);
	}
	
	
}
