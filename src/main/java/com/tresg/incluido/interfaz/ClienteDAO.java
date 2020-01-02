package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.ClienteJPA;

public interface ClienteDAO {
	// CU Mantenimiento clientes
	List<ClienteJPA> listarCliente();

	ClienteJPA buscarClientePorCodigo(int codigo);
	
	ClienteJPA buscarClientePorRuc(String ruc);
	
	String actualizarCliente(ClienteJPA cliente);
	
	


}
