package com.tresg.incluido.service;

import java.io.Serializable;
import java.util.List;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.SerieJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.seguridad.jpa.RolJPA;


public class ComboService implements ComboService_I,Serializable {


	private static final long serialVersionUID = 1L;
	
	DAOFactory fabrica = DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	ComboDAO iComboDAO = fabrica.getComboDAO();
	
	@Override
	public List<TipoProductoJPA> listaTipoProducto() {
		return iComboDAO.listarTipoProducto();
	}

	@Override
	public List<UnidadMedidaJPA> listaUnidadMedida() {
		return iComboDAO.listarUnidadMedida();
	}


	@Override
	public List<AlmacenJPA> listaAlamcen() {
		return iComboDAO.listarAlamcen();
	}
	
	@Override
	public List<DocumentoIdentidadJPA> comboIdentidad() {
		return iComboDAO.comboIdentidad();
	}
	
	@Override
	public List<ComprobanteJPA> comboComprobante() {
		return iComboDAO.comboComprobante();
	}
	
	@Override
	public List<SerieJPA> comboSerie(int comprobante) {
		return iComboDAO.comboSerie(comprobante);
	}

	@Override
	public List<MediosPagoJPA> comboPago() {
		return iComboDAO.comboPago();
	}

	@Override
	public List<RolJPA> comboRol() {
		return iComboDAO.comboRol();
	}
}
