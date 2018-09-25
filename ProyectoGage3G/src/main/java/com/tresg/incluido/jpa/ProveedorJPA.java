package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import javax.persistence.Table;


@Entity
@NamedQueries({
		@NamedQuery(name = "proveedor.listarProveedor", query = "select c from ProveedorJPA c"),
		@NamedQuery(name = "proveedor.obtenerProveedorRuc", query = "select c.ruc from ProveedorJPA c where c.ruc= :p1") })
@Table(name = "tb_proveedor")

public class ProveedorJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String LISTAR_PROVEEDOR = "proveedor.listarProveedor";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProveedor;
	private String nombre;
	private String direccion;
	private String ruc;
	private String telefono;
	private String correo;

	public int getCodProveedor() {
		return codProveedor;
	}

	public void setCodProveedor(int codProveedor) {
		this.codProveedor = codProveedor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

}
