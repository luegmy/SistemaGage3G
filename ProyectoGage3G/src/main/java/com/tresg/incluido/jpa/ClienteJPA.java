package com.tresg.incluido.jpa;

import java.io.Serializable;

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
@Table(name = "tb_cliente")

@NamedQueries({ @NamedQuery(name = "cliente.listarCliente", query = "select c from ClienteJPA c"),
		@NamedQuery(name = "cliente.buscarClienteRuc", query = "select c from ClienteJPA c where c.nroDocumento= :p1")

})

public class ClienteJPA implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String LISTAR_CLIENTE = "cliente.listarCliente";
	public static final String BUSCAR_CLIENTE_RUC = "cliente.buscarClienteRuc";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int codCliente;
	private String nombre;
	private String direccion;
	private String nroDocumento;
	private String telefono;
	private String correo;

	@Transient
	private String codigoDocumento;

	@ManyToOne
	@JoinColumn(name = "codDocumento")
	private DocumentoIdentidadJPA documento;

	public int getCodCliente() {
		return codCliente;
	}

	public void setCodCliente(int codCliente) {
		this.codCliente = codCliente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNroDocumento() {
		return nroDocumento;
	}

	public void setNroDocumento(String nroDocumento) {
		this.nroDocumento = nroDocumento;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	public DocumentoIdentidadJPA getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoIdentidadJPA documento) {
		this.documento = documento;
	}

}
