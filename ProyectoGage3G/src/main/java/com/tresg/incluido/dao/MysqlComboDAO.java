package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.incluido.jpa.SerieJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlComboDAO implements ComboDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TipoProductoJPA> listarTipoProducto() {
		open();
		Query q = em.createNamedQuery("tipo.comboTipoProducto");

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UnidadMedidaJPA> listarUnidadMedida() {
		open();

		Query q = em.createNamedQuery("medida.comboUnidadMedida");
		return q.getResultList();

	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<AlmacenJPA> listarAlamcen() {
		open();
		Query q = em.createNamedQuery(AlmacenJPA.COMBO_ALMACEN);

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DocumentoIdentidadJPA> comboIdentidad() {
		open();
		Query q = em.createNamedQuery("identidad.comboIdentidad");

		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteJPA> comboComprobante() {
		open();
		Query q = em.createNamedQuery("comprobante.comboComprobante");
		return q.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<MediosPagoJPA> comboPago() {
		open();
		Query q = em.createNamedQuery("pago.comboPago");

		return q.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SerieJPA> comboSerie(int comprobante) {
		open();
		Query q = em.createNamedQuery("serie.comboSerie").setParameter("x", comprobante);

		return q.getResultList();
	}
	

}
