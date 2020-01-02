package com.tresg.ventas.service;

import java.util.List;

import com.tresg.ventas.jpa.CobranzaJPA;

public interface CobranzaBusinessService {
	
	public List<CobranzaJPA> listaFacturasPorCobrar();
	public String actualizaFacturaCredito(CobranzaJPA cobranza) ;
	

}
