package com.tresg.ventas.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tb_notacredito")
@NamedQuery(name = "notaCredito.comboNotaCredito", query = "select o from NotaCreditoJPA o")
public class NotaCreditoJPA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String codCredito;
	private String descripcion;
	

	public String getCodCredito() {
		return codCredito;
	}
	public void setCodCredito(String codCredito) {
		this.codCredito = codCredito;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	

	

}
