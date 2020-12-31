package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_clase")
@NamedQuery(name = "clase.comboClase", query = "select c from ClaseJPA c")
public class ClaseJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codClase;
	private String descripcion;

	public int getCodClase() {
		return codClase;
	}

	public void setCodClase(int codClase) {
		this.codClase = codClase;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
