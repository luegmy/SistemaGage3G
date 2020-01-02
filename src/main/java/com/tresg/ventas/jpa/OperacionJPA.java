package com.tresg.ventas.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tb_operacion")
@NamedQuery(name = "operacion.comboOperacion", query = "select o from OperacionJPA o")
public class OperacionJPA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String codOperacion;
	private String descripcion;
	
	
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
