package com.tresg.ventas.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.tresg.ventas.jpa.CobranzaFacturaJPAPK;
import com.tresg.ventas.jpa.CobranzaJPA;
import com.tresg.ventas.service.CobranzaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "ventaFacturaBean")
@SessionScoped
public class CobranzaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<CobranzaJPA> cobranzas;

	private int numeroComprobante;
	private BigDecimal monto;
	private Date fecha=new Date();
	private Date fechaIni=new Date();
	private Date fechaFin=new Date();
	private String nombreCliente;
	private double acumulado;

	CobranzaBusinessService sCobranza = VentasBusinessDelegate.getCobranzaService();

	public void listarCobranzaXFecha() {
		cobranzas = new ArrayList<>();
		sCobranza.listaFacturasPorCobrar().stream().filter(c -> fecha.equals(c.getFecha())).forEach(cobranzas::add);
	}

	public void listarCobranzaXRangoFecha() {

		FacesContext context = FacesContext.getCurrentInstance();
		cobranzas = new ArrayList<>();
		if (getFechaFin().before(getFechaIni())) {
			context.addMessage("mensajeRangoFecha", new FacesMessage(FacesMessage.SEVERITY_INFO,
					"La fecha final no puede ser menor a fecha inicial", null));
		}
		sCobranza.listaFacturasPorCobrar().stream()
				.filter(f -> (f.getFecha().after(fechaIni) || fechaIni.equals(f.getFecha()))
						&& (f.getFecha().before(fechaFin) || fechaFin.equals(f.getFecha())))
				.forEach(cobranzas::add);

	}

	public void listarCobranzaXCliente() {
		cobranzas = new ArrayList<>();
		sCobranza.listaFacturasPorCobrar().stream()
				.filter(c -> c.getVenta().getCliente().getNombre().toLowerCase().contains(nombreCliente))
				.forEach(cobranzas::add);

	}

	public void cargarNumeroComprobante(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		setNumeroComprobante(codigo);

	}

	public void actualizarFacturaCredito() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (monto.signum() <= 0) {
			context.addMessage("mensajeCobranza", new FacesMessage(FacesMessage.SEVERITY_WARN, "Ingrese monto", null));
		} else {
			CobranzaJPA objC = new CobranzaJPA();
			CobranzaFacturaJPAPK objCF = new CobranzaFacturaJPAPK();
			objCF.setNumComprobante(numeroComprobante);
			objC.setMontoPago(monto);
			objC.setId(objCF);
			String mensaje = sCobranza.actualizaFacturaCredito(objC);
			context.addMessage("mensajeCobranza", new FacesMessage(FacesMessage.SEVERITY_INFO, mensaje, null));
			getCobranzas();

			numeroComprobante = 0;
			monto = new java.math.BigDecimal("0.00");
		}
	}

	public int getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(int numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public List<CobranzaJPA> getCobranzas() {
		return cobranzas;

	}

	public void setCobranzas(List<CobranzaJPA> cobranzas) {
		this.cobranzas = cobranzas;
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

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public double getAcumulado() {
		return sCobranza.listaFacturasPorCobrar().stream()
				.filter(f -> fecha.equals(f.getFecha()))
				.mapToDouble(m -> m.getMontoPago().doubleValue()).sum();
	}

	public void setAcumulado(double acumulado) {
		this.acumulado = acumulado;
	}

}
