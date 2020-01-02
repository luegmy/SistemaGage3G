package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.tresg.incluido.interfaz.ProveedorDAO;
import com.tresg.incluido.jpa.ProveedorJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlProveedorDAO implements ProveedorDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProveedorJPA> listarProveedor() {
		open();
		Query q = em.createNamedQuery(ProveedorJPA.LISTAR_PROVEEDOR);
		
		return q.getResultList();
	}

	@Override
	public ProveedorJPA buscarProveedorPorCodigo(int codigo) {
		open();
		
		return em.find(ProveedorJPA.class, codigo);
	}

	@Override
	public String actualizarProveedor(ProveedorJPA proveedor) {
		open();
		String mensaje;
		try {
			em.getTransaction().begin();
			if (proveedor.getCodProveedor() == 0) {
				em.persist(proveedor);
				mensaje = "Proveedor registrado";
			} else {
				em.merge(proveedor);
				mensaje = "Proveedor actualizado";
			}
			em.getTransaction().commit();
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		} finally {
			em.close();
		}

		return mensaje;
	}

	@Override
	public String obtenerRuc(String ruc) {
		open();

		Query q = em.createNamedQuery("proveedor.obtenerProveedorRuc").setParameter("p1", ruc);
		if(!q.getResultList().isEmpty()){
			return (String) q.getResultList().get(0);
		}
		return null;


	}
}