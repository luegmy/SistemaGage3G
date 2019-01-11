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

	// Para generar el archivo de la cabecera .cab
	public static final String RUC_EMISOR = "20601534208";
	private List<SelectItem> comprobantes;
	private int codigoComprobante;
	private String numeroSerie;
	private int numeroComprobante;

	// campos de la cabecera de la venta
	private List<SelectItem> operaciones;
	private String codigoOperacion = "";
	private Date fecha = new Date();
	private String hora;
	private Date fechaVence;
	public static final String CODIGO_DOMICILIO_FISCAL = "0000";
	ClienteJPA cliente = new ClienteJPA();
	private List<ClienteJPA> clientes;
	public static final String CODIGO_MONEDA = "PEN";

	// para las notas de credito y debito
	private List<SelectItem> notaCreditos;
	private List<SelectItem> notaDebitos;
	private List<SelectItem> notas;
	private int codigoComprobanteNota;
	private int numeroNota;
	private String codigoNota;
		
	private int facturaAnulada;
	private int nroFacturaAnulada;	
	private String serieAnulada;

	private BigDecimal igv = new java.math.BigDecimal("0.00");
	private BigDecimal subtotal = new java.math.BigDecimal("0.00");
	private BigDecimal total = new java.math.BigDecimal("0.00");
	public static final String TOTAL_DESCUENTOS = "0.00";
	public static final String SUMATORIA_OTROS_CARGOS = "0.00";
	public static final String TOTAL_ANTICIPOS = "0.00";
	public static final String VERSION_UBL = "2.1";
	public static final String CUSTOMIZACION_DOCUMENTO = "2.0";

	// campos del detalle de la venta
	private List<ProductoJPA> productos;
	private String unidad;
	private BigDecimal cantidad = new java.math.BigDecimal("0.00");
	private int codigoProducto;
	public static final String CODIGO_PRODUCTO_SUNAT = "-";
	private String descripcionProducto;
	private BigDecimal precio = new java.math.BigDecimal("0.00");
	public static final String CODIGO_IGV = "1000";
	public static final String NOMBRE_TRIBUTO_ITEM_IGV = "IGV";
	public static final String CODIGO_TIPO_TRIBUTO_IGV = "VAT";
	public static final String AFECTACION_IGV_ITEM = "10";
	public static final String PORCENTAJE_IGV = "18.00";

	public static final String CODIGO_ISC = "-";
	public static final String MONTO_ISC = "0.00";
	public static final String BASE_IMPONIBLE_ISC_ITEM = "0.00";
	public static final String NOMBRE_TRIBUTO_ITEM_ISC = "";
	public static final String CODIGO_TIPO_TRIBUTO_ISC = "";
	public static final String TIPO_SISTEMA_ISC = "";
	public static final String PORCENTAJE_ISC = "";

	public static final String VALOR_REFERENCIAL = "0.00";

	private int codigoTipo;
	private String observacion;
	private BigDecimal valor = new BigDecimal("1.18");

	// Adicional manejo interno
	private List<SelectItem> pagos;
	private int codigoPago;

	// atributos para consulta y actualizacion de venta
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();

	// atributo para adjuntar una guia
	private boolean guiaVenta;
	private String guiaSerie = "";
	private int guiaNumero = 0;

	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public List<SelectItem> getComprobantes() {
		comprobantes = new ArrayList<>();
		sCombo.comboComprobante().stream().filter(c->c.getCodComprobante()!=7||c.getCodComprobante()!=8)
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

	public String getNumeroSerie() {
		return numeroSerie;
	}

	public void setNumeroSerie(String numeroSerie) {
		this.numeroSerie = numeroSerie;
	}

	public int getNumeroComprobante() {
		return numeroComprobante;
	}

	public void setNumeroComprobante(int numeroComprobante) {
		this.numeroComprobante = numeroComprobante;
	}

	public List<SelectItem> getOperaciones() {
		operaciones = new ArrayList<>();
		sCombo.comboOperacion().stream()
				.forEach(o -> operaciones.add(new SelectItem(o.getCodOperacion(), o.getDescripcion())));
		return operaciones;
	}

	public void setOperaciones(List<SelectItem> operaciones) {
		this.operaciones = operaciones;
	}

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Date getFechaVence() {
		return fechaVence;
	}

	public void setFechaVence(Date fechaVence) {
		this.fechaVence = fechaVence;
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

	public List<SelectItem> getNotaCreditos() {
		notaCreditos = new ArrayList<>();
		sCombo.comboCredito().stream()
				.forEach(c -> notaCreditos.add(new SelectItem(c.getCodCredito(), c.getDescripcion())));
		return notaCreditos;
	}

	public void setNotaCreditos(List<SelectItem> notaCreditos) {
		this.notaCreditos = notaCreditos;
	}

	public List<SelectItem> getNotaDebitos() {
		notaDebitos=new ArrayList<>();
		sCombo.comboDebito().stream()
		.forEach(c -> notaDebitos.add(new SelectItem(c.getCodDebito(), c.getDescripcion())));
		return notaDebitos;
	}

	public void setNotaDebitos(List<SelectItem> notaDebitos) {
		this.notaDebitos = notaDebitos;
	}

	public List<SelectItem> getNotas() {
		notas=new ArrayList<>();
		sCombo.comboComprobante().stream().filter(c->c.getCodComprobante()==7 || c.getCodComprobante()==8)
		.forEach(c->notas.add(new SelectItem(c.getCodComprobante(),c.getDescripcion())));
		return notas;
	}

	public void setNotas(List<SelectItem> notas) {
		this.notas = notas;
	}

	public String getCodigoNota() {
		return codigoNota;
	}

	public void setCodigoNota(String codigoNota) {
		this.codigoNota = codigoNota;
	}

	public int getCodigoComprobanteNota() {
		return codigoComprobanteNota;
	}

	public void setCodigoComprobanteNota(int codigoComprobanteNota) {
		this.codigoComprobanteNota = codigoComprobanteNota;
	}

	public int getFacturaAnulada() {
		return facturaAnulada;
	}

	public void setFacturaAnulada(int facturaAnulada) {
		this.facturaAnulada = facturaAnulada;
	}

	public int getNroFacturaAnulada() {
		return nroFacturaAnulada;
	}

	public void setNroFacturaAnulada(int nroFacturaAnulada) {
		this.nroFacturaAnulada = nroFacturaAnulada;
	}

	public String getSerieAnulada() {
		return serieAnulada;
	}

	public void setSerieAnulada(String serieAnulada) {
		this.serieAnulada = serieAnulada;
	}

	public int getNumeroNota() {
		return numeroNota;
	}

	public void setNumeroNota(int numeroNota) {
		this.numeroNota = numeroNota;
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

	public String getUnidad() {
		return unidad;
	}

	public void setUnidad(String unidad) {
		this.unidad = unidad;
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

	public boolean isGuiaVenta() {
		return guiaVenta;
	}

	public void setGuiaVenta(boolean guiaVenta) {
		this.guiaVenta = guiaVenta;
	}

	public String getGuiaSerie() {
		return guiaSerie;
	}

	public void setGuiaSerie(String guiaSerie) {
		this.guiaSerie = guiaSerie;
	}

	public int getGuiaNumero() {
		return guiaNumero;
	}

	public void setGuiaNumero(int guiaNumero) {
		this.guiaNumero = guiaNumero;
	}

}
