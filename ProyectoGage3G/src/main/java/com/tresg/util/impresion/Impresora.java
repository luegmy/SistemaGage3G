package com.tresg.util.impresion;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import java.util.HashMap;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.tresg.util.conexion.Conexion;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

@SuppressWarnings({ "deprecation" })
public class Impresora {

	private static Impresora instancia;
	String pdf = "application/pdf";

	private Impresora() {

	}

	static {
		try {
			instancia = new Impresora();
		} catch (RuntimeException ex) {
			throw ex;
		}
	}

	public static Impresora getImpresora() {
		return instancia;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void imprimirVenta(int numero, String montoLetra, String codigoBarra, String hash, String guiaSerie,
			String guia, String fechaPago) throws IOException, ClassNotFoundException, JRException, SQLException {
		Conexion.getInstancia().conectar();

		JasperReport ventaReporte = (JasperReport) JRLoader.loadObject(retornaFile("/facturaJSF.jasper"));
		Map parametro = new HashMap();
		parametro.put("numComprobante", numero);
		parametro.put("montoLetra", montoLetra);
		parametro.put("codigoBarra", codigoBarra);
		parametro.put("resumen", hash);
		parametro.put("guia", guiaSerie.concat("-").concat(guia));
		parametro.put("fechaPago", fechaPago);

		JasperPrint ventaPrint = JasperFillManager.fillReport(ventaReporte, parametro,
				Conexion.getInstancia().getConectar());
		retornaExporter(ventaPrint).exportReport();

		FacesContext.getCurrentInstance().responseComplete();

		Conexion.getInstancia().desconectar();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void verFactura(int numero, String montoLetra)
			throws IOException, ClassNotFoundException, JRException, SQLException {
		Conexion.getInstancia().conectar();

		JasperReport reporte = (JasperReport) JRLoader.loadObject(retornaFile("/facturaJSF.jasper"));
		Map parametro = new HashMap();
		parametro.put("numComprobante", numero);
		parametro.put("montoLetra", montoLetra);
		JasperPrint jasperPrint = JasperFillManager.fillReport(reporte, parametro,
				Conexion.getInstancia().getConectar());
		retornaExporter(jasperPrint).exportReport();

		FacesContext.getCurrentInstance().responseComplete();

		Conexion.getInstancia().desconectar();

	}

	ServletOutputStream retornaOut() throws IOException {
		FacesContext fcontext = FacesContext.getCurrentInstance();
		ExternalContext econtext = fcontext.getExternalContext();
		HttpServletResponse response = (HttpServletResponse) econtext.getResponse();
		ServletOutputStream out = response.getOutputStream();

		// archivo donde se guardara el reporte con sus datos
		response.setContentType(pdf);
		return out;
	}

	File retornaFile(String jasper) throws ClassNotFoundException, SQLException {
		FacesContext fcontext = FacesContext.getCurrentInstance();
		ExternalContext econtext = fcontext.getExternalContext();

		Conexion.getInstancia().conectar();

		// llamamos al metodo get conection que nos devuelve un Objeto
		// connection
		return new File(econtext.getRealPath(jasper));

	}

	@SuppressWarnings("rawtypes")
	JRExporter retornaExporter(JasperPrint jasperPrint) throws IOException {
		JRExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, retornaOut());

		return exporter;

	}

}
