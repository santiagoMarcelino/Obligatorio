package peliculasDB.sb;

import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Director;
import peliculasDB.entidad.Persona;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
@LocalBean
public class DirectorBean {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private EntityManager em;

    public Director crearDirector(String nombre, String email, String nacionalidad) throws MiExcepcion {
        try {
            Director director = new Director();
            director.setNombre(nombre);
            director.setEmail(email);
            director.setNacionalidad(nacionalidad);
            em.persist(director);
            log.info(director.toString() + "creado.");
            return director;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    //modifica un director comparando por el nombre (identificador unico)
    public Director modificarDirector(String nombre, String email, String nacionalidad) throws MiExcepcion {
        try {
            Director director = new Director();
            director.setNombre(nombre);
            director.setEmail(email);
            director.setNacionalidad(nacionalidad);
            Director dir = (Director) this.obtenerPersona(director.getNombre());
            if (dir != null) {
                dir.setNombre(director.getNombre());
                dir.setEmail(director.getEmail());
                dir.setNacionalidad(director.getNacionalidad());
            } else {
                throw new PersistenceException();
            }
            return director;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    public Persona obtenerPersona(String nombre) {
        return (Persona) em.createQuery("SELECT d FROM Director d WHERE d.nombre = :nombrePersona")
                .setParameter("nombrePersona", nombre)
                .getSingleResult();
    }

    public Persona obtenerPersonaPorId(Long id) {
        return (Persona) em.find(Director.class, id);
    }
}
