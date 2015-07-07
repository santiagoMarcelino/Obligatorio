package peliculasDB.sb;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import mensajes.ProductorMensajes;
import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Critico;
import peliculasDB.entidad.Resenia;
import peliculasDB.entidad.RespuestaResenia;

@Singleton
@LocalBean
public class RespuestaReseniaBean {

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private    EntityManager em;

    private    Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @EJB
    private ReseniaBean miRes;

    @EJB
    private CriticoBean miCri;

    @EJB
    private ProductorMensajes productor;

    public RespuestaReseniaBean() {
        // this.productor = new ProductorMensajes();
    }

    public RespuestaResenia AgregarRespuesta(Long idResenia, String nombreCritico, String comentarios) throws MiExcepcion {
        Resenia miResenia = miRes.ObtenerReseniaPorId(idResenia);
        Critico miCritico = (Critico) miCri.obtenerPersona(nombreCritico);
        RespuestaResenia resp = new RespuestaResenia();
        resp.setCritico(miCritico);
        resp.setRespondida(miResenia);
        resp.setComentarios(comentarios);
        resp.setPelicula(miResenia.getPelicula());
        this.persistirRespuestaResenia(resp);
        productor.Send(resp);

        return resp;
    }

    private void persistirRespuestaResenia(RespuestaResenia c) throws MiExcepcion {
        try {

            em.persist(c);

        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        } catch (Exception ex) {
            log.error(ex);
        }
    }

}
