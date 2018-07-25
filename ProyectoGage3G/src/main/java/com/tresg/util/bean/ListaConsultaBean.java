package com.tresg.util.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.jpa.VentaJPA;
import com.tresg.ventas.service.ConsultarVentaBusinessService;
import com.tresg.ventas.service.RegistrarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

public class ListaConsultaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	RegistrarVentaBusinessService sVenta = VentasBusinessDelegate.getRegistrarVentaService();
	ConsultarVentaBusinessService sConsultaVenta = VentasBusinessDelegate.getConsultarVentaService();

	public List<VentaJPA> listarVentaPorFecha(Date fecha) {
		List<VentaJPA> ventas = new ArrayList<>();
		sVenta.listaVenta().stream().filter(f -> fecha.equals(f.getFecha())).forEach(ventas::add);
		return ventas;

	}

	public List<VentaJPA> listarVentaPorRangoFecha(Date fechaIni, Date fechaFin) {
		List<VentaJPA> ventas = new ArrayList<>();
		sVenta.listaVenta().stream().filter(f -> (f.getFecha().after(fechaIni) || fechaIni.equals(f.getFecha()))
				&& (f.getFecha().before(fechaFin) || fechaFin.equals(f.getFecha()))).forEach(ventas::add);
		return ventas;

	}

	public List<VentaJPA> listarPorCliente(String cliente) {
		List<VentaJPA> ventas = new ArrayList<>();
		sVenta.listaVenta().stream().filter(v -> v.getCliente().getNombre().toLowerCase().contains(cliente))
				.forEach(ventas::add);
		return ventas;
	}
	
	

	public double acumuladoContadoVentaXFecha(Date fecha) {
		return sVenta.listaVenta().stream().filter(f -> (fecha.equals(f.getFecha()) && f.getEstado().getCodEstado()==1))
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}
	
	public double acumuladoDepositoVentaXFecha(Date fecha) {
		return sVenta.listaVenta().stream().filter(f -> (fecha.equals(f.getFecha()) && f.getEstado().getCodEstado()==8))
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}
	
	public double acumuladoCreditoVentaXFecha(Date fecha) {
		return sVenta.listaVenta().stream().filter(f -> (fecha.equals(f.getFecha()) && f.getEstado().getCodEstado()==2))
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}

	public double acumuladoContadoVentaXRangoFecha(Date fechaIni, Date fechaFin) {
		return sVenta.listaVenta().stream()
				.filter(f -> (f.getFecha().after(fechaIni) || fechaIni.equals(f.getFecha()))
						&& (f.getFecha().before(fechaFin) || fechaFin.equals(f.getFecha()))
						&& f.getEstado().getCodEstado() == 1)
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}
	
	public double acumuladoCreditoVentaXRangoFecha(Date fechaIni, Date fechaFin) {
		return sVenta.listaVenta().stream()
				.filter(f -> (f.getFecha().after(fechaIni) || fechaIni.equals(f.getFecha()))
						&& (f.getFecha().before(fechaFin) || fechaFin.equals(f.getFecha()))
						&& f.getEstado().getCodEstado() == 2)
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}
	
	public double acumuladoDepositoVentaXRangoFecha(Date fechaIni, Date fechaFin) {
		return sVenta.listaVenta().stream()
				.filter(f -> (f.getFecha().after(fechaIni) || fechaIni.equals(f.getFecha()))
						&& (f.getFecha().before(fechaFin) || fechaFin.equals(f.getFecha()))
						&& f.getEstado().getCodEstado() == 8)
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}

	public double acumuladoXCliente(String cliente) {
		return sVenta.listaVenta().stream().filter(v -> v.getCliente().getNombre().toLowerCase().contains(cliente))
				.mapToDouble(m -> m.getMonto().doubleValue()).sum();
	}

	public List<DetalleVentaJPA> mostrarDetalleVenta(int numero) {

		List<DetalleVentaJPA> detalles = new ArrayList<>();
		sConsultaVenta.listaDetalleVenta().stream().filter(d->d.getVenta().getNumComprobante()==numero)
				.forEach(detalles::add);
		return detalles;
	}
}
