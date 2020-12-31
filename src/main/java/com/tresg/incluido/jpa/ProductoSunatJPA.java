package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_productosunat")

@NamedQuery(name = "prodSunat.comboProdSunat", query = "select p from ProductoSunatJPA p")
public class ProductoSunatJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProdSunat;
	private String descripcion;

	@ManyToOne
	@JoinColumn(name = "codClase")
	private ClaseJPA clase;

	public int getCodProdSunat() {
		return codProdSunat;
	}

	public void setCodProdSunat(int codProdSunat) {
		this.codProdSunat = codProdSunat;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ClaseJPA getClase() {
		return clase;
	}

	public void setClase(ClaseJPA clase) {
		this.clase = clase;
	}

}
