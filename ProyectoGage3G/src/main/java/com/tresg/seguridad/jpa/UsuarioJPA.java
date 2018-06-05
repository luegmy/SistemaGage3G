package com.tresg.seguridad.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tresg.incluido.jpa.EmpleadoJPA;

@Entity
@NamedQueries({
@NamedQuery(name="usuario.buscarUsuarioNombre",query="select u from UsuarioJPA u where u.usuario like :p1"),
@NamedQuery(name="usuario.buscarUsuario",query="select u from UsuarioJPA u where u.usuario=:x")
})

@Table(name="tb_usuario")
public class UsuarioJPA implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codUsuario;
	private String usuario;
	private String clave;
	
	@ManyToOne
	@JoinColumn(name="codRol")
	private RolJPA rol;
	
	@ManyToOne
	@JoinColumn(name="dni")
	private EmpleadoJPA empleado;
	
	
	public void setCodUsuario(int codUsuario) {
		this.codUsuario = codUsuario;
	}
	public int getCodUsuario() {
		return codUsuario;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}
	public void setClave(String clave) {
		this.clave = clave;
	}
	public RolJPA getRol() {
		return rol;
	}
	public void setRol(RolJPA rol) {
		this.rol = rol;
	}
	public EmpleadoJPA getEmpleado() {
		return empleado;
	}
	public void setEmpleado(EmpleadoJPA empleado) {
		this.empleado = empleado;
	}
	
}
