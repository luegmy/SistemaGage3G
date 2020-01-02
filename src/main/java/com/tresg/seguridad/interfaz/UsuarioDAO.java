package com.tresg.seguridad.interfaz;

import java.util.List;

import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;

public interface UsuarioDAO {

	UsuarioJPA validarUsuario(String usuario);

	List<UsuarioJPA> buscarUsuarioPorNombre(String nombre);

	UsuarioJPA buscarUsuarioPorCodigo(int codigo);

	String registrarUsuario(EmpleadoJPA empleado, UsuarioJPA usuario);

}
