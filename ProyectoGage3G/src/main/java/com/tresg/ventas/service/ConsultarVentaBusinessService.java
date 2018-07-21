package com.tresg.ventas.service;

import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;


public interface ConsultarVentaBusinessService {

	public List<DetalleVentaJPA> listaDetalleVenta();

	public List<DetalleVentaJPA> consultaDetalleProductoPorVenta();

}
