package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlProductoDAO implements ProductoDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	private void close() {
		 em.close();
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

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductoJPA> buscarProductoPorDescripcion(String descripcion) {
		open();
		Query q = em.createNamedQuery(ProductoJPA.LISTAR_PRODUCTO_POR_DESCRIPCION).setParameter("x",
				"%" + descripcion + "%");

		return q.getResultList();
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