package com.tresg.incluido.interfaz;

import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.SerieJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public interface ComboDAO {

	List<TipoProductoJPA> listarTipoProducto() ;
	
	List<UnidadMedidaJPA> listarUnidadMedida();

	List<AlmacenJPA> listarAlamcen();

	List<DocumentoIdentidadJPA> comboIdentidad();

	List<ComprobanteJPA> comboComprobante();

	List<MediosPagoJPA> comboPago();

	List<SerieJPA> comboSerie(int comprobante);

}
