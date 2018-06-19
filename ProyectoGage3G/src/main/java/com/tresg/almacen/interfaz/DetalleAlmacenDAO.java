package com.tresg.almacen.interfaz;

import java.util.List;


import com.tresg.almacen.jpa.DetalleAlmacenJPA;

public interface DetalleAlmacenDAO {

	//CU excluido para productos
	List<DetalleAlmacenJPA> listarDetalleAlmacen();


}
