package com.tresg.seguridad.service;

import java.util.List;

import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;


public interface AutenticarUsuarioBusinessService {

	public UsuarioJPA validaUsuario(String usuario) ;
	public String registraUsuario(EmpleadoJPA empleado,UsuarioJPA usuario);
	public List<UsuarioJPA> buscaUsuarioPorNombre(String nombre);
	public UsuarioJPA buscaUsuarioPorCodigo(int codigo) ;
	

}
