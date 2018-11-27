
package com.tresg.incluido.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.DocumentoIdentidadJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarClienteService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name = "clienteBean")
@SessionScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	// parametro de busqueda para la lista clientes
	private String busqueda = "";

	private int codigoCliente;
	private String nombreCliente;
	private String direccion;
	private List<SelectItem> identidades;
	private String codIdentidad;
	private String ruc;
	private String telefono;
	private String correo;

	private List<ClienteJPA> clientes;

	GestionarClienteService_I sCliente = IncluidoBusinessDelegate.getGestionarClienteService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	// Metodo donde se agrega los atributos del cliente en las respectivas
	// cajas de texto del formulario venta
	public void editarCliente(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		ClienteJPA objCliente = sCliente.buscaClientePorCodigo(codigo);
		setCodigoCliente(objCliente.getCodCliente());
		setNombreCliente(objCliente.getNombre());
		setDireccion(objCliente.getDireccion());
		setCodIdentidad(objCliente.getDocumento().getCodDocumento());
		setRuc(objCliente.getNroDocumento());
		setTelefono(objCliente.getTelefono());
		setCorreo(objCliente.getCorreo());
	}

	public void actualizarCliente() {
		FacesContext context = FacesContext.getCurrentInstance();
		ClienteJPA objC;
		DocumentoIdentidadJPA objIdentidad;
		Optional<ClienteJPA> existeRuc = sCliente.listaCliente().stream().filter(c -> c.getNroDocumento().equals(ruc))
				.findFirst();

		if (!existeRuc.isPresent() || codigoCliente != 0) {
			objC = new ClienteJPA();
			objIdentidad = new DocumentoIdentidadJPA();
			objIdentidad.setCodDocumento(codIdentidad);

			objC.setCodCliente(codigoCliente);
			objC.setNombre(nombreCliente);
			objC.setDireccion(direccion);
			objC.setDocumento(objIdentidad);
			objC.setNroDocumento(ruc);
			objC.setTelefono(telefono);
			objC.setCorreo(correo);

			context.addMessage("mensajeRegistroCliente",
					new FacesMessage(FacesMessage.SEVERITY_INFO, sCliente.actualizaCliente(objC), null));

		} else {
			context.addMessage("mensajeRucExiste", new FacesMessage(FacesMessage.SEVERITY_WARN,
					"Ya existe dicho cliente con".concat(" ").concat(ruc), null));
		}
		limpiar();
	}

	public void limpiar() {
		codigoCliente = 0;
		nombreCliente = "";
		direccion = "";
		codIdentidad = "";
		ruc = "";
		telefono = "";
		correo = "";
	}


	public void setCodigoCliente(int codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public int getCodigoCliente() {
		return codigoCliente;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public List<SelectItem> getIdentidades() {
		identidades = new ArrayList<>();
		sCombo.comboIdentidad().forEach(p -> identidades.add(new SelectItem(p.getCodDocumento(), p.getDescripcion())));
		return identidades;
	}

	public void setIdentidades(List<SelectItem> identidades) {
		this.identidades = identidades;
	}

	public String getCodIdentidad() {
		return codIdentidad;
	}

	public void setCodIdentidad(String codIdentidad) {
		this.codIdentidad = codIdentidad;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setClientes(List<ClienteJPA> clientes) {
		this.clientes = clientes;
	}

	public List<ClienteJPA> getClientes() {
		clientes = new ArrayList<>();
		sCliente.listaCliente().stream().filter(c -> c.getNombre().toLowerCase().contains(busqueda.toLowerCase()))
				.forEach(clientes::add);
		return clientes;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
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

}
