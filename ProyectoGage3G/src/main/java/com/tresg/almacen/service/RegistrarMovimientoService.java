package com.tresg.almacen.service;



import com.tresg.almacen.interfaz.MovimientoDAO;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.factoria.DAOFactory;

public class RegistrarMovimientoService implements RegistrarMovimientoBusinessService{
	
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	MovimientoDAO iAlmacen=fabrica.getMovimientoDAO();


	@Override
	public String registraMovimiento(MovimientoJPA movimiento) {
		return iAlmacen.registrarMovimiento(movimiento);
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
