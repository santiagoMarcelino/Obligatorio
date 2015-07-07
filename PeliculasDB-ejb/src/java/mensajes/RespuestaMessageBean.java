package mensajes;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import peliculasDB.entidad.RespuestaResenia;
import peliculasDB.sb.RespuestaReseniaBean;
import peliculasDB.sb.EmailBean;

/**
 *
 * @author konko
 */
@MessageDriven(mappedName = "jms/Queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class RespuestaMessageBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    @EJB
    private EmailBean emailBean;

    @EJB
    private RespuestaReseniaBean respuestaBean;

    public RespuestaMessageBean() {

    }

    @Override
    public void onMessage(Message message) {
        ObjectMessage msg;
        try {
            if (message instanceof ObjectMessage) {
                msg = (ObjectMessage) message;
                //Recibimos el mensaje
                RespuestaResenia respuesta = (RespuestaResenia) msg.getObject();
                emailBean.sendEmail(respuesta);
            }
        } catch (JMSException e) {
            mdc.setRollbackOnly();
        } catch (Exception te) {
        }
    }
}
