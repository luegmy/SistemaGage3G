package com.tresg.ventas.service;

public class VentasBusinessDelegate {
	
	private VentasBusinessDelegate(){}
	
	public static RegistrarVentaBusinessService getRegistrarVentaService(){
		return new RegistrarVentaService();
		
	}
	
}
