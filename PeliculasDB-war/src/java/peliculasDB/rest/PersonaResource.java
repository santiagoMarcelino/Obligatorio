package peliculasDB.rest;

import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import peliculasDB.sb.PersonaBean;
import com.google.gson.Gson;
import javax.ws.rs.POST;
import javax.ws.rs.QueryParam;
import peliculasDB.entidad.Actor;

@Path("persona")
public class PersonaResource {
    
    @EJB
    private PersonaBean personaBean;

    @Context
    private UriInfo context;

    private Gson gson;
 
    public PersonaResource() {
        gson = new Gson();
    }

    @GET
    @Path("/crearActor")
    //@Produces("application/json")
    @Produces("text/plain")
    public String crearActor(@QueryParam("nombre") String nombre
            ,@QueryParam("email") String email
            ,@QueryParam("nacionalidad") String nacionalidad){
        try {
            Actor s = personaBean.crearActor(nombre, email, nacionalidad);            
            //return Response.ok(gson.toJson(s)).build();
            return "hola";
        }
        catch (Exception ex){
            //return Response.ok(gson.toJson(ex)).build();
            return ex.toString();
        }
    }
}
