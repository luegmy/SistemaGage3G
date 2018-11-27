
package com.tresg.ventas.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.EstadoJPA;
import com.tresg.incluido.jpa.MediosPagoJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;

@Entity
@Table(name = "tb_venta")
@NamedQueries({ @NamedQuery(name = "venta.listarVentas", query = "select v from VentaJPA v"),

		@NamedQuery(name = "venta.anularVenta", query = "update VentaJPA v set v.estado=:x , v.monto=0 where v.numComprobante=:y") })

public class VentaJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String LISTAR_VENTAS = "venta.listarVentas";
	public static final String ANULAR_VENTA = "venta.anularVenta";

	@Id
	private int numComprobante;
	private String serie;
	@Temporal(TemporalType.DATE)
	private Date fecha;
	private String fecVence;
	private String hora;
	private BigDecimal monto;
	private String observacion;

	@ManyToOne
	@JoinColumn(name = "codCliente")
	private ClienteJPA cliente;

	@ManyToOne
	@JoinColumn(name = "codPago")
	private MediosPagoJPA pago;

	@ManyToOne
	@JoinColumn(name = "codComprobante")
	private ComprobanteJPA comprobante;

	@ManyToOne
	@JoinColumn(name = "codEstado")
	private EstadoJPA estado;

	@ManyToOne
	@JoinColumn(name = "codUsuario")
	private UsuarioJPA usuario;

	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private List<DetalleVentaJPA> detalles;

	@OneToMany(mappedBy = "venta", cascade = CascadeType.ALL)
	private List<CobranzaJPA> cobranzas;
	
	@OneToOne(mappedBy="venta",cascade = CascadeType.ALL,fetch=FetchType.LAZY)
	private GuiaRemisionJPA guiaRemision;

	public int getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getFecVence() {
		return fecVence;
	}

	public void setFecVence(String fecVence) {
		this.fecVence = fecVence;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public ClienteJPA getCliente() {
		return cliente;
	}

	public void setCliente(ClienteJPA cliente) {
		this.cliente = cliente;
	}

	public MediosPagoJPA getPago() {
		return pago;
	}

	public void setPago(MediosPagoJPA pago) {
		this.pago = pago;
	}

	public ComprobanteJPA getComprobante() {
		return comprobante;
	}

	public void setComprobante(ComprobanteJPA comprobante) {
		this.comprobante = comprobante;
	}

	public EstadoJPA getEstado() {
		return estado;
	}

	public void setEstado(EstadoJPA estado) {
		this.estado = estado;
	}

	public UsuarioJPA getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioJPA usuario) {
		this.usuario = usuario;
	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
	}

	public List<CobranzaJPA> getCobranzas() {
		return cobranzas;
	}

	public void setCobranzas(List<CobranzaJPA> cobranzas) {
		this.cobranzas = cobranzas;
	}

	public GuiaRemisionJPA getGuiaRemision() {
		return guiaRemision;
	}

	public void setGuiaRemision(GuiaRemisionJPA guiaRemision) {
		this.guiaRemision = guiaRemision;
	}
	

}
