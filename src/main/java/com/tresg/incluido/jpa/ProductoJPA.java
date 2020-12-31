package com.tresg.incluido.jpa;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_producto")

@NamedQueries({
		@NamedQuery(name = "producto.listarProductoPorExistencia", query = "select d.producto.codProducto,d.producto.descripcion,d.producto.tipo.descripcion,d.producto.precioVenta,"
				+ "d.producto.medida.abreviatura,sum(d.existencia),d.producto.prodSunat from DetalleAlmacenJPA d where d.producto.descripcion like :x "
				+ "group by d.producto.codProducto"),
		@NamedQuery(name = "producto.listarProducto", query = "select p from ProductoJPA p") })

public class ProductoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String LISTAR_PRODUCTO_EXISTENCIA = "producto.listarProductoPorExistencia";
	public static final String LISTAR_PRODUCTO = "producto.listarProducto";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProducto;

	private String descripcion;
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;
	private String prodSunat;

	@ManyToOne
	@JoinColumn(name = "codTipo")
	private TipoProductoJPA tipo;

	@ManyToOne
	@JoinColumn(name = "codMedida")
	private UnidadMedidaJPA medida;

	@Transient
	private String descripcionTipo;

	@Transient
	private String descripcionMedida;

	@Transient
	private int cantidad;

	public int getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	public String getProdSunat() {
		return prodSunat;
	}

	public void setProdSunat(String prodSunat) {
		this.prodSunat = prodSunat;
	}

	public TipoProductoJPA getTipo() {
		return tipo;
	}

	public void setTipo(TipoProductoJPA tipo) {
		this.tipo = tipo;
	}

	public UnidadMedidaJPA getMedida() {
		return medida;
	}

	public void setMedida(UnidadMedidaJPA medida) {
		this.medida = medida;
	}

	public String getDescripcionTipo() {
		return descripcionTipo;
	}

	public void setDescripcionTipo(String descripcionTipo) {
		this.descripcionTipo = descripcionTipo;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getDescripcionMedida() {
		return descripcionMedida;
	}

	public void setDescripcionMedida(String descripcionMedida) {
		this.descripcionMedida = descripcionMedida;
	}

}
