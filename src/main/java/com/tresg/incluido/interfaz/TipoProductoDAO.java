package com.tresg.incluido.interfaz;


import java.util.List;

import com.tresg.incluido.jpa.TipoProductoJPA;



public interface TipoProductoDAO {
	public abstract List<TipoProductoJPA> buscarTipoPorDescripcion2(String descripcion);
	public abstract TipoProductoJPA buscarTipoPorCodigo(int codigo) ;
	public abstract String actualizarTipo(TipoProductoJPA tipo) ;
}
