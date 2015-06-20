package peliculasDB.entidad;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;


@Entity
public class Usuario extends Persona {
    
    @Column(nullable = false)
    private String clave;  
    
    public String getClave() {
        return clave;
    }
    
    public void setClave(String c) {
        this.clave = c;
    }  
    
    @Override
    public String toString() {
        return "Actor{" + "nombre=" + this.getNombre() + '}';
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
        final Actor other = (Actor) object;
        if ((this.getNombre() == null) ? (other.getNombre() != null) : !this.getNombre().equals(other.getNombre())) {
            return false;
        }
        return true;
    }    
}
