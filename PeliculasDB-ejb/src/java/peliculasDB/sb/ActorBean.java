package peliculasDB.sb;

import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Actor;
import peliculasDB.entidad.Persona;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
@LocalBean
public class ActorBean {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private EntityManager em;

    public Actor crearActor(String nombre, String email, String nacionalidad) throws MiExcepcion {
        try {
            Actor actor = new Actor();
            actor.setNombre(nombre);
            actor.setEmail(email);
            actor.setNacionalidad(nacionalidad);
            em.persist(actor);
            log.info(actor.toString() + " creado.");
            return actor;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    //modifica un actor comparando por el nombre (identificador unico)
    public Actor modificarActor(String nombre, String email, String nacionalidad) throws MiExcepcion {
        try {
            Actor actor = new Actor();
            actor.setNombre(nombre);
            actor.setEmail(email);
            actor.setNacionalidad(nacionalidad);
            Actor act = (Actor) this.obtenerPersona(actor.getNombre());
            if (act != null) {
                act.setNombre(actor.getNombre());
                act.setEmail(actor.getEmail());
                act.setNacionalidad(actor.getNacionalidad());
            } else {
                throw new PersistenceException();
            }
            return actor;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("El registro no existe", "Err0003");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    public Persona obtenerPersona(String nombre) {
        return (Persona) em.createQuery("SELECT a FROM Actor a WHERE a.nombre = :nombrePersona")
                .setParameter("nombrePersona", nombre)
                .getSingleResult();
    }

    public Persona obtenerActorPorId(Long id) {
        return (Persona) em.find(Actor.class, id);
    }
}
