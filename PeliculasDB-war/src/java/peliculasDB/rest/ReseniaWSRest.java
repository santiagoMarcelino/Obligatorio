package peliculasDB.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Resenia;
import peliculasDB.entidad.RespuestaResenia;
import peliculasDB.sb.ReseniaBean;
import peliculasDB.sb.RespuestaReseniaBean;

@ManagedBean
@Path("/resenia")
public class ReseniaWSRest {

    @EJB
    private ReseniaBean bLocal;
    @EJB
    private RespuestaReseniaBean bRespRes;

    private Gson gson;

    public ReseniaWSRest() {
        gson = new Gson();
    }

    @POST
    @Produces("application/json")
    @Path("/crearResenia")
    public Response CrearResenia(@QueryParam("nombrePelicula") String nombrePelicula,
            @QueryParam("nombreCritico") String nombreCritico,
            @QueryParam("comentarios") String comentarios,
            @QueryParam("puntuacion") int puntuacion) throws MiExcepcion {

        if (nombreCritico == null || nombrePelicula == null || comentarios == null) {
            return Response.ok(gson.toJson("Hay atributos nulos")).build();
        }

        if (puntuacion < 1 || puntuacion > 10) {
            return Response.ok(gson.toJson("Los valores de la puntuacion deben ser entre 1 y 10")).build();
        }

        try {
            Resenia res = bLocal.agregarResenia(nombrePelicula, nombreCritico, comentarios, puntuacion);
            return Response.ok(gson.toJson(res)).build();
        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/consultarHistorial")
    public Response obtenerHistorialResenias(@QueryParam("id") Long id) throws MiExcepcion {
        try {
            List<Resenia> lista = bLocal.ObtenerReseniasPorCritico(id);

            return Response.ok(gson.toJson(lista)).build();
        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

    @POST
    @Produces("application/json")
    @Path("/responderReseniaResenia")
    public Response responderResenia(@QueryParam("id") Long idResenia,
            @QueryParam("nombreCritico") String nombreCritico,
            @QueryParam("comentarios") String comentarios) throws MiExcepcion {
        try {
            RespuestaResenia resp = bRespRes.AgregarRespuesta(idResenia,
                    nombreCritico,
                    comentarios);

            return Response.ok(gson.toJson(resp)).build();
        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

}
