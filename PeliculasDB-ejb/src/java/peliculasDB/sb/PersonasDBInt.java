package peliculasDB.sb;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import peliculasDB.entidad.Actor;
import peliculasDB.entidad.Critico;
import peliculasDB.entidad.Director;
import peliculasDB.entidad.Usuario;
import peliculasDB.auxiliar.MiExcepcion;
import org.apache.log4j.Logger;


@Stateless
@LocalBean
public class PersonasDBInt {
    
    @PersistenceContext(unitName="PeliculasDB-ejbPU")
    EntityManager em;
    Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    
    public void persistirActor(Actor a) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.persist(a);
            em.getTransaction().commit();
        }
        catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        }
        catch (Exception ex){
            log.error(ex);
        }
    }
    
    public void persistirDirector(Director d) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.persist(d);
            em.getTransaction().commit();
        }
        catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
         
        }
        catch (Exception ex){
            log.error(ex);
        }
    } 
    
    public void persistirCritico(Critico c) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.persist(c);
            em.getTransaction().commit();
        }
        catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        }
        catch (Exception ex){
            log.error(ex);
        }        
    }   

    public void persistirUsuario(Usuario u) throws MiExcepcion {
        try {
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        }
        catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        }
        catch (Exception ex){
            log.error(ex);
        }   
    } 
}
