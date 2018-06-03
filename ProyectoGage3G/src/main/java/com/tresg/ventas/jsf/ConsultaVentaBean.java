package com.tresg.ventas.jsf;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.component.datatable.DataTable;

import com.tresg.util.bean.AtributoBean;
import com.tresg.util.bean.ListaConsultaBean;
import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;


@ManagedBean(name = "ventaConsultaBean")
@SessionScoped
public class ConsultaVentaBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// para limpiar el filtro de una datatable
	DataTable tbVenta;

	// lista las ventas
	private List<VentaJPA> ventas;

	// lista los detalles de dicha venta
	private List<DetalleVentaJPA> detalles;

	// mostrar el monto de la lista de ventas
	private double acumulado;

	ListaConsultaBean listaUtil = new ListaConsultaBean();
	AtributoBean atributoUtil = new AtributoBean();

	public void listarVentaXFecha() {
		this.tbVenta.reset();
		ventas = listaUtil.listarVentaPorFechaContado(atributoUtil.getFecha());
		acumulado=listaUtil.acumuladoVentaXFecha(atributoUtil.getFecha());
	}

	public void listarVentaRangoFecha() {
		this.tbVenta.reset();
		FacesContext context = FacesContext.getCurrentInstance();

		if (atributoUtil.getFechaFin().before(atributoUtil.getFechaIni())) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		}
		ventas = listaUtil.listarVentaPorRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
		acumulado=listaUtil.acumuladoVentaXRangoFecha(atributoUtil.getFechaIni(), atributoUtil.getFechaFin());
	}

	public void listarVentaXCliente() {
		this.tbVenta.reset();
		ventas = listaUtil.listarPorCliente(atributoUtil.getCliente().getNombre());
		acumulado=listaUtil.acumuladoXCliente(atributoUtil.getCliente().getNombre());
	}

	public void mostrarDetalleVenta(ActionEvent e) {
		int numero = (int) e.getComponent().getAttributes().get("numeroDetalle");
		detalles = listaUtil.mostrarDetalleVenta(numero);
	}

	public DataTable getTbVenta() {
		return tbVenta;
	}

	public void setTbVenta(DataTable tbVenta) {
		this.tbVenta = tbVenta;
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

	public double getAcumulado() {
		return acumulado;
	}

	public void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
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
