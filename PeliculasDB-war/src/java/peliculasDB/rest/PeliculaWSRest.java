package peliculasDB.rest;

import com.google.gson.Gson;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import peliculasDB.auxiliar.MiExcepcion;
import peliculasDB.entidad.Pelicula;
import peliculasDB.sb.PeliculaBean;

@ManagedBean
@Path("/pelicula")
public class PeliculaWSRest {

    @EJB
    private PeliculaBean peliculaBean;

    private Gson gson;

    public PeliculaWSRest() {
        gson = new Gson();
    }

    @GET
    @Produces("application/json")
    @Path("/consultarCatalogo")
    public Response Catalogo() throws MiExcepcion {
//Genero genero =

        List<Pelicula> lista = peliculaBean.obtenerCatalogo();

        if (lista.isEmpty()) {
            return Response.ok("Todavia no hay peliculas ingresadas en la base de datos").build();
        }

        return Response.ok(gson.toJson(lista)).build();
    }

    @POST
    @Produces("application/json")
    @Path("/actualizarPelicula")
    public Response actualizar(@QueryParam("id") Long id) throws MiExcepcion {
//Genero genero = QueryParam("nombre")String nombre,@QueryParam("nombreGenero")String nombreGenero,@QueryParam("nombreDirector")String nombreDirector,@QueryParam("anio")int anio
        try {
            Pelicula p = peliculaBean.obtenerPeliculaPorID(id);

            return Response.ok(gson.toJson(p)).build();
        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/calcularPromedio")
    public Response calcularPromedio(@QueryParam("id") Long id) throws MiExcepcion {
//Genero genero = QueryParam("nombre")String nombre,@QueryParam("nombreGenero")String nombreGenero,@QueryParam("nombreDirector")String nombreDirector,@QueryParam("anio")int anio
        try {
            int retorno = peliculaBean.CalcularPromedioPuntuacion(id);

            if (retorno == -1) {
                return Response.ok("La pelicula indicada no tiene calificacion").build();
            } else {
                return Response.ok("La pelicula tiene " + retorno + " puntos de calificacion").build();
            }

        } catch (Exception ex) {
            return Response.ok(gson.toJson(ex)).build();
        }
    }

    @GET
    @Produces("application/json")
    @Path("/consultarCatalogoMayoresAnio")
    public Response CatalogoMayores(@QueryParam("anio") int anio) throws MiExcepcion {
//Genero genero =

        List<Pelicula> lista = peliculaBean.obtenerCatalogoMayoresAnio(anio);
        return Response.ok(gson.toJson(lista)).build();
    }

    @GET
    @Produces("application/json")
    @Path("/consultarCatalogoPorGenero")
    public Response catalogoGenero(@QueryParam("nombreGenero") String nombreGenero) throws MiExcepcion {
//Genero genero =
        if (nombreGenero == null) {
            return Response.ok("Se necesita un genero para buscar por genero").build();
        }

        List<Pelicula> lista = peliculaBean.obtenerCatalogoPorGenero(nombreGenero);
        return Response.ok(gson.toJson(lista)).build();
    }

    @POST
    @Path("/crearPelicula")
    @Produces("application/json")
    public Response crearPelicula(@QueryParam("nombre") String nombre,
            @QueryParam("idDirector") Long idDirector,
            @QueryParam("idGenero") Long idGenero,
            @QueryParam("anio") Integer anio,
            @QueryParam("listaActores") final List<Long> actores) {
        try {
            Pelicula pelicula = peliculaBean.crearPelicula(nombre, idDirector, idGenero, anio, actores);
            return Response.ok(gson.toJson(pelicula)).build();
        } catch (Exception ex) {

            return Response.status(400).build();
        }
    }

    @PUT
    @Path("/modificarPelicula")
    @Produces("application/json")
    public Response modificarActor(@QueryParam("nombre") String nombre,
            @QueryParam("idDirector") Long idDirector,
            @QueryParam("idGenero") Long idGenero,
            @QueryParam("anio") Integer anio,
            @QueryParam("listaActores") final List<Long> actores) {
        try {
            Pelicula pelicula = peliculaBean.modificarPelicula(nombre, idDirector, idGenero,
                    anio, actores);
            return Response.ok(gson.toJson(pelicula)).build();
        } catch (Exception ex) {

            return Response.status(400).build();
        }
    }

}
