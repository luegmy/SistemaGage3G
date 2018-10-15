package com.tresg.incluido.service;

import java.util.List;

import com.tresg.incluido.jpa.ClienteJPA;

public interface GestionarClienteService_I {

	public List<ClienteJPA> listaCliente();

	public ClienteJPA buscaClientePorCodigo(int codigo);
	
	public ClienteJPA buscaClientePorRuc(String ruc);

	public String actualizaCliente(ClienteJPA cliente);

}
