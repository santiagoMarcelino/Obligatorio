package peliculasDB.rest;

import com.google.gson.Gson;

import org.apache.log4j.Logger;
import peliculasDB.entidad.Critico;
import peliculasDB.sb.CriticoBean;

import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@ManagedBean
@Path("critico")
public class CriticoWSRest {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    @EJB
    private CriticoBean criticoBean;

    private final Gson gson;

    public CriticoWSRest() {
        gson = new Gson();
    }

    @POST
    @Path("/crearCritico")
    @Produces("application/json")
    public Response crearCritico(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("clave") String clave) {
        if (nombre == null || email == null || clave == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {

            Critico critico = criticoBean.crearCritico(nombre, email, clave);
            return Response.ok(gson.toJson(critico)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/modificarCritico")
    @Produces("application/json")
    public Response modificarCritico(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("clave") String clave) {
        if (nombre == null || email == null || clave == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Critico critico = criticoBean.modificarCritico(nombre, email, clave);
            return Response.ok(gson.toJson(critico)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }
}
