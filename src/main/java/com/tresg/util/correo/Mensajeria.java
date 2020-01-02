package com.tresg.util.correo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class Mensajeria {


	public void envioFirma(String cliente, String facturacion, String fecha, String monto, String firma, String destinatario) throws Exception {
		// Propiedades de la conexi�n
		Properties props = new Properties();

		props.setProperty("mail.smtp.host", "smtp-mail.outlook.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port", "587");
		props.setProperty("mail.smtp.user", "Importaciones-seg-ind-3G@outlook.com");
		props.setProperty("mail.smtp.auth", "true");

		// Preparamos la sesion
		Session sesionEmail = Session.getDefaultInstance(props, null);
		sesionEmail.setDebug(true);

		/** MultiPart para crear mensajes compuestos. */
		MimeMultipart multiParte = new MimeMultipart();

		// Construir un correo de texto con un adjunto
		BodyPart texto = new MimeBodyPart();
		Map<String, String> input = new HashMap<>();
		input.put("cliente", cliente);
		input.put("factura", facturacion);
		input.put("fecha", fecha);
		input.put("monto", monto);
		
		String contenidoHTML = leerEmailDesdeHtml("C:\\Program Files\\Apache Software Foundation\\apache-tomcat-8.0.14\\webapps\\ProyectoGage3G\\email.xhtml", input);
		texto.setContent(contenidoHTML, "text/html");
		multiParte.addBodyPart(texto);

		BodyPart adjuntoXML = new MimeBodyPart();
		adjuntoXML.setDataHandler(
				new DataHandler(new FileDataSource("C:\\SFS_v1.2\\sunat_archivos\\sfs\\FIRMA\\" + firma + ".xml")));
		adjuntoXML.setFileName(firma + ".xml");
		multiParte.addBodyPart(adjuntoXML);
		
		BodyPart adjuntoPDF = new MimeBodyPart();
		adjuntoPDF.setDataHandler(
				new DataHandler(new FileDataSource("C:\\SFS_v1.2\\sunat_archivos\\sfs\\REPO\\" + facturacion + ".pdf")));
		adjuntoPDF.setFileName(facturacion + ".pdf");
		multiParte.addBodyPart(adjuntoPDF);

		// Lo enviamos.
		Transport t = sesionEmail.getTransport();// "smtp"

		// Construimos el mensaje
		MimeMessage message = new MimeMessage(sesionEmail);
		// Agregar quien env�a el correo
		message.setFrom(new InternetAddress("Importaciones-seg-ind-3G@outlook.com"));
		// Se rellenan los destinatarios
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
		// Se rellena el subject
		message.setSubject("IMPORTACIONES & SEGURIDAD INDUSTRIAL 3G te ha enviado la Factura Electronica : "+ facturacion);
		// Se mete el texto y la foto adjunta.
		message.setContent(multiParte, "text/html");

		t.connect("Importaciones-seg-ind-3G@outlook.com", "trinidad3G");
		t.sendMessage(message, message.getAllRecipients());
		t.close();

	}

	protected String leerEmailDesdeHtml(String ruta, Map<String, String> entrada) {
		
		String msg = leerContenidoDesdeArchivo(ruta);
		try {
			Set<Entry<String, String>> entries = entrada.entrySet();
			for (Map.Entry<String, String> entry : entries) {
				msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return msg;
	}

	private String leerContenidoDesdeArchivo(String archivo) {
		
		StringBuffer contenidos = new StringBuffer();

		try {
			// use buffering, reading one line at a time
			BufferedReader reader = new BufferedReader(new FileReader(archivo));
			try {
				String line = null;
				while ((line = reader.readLine()) != null) {
					contenidos.append(line);
					contenidos.append(System.getProperty("line.separator"));
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contenidos.toString();
	}

}
