package com.tresg.almacen.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.util.jpa.JpaUtil;


public class MysqlDetalleMovimientoDAO implements DetalleMovimientoDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	

	@Override
	public AlmacenJPA consultarAlmacen(int almacen)  {
		open();
			
		AlmacenJPA objAlmacen=(AlmacenJPA) em.find(AlmacenJPA.class, almacen);
		em.refresh(objAlmacen);
		return objAlmacen;
	}

	

	@SuppressWarnings("unchecked")
	@Override
	public List<DetalleAlmacenJPA> listarProductoPorAlmacen(String descripcion) {
		open();
		Query q=em.createNamedQuery(DetalleAlmacenJPA.LISTAR_DETALLE_ALMACEN).setParameter("x", "%"+ descripcion+ "%");
		List<DetalleAlmacenJPA>lista=q.getResultList();
		DetalleAlmacenJPA objDetalle=null;
		for (DetalleAlmacenJPA d : lista) {
			objDetalle=em.find(DetalleAlmacenJPA.class, d.getId());
		}
		em.refresh(objDetalle);
		return lista;
	}
	

}
