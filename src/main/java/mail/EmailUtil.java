package main.java.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailUtil {

	/**
	 * Utility method to send simple HTML email
	 * @param session
	 * @param toEmail
	 * @param subject
	 * @param body
	 */
	public static void sendEmail(Session session, String[] toEmail, String subject, String body){
		try {
//			File file = new File("test-output\\emailable-report.html");
			File file = new File("CC_Logs_2p\\reporte\\ReporteAutomatizacion_.html");
			String path = file.getAbsolutePath();
			
			
			Message msg = new MimeMessage(session);
			
			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();
			
			// Now set the actual message
			messageBodyPart.setText("Atención, \n Se envia archivo adjunto, con el estado de la ejecución. \n\n\n\n Saludos. \n Correo automatico.");
	        
			//set message headers
			msg.setHeader("Content-type", "text/plain; charset=\"UTF-8\"");
			msg.setFrom(new InternetAddress("automatizacion@test.cl", "Alerta Monitoreo de Codigo Base"));
			msg.setReplyTo(InternetAddress.parse("no_reply@example.com", false));
			msg.setSubject(subject);
			
			// Create a multipar message
			Multipart multipart = new MimeMultipart();
			
			// Set text message part
			multipart.addBodyPart(messageBodyPart);
			
	        // Part two is attachment
			messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(path);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(path);
			multipart.addBodyPart(messageBodyPart);
	        
			msg.setContent(multipart, "text/html");
			
			msg.setSentDate(new Date());	
			
			InternetAddress[] iCorreos = new InternetAddress [toEmail.length] ;
			
			for(int i=0;i<toEmail.length;i++){
				iCorreos[i] = new InternetAddress(toEmail[i]);
			}
			
			msg.setRecipients(Message.RecipientType.TO, iCorreos);
			
			System.out.println("sending mail....");
			Thread.sleep(10000);
			System.out.println("Message is ready");
			Transport.send(msg);  
			
			System.out.println("EMail Sent Successfully!!");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("***No se envio email***");
		}
	}
	
	public static String[] correos() {
		String[] sCorreos = { "ra.saez.vargas@gmail.com" };

		return sCorreos;
	}

	public static String bodyEmail() {
		File file = new File("Base-Structure-TG\\test-output\\index.html");
		String path = file.getAbsolutePath();

		String mensajeCorreo = "<!DOCTYPE html>\r\n" 
				+ "<html>\r\n" 
				+ "<body>\r\n" 
				+ "\r\n"
				+ "<h1>******  Alert  ******</h1>\r\n" 
				+ "<p>Este es un correo de prueba.</p>\r\n" 
				+ "<p><a href=" + path + ">index.html</a><p>\r\n" 
				+ "<p><a href=\"https://testgroup.cl//\">Visit TestGroup.com!</a></p>\r\n"
				+ "\r\n" 
				+ "</body>\r\n" 
				+ "</html>";

		return mensajeCorreo;
	}
	
	
}