
package com.tresg.factoria;

import com.tresg.incluido.interfaz.ClienteDAO;
import com.tresg.incluido.interfaz.ComboDAO;
import com.tresg.incluido.interfaz.ProductoDAO;
import com.tresg.incluido.interfaz.TipoProductoDAO;
import com.tresg.incluido.interfaz.UnidadMedidaDAO;

public abstract class DAOFactory{

	public static final int MYSQL = 1;
	
    public static DAOFactory getDAOFactory(int whichFactory){
        switch(whichFactory){
       	case MYSQL:
        	    return new MySqlDAOFactory();
        	default:
        	    return null;
        }
     }
    
    // Existira un metodo por cada DAO que pueda ser creado.

	//MODULO GESTION Y/O INCLUIDO
    public abstract TipoProductoDAO getTipoProductoDAO();
    public abstract UnidadMedidaDAO getUnidadMedidaDAO();
    public abstract ProductoDAO getProductoDAO();
    public abstract ComboDAO getComboDAO();
    
    public abstract ClienteDAO getClienteDAO();
   
    
    
}
