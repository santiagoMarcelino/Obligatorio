package peliculasDB.rest;

import com.google.gson.Gson;

import org.apache.log4j.Logger;
import peliculasDB.entidad.Director;
import peliculasDB.sb.DirectorBean;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@ManagedBean
@Path("/director")
public class DirectorWSRest {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    @EJB
    private DirectorBean directorBean;

    @Context
    private UriInfo context;

    private final Gson gson;

    public DirectorWSRest() {
        gson = new Gson();
    }

    @POST
    @Path("/crearDirector")
    @Produces("application/json")
    public Response crearDirector(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("nacionalidad") String nacionalidad) {
        if (nombre == null || email == null || nacionalidad == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Director director = directorBean.crearDirector(nombre, email, nacionalidad);
            return Response.ok(gson.toJson(director)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/modificarDirector")
    @Produces("application/json")
    public Response modificarDirector(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("nacionalidad") String nacionalidad) {
        if (nombre == null || email == null || nacionalidad == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Director director = directorBean.modificarDirector(nombre, email, nacionalidad);
            return Response.ok(gson.toJson(director)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }
}
