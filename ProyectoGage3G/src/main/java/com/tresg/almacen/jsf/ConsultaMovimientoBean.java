package com.tresg.almacen.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.tresg.almacen.jpa.DetalleMovimientoJPA;
import com.tresg.almacen.jpa.MovimientoJPA;
import com.tresg.almacen.service.AlmacenBusinessDelegate;
import com.tresg.almacen.service.ConsultarMovimientoBusinessService;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name = "movimientoConsultaBean")
@SessionScoped
public class ConsultaMovimientoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// filtrar por fecha en kardex
	private Date fecha = new Date();
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();

	private List<DetalleMovimientoJPA> movimientos;

	ConsultarMovimientoBusinessService sAlmacen = AlmacenBusinessDelegate.getConsultarMovimientoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public void listarMovimientoXFecha() {
		movimientos = new ArrayList<>();

		for (MovimientoJPA m : sAlmacen.listaMovimientos()) {
			if (fecha.equals(m.getFecha())) {
				for (DetalleMovimientoJPA d : m.getDetalles()) {
					movimientos.add(d);
				}
				
			}			
		}

	}

	public void listarMovimientoXRangoFecha() {
		FacesContext context = FacesContext.getCurrentInstance();
		movimientos = new ArrayList<>();

		if (fechaFin.before(fechaIni)) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		}
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


}
