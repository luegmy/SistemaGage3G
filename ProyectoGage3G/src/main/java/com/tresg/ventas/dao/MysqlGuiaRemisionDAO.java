package com.tresg.ventas.dao;


import javax.persistence.EntityManager;

import com.tresg.util.jpa.JpaUtil;
import com.tresg.ventas.interfaz.GuiaRemisionDAO;
import com.tresg.ventas.jpa.GuiaRemisionJPA;

public class MysqlGuiaRemisionDAO implements GuiaRemisionDAO {
	EntityManager em = null;

	private void open() {
		em = JpaUtil.getEntityManager();
	}

	private void close() {
		em.close();
	}
	
	
	@Override
	public String registrarGuia(GuiaRemisionJPA guia)  {
		open();
		
		em.getTransaction().begin();
		try {
			em.persist(guia); 
		} catch (RuntimeException e) {
			em.getTransaction().rollback();
			throw e;
		}		
		em.getTransaction().commit();
		close();
		
		return "Guia de remision registrada exitosamente";
	}



}
