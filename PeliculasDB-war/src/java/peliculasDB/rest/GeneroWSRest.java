package peliculasDB.rest;

import com.google.gson.Gson;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Genero;

import peliculasDB.sb.GeneroBean;

@ManagedBean
@Path("/genero")
public class GeneroWSRest {

    @EJB
    private GeneroBean generoBean;
    private Gson gson;

    public GeneroWSRest() {
        gson = new Gson();
    }

    @POST
    @Produces("application/json")
    @Path("/crearGenero")
    public final Response alta(@QueryParam("nombre") String nombre, @QueryParam("descripcion") String descripcion) throws MiExcepcion {

        if (nombre == null || descripcion == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Genero g = new Genero();
            g.setNombre(nombre);
            g.setDescripcion(descripcion);
            generoBean.persistirGenero(g);
            return Response.ok(gson.toJson(g)).build();
        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

    @POST
    @Produces("application/json")
    @Path("/actualizarGenero")
    public Response actualizar(@QueryParam("nombre") String nombre, @QueryParam("descripcion") String descripcion, @QueryParam("id") Long id) throws MiExcepcion {
//Genero genero =
        if (nombre == null || descripcion == null) {
            return Response.ok(gson.toJson("No puede haber campos nulos")).build();
        }
        try {
            Genero g = generoBean.actualizarGenero(id, nombre, descripcion);
            return Response.ok(gson.toJson(g)).build();
        } catch (MiExcepcion ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }
}
