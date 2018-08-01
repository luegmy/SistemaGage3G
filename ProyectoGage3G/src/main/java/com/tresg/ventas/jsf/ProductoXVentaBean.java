package com.tresg.ventas.jsf;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.tresg.ventas.jpa.DetalleVentaJPA;
import com.tresg.ventas.service.ConsultarVentaBusinessService;
import com.tresg.ventas.service.VentasBusinessDelegate;

@ManagedBean(name = "productoVentaBean")
@SessionScoped
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

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=reporteProductosPorVentas.xlsx");
			response.setContentType("application/vnd.ms-excel");

			XSSFWorkbook libro = new XSSFWorkbook();
			XSSFSheet hoja = libro.createSheet("Productos por venta");

			XSSFRow filaCabecera = hoja.createRow(0);

			Cell celda = filaCabecera.createCell(0);
			celda.setCellValue("CDP");
			Cell celda1 = filaCabecera.createCell(1);
			celda1.setCellValue("NRO");
			Cell celda2 = filaCabecera.createCell(2);
			celda2.setCellValue("COD");
			Cell celda3 = filaCabecera.createCell(3);
			celda3.setCellValue("PRODUCTO");
			Cell celda4 = filaCabecera.createCell(4);
			celda4.setCellValue("TIPO");
			Cell celda5 = filaCabecera.createCell(5);
			celda5.setCellValue("CANT");
			Cell celda6 = filaCabecera.createCell(6);
			celda6.setCellValue("PRECIO");

			XSSFCellStyle cabeceraEstilo = libro.createCellStyle();
			cabeceraEstilo.setFillBackgroundColor(IndexedColors.BLACK.getIndex());
			cabeceraEstilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			
			XSSFFont cabeceraFont = libro.createFont();
			cabeceraFont.setBold(true);
			cabeceraFont.setFontName("Arial");
			cabeceraFont.setFontHeightInPoints((short) 12);
			cabeceraFont.setColor(IndexedColors.WHITE.getIndex());
			cabeceraEstilo.setFont(cabeceraFont);

			celda.setCellStyle(cabeceraEstilo);
			celda1.setCellStyle(cabeceraEstilo);
			celda2.setCellStyle(cabeceraEstilo);
			celda3.setCellStyle(cabeceraEstilo);
			celda4.setCellStyle(cabeceraEstilo);
			celda5.setCellStyle(cabeceraEstilo);
			celda6.setCellStyle(cabeceraEstilo);

			short rowNo = 1;

			for (DetalleVentaJPA d : detalles) {

				XSSFRow fila = hoja.createRow(rowNo);
				fila.createCell(0).setCellValue(d.getVenta().getComprobante().getDescripcion());
				fila.createCell(1).setCellValue(d.getVenta().getNumComprobante() % 10000000);
				fila.createCell(2).setCellValue(d.getId().getCodProducto());
				fila.createCell(3).setCellValue(d.getProducto().getDescripcion());
				fila.createCell(4).setCellValue(d.getProducto().getTipo().getDescripcion());
				fila.createCell(5).setCellValue(d.getCantidad().intValue());
				fila.createCell(6).setCellValue(d.getPrecio().doubleValue());

				rowNo++;
			}

			OutputStream out = response.getOutputStream();
			libro.write(out);
			response.getOutputStream().flush();
			FacesContext.getCurrentInstance().responseComplete();

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
