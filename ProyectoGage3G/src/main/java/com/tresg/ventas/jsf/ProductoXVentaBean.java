package com.tresg.ventas.jsf;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.ConsultarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "productoVentaBean")
@SessionScoped
public class ProductoXVentaBean {

	// lista los detalles de dicha venta
	private List<DetalleVentaJPA> detalles;
	private Date fecha = new Date();
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();
	
	
	private int acumulador = 0;
	private double monto = 0;

	ConsultarVentaBusinessService sConsultaVenta = VentasBusinessDelegate.getConsultarVentaService();

	public ProductoXVentaBean() {
		detalles = new ArrayList<>();
	}
	
	public void listarProductoVentaPorFecha() {
		detalles = new ArrayList<>();
		sConsultaVenta.listaDetalleVenta().stream().filter(f -> fecha.equals(f.getVenta().getFecha()))
		.forEach(detalles::add);

	}

	public int sumaPorFecha() {
		int aux = acumulador ;
        acumulador = 0;
        return aux;
	}
	
	public double montoPorFecha() {
		double aux = monto ;
        monto = 0;
        return aux;
	}
	
	public void valorAcumulado(int valor)
    {
        acumulador += valor;
    }
	
	public void montoAcumulado(Double valor)
    {
        monto += valor*acumulador;
    }

	public void listarProductoVentaPorRangoFecha() {
		detalles = new ArrayList<>();
		sConsultaVenta.listaDetalleVenta().stream()
				.filter(f -> (f.getVenta().getFecha().after(fechaIni) || fechaIni.equals(f.getVenta().getFecha()))
						&& (f.getVenta().getFecha().before(fechaFin) || fechaFin.equals(f.getVenta().getFecha())))
				.forEach(detalles::add);

	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
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

}
