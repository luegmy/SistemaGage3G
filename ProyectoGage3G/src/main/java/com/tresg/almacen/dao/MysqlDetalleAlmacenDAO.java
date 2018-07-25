package com.tresg.almacen.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

import org.eclipse.persistence.config.QueryHints;

import com.tresg.almacen.interfaz.DetalleAlmacenDAO;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.util.jpa.JpaUtil;


public class MysqlDetalleAlmacenDAO implements DetalleAlmacenDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleAlmacenJPA> listarDetalleAlmacen() {
		open();
		Query q=em.createNamedQuery(DetalleAlmacenJPA.LISTAR_DETALLE_ALMACEN).setHint(QueryHints.REFRESH, true);
		return q.getResultList();
	}
	

}
