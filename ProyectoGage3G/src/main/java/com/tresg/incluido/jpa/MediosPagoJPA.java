package com.tresg.incluido.jpa;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_medio_pago")
@NamedQuery(name = "pago.comboPago", query = "select p from MediosPagoJPA p")
public class MediosPagoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int codPago;
	private String descripcion;

	public int getCodPago() {
		return codPago;
	}

	public void setCodPago(int codPago) {
		this.codPago = codPago;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
