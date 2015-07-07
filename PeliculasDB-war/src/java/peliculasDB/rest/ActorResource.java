package peliculasDB.rest;

import com.google.gson.Gson;

import org.apache.log4j.Logger;
import peliculasDB.entidad.Actor;
import peliculasDB.sb.ActorBean;

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
@Path("/actor")
public class ActorResource {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    @EJB
    private ActorBean actorBean;

    @Context
    private UriInfo context;

    private final Gson gson;

    public ActorResource() {
        gson = new Gson();
    }

    @POST
    @Path("/crearActor")
    @Produces("application/json")
    public Response crearActor(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("nacionalidad") String nacionalidad) {
        if (nombre == null || email == null || nacionalidad == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }

        try {
            Actor actor = actorBean.crearActor(nombre, email, nacionalidad);
            return Response.ok(gson.toJson(actor)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/modificarActor")
    @Produces("application/json")
    public Response modificarActor(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("nacionalidad") String nacionalidad) {
        if (nombre == null || email == null || nacionalidad == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Actor actor = actorBean.modificarActor(nombre, email, nacionalidad);
            return Response.ok(gson.toJson(actor)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }
}
