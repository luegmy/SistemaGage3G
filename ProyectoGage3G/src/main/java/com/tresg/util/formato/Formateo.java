package com.tresg.util.formato;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tresg.ventas.dao.MysqlDetalleVentaDAO;
import com.tresg.ventas.jpa.DetalleVentaJPA;

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
	
	public static void main(String[] args) throws IOException {
		MysqlDetalleVentaDAO m=new MysqlDetalleVentaDAO();
		List<DetalleVentaJPA>detalles=m.listarDetalleVenta();
		if (detalles != null && !detalles.isEmpty()) {

			XSSFWorkbook libro = new XSSFWorkbook();
			XSSFSheet hoja = libro.createSheet("Productos por venta");

			XSSFRow filaCabecera = hoja.createRow(0);

			Cell celda = filaCabecera.createCell(0);
			celda.setCellValue("CDP");
			Cell celda1 = filaCabecera.createCell(1);
			celda1.setCellValue("NUMERO");
			Cell celda2 = filaCabecera.createCell(2);
			celda2.setCellValue("CODIGO");
			Cell celda3 = filaCabecera.createCell(3);
			celda3.setCellValue("PRODUCTO");
			Cell celda4 = filaCabecera.createCell(4);
			celda4.setCellValue("TIPO");
			Cell celda5 = filaCabecera.createCell(5);
			celda5.setCellValue("CANTIDAD");
			Cell celda6 = filaCabecera.createCell(6);
			celda6.setCellValue("PRECIO");

			Font cabeceraFont = libro.createFont();
			cabeceraFont.setBold(true);
			cabeceraFont.setFontName("Arial");
			cabeceraFont.setFontHeightInPoints((short) 12);

			XSSFCellStyle cabeceraStyle = libro.createCellStyle();
			cabeceraStyle.setFont(cabeceraFont);
			cabeceraStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(128, 0, 128)));
			cabeceraStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			celda.setCellStyle(cabeceraStyle);
			celda1.setCellStyle(cabeceraStyle);
			celda2.setCellStyle(cabeceraStyle);
			celda3.setCellStyle(cabeceraStyle);
			celda4.setCellStyle(cabeceraStyle);
			celda5.setCellStyle(cabeceraStyle);
			celda6.setCellStyle(cabeceraStyle);

			short rowNo = 1;

			for (DetalleVentaJPA d : detalles) {
				XSSFRow fila = hoja.createRow(rowNo);
				fila.createCell(0).setCellValue(d.getVenta().getComprobante().getDescripcion());
				fila.createCell(1).setCellValue(d.getVenta().getNumComprobante());
				fila.createCell(2).setCellValue(d.getId().getCodProducto());
				fila.createCell(3).setCellValue(d.getProducto().getDescripcion());
				fila.createCell(4).setCellValue(d.getProducto().getTipo().getDescripcion());
				fila.createCell(5).setCellValue(d.getCantidad().intValue());
				fila.createCell(6).setCellValue(d.getPrecio().doubleValue());


				rowNo++;
			}

			String rutaArchivo = System.getProperty("user.home").concat("/ejemploExcelJava.xls");
			File file = new File(rutaArchivo);
			FileOutputStream archivo = new FileOutputStream(file);
			libro.write(archivo);
			archivo.close();
			
			Desktop.getDesktop().open(file);

		}
	}

		
}
