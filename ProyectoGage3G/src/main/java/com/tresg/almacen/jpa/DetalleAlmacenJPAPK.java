package com.tresg.almacen.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;


@Embeddable
public class DetalleAlmacenJPAPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int codProducto;
	private int codAlmacen;

	public int getCodProducto() {
		return codProducto;
	}

	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	
	public void setCodAlmacen(int codAlmacen) {
		this.codAlmacen = codAlmacen;
	}

	public int getCodAlmacen() {
		return codAlmacen;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codAlmacen;
		result = prime * result + codProducto;
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
		DetalleAlmacenJPAPK other = (DetalleAlmacenJPAPK) obj;
		if (codAlmacen != other.codAlmacen)
			return false;
		if (codProducto != other.codProducto)
			return false;
		return true;
	}
	

}
