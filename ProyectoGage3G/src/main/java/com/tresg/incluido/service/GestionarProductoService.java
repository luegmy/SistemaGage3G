package com.tresg.incluido.service;


import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.interfaz.UnidadMedidaDAO;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;


public class GestionarProductoService implements GestionarProductoService_I{
	

	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ProductoDAO iProducto=fabrica.getProductoDAO();
	UnidadMedidaDAO iMedida=fabrica.getUnidadMedidaDAO();
	
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

	@Override
	public List<UnidadMedidaJPA> buscaMedidaPorDescripcion2(String descripcion)
			 {
		return iMedida.buscarMedidaPorDescripcion2(descripcion);
	}

	@Override
	public UnidadMedidaJPA buscaMedidaPorCodigo(int codigo)  {
		return iMedida.buscarMedidaPorCodigo(codigo);
	}

	@Override
	public String actualizaMedida(UnidadMedidaJPA medida)  {
		return iMedida.actualizarMedida(medida);
	}
	
}
