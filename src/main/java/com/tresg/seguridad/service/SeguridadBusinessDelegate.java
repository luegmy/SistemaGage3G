package com.tresg.seguridad.service;

public class SeguridadBusinessDelegate {

	private SeguridadBusinessDelegate(){}
	
	public static AutenticarUsuarioBusinessService getAutenticarUsuarioService(){
		return new AutenticarUsuarioService();
		
	}
}
