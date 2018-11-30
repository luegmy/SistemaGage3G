package com.tresg.util.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class Conexion {

	private Connection conectar; // Abstrae una conexion a la base de datos
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String baseDatos = "jdbc:mysql://localhost:3306/bd_3g?zeroDateTimeBehavior=convertToNull&amp;serverTimezone=UTC";
	private static Conexion instancia;

	private Conexion() {

	}

	/** Crea a new instancia de Conexion */
	public static Conexion getInstancia() {
		if (Conexion.instancia == null) {
			Conexion.instancia = new Conexion();
		}
		return instancia;
	}

	public void conectar() throws ClassNotFoundException, SQLException {
		// si la conecion es null nos conectamos
		if (this.getConectar() != null) {
			return;
		} else if (this.getConectar() == null) {

			Class.forName(this.getDriver());
			this.setConectar((Connection) DriverManager.getConnection(this.getBaseDatos(), "root", "admin"));
		}
	}

	/** desconecta de la base de datos */
	public void desconectar() {
		if (this.getConectar() == null)
			this.setConectar(null);

	}

	/* Metodos getter y setter */

	public Connection getConectar() {
		return conectar;
	}

	public void setConectar(Connection conectar) {
		this.conectar = conectar;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getBaseDatos() {
		return baseDatos;
	}

	public void setBaseDatos(String baseDatos) {
		this.baseDatos = baseDatos;
	}
	
}
