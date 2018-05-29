
package com.tresg.ventas.interfaz;

import java.util.List;

import com.tresg.ventas.jpa.CobranzaJPA;


public interface CobranzaDAO {

	// CU consultar ventas al credito
	List<CobranzaJPA> listarFacturasPorCobrar();
	
	// CU actualizar ventas al credito
	String actualizarFacturaCredito(CobranzaJPA cobranza) ;

	
}
