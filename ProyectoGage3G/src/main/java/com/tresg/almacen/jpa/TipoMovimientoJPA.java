package com.tresg.almacen.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Table(name="tb_tipomovimiento")
@NamedQuery(name = "tipoMovimiento.comboMovimiento",query="select m from TipoMovimientoJPA m")

public class TipoMovimientoJPA implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	private int codMovimiento;
	private String descripcion;
	

	public int getCodMovimiento() {
		return codMovimiento;
	}

	public void setCodMovimiento(int codMovimiento) {
		this.codMovimiento = codMovimiento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
