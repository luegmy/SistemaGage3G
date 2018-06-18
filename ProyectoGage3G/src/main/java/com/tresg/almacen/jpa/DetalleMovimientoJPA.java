package com.tresg.almacen.jpa;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_detalle_movimiento")
public class DetalleMovimientoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DetalleMovimientoJPAPK id;

	private int cantidad;

	@Transient
	private String descripcionProducto;

	@ManyToOne
	@JoinColumn(name = "nroMovimiento", nullable = false, insertable = false, updatable = false)
	private MovimientoJPA movimiento;

	@ManyToOne
	@JoinColumns({
			@JoinColumn(name = "codAlmacen", referencedColumnName = "codAlmacen", nullable = false, insertable = false, updatable = false),
			@JoinColumn(name = "codProducto", referencedColumnName = "codProducto", nullable = false, insertable = false, updatable = false) })
	private DetalleAlmacenJPA detalleAlmacen;

	public DetalleMovimientoJPAPK getId() {
		return id;
	}

	public void setId(DetalleMovimientoJPAPK id) {
		this.id = id;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public MovimientoJPA getMovimiento() {
		return movimiento;
	}

	public void setMovimiento(MovimientoJPA movimiento) {
		this.movimiento = movimiento;
	}

	public DetalleAlmacenJPA getDetalleAlmacen() {
		return detalleAlmacen;
	}

	public void setDetalleAlmacen(DetalleAlmacenJPA detalleAlmacen) {
		this.detalleAlmacen = detalleAlmacen;
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
		DetalleMovimientoJPA other = (DetalleMovimientoJPA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
