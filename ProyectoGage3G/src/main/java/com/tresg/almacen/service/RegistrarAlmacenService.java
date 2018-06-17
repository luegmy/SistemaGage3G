package com.tresg.almacen.service;


import java.util.List;

import com.tresg.almacen.interfaz.AlmacenDAO;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.factoria.DAOFactory;

public class RegistrarAlmacenService implements RegistrarAlmacenBusinessService{
	
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	AlmacenDAO iAlmacen=fabrica.getAlmacenDAO();


	@Override
	public String registraMovimiento(MovimientoJPA movimiento,int destino) {
		return iAlmacen.registrarMovimiento(movimiento,destino);
	}

	@Override
	public int obtieneNumero() {
		return iAlmacen.obtenerNumero();
	}
	
	@Override
	public int generaNumeroNota(){
		return iAlmacen.generarNumeroNota();
	}



}
