package peliculasDB.entidad;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Genero extends EntidadPersistente{
    
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = true)
    private String descripcion;
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String d) {
        this.descripcion = d;
    }    
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String n) {
        this.nombre = n;
    }  
    
    @Override
    public String toString() {
        return "Genero{" + "nombre=" + this.getNombre() + ", descripcion=" + this.descripcion + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.getNombre() != null ? this.getNombre().hashCode() : 0);
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
        final Genero other = (Genero) object;
        if ((this.getNombre() == null) ? (other.getNombre() != null) : !this.getNombre().equals(other.getNombre())) {
            return false;
        }
        return true;
    }    
}
