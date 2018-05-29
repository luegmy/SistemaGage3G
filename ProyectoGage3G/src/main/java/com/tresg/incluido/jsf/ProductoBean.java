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

import com.tresg.incluido.jpa.ProductoJPA;
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
	private String descripcionProducto;
	private List<ProductoJPA> productos;

	// Formulario producto
	private int codigoProducto;
	private String descripcion;
	private BigDecimal precioCompra;
	private BigDecimal precioVenta;
	private int codigoTipoProducto;
	private int codigoMedida;

	private List<SelectItem> tiposProductos;
	private List<SelectItem> medidas;

	GestionarProductoService_I sProducto = IncluidoBusinessDelegate.getGestionarProductoService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

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
	}

	public void cancelarProducto() {
		limpiar();
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
		sProducto.listaProducto().stream().filter(p -> p.getDescripcion().toLowerCase().contains(descripcionProducto))
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
		for (TipoProductoJPA p : sCombo.listaTipoProducto()) {
			tiposProductos.add(new SelectItem(p.getCodTipo(), p.getDescripcion()));
		}
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
		for (UnidadMedidaJPA u : sCombo.listaUnidadMedida()) {
			medidas.add(new SelectItem(u.getCodMedida(), u.getDescripcion()));
		}
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

}
