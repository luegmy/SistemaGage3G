package com.tresg.almacen.service;

public class AlmacenBusinessDelegate {
	
	private AlmacenBusinessDelegate(){}
	
	public static RegistrarMovimientoBusinessService getRegistrarAlmacenService(){
		return new RegistrarMovimientoService();	
	}
	
	public static ConsultarAlmacenBusinessService getConsultarAlmacenService(){
		return new ConsultarAlmacenService();	
	}
	
	public static ConsultarMovimientoBusinessService getConsultarMovimientoService(){
		return new ConsultarMovimientoService();	
	}

}
