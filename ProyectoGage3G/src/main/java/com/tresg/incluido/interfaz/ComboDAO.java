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

public interface ComboDAO {

	List<TipoProductoJPA> comboTipoProducto() ;
	
	List<UnidadMedidaJPA> comboUnidadMedida();

	List<AlmacenJPA> comboAlamcen();
	
	List<TipoMovimientoJPA> comboTipoMovimiento();

	List<DocumentoIdentidadJPA> comboIdentidad();

	List<ComprobanteJPA> comboComprobante();

	List<MediosPagoJPA> comboPago();
	
	List<RolJPA> comboRol() ;	
	
}
