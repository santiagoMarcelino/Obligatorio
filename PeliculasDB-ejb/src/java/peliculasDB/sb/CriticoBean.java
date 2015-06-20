package PeliculasDB.sb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import peliculasDB.entidad.Critico;

@Singleton
@LocalBean
public class CriticoBean {

    @PersistenceContext
    private EntityManager em;

    public void agregarDirector(Critico c) {
        em.persist(c);
    }
}
