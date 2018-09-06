package com.tresg.incluido.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "tb_documento_identidad")
@NamedQuery(name = "identidad.comboIdentidad", query = "select i from DocumentoIdentidadJPA i")
public class DocumentoIdentidadJPA implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String codDocumento="";
	
	private String descripcion;

	public String getCodDocumento() {
		return codDocumento;
	}

	public void setCodDocumento(String codDocumento) {
		this.codDocumento = codDocumento;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	

}
