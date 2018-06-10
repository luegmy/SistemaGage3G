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
		@NamedQuery(name = "detalleVenta.listarProductoXVenta", query = "select d.producto.codProducto,d.producto.descripcion,sum(d.cantidad),sum(d.cantidad * d.precio) from DetalleVentaJPA d where d.venta.fecha between :p and :q group by d.producto.codProducto"),

		@NamedQuery(name = "detalleVenta.listarDetalleVenta", query = "select d from DetalleVentaJPA d"),

		@NamedQuery(name = "detalleVenta.eliminarItemDetalle", query = "delete from DetalleVentaJPA d where d.producto.codProducto =:p and d.venta.numComprobante=:v") })

public class DetalleVentaJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String ELIMINAR_ITEM_DETALLEVENTA = "detalleVenta.eliminarItemDetalle";
	public static final String LISTAR_PRODUCTOS_VENTAS = "detalleVenta.listarProductoXVenta";
	public static final String CANTIDAD_ACUMULADA_PRODUCTOS_VENTAS = "detalleVenta.obtenerCantidadAcumuladaProductoXVenta";
	public static final String MONTO_PRODUCTOS_VENTAS = "detalleVenta.obtenerMontoProductoXVenta";
	public static final String LISTAR_DETALLE_VENTAS = "detalleVenta.listarDetalleVenta";

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

	// para mostrar la descripcion de tipo de producto en el detalle
	@Transient
	private String descripcion;

	// para mostrar medida de guia
	@Transient
	private String productoDescripcion;
	@Transient
	private String medidaDescripcion;
	@Transient
	private String abrevia;

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getProductoDescripcion() {
		return producto.getDescripcion().concat(" ").concat(producto.getTipo().getDescripcion());
	}

	public void setProductoDescripcion(String productoDescripcion) {
		this.productoDescripcion = productoDescripcion;
	}

	public String getMedidaDescripcion() {
		return producto.getMedida().getDescripcion();
	}

	public void setMedidaDescripcion(String medidaDescripcion) {
		this.medidaDescripcion = medidaDescripcion;
	}

	public String getAbrevia() {
		return producto.getMedida().getAbreviatura();
	}

	public void setAbrevia(String abrevia) {
		this.abrevia = abrevia;
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
