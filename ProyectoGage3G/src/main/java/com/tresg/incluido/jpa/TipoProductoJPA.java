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
@Table(name = "tb_tipoproducto")
@NamedQueries({ @NamedQuery(name = "tipo.comboTipoProducto", query = "select p from TipoProductoJPA p"),
		//@NamedQuery(name = "tipo.comboTipoProductoPolvo", query = "select p from TipoProductoJPA p where p.codTipo in(4,5,6)"),
		@NamedQuery(name = "tipo.buscarTipo", query = "select p from TipoProductoJPA p where p.descripcion like :p1") })
public class TipoProductoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codTipo;
	private String descripcion;

	public void setCodTipo(int codTipo) {
		this.codTipo = codTipo;
	}

	public int getCodTipo() {
		return codTipo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
