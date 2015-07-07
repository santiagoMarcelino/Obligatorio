package peliculasDB.rest;

import java.util.Set;
import javax.ws.rs.core.Application;

@javax.ws.rs.ApplicationPath("")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(peliculasDB.rest.ActorResource.class);
        resources.add(peliculasDB.rest.CriticoWSRest.class);
        resources.add(peliculasDB.rest.DirectorWSRest.class);
        resources.add(peliculasDB.rest.GeneroWSRest.class);
        resources.add(peliculasDB.rest.PeliculaWSRest.class);
        resources.add(peliculasDB.rest.ReseniaWSRest.class);
        resources.add(peliculasDB.rest.UsuarioWSRest.class);
    }

}
