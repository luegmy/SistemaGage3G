package com.tresg.incluido.service;

import java.util.List;

import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface GestionarProductoService_I {

	public List<ProductoJPA> listaProducto();

	public ProductoJPA buscaProductoPorCodigo(int codigo);

	public String actualizaProducto(ProductoJPA producto);

	public String eliminaProducto(int codigo);
	
	public List<UnidadMedidaJPA> buscaMedidaPorDescripcion2(String descripcion);

	public UnidadMedidaJPA buscaMedidaPorCodigo(int codigo);

	public String actualizaMedida(UnidadMedidaJPA medida);


}
