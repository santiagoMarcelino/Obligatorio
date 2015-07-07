package peliculasDB.sb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Critico;
import peliculasDB.entidad.Pelicula;
import peliculasDB.entidad.Resenia;

@Singleton
@LocalBean
public class ReseniaBean {

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private    EntityManager em;
    private    Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @EJB
    private CriticoBean criticoBean;

    @EJB
    private    PeliculaBean peliculaBean;

    public void persistirResenia(Resenia c) throws MiExcepcion {
        try {
            em.persist(c);
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public Resenia agregarResenia(String nombrePelicula, String nombreCritico, String comentarios, int puntuacion) throws MiExcepcion {
        Resenia res = new Resenia();
        res.setCalificacion(puntuacion);
        Critico critico = (Critico) criticoBean.obtenerPersona(nombreCritico);
        Pelicula pelicula = peliculaBean.obtenerPeliculaPorNombre(nombrePelicula);
        res.setCritico(critico);
        res.setComentarios(comentarios);
        res.setPelicula(pelicula);

        this.persistirResenia(res);
        return res;
    }

    public List<Resenia> ObtenerReseniasPorCritico(Long ID) throws MiExcepcion {

        Query q;
        q = em.createQuery("SELECT r FROM Resenia r WHERE r.critico.id = :idCritico");
        try {
            return q.setParameter("idCritico", ID).getResultList();
        } catch (Exception e) {
            log.error(e);
            throw new MiExcepcion("Error obteniendo lista", "Err0002");
        }
    }

    public Resenia ObtenerReseniaPorId(Long id) throws MiExcepcion {

        Query q;
        q = em.createQuery("SELECT r FROM Resenia r WHERE r.id = :idResenia");

        try {
            return (Resenia) q.setParameter("idResenia", id).getSingleResult();
        } catch (NoResultException nre) {
            log.error(nre);
            throw new MiExcepcion("Error obteniendo lista", "Err0002");
        }

    }

}
