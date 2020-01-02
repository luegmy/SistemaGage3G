package com.tresg.incluido.jsf;


import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.tresg.incluido.jpa.ProveedorJPA;
import com.tresg.incluido.service.GestionarProveedorService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name = "proveedorBean")
@SessionScoped
public class ProveedorBean{

	// parametro de busqueda para la lista Proveedors
	private String busqueda = "";

	private int codigoProveedor;
	private String nombreProveedor;
	private String direccion;
	private String ruc;
	private String telefono;
	private String correo;

	private List<ProveedorJPA> proveedores;

	GestionarProveedorService_I sProveedor = IncluidoBusinessDelegate.getGestionarProveedorService();

	// Metodo donde se agrega los atributos del Proveedor en las respectivas
	// cajas de texto del formulario venta
	public void editarProveedor(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		ProveedorJPA objProveedor;
		objProveedor = sProveedor.buscaProveedorPorCodigo(codigo);
		setCodigoProveedor(objProveedor.getCodProveedor());
		setNombreProveedor(objProveedor.getNombre());
		setDireccion(objProveedor.getDireccion());
		setRuc(objProveedor.getRuc());
		setTelefono(objProveedor.getTelefono());
		setCorreo(objProveedor.getCorreo());
	}

	public void actualizarProveedor() {
		FacesContext context = FacesContext.getCurrentInstance();
		ProveedorJPA objC;
		if (sProveedor.obtieneRuc(ruc)==null) {
		objC=new ProveedorJPA();
		objC.setCodProveedor(codigoProveedor);
		objC.setNombre(nombreProveedor);
		objC.setDireccion(direccion);
		objC.setRuc(ruc);
		objC.setTelefono(telefono);
		objC.setCorreo(correo);
		
		context.addMessage("mensajeRegistroProveedor", new FacesMessage(
				FacesMessage.SEVERITY_INFO, sProveedor.actualizaProveedor(objC),
				null));
		}else{
			context.addMessage("mensajeRucExiste",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Ya existe dicho proveedor con" + " " + ruc, null));
		}
		limpiar();
	}

	public void limpiar() {
		codigoProveedor = 0;
		nombreProveedor = "";
		direccion = "";
		ruc = "";
		telefono = "";
		correo = "";
	}

	public void cancelarProveedor(){
		limpiar();
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public int getCodigoProveedor() {
		return codigoProveedor;
	}

	public void setCodigoProveedor(int codigoProveedor) {
		this.codigoProveedor = codigoProveedor;
	}

	public String getNombreProveedor() {
		return nombreProveedor;
	}

	public void setNombreProveedor(String nombreProveedor) {
		this.nombreProveedor = nombreProveedor;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
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

	public List<ProveedorJPA> getProveedores() {
		proveedores = new ArrayList<>();
		sProveedor.listaProveedor().forEach(proveedores::add);
		return proveedores;
	}

	public void setProveedores(List<ProveedorJPA> proveedores) {
		this.proveedores = proveedores;
	}

}
