package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_moneda")

@NamedQuery(name = "moneda.comboMoneda", query = "select c from MonedaJPA c")
public class MonedaJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int codMoneda;
	private String descripcion;
	private String abreviatura;

	public int getCodMoneda() {
		return codMoneda;
	}

	public void setCodMoneda(int codMoneda) {
		this.codMoneda = codMoneda;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

}
