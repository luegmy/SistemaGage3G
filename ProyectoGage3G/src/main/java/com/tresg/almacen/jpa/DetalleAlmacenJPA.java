package com.tresg.almacen.jpa;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.tresg.incluido.jpa.ProductoJPA;

@Entity
@Table(name = "tb_detalle_almacen")
@NamedQueries({
		@NamedQuery(name = "detalleAlmacen.comprobarStockAlmacen", query = "select d.existencia from DetalleAlmacenJPA d where d.producto.codProducto = :p1 and d.almacen.codAlmacen=3"),
		@NamedQuery(name = "detalleAlmacen.actualizarAlmacenDecremento", query = "update DetalleAlmacenJPA d set d.existencia=d.existencia - :p1 where d.id= :p2"),
		@NamedQuery(name = "detalleAlmacen.actualizarAlmacenIncremento", query = "update DetalleAlmacenJPA d set d.existencia=d.existencia + :p1 where d.id= :p2"),
		@NamedQuery(name = "detalleAlmacen.actualizarProductoNacional", query = "update DetalleAlmacenJPA d set d.existencia=d.existencia - :x "
				+ "where d.id.codAlmacen=4"),
		@NamedQuery(name = "detalleAlmacen.desactualizarProductoNacional", query = "update DetalleAlmacenJPA d set d.existencia=d.existencia + :x "
				+ "where d.id.codAlmacen=4"),
		@NamedQuery(name = "detalleAlmacen.listarProductoPorAlmacen", query = "select d from DetalleAlmacenJPA d where d.producto.descripcion like :x") })

public class DetalleAlmacenJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String VERIFICAR_EXISTENCIA = "detalleAlmacen.comprobarStockAlmacen";
	public static final String ACTUALIZAR_ALMACEN_DECREMENTO = "detalleAlmacen.actualizarAlmacenDecremento";
	public static final String ACTUALIZAR_ALMACEN_INCREMENTO = "detalleAlmacen.actualizarAlmacenIncremento";
	public static final String ACTUALIZAR_ACCESORIO_EXTINTOR_NACIONAL = "detalleAlmacen.actualizarProductoNacional";
	public static final String DESACTUALIZAR_ACCESORIO_EXTINTOR_NACIONAL = "detalleAlmacen.desactualizarProductoNacional";
	public static final String LISTAR_PRODUCTO_POR_ALMACEN = "detalleAlmacen.listarProductoPorAlmacen";

	@EmbeddedId
	private DetalleAlmacenJPAPK id;
	private int existencia;

	@ManyToOne
	@JoinColumn(name = "codAlmacen", nullable = false, insertable = false, updatable = false)
	private AlmacenJPA almacen;

	@ManyToOne
	@JoinColumn(name = "codProducto", nullable = false, insertable = false, updatable = false)
	private ProductoJPA producto;

	public DetalleAlmacenJPAPK getId() {
		return id;
	}

	public void setId(DetalleAlmacenJPAPK id) {
		this.id = id;
	}

	public int getExistencia() {
		return existencia;
	}

	public void setExistencia(int existencia) {
		this.existencia = existencia;
	}

	public AlmacenJPA getAlmacen() {
		return almacen;
	}

	public void setAlmacen(AlmacenJPA almacen) {
		this.almacen = almacen;
	}

	public ProductoJPA getProducto() {
		return producto;
	}

	public void setProducto(ProductoJPA producto) {
		this.producto = producto;
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
		DetalleAlmacenJPA other = (DetalleAlmacenJPA) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
