package com.tresg.incluido.jsf;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.tresg.almacen.jpa.DetalleAlmacenJPA;
import com.tresg.almacen.service.AlmacenBusinessDelegate;
import com.tresg.almacen.service.ConsultarAlmacenBusinessService;
import com.tresg.incluido.dao.MysqlProductoDAO;
import com.tresg.incluido.jpa.ClaseJPA;
import com.tresg.incluido.jpa.ProductoJPA;
import com.tresg.incluido.jpa.ProductoSunatJPA;
import com.tresg.incluido.jpa.TipoProductoJPA;
import com.tresg.incluido.jpa.UnidadMedidaJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.GestionarProductoService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;

@ManagedBean(name = "productoBean")
@SessionScoped
public class ProductoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Formulario buscar
	private String descripcionProducto = "";
	private List<ProductoJPA> productos;

	// Formulario producto
	private int codigoProducto;
	private String descripcion;
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;
	private int codigoTipoProducto;
	private int codigoMedida;
	private int codigoClase;
	private String codigoProdSunat;

	private List<SelectItem> tiposProductos;
	private List<SelectItem> medidas;
	private List<SelectItem> clases;
	private List<SelectItem> prodSunats;

	private List<DetalleAlmacenJPA> existencias;

	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();
	ConsultarAlmacenBusinessService sConsultaAlmacen = AlmacenBusinessDelegate.getConsultarAlmacenService();

	// Metodo donde se agrega los atributos del producto en las respectivas
	// cajas de texto del formulario producto
	public void editarProducto(ActionEvent e) {
		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		ProductoJPA objProducto;
		objProducto = sProducto.buscaProductoPorCodigo(codigo);
		setCodigoProducto(objProducto.getCodProducto());
		setDescripcion(objProducto.getDescripcion());
		setPrecioCompra(objProducto.getPrecioCompra());
		setPrecioVenta(objProducto.getPrecioVenta());
		setCodigoTipoProducto(objProducto.getTipo().getCodTipo());
		setCodigoMedida(objProducto.getMedida().getCodMedida());
		setCodigoProdSunat(objProducto.getProdSunat());
	}

	public void actualizarProducto() {
		FacesContext context = FacesContext.getCurrentInstance();

		TipoProductoJPA objT = new TipoProductoJPA();
		objT.setCodTipo(codigoTipoProducto);

		UnidadMedidaJPA objM = new UnidadMedidaJPA();
		objM.setCodMedida(codigoMedida);

		ProductoJPA objP = new ProductoJPA();
		objP.setCodProducto(codigoProducto);
		objP.setDescripcion(descripcion);
		objT.setCodTipo(codigoTipoProducto);
		objP.setPrecioCompra(precioCompra);
		objP.setPrecioVenta(precioVenta);
		objP.setProdSunat(codigoProdSunat);
		objP.setTipo(objT);
		objP.setMedida(objM);

		context.addMessage("mensajeRegistroProducto",
				new FacesMessage(FacesMessage.SEVERITY_INFO, sProducto.actualizaProducto(objP), null));
		limpiar();
	}

	public void eliminarProducto(ActionEvent e) {
		FacesContext context = FacesContext.getCurrentInstance();
		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		context.addMessage("mensajeProductoEliminado",
				new FacesMessage(FacesMessage.SEVERITY_INFO, sProducto.eliminaProducto(codigo), null));
	}

	public void limpiar() {
		codigoProducto = 0;
		descripcion = "";
		precioCompra = new java.math.BigDecimal("0.00");
		precioVenta = new java.math.BigDecimal("0.00");
		codigoTipoProducto = 0;
		codigoMedida = 0;
		codigoClase = 0;
		codigoProdSunat = "";
	}

	public void mostrarExistenciaXAlmacen(ActionEvent e) {
		existencias = new ArrayList<>();
		int codigo = (int) e.getComponent().getAttributes().get("codigo");
		sConsultaAlmacen.listaDetalleAlmacen().stream().filter(d -> d.getId().getCodProducto() == codigo)
				.forEach(existencias::add);

	}

	public void setProductos(List<ProductoJPA> productos) {
		this.productos = productos;
	}

	public void setDescripcionProducto(String descripcionProducto) {
		this.descripcionProducto = descripcionProducto;
	}

	public String getDescripcionProducto() {
		return descripcionProducto;
	}

	public List<ProductoJPA> getProductos() {
		productos = new ArrayList<>();
		sProducto.listaProducto().stream()
				.filter(p -> p.getDescripcion().toLowerCase().contains(descripcionProducto.toLowerCase()))
				.forEach(productos::add);
		return productos;
	}

	public void setCodigoProducto(int codigoProducto) {
		this.codigoProducto = codigoProducto;
	}

	public int getCodigoProducto() {
		return codigoProducto;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public BigDecimal getPrecioCompra() {
		return precioCompra;
	}

	public void setPrecioCompra(BigDecimal precioCompra) {
		this.precioCompra = precioCompra;
	}

	public BigDecimal getPrecioVenta() {
		return precioVenta;
	}

	public void setPrecioVenta(BigDecimal precioVenta) {
		this.precioVenta = precioVenta;
	}

	// lista despegable tipo de productos
	public List<SelectItem> getTiposProductos() {
		tiposProductos = new ArrayList<>();
		sCombo.comboTipoProducto().stream()
				.forEach(p -> tiposProductos.add(new SelectItem(p.getCodTipo(), p.getDescripcion())));
		return tiposProductos;
	}

	public void setTiposProductos(List<SelectItem> tiposProductos) {
		this.tiposProductos = tiposProductos;
	}

	public int getCodigoTipoProducto() {
		return codigoTipoProducto;
	}

	public void setCodigoTipoProducto(int codigoTipoProducto) {
		this.codigoTipoProducto = codigoTipoProducto;
	}

	public List<SelectItem> getMedidas() {
		medidas = new ArrayList<>();
		sCombo.comboUnidadMedida().stream()
				.forEach(u -> medidas.add(new SelectItem(u.getCodMedida(), u.getDescripcion())));

		return medidas;
	}

	public void setMedidas(List<SelectItem> medidas) {
		this.medidas = medidas;
	}

	public int getCodigoMedida() {
		return codigoMedida;
	}

	public void setCodigoMedida(int codigoMedida) {
		this.codigoMedida = codigoMedida;
	}

	public int getCodigoClase() {
		return codigoClase;
	}

	public void setCodigoClase(int codigoClase) {
		this.codigoClase = codigoClase;
	}

	public String getCodigoProdSunat() {
		return codigoProdSunat;
	}

	public void setCodigoProdSunat(String codigoProdSunat) {
		this.codigoProdSunat = codigoProdSunat;
	}

	public List<SelectItem> getClases() {
		clases = new ArrayList<>();
		sCombo.comboClase().stream().forEach(c -> clases.add(new SelectItem(c.getCodClase(), c.getDescripcion())));

		return clases;
	}

	public void setClases(List<SelectItem> clases) {
		this.clases = clases;
	}

	public List<SelectItem> getProdSunats() {
		prodSunats = new ArrayList<>();
		sCombo.comboProductoSunat().stream().filter(m->m.getClase().getCodClase()==codigoClase)
				.forEach(p -> prodSunats.add(new SelectItem(p.getCodProdSunat(), p.getDescripcion())));

		return prodSunats;
	}

	public void setProdSunats(List<SelectItem> prodSunats) {
		this.prodSunats = prodSunats;
	}

	public List<DetalleAlmacenJPA> getExistencias() {
		return existencias;
	}

	public void setExistencias(List<DetalleAlmacenJPA> existencias) {
		this.existencias = existencias;
	}

}
