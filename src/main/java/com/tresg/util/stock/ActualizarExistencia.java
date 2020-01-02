package com.tresg.util.stock;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.util.jpa.JpaUtil;

public class ActualizarExistencia {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	private void close() {
		 em.close();
	}
	
	public void actualizarAlmacenDecremento(BigDecimal cantidad, DetalleAlmacenJPA almacen) {
		open();
		
		em.getTransaction().begin();
		Query q3 = em.createNamedQuery(DetalleAlmacenJPA.ACTUALIZAR_ALMACEN_DECREMENTO);
		q3.setParameter("p1", cantidad);
		q3.setParameter("p2", almacen.getId());
		q3.executeUpdate();
		em.getTransaction().commit();
		close();
	}
	
	public void actualizarAlmacenIncremento(BigDecimal cantidad, DetalleAlmacenJPA almacen) {
		open();
		
		em.getTransaction().begin();
		Query q3 = em.createNamedQuery(DetalleAlmacenJPA.ACTUALIZAR_ALMACEN_INCREMENTO);
		q3.setParameter("p1", cantidad);
		q3.setParameter("p2", almacen.getId());
		q3.executeUpdate();
		em.getTransaction().commit();
		close();
	}

}
