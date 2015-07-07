package peliculasDB.entidad;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class Pelicula extends EntidadPersistente {

    @Column(unique=true)
    private String nombre;

    private Director director;

    private Genero genero;

    @Column(nullable = false)
    private int anio;
    @ManyToMany
    private List<Actor> actores;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director d) {
        this.director = d;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero g) {
        this.genero = g;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int a) {
        this.anio = a;
    }

    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> a) {
        this.actores = a;
    }

    @Override
    public String toString() {
        return "Pelicula{" + "nombre=" + nombre
                + ", director="
                + director.getNombre()
                + ", genero=" + genero.getNombre() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (getClass() != object.getClass()) {
            return false;
        }
        final Pelicula other = (Pelicula) object;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
}
