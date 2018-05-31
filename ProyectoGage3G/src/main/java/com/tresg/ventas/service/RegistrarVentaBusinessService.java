package com.tresg.ventas.service;

import java.util.List;

import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.ventas.jpa.VentaJPA;

public interface RegistrarVentaBusinessService {

	public List<VentaJPA> listaVenta();

	public int compruebaExistenciaProducto(int producto);

	public String registraVenta(VentaJPA venta);

	public String actualizaVenta(VentaJPA venta);

	public void actualizaItemVentaEliminada(ProductoJPA producto);

	public String anulaVenta(int venta);

}
