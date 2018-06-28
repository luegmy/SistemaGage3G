package com.tresg.almacen.interfaz;

import java.util.List;


import com.tresg.almacen.jpa.DetalleAlmacenJPA;

public interface DetalleAlmacenDAO {

	//CU excluido para productos
	//consultar cantidad de productos que hay 
	//en cada almacen
	List<DetalleAlmacenJPA> listarDetalleAlmacen();

}
