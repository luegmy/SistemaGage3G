package com.tresg.incluido.service;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.TipoMovimientoJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.seguridad.jpa.RolJPA;
import com.tresg.ventas.jpa.OperacionJPA;

public interface ComboService_I {
	
	public List<TipoProductoJPA> comboTipoProducto() ;

	public List<UnidadMedidaJPA> comboUnidadMedida();

	public List<AlmacenJPA> comboAlamcen();
	
	public List<TipoMovimientoJPA> comboTipoMovimiento();

	public List<DocumentoIdentidadJPA> comboIdentidad();

	public List<ComprobanteJPA> comboComprobante();
	
	public List<OperacionJPA> comboOperacion();

	public List<MediosPagoJPA> comboPago();
	
	public List<RolJPA> comboRol();

}
