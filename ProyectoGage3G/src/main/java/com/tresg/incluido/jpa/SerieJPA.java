package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_serie")

@NamedQuery(name = "serie.comboSerie", query = "select s from SerieJPA s where s.comprobante.codComprobante=:x")
public class SerieJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	private int nroSerie;
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name = "codComprobante")
	private ComprobanteJPA comprobante;

	public int getNroSerie() {
		return nroSerie;
	}

	public void setNroSerie(int nroSerie) {
		this.nroSerie = nroSerie;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
