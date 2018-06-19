package com.tresg.almacen.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

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
		Query q=em.createNamedQuery(DetalleAlmacenJPA.LISTAR_DETALLE_ALMACEN);
		List<DetalleAlmacenJPA>lista=q.getResultList();
		DetalleAlmacenJPA objDetalle=null;
		for (DetalleAlmacenJPA d : lista) {
			objDetalle=em.find(DetalleAlmacenJPA.class, d.getId());
		}
		em.refresh(objDetalle);
		return lista;
	}
	

}
