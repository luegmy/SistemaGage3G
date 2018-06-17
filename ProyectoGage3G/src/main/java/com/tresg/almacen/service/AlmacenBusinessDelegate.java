package com.tresg.almacen.service;

public class AlmacenBusinessDelegate {
	
	private AlmacenBusinessDelegate(){}
	
	public static RegistrarAlmacenBusinessService getRegistrarAlmacenService(){
		return new RegistrarAlmacenService();	
	}
	public static ConsultarAlmacenBusinessService getConsultarAlmacenService(){
		return new ConsultarAlmacenService();	
	}
}
