package com.tresg.incluido.jpa;

import java.io.Serializable;
import java.math.BigDecimal;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "tb_producto")


@NamedQuery(name = "producto.listarProducto", query = "select p from ProductoJPA p")

public class ProductoJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String LISTAR_PRODUCTO= "producto.listarProducto";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codProducto;

	private String descripcion;
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;

	@ManyToOne
	@JoinColumn(name = "codTipo")
	private TipoProductoJPA tipo;

	@ManyToOne
	@JoinColumn(name = "codMedida")
	private UnidadMedidaJPA medida;

	@Transient
	private BigDecimal cantidad = new BigDecimal(0);

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

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

}
