package com.example.masanz.aimar.actividades.model.service;

import com.example.masanz.aimar.actividades.model.entity.CorreoDTO;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class CorreoService {

    final private String destinatario = "aimarhuici@gmail.com";

    public String enviarCorreo(CorreoDTO correo){
        final String remitente = "summitracksoporte@gmail.com";
        final String clave = "iylx oawv ehec whfl"; // No uses tu clave real, usa una clave de aplicación si usas Gmail

        String destinatario = this.destinatario ;
        String asunto = correo.getAsunto();
        String cuerpo = "Mensaje de " + correo.getNombre() + " con correo " + correo.getRemitente() + "\n\nAsunto: " + correo.getAsunto() + "\n\nMensaje: " + correo.getCuerpo();

        if (asunto.length() < 5 ) return "El asunto debe tener al menos 5 caracteres";
        else if (correo.getCuerpo().length() < 15) return "El cuerpo debe tener al menos 15 caracteres";
        else if (correo.getNombre().length() < 3) return "El nombre debe tener al menos 3 caracteres";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });

        /* CORREO DUEÑO*/
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("ERROR: CorreoService enviarCorreo -> Error al enviar correo al dueño" + e.getMessage()) ;
        }

        /* CORREO USUARIO*/
        try {
            destinatario = correo.getRemitente();
            cuerpo = "Copia del correo enviado a soporte Summitrack  " + "\n\nAsunto: " + correo.getAsunto() + "\n\nMensaje: " + correo.getCuerpo();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(cuerpo);

            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("ERROR: CorreoService enviarCorreo -> Error al enviar correo al usuario" + e.getMessage()) ;
        }
        return null;
    }
}
