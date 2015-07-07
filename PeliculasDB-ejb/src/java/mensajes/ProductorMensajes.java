/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mensajes;

import java.util.Properties;
import java.util.logging.Level;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.log4j.Logger;
import peliculasDB.entidad.RespuestaResenia;
import com.sun.messaging.Queue;

@Singleton
@LocalBean
public class ProductorMensajes {

private Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    /*
     @EJB
     private EmailBean email;
     */
    private Properties props;
    private InitialContext ic;
    private ConnectionFactory connectionFactory;
    private Queue queue;
    private Connection connection;
    private Session session;
    private MessageProducer messageProducer;

    public ProductorMensajes() {
        /* props = new Properties();
         props.setProperty("java.naming.factory.initial",
         "com.sun.enterprise.naming.SerialInitContextFactory");
         props.setProperty("java.naming.factory.url.pkgs", "com.sun.enterprise.naming");
         props.setProperty("java.naming.factory.state",
         "com.sun.corba.ee.impl.presentation.rmi.JNDIStateFactoryImpl");
         props.setProperty("org.omg.CORBA.ORBInitialHost", "localhost");
         props.setProperty("org.omg.CORBA.ORBInitialPort", "3700");
         */

        try {
            //Creo el Contexto para obtener los recursos del servidor
            ic = new InitialContext(props);
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            // Obtenemos a traves del servicio JNDI la ConnectionFactory del
            // servidor de aplicaciones
            connectionFactory = (ConnectionFactory) ic.lookup("ConnectionFactory");
        } catch (NamingException ex) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (connectionFactory == null) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, "COONECTION VACIA!!!");
        }

        try {
            //Creo la Connection mediante la ConnectionFactory
            connection = connectionFactory.createConnection();
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Send(RespuestaResenia unaRespuesta) {

        try {
            //Creo la Session mediante la Connection
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            try {
                //Creo la MessageProducer mediante la Session
                messageProducer = session.createProducer((Destination) ic.lookup("jms/Queue"));
                //Creo la TextMessage mediante la Session
            } catch (NamingException ex) {
                java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (JMSException ex) {
            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Message msg = session.createObjectMessage(unaRespuesta);
            messageProducer.send(msg);
            session.close();
            connection.close();

        } catch (JMSException ex) {

            java.util.logging.Logger.getLogger(ProductorMensajes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
