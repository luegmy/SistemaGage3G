package com.tresg.factoria;

import com.tresg.incluido.dao.MysqlTipoProdiuctoDAO;
import com.tresg.incluido.dao.MysqlUnidadMedidaDAO;
import com.tresg.almacen.dao.MysqlMovimientoDAO;
import com.tresg.almacen.dao.MysqlDetalleAlmacenDAO;
import com.tresg.almacen.dao.MysqlDetalleMovimientoDAO;
import com.tresg.almacen.interfaz.MovimientoDAO;
import com.tresg.almacen.interfaz.DetalleAlmacenDAO;
import com.tresg.almacen.interfaz.DetalleMovimientoDAO;
import com.tresg.incluido.dao.MysqlClienteDAO;
import com.tresg.incluido.dao.MysqlComboDAO;
import com.tresg.incluido.dao.MysqlProductoDAO;
import com.tresg.incluido.interfaz.ClienteDAO;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.interfaz.TipoProductoDAO;
import com.tresg.incluido.interfaz.UnidadMedidaDAO;
import com.tresg.seguridad.dao.MysqlUsuarioDAO;
import com.tresg.seguridad.interfaz.UsuarioDAO;
import com.tresg.ventas.dao.MysqlCobranzaDAO;
import com.tresg.ventas.dao.MysqlDetalleVentaDAO;
import com.tresg.ventas.dao.MysqlVentaDAO;
import com.tresg.ventas.interfaz.CobranzaDAO;
import com.tresg.ventas.interfaz.DetalleVentaDAO;
import com.tresg.ventas.interfaz.VentaDAO;

public class MySqlDAOFactory extends DAOFactory {


	// Esta es una fabrica que crea DAOs especificos para  Mysql 


	@Override
	public TipoProductoDAO getTipoProductoDAO() {
		return new MysqlTipoProdiuctoDAO();
	}

	@Override
	public UnidadMedidaDAO getUnidadMedidaDAO() {
		return new MysqlUnidadMedidaDAO();
	}
	
	@Override
	public ProductoDAO getProductoDAO() {
		return new MysqlProductoDAO();
	}

	@Override
	public ComboDAO getComboDAO() {
		return new MysqlComboDAO();
	}
	
	@Override
	public ClienteDAO getClienteDAO() {
		return new MysqlClienteDAO();
	}

	@Override
	public VentaDAO getVentaDAO() {
		return new MysqlVentaDAO();
	}
	
	@Override
	public DetalleVentaDAO getDetalleVentaDAO() {
		return new MysqlDetalleVentaDAO();
	}
	
	@Override
	public CobranzaDAO getCobranzaDAO() {
		return new MysqlCobranzaDAO();
	}
	
	@Override
	public MovimientoDAO getMovimientoDAO() {
		return new MysqlMovimientoDAO();
	}

	@Override
	public DetalleAlmacenDAO getDetalleAlmacenDAO() {
		return new MysqlDetalleAlmacenDAO();
	}
	
	@Override
	public DetalleMovimientoDAO getDetalleMovimientoDAO() {
		return new MysqlDetalleMovimientoDAO();
	}



	@Override
	public UsuarioDAO getUsuarioDAO() {
		return new MysqlUsuarioDAO();
	}

	

	
}
