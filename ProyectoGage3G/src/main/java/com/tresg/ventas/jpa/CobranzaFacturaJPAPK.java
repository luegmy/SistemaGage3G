package com.tresg.ventas.jpa;

import java.io.Serializable;

import javax.persistence.Embeddable;



@Embeddable
public class CobranzaFacturaJPAPK implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int numComprobante;
	private int numCobranza;
	

	public int getNumComprobante() {
		return numComprobante;
	}

	public void setNumComprobante(int numComprobante) {
		this.numComprobante = numComprobante;
	}

	public int getNumCobranza() {
		return numCobranza;
	}

	public void setNumCobranza(int numCobranza) {
		this.numCobranza = numCobranza;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numCobranza;
		result = prime * result + numComprobante;
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
		CobranzaFacturaJPAPK other = (CobranzaFacturaJPAPK) obj;
		if (numCobranza != other.numCobranza)
			return false;
		if (numComprobante != other.numComprobante)
			return false;
		return true;
	}

	
	
	

}
