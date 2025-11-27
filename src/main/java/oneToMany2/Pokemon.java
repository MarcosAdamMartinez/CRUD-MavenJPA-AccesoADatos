package oneToMany2;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "pokedex")
public class Pokemon {

    @Id
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "tipo1")
    private String tipo1;

    @Column(name = "tipo2")
    private String tipo2;

    // Creamos el OneToMany y le añadimos las propiedades:
    //    mappedBy que busca en la otra clase el campo cuyo nombre coincida con el que has puesto,
    //    cascade, para que las operaciones que hagas sobre el padre también se aplican automáticamente a los hijos
    //    orphanRemoval, que si un hijo deja de estar asociado al padre, se elimina de la base de datos automáticamente
    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Evolucion> evoluciones = new HashSet<>();

    public Pokemon() {}

    public Pokemon(String nombre, int nivel, String tipo1, String tipo2) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    public void addEvolucion(String nombre, int nivel, String tipo1, String tipo2) {
        Evolucion ev = new Evolucion(nombre, nivel, tipo1, tipo2, this);
        evoluciones.add(ev);
    }

    public boolean removeEvolucion(Evolucion ev) {
        return evoluciones.remove(ev);
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivel() { return nivel; }

    public void setNivel(int nivel) { this.nivel = nivel; }

    public String getTipo1() { return tipo1; }

    public void setTipo1(String tipo1) { this.tipo1 = tipo1; }

    public String getTipo2() { return tipo2; }

    public void setTipo2(String tipo2) { this.tipo2 = tipo2; }

    public Set<Evolucion> getEvoluciones() { return evoluciones; }
}
