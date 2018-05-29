package com.tresg.ventas.service;


import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.ventas.interfaz.VentaDAO;
import com.tresg.ventas.jpa.VentaJPA;

public class RegistrarVentaService implements RegistrarVentaBusinessService {
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	VentaDAO iVentaDAO = fabrica.getVentaDAO();
	
	@Override
	public List<VentaJPA> listaVenta() {
		return iVentaDAO.listarVenta();
	}

	@Override
	public VentaJPA validaNumeroComprobantePago(int comprobante) {
		return iVentaDAO.validarNumeroComprobantePago(comprobante);
	}

	@Override
	public int compruebaExistenciaProducto(int producto)  {
		return iVentaDAO.comprobarExistenciaProducto(producto);
	}

	@Override
	public String registraVenta(VentaJPA venta)  {
		return iVentaDAO.registrarVenta(venta);
	}

	@Override
	public String actualizaVenta(VentaJPA venta) {
		return iVentaDAO.actualizarVenta(venta);
	}

	@Override
	public void actualizaItemVentaEliminada(ProductoJPA producto) {
		iVentaDAO.actualizarItemVentaEliminada(producto);
		
	}
	
	@Override
	public String anulaVenta(int venta) {
		return iVentaDAO.anularVenta(venta);
	}

}
