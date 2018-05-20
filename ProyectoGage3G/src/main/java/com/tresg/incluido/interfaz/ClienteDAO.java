package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.ClienteJPA;

public interface ClienteDAO {
	// CU Mantenimiento clientes
	List<ClienteJPA> listarCliente();

	ClienteJPA buscarClientePorCodigo(int codigo);
	
	String actualizarCliente(ClienteJPA cliente);
	//metodo para verificar que un cliente no se repita
	String obtenerRuc(String ruc);

}
