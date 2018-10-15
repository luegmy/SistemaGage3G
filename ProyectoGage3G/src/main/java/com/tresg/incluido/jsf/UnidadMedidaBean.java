package com.tresg.incluido.jsf;


import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name="medidaBean")
@SessionScoped
public class UnidadMedidaBean {


	// parametro de busqueda para la lista unidades de medidas
	private String busqueda="";
		
	private int codigoMedida;
	private String descripcion;
	private String abreviatura;
	
	private List<UnidadMedidaJPA> medidas;
	
	// mostrar mensaje ya sea de actualizacion o de insercion
	private String mensaje;
	
	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	
	// Metodo donde se agrega los atributos del cliente en las respectivas
	// cajas de texto del formulario venta
	public void editarMedida(ActionEvent e)  {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		UnidadMedidaJPA objMedida;
		objMedida  = sProducto.buscaMedidaPorCodigo(codigo);
		setCodigoMedida(objMedida.getCodMedida());
		setDescripcion(objMedida.getDescripcion());
		setAbreviatura(objMedida.getAbreviatura());
		setMensaje("");
	}
	
	public void actualizarMedida()  {
		FacesContext context = FacesContext.getCurrentInstance();
		UnidadMedidaJPA objM = new UnidadMedidaJPA();
		objM.setCodMedida(codigoMedida);
		objM.setDescripcion(descripcion);
		objM.setAbreviatura(abreviatura);
		
		context.addMessage("mensajeRegistroMedida", new FacesMessage(
				FacesMessage.SEVERITY_INFO, sProducto.actualizaMedida(objM),
				null));
		
		limpiar();
	}

	public void limpiar() {
		codigoMedida = 0;
		descripcion = "";
		abreviatura = "";
		mensaje = "";
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public int getCodigoMedida() {
		return codigoMedida;
	}

	public void setCodigoMedida(int codigoMedida) {
		this.codigoMedida = codigoMedida;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public List<UnidadMedidaJPA> getMedidas()  {
		medidas=new ArrayList<>();
		sProducto.buscaMedidaPorDescripcion2(busqueda).forEach(medidas::add);			
		return medidas;
	}

	public void setMedidas(List<UnidadMedidaJPA> medidas) {
		this.medidas = medidas;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	

}
