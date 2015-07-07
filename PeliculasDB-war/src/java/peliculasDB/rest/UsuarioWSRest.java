package peliculasDB.rest;

import com.google.gson.Gson;

import org.apache.log4j.Logger;
import peliculasDB.entidad.Usuario;
import peliculasDB.sb.UsuarioBean;

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
@Path("usuario")
public class UsuarioWSRest {

    private final Logger log = Logger.getLogger(peliculasDB.auxiliar.MiLogger.class.getName());
    @EJB
    private UsuarioBean usuarioBean;

    @Context
    private UriInfo context;

    private final Gson gson;

    public UsuarioWSRest() {
        gson = new Gson();
    }

    @POST
    @Path("/crearUsuario")
    @Produces("application/json")
    public Response crearUsuario(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("clave") String clave) {
        if (nombre == null || email == null || clave == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Usuario usuario = usuarioBean.crearUsuario(nombre, email, clave);
            return Response.ok(gson.toJson(usuario)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/modificarUsuario")
    @Produces("application/json")
    public Response modificarUsuario(@QueryParam("nombre") String nombre,
            @QueryParam("email") String email,
            @QueryParam("clave") String clave) {
        if (nombre == null || email == null || clave == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Usuario usuario = usuarioBean.modificarUsuario(nombre, email, clave);
            return Response.ok(gson.toJson(usuario)).build();
        } catch (Exception ex) {
            log.error(ex);
            return Response.status(400).build();
        }
    }
}
