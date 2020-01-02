package com.tresg.ventas.service;

import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface RegistrarVentaBusinessService {

	public List<VentaJPA> listaVenta();
	
	public VentaJPA obtieneVenta(int comprobante);

	public int compruebaExistenciaProducto(int producto);

	public String registraVenta(VentaJPA venta);

	public String actualizaVenta(VentaJPA venta);

	public void actualizaItemVentaEliminada(DetalleVentaJPA dv);

	public String anulaVenta(int venta);

}
