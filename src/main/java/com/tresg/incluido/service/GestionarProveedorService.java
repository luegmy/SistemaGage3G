package com.tresg.incluido.service;

import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ProveedorDAO;
import com.tresg.incluido.jpa.ProveedorJPA;


public class GestionarProveedorService implements GestionarProveedorService_I{
	
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ProveedorDAO iProveedor=fabrica.getProveedorDAO();

	@Override
	public List<ProveedorJPA> listaProveedor()
			{
		return iProveedor.listarProveedor();
	}

	@Override
	public ProveedorJPA buscaProveedorPorCodigo(int codigo) {
		return iProveedor.buscarProveedorPorCodigo(codigo);
	}

	@Override
	public String actualizaProveedor(ProveedorJPA proveedor) {
		return iProveedor.actualizarProveedor(proveedor);
	}

	@Override
	public String obtieneRuc(String ruc) {
		return iProveedor.obtenerRuc(ruc);
	}
	
	
}
