package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.TipoMovimientoJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.seguridad.jpa.RolJPA;
import com.tresg.ventas.jpa.NotaCreditoJPA;
import com.tresg.ventas.jpa.NotaDebitoJPA;
import com.tresg.ventas.jpa.OperacionJPA;

public interface ComboDAO {

	List<TipoProductoJPA> comboTipoProducto() ;
	
	List<UnidadMedidaJPA> comboUnidadMedida();

	List<AlmacenJPA> comboAlamcen();
	
	List<TipoMovimientoJPA> comboTipoMovimiento();

	List<DocumentoIdentidadJPA> comboIdentidad();

	List<ComprobanteJPA> comboComprobante();
	
	List<OperacionJPA> comboOperacion();
	
	List<NotaCreditoJPA> comboCredito();
	
	List<NotaDebitoJPA> comboDebito();

	List<MediosPagoJPA> comboPago();
	
	List<RolJPA> comboRol() ;	
	
}
