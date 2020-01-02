package com.tresg.ventas.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tb_notadebito")
@NamedQuery(name = "notaDebito.comboNotaDebito", query = "select o from NotaDebitoJPA o")
public class NotaDebitoJPA implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private String codDebito;
	private String descripcion;
	
	public String getCodDebito() {
		return codDebito;
	}
	public void setCodDebito(String codDebito) {
		this.codDebito = codDebito;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	

}
