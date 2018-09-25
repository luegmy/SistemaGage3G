package com.tresg.almacen.jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.tresg.incluido.jpa.ComprobanteJPA;
import com.tresg.incluido.jpa.ProveedorJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;

@Entity
@Table(name = "tb_movimiento")
@NamedQueries({
		@NamedQuery(name = "movimiento.maxNumeroMovimiento", 
				query = "select max(m.nroMovimiento)+1 from MovimientoJPA m"),
		@NamedQuery(name = "movimiento.maxNumeroMovimientoNota", 
				query = "select max(m.numComprobante)+1 from MovimientoJPA m where m.comprobante.codComprobante=2"),
		@NamedQuery(name = "movimiento.listarMovimientos", 
		query = "select m from MovimientoJPA m"),
		 })

public class MovimientoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String MAXIMO_NUMERO_MOVIMIENTO = "movimiento.maxNumeroMovimiento";
	public static final String NUMERACION_MOVIMIENTO_NOTA = "movimiento.maxNumeroMovimientoNota";
	public static final String LISTA_MOVIMIENTOS = "movimiento.listarMovimientos";

	@Id
	private int nroMovimiento;

	private int numComprobante;

	@Temporal(TemporalType.DATE)
	private Date fecha;

	private String hora;
	private String observacion;

	@OneToMany(mappedBy = "movimiento", cascade = CascadeType.ALL)
	private List<DetalleMovimientoJPA> detalles;

	@ManyToOne(optional = false)
	@JoinColumn(name = "codMovimiento")
	private TipoMovimientoJPA tipoMovimiento;

	@ManyToOne(optional = false)
	@JoinColumn(name = "codUsuario")
	private UsuarioJPA usuario;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "codProveedor")
	private ProveedorJPA proveedor;

	@ManyToOne
	@JoinColumn(name = "codComprobante")
	private ComprobanteJPA comprobante;

	public int getNroMovimiento() {
		return nroMovimiento;
	}

	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}

	public int getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
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

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public List<DetalleMovimientoJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleMovimientoJPA> detalles) {
		this.detalles = detalles;
	}

	public TipoMovimientoJPA getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(TipoMovimientoJPA tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public UsuarioJPA getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioJPA usuario) {
		this.usuario = usuario;
	}

	public ProveedorJPA getProveedor() {
		return proveedor;
	}

	public void setProveedor(ProveedorJPA proveedor) {
		this.proveedor = proveedor;
	}

	public ComprobanteJPA getComprobante() {
		return comprobante;
	}

	public void setComprobante(ComprobanteJPA comprobante) {
		this.comprobante = comprobante;
	}

}
