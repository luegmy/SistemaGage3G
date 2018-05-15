package com.tresg.almacen.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tb_Almacen")
@NamedQuery(name = "almacen.comboAlmacen", query = "select a from AlmacenJPA a")

public class AlmacenJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static final String COMBO_ALMACEN = "almacen.comboAlmacen";

	@Id
	private int codAlmacen;
	private String descripcion;

	@OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL)
	private List<DetalleAlmacenJPA> detalles;

	public int getCodAlmacen() {
		return codAlmacen;
	}

	public void setCodAlmacen(int codAlmacen) {
		this.codAlmacen = codAlmacen;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<DetalleAlmacenJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleAlmacenJPA> detalles) {
		this.detalles = detalles;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codAlmacen;
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
		AlmacenJPA other = (AlmacenJPA) obj;
		if (codAlmacen != other.codAlmacen)
			return false;
		return true;
	}

}
