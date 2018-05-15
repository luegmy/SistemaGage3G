package com.tresg.incluido.service;

import java.util.List;

import com.tresg.incluido.jpa.ProductoJPA;


public interface GestionarProductoService_I {

	public List<ProductoJPA> buscaProductoPorDescripcion(String descripcion);

	public ProductoJPA buscaProductoPorCodigo(int codigo);

	public String actualizaProducto(ProductoJPA producto);

	public String eliminaProducto(int codigo);


}
