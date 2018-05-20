package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface ComboDAO {

	List<UnidadMedidaJPA> listarUnidadMedida();

	List<ProductoJPA> listarProducto(int tipo);

	List<TipoProductoJPA> listarTipoProducto();

	List<AlmacenJPA> listarAlamcen();
	
	 List<DocumentoIdentidadJPA> comboIdentidad();

}
