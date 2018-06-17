package com.tresg.ventas.jsf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.component.datatable.DataTable;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.ConsultarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "productoClienteBean")
@SessionScoped
public class ProductoXClienteBean {

	// para limpiar el filtro de una datatable
	DataTable tbVenta;

	// lista los detalles de dicha venta
	private List<DetalleVentaJPA> detalles;
	private String nombreCliente;

	ConsultarVentaBusinessService sConsultaVenta = VentasBusinessDelegate.getConsultarVentaService();

	public void listarProducto() {

		detalles = new ArrayList<>();
		Comparator<DetalleVentaJPA> comp = (d1,d2)->d1.getVenta().getFecha().compareTo(d2.getVenta().getFecha());
		sConsultaVenta.listaDetalleVenta().stream().sorted(comp.reversed())
				.filter(d -> d.getVenta().getCliente().getNombre().toLowerCase().contains(nombreCliente))			
				.collect(Collectors.toList()).forEach(detalles::add);

	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
	}

	public String getNombreCliente() {
		return nombreCliente;
	}

	public void setNombreCliente(String nombreCliente) {
		this.nombreCliente = nombreCliente;
	}

	public DataTable getTbVenta() {
		return tbVenta;
	}

	public void setTbVenta(DataTable tbVenta) {
		this.tbVenta = tbVenta;
	}

}
