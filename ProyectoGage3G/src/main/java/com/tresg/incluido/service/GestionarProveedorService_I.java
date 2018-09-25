package com.tresg.incluido.service;

import java.util.List;

import com.tresg.incluido.jpa.ProveedorJPA;


public interface GestionarProveedorService_I {
	
	public List<ProveedorJPA> listaProveedor() ;
	public ProveedorJPA buscaProveedorPorCodigo(int codigo) ;
	public String actualizaProveedor(ProveedorJPA proveedor) ;
	public String obtieneRuc(String ruc) ;
	


}
