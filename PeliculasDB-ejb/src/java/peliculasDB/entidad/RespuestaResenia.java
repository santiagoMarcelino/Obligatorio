package peliculasDB.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
public class RespuestaResenia extends EntidadPersistente {

    private Pelicula pelicula;
    @Column(nullable = false)
    private String comentarios;

    private Critico critico;

    private Resenia respondida;

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Resenia getRespondida() {
        return respondida;
    }

    public void setRespondida(Resenia respondida) {
        this.respondida = respondida;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula p) {
        this.pelicula = p;
    }

    public Critico getCritico() {
        return critico;
    }

    public void setCritico(Critico c) {
        this.critico = c;
    }

    @Override
    public String toString() {
        return " comentarios=" + this.comentarios + '}';
    }

    /*@Override
     public int hashCode() {
     int hash = 7;
     hash = 97 * hash + (this.getPelicula() != null ? this.getPelicula().hashCode() : 0);
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
     final Resenia other = (Resenia) object;
     if ((this.getPelicula() == null) ? (other.getPelicula() != null) : !this.getPelicula().equals(other.getPelicula())) {
     return false;
     }
     return true;
     }*/
}
