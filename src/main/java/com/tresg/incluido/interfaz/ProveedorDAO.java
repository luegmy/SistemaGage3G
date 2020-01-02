package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.ProveedorJPA;

public interface ProveedorDAO {
	// CU Mantenimiento proveedores
	public abstract List<ProveedorJPA> listarProveedor();

	public abstract ProveedorJPA buscarProveedorPorCodigo(int codigo);

	public abstract String actualizarProveedor(ProveedorJPA proveedor);

	public abstract String obtenerRuc(String ruc);

}
