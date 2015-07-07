package peliculasDB.sb;

import java.util.ArrayList;
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
import peliculasDB.entidad.Actor;
import peliculasDB.entidad.Director;
import peliculasDB.entidad.Genero;
import peliculasDB.entidad.Pelicula;
import peliculasDB.entidad.Resenia;

@Singleton
@LocalBean
public class PeliculaBean {

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private    EntityManager em;

    private    Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    @EJB
    private DirectorBean directorBean;
    @EJB
    private GeneroBean generoBean;
    @EJB
    private ActorBean actorBean;

    public List<Pelicula> obtenerCatalogo() {
        Query q;
        q = em.createQuery("SELECT p FROM Pelicula p");
        try {
            return q.getResultList();
        } catch (NoResultException nre) {
            log.error(nre);
            return new ArrayList<Pelicula>();
        }
    }

    public void persistirPelicula(Pelicula d) throws MiExcepcion {
        try {

            em.persist(d);

        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");

        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public Pelicula obtenerPeliculaPorID(Long id) {

        try {
            return em.find(Pelicula.class, id);
        } catch (Exception e) {
            log.error(e);
            return new Pelicula();

        }

    }

    public Pelicula obtenerPeliculaPorNombre(String nombrePelicula) throws MiExcepcion {

        try {
            return (Pelicula) em.createQuery("SELECT a FROM Pelicula a WHERE a.nombre = :nombrePelicula")
                    .setParameter("nombrePelicula", nombrePelicula)
                    .getSingleResult();
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("El registro no existe", "Err0003");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }

    }

    public void actualizarPelicula(Pelicula pelicula) throws MiExcepcion {
        try {
            //em.merge(a); --No funciona, merge usa ID para hacer la comparacion, no el equals o el hash code.
            Pelicula pel = (Pelicula) this.obtenerPeliculaPorNombre(pelicula.getNombre());
            if (pel != null) {
                pel.setAnio(pelicula.getAnio());
                pel.setDirector(pelicula.getDirector());
                pel.setGenero(pelicula.getGenero());
                pel.setNombre(pelicula.getNombre());
            }
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }

    }

    public List<Resenia> ObtenerReseniasPorPelicula(Long ID) {

        Query q;
        q = em.createQuery("SELECT r FROM Resenia r WHERE r.pelicula.id = :idPelicula");
        try {
            return q.setParameter("idPelicula", ID).getResultList();
        } catch (NoResultException nre) {
            log.error(nre);
            return new ArrayList<Resenia>();
        }
    }

    public int CalcularPromedioPuntuacion(Long id) {
        List<Resenia> lista = this.ObtenerReseniasPorPelicula(id);

        if (lista.isEmpty()) {
            return -1;
        } else {
            int retorno = 0;

            for (int i = 0; i < lista.size(); i++) {
                retorno = retorno + lista.get(i).getCalificacion();
            }

            return retorno / lista.size();
        }
    }

    public List<Pelicula> obtenerCatalogoMayoresAnio(int anio) {
        Query q;
        q = em.createQuery("SELECT p FROM Pelicula p where p.anio > :anioParam").
                setParameter("anioParam", anio);
        try {
            return q.getResultList();
        } catch (NoResultException nre) {
            log.error(nre);
            return new ArrayList<Pelicula>();
        }
    }

    public List<Pelicula> obtenerCatalogoPorGenero(String nombreGenero) {
        Query q;
        q = em.createQuery("SELECT p FROM Pelicula p where p.genero.nombre = :nombreGenero").
                setParameter("nombreGenero", nombreGenero);
        try {
            return q.getResultList();
        } catch (NoResultException nre) {
            log.error(nre);
            return new ArrayList<Pelicula>();
        }
    }

    public Pelicula crearPelicula(String nombre, Long idDirector, Long idGenero, Integer anio,
            List<Long> actores) throws MiExcepcion {
        try {
            Pelicula pelicula = new Pelicula();
            pelicula.setNombre(nombre);
            Director director = (Director) directorBean.obtenerPersonaPorId(idDirector);
            pelicula.setDirector(director);
            Genero genero = generoBean.obtenerGeneroPorID(idGenero);
            pelicula.setGenero(genero);
            pelicula.setAnio(anio);
            List<Actor> actoresAux = new ArrayList<>();
            for (Long item : actores) {
                Actor actorAct = (Actor) actorBean.obtenerActorPorId(item);
                actoresAux.add(actorAct);
            }
            pelicula.setActores(actoresAux);
            em.persist(pelicula);
            log.info(pelicula.toString() + "creada.");
            return pelicula;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0004");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    //modifica un pelicula comparando por el nombre (identificador unico)
    public Pelicula modificarPelicula(String nombre, Long idDirector, Long idGenero, Integer anio,
            List<Long> actores) throws MiExcepcion {
        try {
            Pelicula pelicula = new Pelicula();
            pelicula.setNombre(nombre);
            Director director = (Director) directorBean.obtenerPersonaPorId(idDirector);
            pelicula.setDirector(director);
            Genero genero = generoBean.obtenerGeneroPorID(idGenero);
            pelicula.setGenero(genero);
            pelicula.setAnio(anio);
            List<Actor> actoresAux = new ArrayList<>();
            for (Long item : actores) {
                Actor actorAct = (Actor) actorBean.obtenerActorPorId(item);
                actoresAux.add(actorAct);
            }
            pelicula.setActores(actoresAux);
            Pelicula pel = this.obtenerPelicula(pelicula.getNombre());
            if (pel != null) {
                pel.setNombre(pelicula.getNombre());
                pel.setDirector(pelicula.getDirector());
                pel.setGenero(pelicula.getGenero());
                pel.setAnio(pelicula.getAnio());
                pel.setActores(pelicula.getActores());
            } else {
                throw new PersistenceException();
            }
            return pelicula;
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("El registro no existe", "Err0003");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");
        }
    }

    public Pelicula obtenerPelicula(String nombre) {
        return (Pelicula) em.createQuery("SELECT p FROM Pelicula p WHERE p.nombre = :nombrePelicula")
                .setParameter("nombrePelicula", nombre)
                .getSingleResult();
    }

    public Pelicula obtenerActorPorId(Long id) {
        return (Pelicula) em.find(Pelicula.class, id);
    }

}
