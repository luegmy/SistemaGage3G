package com.tresg.incluido.service;


import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.jpa.ProductoJPA;


public class GestionarProductoService implements GestionarProductoService_I{
	

	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ProductoDAO iProducto=fabrica.getProductoDAO();
	
	
	@Override
	public List<ProductoJPA> listaProducto()  {
		return iProducto.listarProducto();
	}
	
	@Override
	public ProductoJPA buscaProductoPorCodigo(int codigo)
			 {
		return iProducto.buscarProductoPorCodigo(codigo);
	}


	@Override
	public String actualizaProducto(ProductoJPA producto)  {
		return iProducto.actualizarProducto(producto);
	}

	
	@Override
	public String eliminaProducto(int codigo) {
		return iProducto.eliminarProducto(codigo);
	}

	
}
