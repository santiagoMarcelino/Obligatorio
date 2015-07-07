package peliculasDB.sb;

import org.apache.log4j.Logger;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Persona;
import peliculasDB.entidad.Usuario;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

@Stateless
@LocalBean
public class UsuarioBean {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private EntityManager em;

    public Usuario crearUsuario(String nombre, String email, String clave) throws MiExcepcion {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setClave(clave);
            em.persist(usuario);
            log.info(usuario.toString() + " creado.");
            return usuario;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    //modifica un usuario comparando por el nombre (identificador unico)
    public Usuario modificarUsuario(String nombre, String email, String clave) throws MiExcepcion {
        try {
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setClave(clave);
            Usuario usu = (Usuario) this.obtenerPersona(usuario.getNombre());
            if (usu != null) {
                usu.setNombre(usuario.getNombre());
                usu.setEmail(usuario.getEmail());
                usu.setClave(usuario.getClave());
            } else {
                throw new PersistenceException();
            }
            return usuario;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("El registro no existe", "Err0003");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    public Persona obtenerPersona(String nombre) {
        return (Persona) em.createQuery("SELECT u FROM Usuario u WHERE u.nombre = :nombrePersona")
                .setParameter("nombrePersona", nombre)
                .getSingleResult();
    }
}
