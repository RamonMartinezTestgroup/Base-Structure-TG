package test.java;

import java.io.IOException;
import org.testng.annotations.Test;
import main.java.mail.TLSEmail;
import main.java.mail.EmailUtil;

public class prueba_envio_mail {

	private static String ASUNTO_CORREO = "Prueba de correo AUTOMATIZADO";

	String[] sCorreos = EmailUtil.correos();
	String mensajeCorreo = EmailUtil.bodyEmail();

	@Test(priority = 1, enabled = true)
	public void envioCorreo_test() throws IOException, InterruptedException {
		try {
			TLSEmail.sendEmail(sCorreos, ASUNTO_CORREO, mensajeCorreo);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
