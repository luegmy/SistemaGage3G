package com.tresg.util.formato;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Formateo implements Serializable{

	private static final long serialVersionUID = 1L;

	// talonario: es una funcion que retorna el numero de venta
	// de la concatenacion entre el comprobante y el numero del comprobante
	// 1=factura, 2=nota, 3=boleta, 4=guia de remision
	public int obtenerTalonario(int comprobante, int numeroComprobante) {

		String numero = String.format("%07d", numeroComprobante);
		String cadena = String.valueOf(comprobante).concat(numero);
		return Integer.valueOf(cadena);
	}
	
	public static void main(String[] args) {
		Formateo abc=new Formateo();
		String def=abc.obtenerFormatoNumeroComprobanteAnulado(78);
		System.out.println(def);
	}

	public String obtenerFecha(Date fecha) {
		// formato para registrar la fecha
		if(fecha==null) {
			return "-";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(fecha);

	}
	
	public String obtenerHora() {
		// formato para registrar la hora
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
		return sdf.format(new java.util.Date());

	}
	
	public String obtenerFormatoComprobante(int comprobante) {
		int a = comprobante;
		String patronA = "%02d";
		return String.format(patronA, a);
	}

	public String obtenerFormatoNumeroComprobante(int numero) {
		int b = numero;
		String patronB = "%08d";
		return String.format(patronB, b);
	}
	
	public String obtenerFormatoNumeroComprobanteAnulado(int numero) {
		int b = numero;
		String patronB = "%07d";
		return String.format(patronB, b);
	}

		
}
