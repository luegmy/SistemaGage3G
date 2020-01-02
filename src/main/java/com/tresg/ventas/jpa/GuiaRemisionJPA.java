package com.tresg.ventas.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_guiaremision")
@NamedQuery(name = "guia.mostrarNumeroGuia", query = "select max(g.numGuia)+1 from GuiaRemisionJPA g")
public class GuiaRemisionJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private int numGuia;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	
	@OneToOne()
	@JoinColumn(name="numComprobante")
	private VentaJPA venta;
	
	public int getNumGuia() {
		return numGuia;
	}

	public void setNumGuia(int numGuia) {
		this.numGuia = numGuia;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public VentaJPA getVenta() {
		return venta;
	}

	public void setVenta(VentaJPA venta) {
		this.venta = venta;
	}
	
	
}
