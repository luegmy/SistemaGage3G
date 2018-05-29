package com.tresg.incluido.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.Query;

import com.tresg.incluido.interfaz.ClienteDAO;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.util.jpa.JpaUtil;

public class MysqlClienteDAO implements ClienteDAO {
	EntityManager em = null;
	private void open() {
		 em = JpaUtil.getEntityManager();
	}
	
	private void close() {
		 em.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClienteJPA> listarCliente() {
		open();
		Query q = em.createNamedQuery(ClienteJPA.LISTAR_CLIENTE);
		return q.getResultList();
	}

	@Override
	public ClienteJPA buscarClientePorCodigo(int codigo) {
		open();
		return em.find(ClienteJPA.class, codigo);
	}

	@Override
	public String actualizarCliente(ClienteJPA cliente) {
		open();
		String mensaje;

		try {
			em.getTransaction().begin();
			if (cliente.getCodCliente() == 0) {
				em.persist(cliente);
				mensaje = "Cliente registrado";
			} else {
				em.merge(cliente);
				mensaje = "Cliente actualizado";
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
	public ClienteJPA buscarClientePorRuc(String ruc) {
		open();
		Query q = em.createNamedQuery(ClienteJPA.BUSCAR_CLIENTE_RUC).setParameter("p1", ruc);	
		if(!q.getResultList().isEmpty()){
			return (ClienteJPA) q.getResultList().get(0);
		}	
		return null;
	}

}