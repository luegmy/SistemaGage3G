package com.tresg.ventas.jsf;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.ConsultarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "productoVentaBean")
@ViewScoped
public class ProductoXVentaBean {

	// lista los detalles de dicha venta
	private List<DetalleVentaJPA> detalles;
	private Date fecha = new Date();
	private Date fechaIni = new Date();
	private Date fechaFin = new Date();

	private int acumulador = 0;
	private double monto = 0;

	ConsultarVentaBusinessService sConsultaVenta = VentasBusinessDelegate.getConsultarVentaService();

	public ProductoXVentaBean() {
		detalles = new ArrayList<>();
	}

	public void listarProductoVentaPorFecha() {
		detalles = new ArrayList<>();
		sConsultaVenta.listaDetalleVenta().stream().filter(f -> fecha.equals(f.getVenta().getFecha()))
				.forEach(detalles::add);

	}

	public int sumaPorFecha() {
		int aux = acumulador;
		acumulador = 0;
		return aux;
	}

	public double montoPorFecha() {
		double aux = monto;
		monto = 0;
		return aux;
	}

	public void valorAcumulado(int valor) {
		acumulador += valor;
	}

	public void montoAcumulado(Double valor) {
		monto += valor * acumulador;
	}

	public void listarProductoVentaPorRangoFecha() {
		detalles = new ArrayList<>();
		sConsultaVenta.listaDetalleVenta().stream()
				.filter(f -> (f.getVenta().getFecha().after(fechaIni) || fechaIni.equals(f.getVenta().getFecha()))
						&& (f.getVenta().getFecha().before(fechaFin) || fechaFin.equals(f.getVenta().getFecha())))
				.forEach(detalles::add);

	}

	@SuppressWarnings("resource")
	public void exportar() throws IOException {
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
				fila.createCell(1).setCellValue(d.getVenta().getNumComprobante()%10000000);
				fila.createCell(2).setCellValue(d.getId().getCodProducto());
				fila.createCell(3).setCellValue(d.getProducto().getDescripcion());
				fila.createCell(4).setCellValue(d.getProducto().getTipo().getDescripcion());
				fila.createCell(5).setCellValue(d.getCantidad().intValue());
				fila.createCell(6).setCellValue(d.getPrecio().doubleValue());


				rowNo++;
			}

			String rutaArchivo = System.getProperty("user.home").concat("/productosPorVenta.xls");
			File file = new File(rutaArchivo);
			FileOutputStream archivo = new FileOutputStream(file);
			libro.write(archivo);
			archivo.close();
			
			Desktop.getDesktop().open(file);
		}

	}

	public List<DetalleVentaJPA> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<DetalleVentaJPA> detalles) {
		this.detalles = detalles;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

}
