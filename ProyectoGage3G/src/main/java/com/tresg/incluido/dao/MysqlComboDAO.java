package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tresg.almacen.jpa.AlmacenJPA;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;

public class MysqlComboDAO implements ComboDAO {
	EntityManagerFactory emf = null;
	EntityManager em = null;

	private void open() {
		emf = Persistence.createEntityManagerFactory("tresg");
		em = emf.createEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoJPA> listarProducto(int tipo) {
		open();
		Query q = em.createNamedQuery("producto.comboProducto").setParameter("x", tipo);

		return q.getResultList();
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

	
	

}
