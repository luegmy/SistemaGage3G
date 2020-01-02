package com.tresg.seguridad.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.seguridad.interfaz.UsuarioDAO;
import com.tresg.seguridad.jpa.UsuarioJPA;

public class MysqlUsuarioDAO implements UsuarioDAO{
	
	EntityManagerFactory emf = null;
	EntityManager em = null;

	private void open() {
		emf = Persistence.createEntityManagerFactory("tresg");
		em = emf.createEntityManager();
	}

	private void close() {
		em.close();
		emf.close();
	}

	@SuppressWarnings({ "unchecked"})
	@Override
	public UsuarioJPA validarUsuario(String usuario) {
		open();
		List<UsuarioJPA>usuarios;
		UsuarioJPA objUsuario;
		int codigo = 0;
		
		Query q=em.createNamedQuery("usuario.buscarUsuario").setParameter("x", usuario);
		usuarios=q.getResultList();
		
		if(!usuarios.isEmpty()){
			for (UsuarioJPA u : usuarios) {
				codigo= u.getCodUsuario();
			}
		objUsuario=em.find(UsuarioJPA.class, codigo);
		}else{
			objUsuario = new UsuarioJPA();
			objUsuario.setCodUsuario(0);
			objUsuario.setUsuario("");
			objUsuario.setClave("");
			return objUsuario;
		}
		
		return objUsuario;
		
	}

	@Override
	public String registrarUsuario(EmpleadoJPA empleado,UsuarioJPA usuario)
			{
		open();
		String mensaje;
		try {
			em.getTransaction().begin();
			if (usuario.getCodUsuario()==0) {
				em.persist(empleado);
				em.persist(usuario);
				mensaje = "Empleado registrado";
			} else {
				em.merge(empleado);
				em.merge(usuario);
				mensaje = "Empleado actualizado";
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
	public List<UsuarioJPA> buscarUsuarioPorNombre(String nombre)
			{
		open();
		Query q=em.createNamedQuery("usuario.buscarUsuarioNombre").setParameter("p1","%"+nombre+"%");

		return q.getResultList();
	}

	@Override
	public UsuarioJPA buscarUsuarioPorCodigo(int codigo) {
		open();
		
		return em.find(UsuarioJPA.class, codigo);
	}

}
