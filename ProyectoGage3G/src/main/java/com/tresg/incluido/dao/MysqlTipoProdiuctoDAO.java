package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

import com.tresg.incluido.interfaz.TipoProductoDAO;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlTipoProdiuctoDAO implements TipoProductoDAO {

	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	private void close() {
		 em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TipoProductoJPA> buscarTipoPorDescripcion2(String descripcion)
			 {
		open();
		Query q = em.createNamedQuery("tipo.buscarTipo").setParameter("p1",
				"%" + descripcion + "%");
		
		return q.getResultList();
	}

	@Override
	public TipoProductoJPA buscarTipoPorCodigo(int codigo)  {
		open();
		
		return em.find(TipoProductoJPA.class, codigo);
	}

	@Override
	public String actualizarTipo(TipoProductoJPA tipo)  {
		open();
		String mensaje;
		try {
			em.getTransaction().begin();
			if (tipo.getCodTipo() == 0) {
				em.persist(tipo);
				mensaje = "Tipo de producto registrado";
			} else {
				em.merge(tipo);
				mensaje = "Tipo de producto actualizado";
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

}
