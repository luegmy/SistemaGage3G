package com.tresg.util.formato;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class Formateo {

	// talonario: es una funcion que retorna el numero de venta
	// de la concatenacion entre el comprobante y el numero del comprobante
	// 1=factura, 2=nota, 3=boleta, 4=guia de remision
	public int obtenerTalonario(int comprobante, int numeroComprobante) {

		String numero = String.format("%07d", numeroComprobante);
		String cadena = String.valueOf(comprobante).concat(numero);
		return Integer.valueOf(cadena);
	}
	
	public String obtenerHora() {
		// formato para registrar la hora
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");		
		return sdf.format(new java.util.Date());
		
	}
	
	
}
