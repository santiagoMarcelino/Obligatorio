package peliculasDB.sb;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Actor;
import peliculasDB.entidad.Critico;
import peliculasDB.entidad.Director;
import peliculasDB.entidad.Usuario;
import org.apache.log4j.Logger;

@Stateless
@LocalBean
public class PersonaBean {
    @EJB
    private PersonasDBInt personasDBInt;
    
    Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    public static List<Actor> listarActores() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
      
    public Actor crearActor(String nombre, String email, String nacionalidad) {
        try {
            Actor actor = new Actor();
            actor.setNombre(nombre);
            actor.setEmail(email);
            actor.setNacionalidad(nacionalidad);
            personasDBInt.persistirActor(actor);
            return actor;
        }
        catch (MiExcepcion ex){
            log.error(ex);
            return null;
        }
    }
    
    public Critico crearCritico(String nombre, String email, String clave) {
        try{
            Critico critico = new Critico();
            critico.setNombre(nombre);
            critico.setEmail(email);
            critico.setClave(clave);
            personasDBInt.persistirCritico(critico);
            return critico;
        }
        catch (MiExcepcion ex){
            log.error(ex);
            return null;
        }
    }  
     
    public Director crearDirector(String nombre, String email, String nacionalidad) {
        try{
            Director director = new Director();
            director.setNombre(nombre);
            director.setEmail(email);
            director.setNacionalidad(nacionalidad);
            personasDBInt.persistirDirector(director);
            return director;
        }
        catch (MiExcepcion ex){
            log.error(ex);
            return null;
        }
    }   
    
    public Usuario crearUsuario(String nombre, String email, String clave) {
        try{
            Usuario usuario = new Usuario();
            usuario.setNombre(nombre);
            usuario.setEmail(email);
            usuario.setClave(clave);
            personasDBInt.persistirUsuario(usuario);
            return usuario;
        }
        catch (MiExcepcion ex){
            log.error(ex);
            return null;
        }
    }
}
