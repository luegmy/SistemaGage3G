package com.tresg.almacen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;

import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlDetalleMovimientoDAO implements DetalleMovimientoDAO {
	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleMovimientoJPA> listarDetalleMovimiento() {
		open();
		Query q = em.createNamedQuery(DetalleMovimientoJPA.LISTAR_DETALLE_MOVIMIENTOS).setHint(QueryHints.REFRESH, true);
		
		return q.getResultList();
	}

}
