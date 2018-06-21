package com.tresg.almacen.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.datatable.DataTable;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.almacen.service.AlmacenBusinessDelegate;
import com.tresg.almacen.service.ConsultarAlmacenBusinessService;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name = "kardexConsultaBean")
@SessionScoped
public class ConsultaKardexBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// para limpiar el filtro de una datatable
	DataTable tbKardex;

	// lista desplegable almacen
	private List<SelectItem> almacenes;
	private int codigoAlmacen;

	// filtrar por fecha en kardex
	private Date fecha = new Date();
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();

	private List<DetalleMovimientoJPA> movimientos;

	ConsultarAlmacenBusinessService sAlmacen = AlmacenBusinessDelegate.getConsultarAlmacenService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public void listarMovimientoXFecha() {
		movimientos = new ArrayList<>();

		this.tbKardex.reset();

		
	}

	public void listarMovimientoXRangoFecha() {
		FacesContext context = FacesContext.getCurrentInstance();
		movimientos = new ArrayList<>();

		this.tbKardex.reset();
		if (fechaFin.before(fechaIni)) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		} 
	}



	public void setAlmacenes(List<SelectItem> almacenes) {
		this.almacenes = almacenes;
	}

	public int getCodigoAlmacen() {
		return codigoAlmacen;
	}

	public void setCodigoAlmacen(int codigoAlmacen) {
		this.codigoAlmacen = codigoAlmacen;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(Date fechaIni) {
		this.fechaIni = fechaIni;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public List<DetalleMovimientoJPA> getMovimientos() {
		return movimientos;
	}

	public void setMovimientos(List<DetalleMovimientoJPA> movimientos) {
		this.movimientos = movimientos;
	}

	public DataTable getTbKardex() {
		return tbKardex;
	}

	public void setTbKardex(DataTable tbKardex) {
		this.tbKardex = tbKardex;
	}

}
