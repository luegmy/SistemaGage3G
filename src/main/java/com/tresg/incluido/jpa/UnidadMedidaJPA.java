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
@Table(name = "tb_medida")
@NamedQueries({ @NamedQuery(name = "medida.comboUnidadMedida", query = "select m from UnidadMedidaJPA m"),
		@NamedQuery(name = "medida.buscarMedida", query = "select m from UnidadMedidaJPA m where m.descripcion like :p1") })

public class UnidadMedidaJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codMedida;
	private String descripcion;
	private String abreviatura;

	public UnidadMedidaJPA() {
		super();

	}

	public UnidadMedidaJPA(int codMedida, String descripcion, String abreviatura) {
		super();
		this.codMedida = codMedida;
		this.descripcion = descripcion;
		this.abreviatura = abreviatura;
	}

	public int getCodMedida() {
		return codMedida;
	}

	public void setCodMedida(int codMedida) {
		this.codMedida = codMedida;
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
