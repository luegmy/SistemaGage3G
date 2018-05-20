package com.tresg.incluido.service;

public class IncluidoBusinessDelegate {

	private IncluidoBusinessDelegate() {
	}

	public static GestionarProductoService_I getGestionarProductoService() {
		return new GestionarProductoService();

	}
	
	public static ComboService_I getComboService() {
		return new ComboService();

	}
	
	public static GestionarClienteService_I getGestionarClienteService(){
		return new GestionarClienteService();
		
	}
}
