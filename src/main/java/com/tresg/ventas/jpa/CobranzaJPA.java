package com.tresg.ventas.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tb_cobranza")
@NamedQueries({
		@NamedQuery(name = "cobranza.monto", query = "select c.montoSaldo from CobranzaJPA c where c.id.numComprobante=:p and c.id.numCobranza=(select max(k.id.numCobranza)from CobranzaJPA k where k.id.numComprobante=:p)"),
		@NamedQuery(name = "cobranza.numero", query = "select max(c.id.numCobranza)+1 from CobranzaJPA c where c.id.numComprobante= :p"),
		@NamedQuery(name = "cobranza.listarCobranza", query = "select c from CobranzaJPA c") })
public class CobranzaJPA implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String MONTO_SALDO = "cobranza.monto";
	public static final String GENERA_NUMERO_LETRA = "cobranza.numero";
	public static final String LISTAR_COBRANZA = "cobranza.listarCobranza";

	@EmbeddedId
	private CobranzaFacturaJPAPK id;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private String hora;
	private BigDecimal montoSaldo;
	private BigDecimal montoPago;

	@ManyToOne
	@JoinColumn(name = "numComprobante", nullable = false, insertable = false, updatable = false)
	private VentaJPA venta;

	public CobranzaFacturaJPAPK getId() {
		return id;
	}

	public void setId(CobranzaFacturaJPAPK id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public BigDecimal getMontoSaldo() {
		return montoSaldo;
	}

	public void setMontoSaldo(BigDecimal montoSaldo) {
		this.montoSaldo = montoSaldo;
	}

	public BigDecimal getMontoPago() {
		return montoPago;
	}

	public void setMontoPago(BigDecimal montoPago) {
		this.montoPago = montoPago;
	}

	public VentaJPA getVenta() {
		return venta;
	}

	public void setVenta(VentaJPA venta) {
		this.venta = venta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CobranzaJPA other = (CobranzaJPA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
