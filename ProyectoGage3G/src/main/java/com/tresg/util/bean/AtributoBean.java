package com.tresg.util.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.model.SelectItem;

import com.tresg.incluido.jpa.ClienteJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

/*Clase que se reutiliza para registrar y actualizar una venta
 * 
 */
public class AtributoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<SelectItem> comprobantes;
	private int codigoComprobante;
	private List<SelectItem> series;
	private int numeroSerie;
	private int numeroComprobante;
	private Date fecha = new Date();
	// datos del cliente (tipodocumento,numeroDocumento,razon social)
	ClienteJPA cliente = new ClienteJPA();
	private List<ClienteJPA> clientes;
	private BigDecimal subtotal = new java.math.BigDecimal("0.00");// gravada																	// gravada);
	private BigDecimal igv = new java.math.BigDecimal("0.00");// sumatoria igv
	private BigDecimal total = new java.math.BigDecimal("0.00");// importe total
	private BigDecimal valor = new BigDecimal("1.18");

	// Adicional manejo interno
	private List<SelectItem> pagos;
	private int codigoPago;
	private String observacion;

	// datos del producto
	private String descripcionProducto;
	private int codigoProducto;
	private int codigoTipo;
	private BigDecimal precio = new java.math.BigDecimal("0.00");
	private BigDecimal cantidad = new java.math.BigDecimal("0.00");
	private List<ProductoJPA> productos;

	// atributos para consulta y actualizacion de venta
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();

	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public List<SelectItem> getComprobantes() {
		comprobantes = new ArrayList<>();
		sCombo.comboComprobante().stream()
				.forEach(c -> comprobantes.add(new SelectItem(c.getCodComprobante(), c.getDescripcion())));
		return comprobantes;
	}

	public void setComprobantes(List<SelectItem> comprobantes) {
		this.comprobantes = comprobantes;
	}

	public int getCodigoComprobante() {
		return codigoComprobante;
	}

	public void setCodigoComprobante(int codigoComprobante) {
		this.codigoComprobante = codigoComprobante;
	}

	public List<SelectItem> getSeries() {
		series = new ArrayList<>();
		sCombo.comboSerie(codigoComprobante)
				.forEach(c -> series.add(new SelectItem(c.getNroSerie(), c.getDescripcion())));
		return series;
	}

	public void setSeries(List<SelectItem> series) {
		this.series = series;
	}

	public int getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(int numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public int getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(int numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	public ClienteJPA getCliente() {
		return cliente;
	}

	public void setCliente(ClienteJPA cliente) {
		this.cliente = cliente;
	}

	public List<ClienteJPA> getClientes() {
		return clientes;
	}

	public void setClientes(List<ClienteJPA> clientes) {
		this.clientes = clientes;
	}


	public List<SelectItem> getPagos() {
		pagos = new ArrayList<>();
		sCombo.comboPago().stream().filter(a -> !"Anulado".equals(a.getDescripcion()))
				.forEach(p -> pagos.add(new SelectItem(p.getCodPago(), p.getDescripcion())));
		return pagos;
	}

	public void setPagos(List<SelectItem> pagos) {
		this.pagos = pagos;
	}

	public int getCodigoPago() {
		return codigoPago;
	}

	public void setCodigoPago(int codigoPago) {
		this.codigoPago = codigoPago;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public int getCodigoTipo() {
		return codigoTipo;
	}

	public void setCodigoTipo(int codigoTipo) {
		this.codigoTipo = codigoTipo;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public List<ProductoJPA> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoJPA> productos) {
		this.productos = productos;
	}

	
	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getIgv() {
		return igv;
	}

	public void setIgv(BigDecimal igv) {
		this.igv = igv;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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
