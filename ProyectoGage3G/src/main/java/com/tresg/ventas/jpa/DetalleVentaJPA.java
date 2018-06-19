package com.tresg.ventas.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.tresg.incluido.jpa.ProductoJPA;

@Entity
@Table(name = "tb_detalle_venta")
@NamedQueries({
		@NamedQuery(name = "detalleVenta.listarDetalleVenta", 
					query = "select d from DetalleVentaJPA d"),
		@NamedQuery(name = "detalleVenta.listarProductoXVenta", 
					query = "select d.producto.codProducto,d.producto.descripcion,sum(d.cantidad),sum(d.cantidad * d.precio) from DetalleVentaJPA d where d.venta.fecha between :p and :q group by d.producto.codProducto"),
		@NamedQuery(name = "detalleVenta.eliminarItemDetalle", 
					query = "delete from DetalleVentaJPA d where d.producto.codProducto =:p and d.venta.numComprobante=:v") })

public class DetalleVentaJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String LISTAR_DETALLE_VENTAS = "detalleVenta.listarDetalleVenta";
	public static final String LISTAR_PRODUCTOS_VENTAS = "detalleVenta.listarProductoXVenta";
	public static final String ELIMINAR_ITEM_DETALLEVENTA = "detalleVenta.eliminarItemDetalle";
	

	@EmbeddedId
	private DetalleVentaJPAPK id;

	private BigDecimal cantidad;
	private BigDecimal precio;

	@ManyToOne
	@JoinColumn(name = "numComprobante", nullable = false, insertable = false, updatable = false)
	private VentaJPA venta;

	@ManyToOne
	@JoinColumn(name = "codProducto", nullable = false, insertable = false, updatable = false)
	private ProductoJPA producto;

	// para mostrar la descripcion y codigo del producto en el detalle
	@Transient
	private String descripcionProducto;
	@Transient
	private int codigoProducto;

	public DetalleVentaJPAPK getId() {
		return id;
	}

	public void setId(DetalleVentaJPAPK id) {
		this.id = id;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public VentaJPA getVenta() {
		return venta;
	}

	public void setVenta(VentaJPA venta) {
		this.venta = venta;
	}

	public ProductoJPA getProducto() {
		return producto;
	}

	public void setProducto(ProductoJPA producto) {
		this.producto = producto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public String getProductoDescripcion() {
		return producto.getDescripcion().concat(" ").concat(producto.getTipo().getDescripcion());
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
		DetalleVentaJPA other = (DetalleVentaJPA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
