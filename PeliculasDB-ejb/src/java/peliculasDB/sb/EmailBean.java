/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculasDB.sb;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.RespuestaResenia;

/**
 *
 * @author konko
 */
@Stateless
@LocalBean
public class EmailBean {

    private static final String RUTA_CONFIGURACION = "email.properties";

    private static final String EMAIL_FROM_KEY = "user.email.from";
    private static final String USERNAME_KEY = "user.username";
    private static final String PASSWORD_KEY = "user.password";

    private Properties props = new Properties();

    @PostConstruct
    public final void init() {
        InputStream input = null;
        input = getClass().getResourceAsStream(RUTA_CONFIGURACION);
        try {
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void sendEmail(RespuestaResenia unaResenia)
            throws MiExcepcion {

        // Get the Session object.
        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication
                    getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                props.getProperty(USERNAME_KEY),
                                props.getProperty(PASSWORD_KEY));
                    }
                });

        try {
            // Create a default MimeMessage object.
            Message message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(
                    new InternetAddress(props.getProperty(EMAIL_FROM_KEY)));

            // Set To: header field of the header.
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(unaResenia.getCritico().getEmail()));

            // Set Subject: header field
            message.setSubject("Un usuario ha respondido tu critica");

            // Now set the actual message
            message.setText("El usuario "
                    + unaResenia.getCritico().getNombre()
                    + " ha respondido a tu critica de la pelicula "
                    + unaResenia.getPelicula().getNombre() + "\n Comentario: \n " + unaResenia.getComentarios());

            // Send message
            Transport.send(message);

            System.out.println("Sent message successfully....");

        } catch (MessagingException e) {
            throw new MiExcepcion("Error mandando mensaje", "Err0002");

        }

    }
}
