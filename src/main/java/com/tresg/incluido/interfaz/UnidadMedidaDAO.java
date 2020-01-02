package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface UnidadMedidaDAO {

	List<UnidadMedidaJPA> buscarMedidaPorDescripcion2(String descripcion);

	UnidadMedidaJPA buscarMedidaPorCodigo(int codigo);

	String obtenerDescripcionMedida(int medida);

	String actualizarMedida(UnidadMedidaJPA medida);
}
