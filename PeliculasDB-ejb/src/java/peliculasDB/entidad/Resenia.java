package peliculasDB.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Resenia extends EntidadPersistente{
    
    @Column(nullable = false)
    @OneToOne 
    private Pelicula pelicula;
    @Column(nullable = false)
    private String nota;
    @Column(nullable = false)
    private int calificacion;
    @Column(nullable = false)
    @ManyToOne
    private Critico critico;

    
    public Pelicula getPelicula() {
        return pelicula;
    }
    
    public void setPelicula(Pelicula p) {
        this.pelicula = p;
    } 
    
    public String getNota() {
        return nota;
    }
    
    public void setNota(String n) {
        this.nota = n;
    }    
    
    public int getCalificacion() {
        return calificacion;
    }
    
    public void setCalificacion(int c) {
        this.calificacion = c;
    }  
    
    public Critico getCritico() {
        return critico;
    }
    
    public void setCritico(Critico c) {
        this.critico = c;
    } 
    
    @Override
    public String toString() {
        return "Resenia{" + "pelicula=" + this.pelicula + ", nota=" + this.nota + ", calificacion=" + this.calificacion +'}';
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
