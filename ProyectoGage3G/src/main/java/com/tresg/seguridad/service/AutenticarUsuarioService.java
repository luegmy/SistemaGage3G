package com.tresg.seguridad.service;


import java.util.List;

import com.tresg.factoria.DAOFactory;
import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.seguridad.interfaz.UsuarioDAO;
import com.tresg.seguridad.jpa.UsuarioJPA;



public class AutenticarUsuarioService implements
		AutenticarUsuarioBusinessService {
	DAOFactory fabrica=DAOFactory.getDAOFactory(DAOFactory.MYSQL);
	UsuarioDAO iUsuarioDAO=fabrica.getUsuarioDAO();
	
	@Override
	public UsuarioJPA validaUsuario(String usuario)  {
		return iUsuarioDAO.validarUsuario(usuario);
	}

	@Override
	public String registraUsuario(EmpleadoJPA empleado,UsuarioJPA usuario)  {
		return iUsuarioDAO.registrarUsuario(empleado,usuario);
	}

	@Override
	public List<UsuarioJPA> buscaUsuarioPorNombre(String nombre)
			 {
		return iUsuarioDAO.buscarUsuarioPorNombre(nombre);
	}

	@Override
	public UsuarioJPA buscaUsuarioPorCodigo(int codigo)  {
		return iUsuarioDAO.buscarUsuarioPorCodigo(codigo);
	}

	
	
}
