package com.tresg.incluido.service;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface ComboService_I {

	public List<UnidadMedidaJPA> listaUnidadMedida();

	public List<ProductoJPA> listaProducto(int tipo);

	public List<TipoProductoJPA> listaTipoProducto();

	public List<AlmacenJPA> listaAlamcen();
	
	public List<DocumentoIdentidadJPA> comboIdentidad();

}
