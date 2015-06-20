package peliculasDB.entidad;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Persona extends EntidadPersistente {
    
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String email;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        this.nombre = n;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String e){
        this.email = e;
    }
    
    @Override
    public String toString() {
        return "Persona{" + "nombre=" + nombre + ", email=" + email + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Persona other = (Persona) obj;
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }
}
