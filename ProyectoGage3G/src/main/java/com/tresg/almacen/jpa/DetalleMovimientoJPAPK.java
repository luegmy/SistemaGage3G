package com.tresg.almacen.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DetalleMovimientoJPAPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int nroMovimiento;
	private int codProducto;
	private int codAlmacen;
	
	public int getNroMovimiento() {
		return nroMovimiento;
	}
	public void setNroMovimiento(int nroMovimiento) {
		this.nroMovimiento = nroMovimiento;
	}
	public int getCodProducto() {
		return codProducto;
	}
	public void setCodProducto(int codProducto) {
		this.codProducto = codProducto;
	}
	public int getCodAlmacen() {
		return codAlmacen;
	}
	public void setCodAlmacen(int codAlmacen) {
		this.codAlmacen = codAlmacen;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + codAlmacen;
		result = prime * result + codProducto;
		result = prime * result + nroMovimiento;
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
		DetalleMovimientoJPAPK other = (DetalleMovimientoJPAPK) obj;
		if (codAlmacen != other.codAlmacen)
			return false;
		if (codProducto != other.codProducto)
			return false;
		if (nroMovimiento != other.nroMovimiento)
			return false;
		return true;
	}
	
	
	
}
