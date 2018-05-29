package com.tresg.incluido.service;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.SerieJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface ComboService_I {
	
	public List<TipoProductoJPA> listaTipoProducto() ;

	public List<UnidadMedidaJPA> listaUnidadMedida();

	public List<AlmacenJPA> listaAlamcen();

	public List<DocumentoIdentidadJPA> comboIdentidad();

	public List<ComprobanteJPA> comboComprobante();

	public List<SerieJPA> comboSerie(int comprobante);

	public List<MediosPagoJPA> comboPago();

}
