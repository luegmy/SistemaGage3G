package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.ClienteJPA;

public interface ClienteDAO {
	// CU Mantenimiento clientes
	List<ClienteJPA> listarCliente();

	ClienteJPA buscarClientePorCodigo(int codigo);
	
	String actualizarCliente(ClienteJPA cliente);
	
	// metodo para cargar en la venta segun ruc
	ClienteJPA buscarClientePorRuc(String ruc);


}
