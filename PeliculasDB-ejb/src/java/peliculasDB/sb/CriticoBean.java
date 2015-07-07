package peliculasDB.sb;

import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Critico;
import peliculasDB.entidad.Persona;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
@LocalBean
public class CriticoBean {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private EntityManager em;

    public Critico crearCritico(String nombre, String email, String clave) throws MiExcepcion {
        try {
            Critico critico = new Critico();
            critico.setNombre(nombre);
            critico.setEmail(email);
            critico.setClave(clave);
            em.persist(critico);
            log.info(critico.toString() + " creado.");
            return critico;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    //modifica un usuario comparando por el nombre (identificador unico)
    public Critico modificarCritico(String nombre, String email, String clave) throws MiExcepcion {
        try {
            Critico critico = new Critico();
            critico.setNombre(nombre);
            critico.setEmail(email);
            critico.setClave(clave);
            Critico cri = (Critico) this.obtenerPersona(critico.getNombre());
            if (cri != null) {
                cri.setNombre(critico.getNombre());
                cri.setEmail(critico.getEmail());
                cri.setClave(critico.getClave());
            } else {
                throw new PersistenceException();
            }
            return critico;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    public Persona obtenerPersona(String nombre) {
        return (Persona) em.createQuery("SELECT c FROM Critico c WHERE c.nombre = :nombrePersona")
                .setParameter("nombrePersona", nombre)
                .getSingleResult();

    }

    public Persona obtenerPersonaPorId(Long id) {
        return (Persona) em.find(Critico.class, id);

    }
}
