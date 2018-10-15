package com.tresg.ventas.jsf;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.tresg.incluido.jsf.AtributoBean;
import com.tresg.util.bean.ListaConsultaBean;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;


@ManagedBean(name = "ventaConsultaBean")
@SessionScoped
public class ConsultaVentaBean implements Serializable{

	private static final long serialVersionUID = 1L;

	// lista las ventas
	private List<VentaJPA> ventas;

	// lista los detalles de dicha venta
	private List<DetalleVentaJPA> detalles;

	// mostrar el monto de la lista de ventas
	private double acumuladoContado;
	private double acumuladoCredito;
	private double acumuladoDeposito;

	ListaConsultaBean listaUtil = new ListaConsultaBean();
	AtributoBean atributoUtil = new AtributoBean();

	public void listarVentaXFecha() {
		ventas = listaUtil.listarVentaPorFecha(atributoUtil.getFecha());
		acumuladoContado=listaUtil.acumuladoContadoVentaXFecha(atributoUtil.getFecha());
		acumuladoCredito=listaUtil.acumuladoCreditoVentaXFecha(atributoUtil.getFecha());
		acumuladoDeposito=listaUtil.acumuladoDepositoVentaXFecha(atributoUtil.getFecha());
	}

	public void listarVentaRangoFecha() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (atributoUtil.getFechaFin().before(atributoUtil.getFechaIni())) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		}
		ventas = listaUtil.listarVentaPorRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
		acumuladoContado=listaUtil.acumuladoContadoVentaXRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
		acumuladoCredito=listaUtil.acumuladoCreditoVentaXRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
		acumuladoDeposito=listaUtil.acumuladoDepositoVentaXRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
	}

	public void listarVentaXCliente() {
		ventas = listaUtil.listarVentaPorCliente(atributoUtil.getCliente().getNombre());
		acumuladoContado=listaUtil.acumuladoXCliente(atributoUtil.getCliente().getNombre());
	}

	public void mostrarDetalleVenta(ActionEvent e) {
		int numero = (int) e.getComponent().getAttributes().get("numeroDetalle");
		detalles = listaUtil.mostrarDetalleVenta(numero);
	}

	public List<VentaJPA> getVentas() {
		return ventas;
	}

	public void setVentas(List<VentaJPA> ventas) {
		this.ventas = ventas;
	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
	}

	

	public double getAcumuladoContado() {
		return acumuladoContado;
	}

	public void setAcumuladoContado(double acumuladoContado) {
		this.acumuladoContado = acumuladoContado;
	}

	public double getAcumuladoCredito() {
		return acumuladoCredito;
	}

	public void setAcumuladoCredito(double acumuladoCredito) {
		this.acumuladoCredito = acumuladoCredito;
	}

	public double getAcumuladoDeposito() {
		return acumuladoDeposito;
	}

	public void setAcumuladoDeposito(double acumuladoDeposito) {
		this.acumuladoDeposito = acumuladoDeposito;
	}

	public ListaConsultaBean getListaUtil() {
		return listaUtil;
	}

	public void setListaUtil(ListaConsultaBean listaUtil) {
		this.listaUtil = listaUtil;
	}

	public AtributoBean getAtributoUtil() {
		return atributoUtil;
	}

	public void setAtributoUtil(AtributoBean atributoUtil) {
		this.atributoUtil = atributoUtil;
	}
	

}
