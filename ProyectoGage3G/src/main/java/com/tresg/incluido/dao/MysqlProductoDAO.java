package com.tresg.incluido.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.util.jpa.JpaUtil;
import com.tresg.ventas.jpa.DetalleVentaJPA;

public class MysqlProductoDAO implements ProductoDAO {
	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}

	private void close() {
		em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoJPA> listarProducto() {
		open();

		Query q = em.createNamedQuery(ProductoJPA.LISTAR_PRODUCTO);
		return q.getResultList();
	}
	
	@Override
	public ProductoJPA buscarProductoPorCodigo(int codigo) {
		open();
		return em.find(ProductoJPA.class, codigo);
	}

	@Override
	public String actualizarProducto(ProductoJPA producto) {
		open();
		String mensaje;
		try {
			em.getTransaction().begin();
			if (producto.getCodProducto() == 0) {
				em.persist(producto);
				mensaje = "Producto registrado";
			} else {
				em.merge(producto);
				mensaje = "Producto actualizado";
			}
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			close();
		}

		return mensaje;
	}

	@Override
	public String eliminarProducto(int codigo) {
		open();
		ProductoJPA objProducto = em.find(ProductoJPA.class, codigo);
		try {
			em.getTransaction().begin();
			em.remove(objProducto);
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			close();
		}

		return "Producto eliminado";
	}

}