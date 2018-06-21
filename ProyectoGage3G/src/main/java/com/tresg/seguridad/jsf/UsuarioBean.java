package com.tresg.seguridad.jsf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.tresg.incluido.jpa.EmpleadoJPA;
import com.tresg.incluido.service.ComboService_I;
import com.tresg.incluido.service.IncluidoBusinessDelegate;
import com.tresg.seguridad.jpa.RolJPA;
import com.tresg.seguridad.jpa.UsuarioJPA;
import com.tresg.seguridad.service.AutenticarUsuarioBusinessService;
import com.tresg.seguridad.service.SeguridadBusinessDelegate;

@ManagedBean(name = "usuario")
@SessionScoped
public class UsuarioBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MenuModel modelo=new DefaultMenuModel();
	private int dni;
	private String nombreEmpleado;
	private String direccion;
	private int telefono;
	private int codigoUsuario;
	private String usuario;
	private String clave;
	private List<SelectItem> roles;
	private int codigoRol;

	private List<UsuarioJPA> usuarios;
	private String busqueda = "";
	private int sesionCodigoUsuario;
	private String sesionUsuario;

	String login = "LOGIN";
	String menuVenta = "Venta";
	String menuAlamcen = "Almacen";
	String menuCompra = "Compra";
	String menuContacto = "Contacto";
	String menuCotizacion = "Cotizacion";
	String menuProducto = "Producto";
	String menuReporte = "Reporte";
	String icono = "ui-icon-person";

	AutenticarUsuarioBusinessService sSeguridad = SeguridadBusinessDelegate.getAutenticarUsuarioService();
	ComboService_I sCombo = IncluidoBusinessDelegate.getComboService();

	public String login() {

		FacesContext context = FacesContext.getCurrentInstance();
		UsuarioJPA objUsuario = sSeguridad.validaUsuario(usuario);
		if (!objUsuario.getUsuario().equals(usuario)) {
			context.addMessage("mensajeLogin",
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario incorrecto", null));
			return login;
		} else if (!objUsuario.getClave().equals(clave)) {
			context.addMessage("mensajeLogin", new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave incorrecta", null));
			return login;
		} else {
			if (objUsuario.getRol().getCodRol() == 1) {
				menuTodo();

			}
			if (objUsuario.getRol().getCodRol() == 2) {
				menuVenta();
				menuProducto();
				menuCotizacion();
				menuReporte();
			}
			if (objUsuario.getRol().getCodRol() == 3) {
				menuAlmacen();
				menuProducto();
			}

			setSesionUsuario(objUsuario.getEmpleado().getNombre());
			setSesionCodigoUsuario(objUsuario.getCodUsuario());
			return  "home.xhtml?faces-redirect=true";
		}

	}

	public String logout() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return login;// indicas a donde quieres direccionar despu�s de cerrar
						// sesi�n
	}

	public void editarUsuario(ActionEvent e) {
		int codigo = (Integer) e.getComponent().getAttributes().get("codigo");
		UsuarioJPA objUsuario = sSeguridad.buscaUsuarioPorCodigo(codigo);
		setDni(objUsuario.getEmpleado().getDni());
		setNombreEmpleado(objUsuario.getEmpleado().getNombre());
		setDireccion(objUsuario.getEmpleado().getDireccion());
		setTelefono(objUsuario.getEmpleado().getTelefono());
		setCodigoUsuario(objUsuario.getCodUsuario());
		setUsuario(objUsuario.getUsuario());
		setClave(objUsuario.getClave());
		setCodigoRol(objUsuario.getRol().getCodRol());

	}

	public void actualizarUsuario() {
		FacesContext context = FacesContext.getCurrentInstance();
		EmpleadoJPA objE = new EmpleadoJPA();
		objE.setDni(dni);
		objE.setNombre(nombreEmpleado);
		objE.setDireccion(direccion);
		objE.setTelefono(telefono);

		RolJPA objRol = new RolJPA();
		objRol.setCodRol(codigoRol);

		UsuarioJPA objU = new UsuarioJPA();
		objU.setCodUsuario(codigoUsuario);
		objU.setUsuario(usuario);
		objU.setClave(clave);
		objU.setRol(objRol);
		objU.setEmpleado(objE);

		context.addMessage("mensajeRegistroUsuario",
				new FacesMessage(FacesMessage.SEVERITY_INFO, sSeguridad.registraUsuario(objE, objU), null));

		limpiar();
	}

	public void cancelarUsuario() {
		limpiar();
	}

	public void limpiar() {
		dni = 0;
		codigoUsuario = 0;
		nombreEmpleado = "";
		direccion = "";
		telefono = 0;
		usuario = "";
		clave = "";
		codigoRol = 0;

	}

	public void menuVenta() {
		
		DefaultSubMenu ventaSubmenu = new DefaultSubMenu(menuVenta);

		DefaultMenuItem item = new DefaultMenuItem("Registrar venta");
		item.setUrl("registroVenta.xhtml");
		item.setIcon("ui-icon-cart");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Venta realizada");
		item.setUrl("consultaVentaModificada.xhtml");
		item.setIcon("ui-icon-calculator");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Venta por cobrar");
		item.setUrl("consultaFacturaPendiente.xhtml");
		item.setIcon("ui-icon-clipboard");
		ventaSubmenu.addElement(item);

		modelo.addElement(ventaSubmenu);

		DefaultSubMenu contactoSubmenu = new DefaultSubMenu(menuContacto);

		item = new DefaultMenuItem("Cliente");
		item.setUrl("actualizaCliente.xhtml");
		item.setIcon("ui-icon-person");
		contactoSubmenu.addElement(item);

		modelo.addElement(contactoSubmenu);

	}

	public void menuAlmacen() {
		
		DefaultSubMenu almacenSubmenu = new DefaultSubMenu(menuAlamcen);

		DefaultMenuItem item = new DefaultMenuItem("Movimientos");
		item.setUrl("registroAlmacen.xhtml");
		item.setIcon("ui-icon-bookmark");
		almacenSubmenu.addElement(item);

		item = new DefaultMenuItem("Kardex");
		item.setUrl("consultaKardex.xhtml");
		item.setIcon("ui-icon-flag");
		almacenSubmenu.addElement(item);
		
		modelo.addElement(almacenSubmenu);

		DefaultSubMenu contactoSubmenu = new DefaultSubMenu(menuContacto);

		item = new DefaultMenuItem("Proveedor");
		item.setUrl("actualizaProveedor.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);

		modelo.addElement(contactoSubmenu);

	}
	
	public void menuCompra() {
		
		DefaultSubMenu compraSubmenu = new DefaultSubMenu(menuCompra);

		DefaultMenuItem item = new DefaultMenuItem("Registrar compra");
		item.setUrl("registroCompra.xhtml");
		item.setIcon("ui-icon-newwin");
		compraSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Consultar compra");
		item.setUrl("consultaCompra.xhtml");
		item.setIcon("ui-icon-newwin");
		compraSubmenu.addElement(item);
		
		modelo.addElement(compraSubmenu);

	}

	public void menuCotizacion() {
		
		DefaultSubMenu cotizacionSubmenu = new DefaultSubMenu(menuCotizacion);

		DefaultMenuItem item = new DefaultMenuItem(menuCotizacion);
		item.setUrl("registroCotizacion.xhtml");
		item.setIcon("ui-icon-copy");
		cotizacionSubmenu.addElement(item);

		item = new DefaultMenuItem("Consultas");
		item.setUrl("consultaCotizacion.xhtml");
		item.setIcon("ui-icon-note");
		cotizacionSubmenu.addElement(item);

		modelo.addElement(cotizacionSubmenu);

	}

	public void menuContacto() {
		
		DefaultSubMenu contactoSubmenu = new DefaultSubMenu(menuContacto);

		DefaultMenuItem item = new DefaultMenuItem("Cliente");
		item.setUrl("actualizaCliente.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);

		item = new DefaultMenuItem("Proveedor");
		item.setUrl("actualizaProveedor.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);

		item = new DefaultMenuItem("Empleado");
		item.setUrl("actualizaUsuario.xhtml");
		item.setIcon(icono);
		contactoSubmenu.addElement(item);

		modelo.addElement(contactoSubmenu);

	}

	public void menuProducto() {
		
		DefaultSubMenu productoSubmenu = new DefaultSubMenu(menuProducto);

		DefaultMenuItem item = new DefaultMenuItem(menuProducto);
		item.setUrl("actualizaProducto.xhtml");
		item.setIcon("ui-icon-tag");
		productoSubmenu.addElement(item);

		item = new DefaultMenuItem("Tipo Producto");
		item.setUrl("actualizaTipoProducto.xhtml");
		item.setIcon("ui-icon-lightbulb");
		productoSubmenu.addElement(item);

		item = new DefaultMenuItem("Unidad Medida");
		item.setUrl("actualizaMedida.xhtml");
		item.setIcon("ui-icon-wrench");
		productoSubmenu.addElement(item);

		modelo.addElement(productoSubmenu);

	}

	public void menuReporte() {

		DefaultSubMenu reporteSubmenu = new DefaultSubMenu(menuReporte);

		DefaultMenuItem item = new DefaultMenuItem("Consulta venta");
		item.setUrl("consultaVenta.xhtml");
		item.setIcon("ui-icon-info");
		reporteSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Productos cliente");
		item.setUrl("consultaProductoXCliente.xhtml");
		item.setIcon("ui-icon-pin-w");
		reporteSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Productos venta");
		item.setUrl("consultaProductoXVenta.xhtml");
		item.setIcon("ui-icon-pin-w");
		reporteSubmenu.addElement(item);
		
		modelo.addElement(reporteSubmenu);

	}

	public void menuTodo() {
		

		DefaultSubMenu ventaSubmenu = new DefaultSubMenu(menuVenta);

		DefaultMenuItem item = new DefaultMenuItem("Registrar venta");
		item.setUrl("registroVenta.xhtml");
		item.setIcon("ui-icon-cart");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Venta realizada");
		item.setUrl("consultaVentaModificada.xhtml");
		item.setIcon("ui-icon-calculator");
		ventaSubmenu.addElement(item);

		item = new DefaultMenuItem("Venta por cobrar");
		item.setUrl("consultaFacturaPendiente.xhtml");
		item.setIcon("ui-icon-clipboard");
		ventaSubmenu.addElement(item);

		modelo.addElement(ventaSubmenu);

		DefaultSubMenu almacenSubmenu = new DefaultSubMenu(menuAlamcen);

		item = new DefaultMenuItem("Movimientos");
		item.setUrl("registroAlmacen.xhtml");
		item.setIcon("ui-icon-bookmark");
		almacenSubmenu.addElement(item);

		item = new DefaultMenuItem("Kardex");
		item.setUrl("consultaKardex.xhtml");
		item.setIcon("ui-icon-flag");
		almacenSubmenu.addElement(item);
		
		item = new DefaultMenuItem("Inventario");
		item.setUrl("registroInventario.xhtml");
		item.setIcon("ui-icon-star");
		almacenSubmenu.addElement(item);

		modelo.addElement(almacenSubmenu);

		menuCompra();
		menuCotizacion();
		menuContacto();
		menuProducto();
		menuReporte();

	}

	public List<UsuarioJPA> getUsuarios() {
		usuarios = new ArrayList<>();
		for (UsuarioJPA c : sSeguridad.buscaUsuarioPorNombre(busqueda)) {
			usuarios.add(c);
		}
		return usuarios;
	}

	public void setUsuarios(List<UsuarioJPA> usuarios) {
		this.usuarios = usuarios;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public String getNombreEmpleado() {
		return nombreEmpleado;
	}

	public void setNombreEmpleado(String nombreEmpleado) {
		this.nombreEmpleado = nombreEmpleado;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public int getTelefono() {
		return telefono;
	}

	public void setTelefono(int telefono) {
		this.telefono = telefono;
	}

	public int getCodigoUsuario() {
		return codigoUsuario;
	}

	public void setCodigoUsuario(int codigoUsuario) {
		this.codigoUsuario = codigoUsuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public List<SelectItem> getRoles() {
		roles = new ArrayList<>();
		for (RolJPA a : sCombo.comboRol()) {
			roles.add(new SelectItem(a.getCodRol(), a.getDescripcion()));
		}
		return roles;
	}

	public void setRoles(List<SelectItem> roles) {
		this.roles = roles;
	}

	public int getCodigoRol() {
		return codigoRol;
	}

	public void setCodigoRol(int codigoRol) {
		this.codigoRol = codigoRol;
	}

	public String getBusqueda() {
		return busqueda;
	}

	public void setBusqueda(String busqueda) {
		this.busqueda = busqueda;
	}

	public String getSesionUsuario() {
		return sesionUsuario;
	}

	public void setSesionUsuario(String sesionUsuario) {
		this.sesionUsuario = sesionUsuario;
	}

	public int getSesionCodigoUsuario() {
		return sesionCodigoUsuario;
	}

	public void setSesionCodigoUsuario(int sesionCodigoUsuario) {
		this.sesionCodigoUsuario = sesionCodigoUsuario;
	}

	public MenuModel getModelo() {
		return modelo;
	}

	public void setModelo(MenuModel modelo) {
		this.modelo = modelo;
	}

}
