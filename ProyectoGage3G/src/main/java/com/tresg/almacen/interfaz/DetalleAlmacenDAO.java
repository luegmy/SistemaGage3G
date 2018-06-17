package com.tresg.almacen.interfaz;

import java.util.List;


import com.tresg.almacen.jpa.DetalleAlmacenJPA;

public interface DetalleAlmacenDAO {

	//CU incluido para ventas
	List<DetalleAlmacenJPA> listarProductoPorAlmacen(String descripcion);


}
