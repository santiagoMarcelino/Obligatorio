package peliculasDB.sb;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Genero;

@Stateless
@LocalBean
public class GeneroBean {

    @PersistenceContext(unitName = "PeliculasDB-ejbPU")
    private EntityManager em; //= emf.createEntityManager();
    private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());

    public Genero agregarGenero(String nombre, String descripcion) throws MiExcepcion {

        Genero g = new Genero();
        g.setDescripcion(descripcion);
        g.setNombre(nombre);
        this.persistirGenero(g);
        return g;
    }

    public Genero actualizarGenero(Long id, String nombre, String descripcion) throws MiExcepcion {
        Genero g = this.obtenerGeneroPorID(id);
        g.setDescripcion(descripcion);
        g.setNombre(nombre);
        this.persistirActualizacion(g);
        return g;
    }

    public void persistirGenero(Genero a) throws MiExcepcion {
        try {
            em.persist(a);
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        } catch (Exception ex2) {
            throw new MiExcepcion("Error desconocido", "Err0002");

        }
    }

    public Genero obtenerGeneroPorID(Long id) {

        try {
            try {
                Genero gen = (Genero) em.find(Genero.class, id);
                return gen;

            } catch (PersistenceException ex) {
                log.error(ex);
                throw new MiExcepcion("El registro no existe", "Err0003");
            } catch (Exception ex) {
                log.error(ex);
                throw new MiExcepcion("Error accediendo a la base de datos",
                        "Err0002");

            }
        } catch (Exception e) {
            log.error(e);
            return new Genero();
        }

    }

    public void persistirActualizacion(Genero genero) throws MiExcepcion {
        try {
            em.merge(genero);
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("Error persistiendo objeto", "Err0002");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos", "Err0002");

        }

    }

    public Genero obtenerGeneroPorNombre(
            String nombreGenero)
            throws MiExcepcion {

        try {
            return (Genero) em.createQuery("SELECT a FROM Genero a WHERE a.nombre = :nombreGenero")
                    .setParameter("nombreGenero", nombreGenero)
                    .getSingleResult();
        } catch (PersistenceException ex) {
            log.error(ex);
            throw new MiExcepcion("El registro no existe", "Err0003");
        } catch (Exception ex) {
            log.error(ex);
            throw new MiExcepcion("Error accediendo a la base de datos",
                    "Err0002");

        }

    }

}
