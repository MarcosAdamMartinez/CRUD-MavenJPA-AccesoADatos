package oneToMany;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Evolucion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String nombre;

    @ManyToOne
    Pokemon pokemon;

    public Evolucion(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
