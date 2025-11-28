package oneToMany;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pokedex")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "tipo1")
    private String tipo1;

    @Column(name = "tipo2")
    private String tipo2;

    // Creamos el OneToMany y le añadimos las propiedades:
    //    mappedBy que busca en la otra clase el campo cuyo nombre coincida con el que has puesto y evita que se cree una tabla intermedia,
    //    cascade, para que las operaciones que hagas sobre el padre también se aplican automáticamente a los hijos
    //    orphanRemoval, que si un hijo deja de estar asociado al padre, se elimina de la base de datos automáticamente
    //    fetch que hace que si esta en EAGER al recuperar un pokemon recupere tambien sus Evoluciones asociadas, mientras que si es LAZY no
    @OneToMany(mappedBy = "pokemon", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Evolucion> evoluciones = new LinkedHashSet<>();

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

    public Evolucion addEvolucion(Evolucion ev) {
        evoluciones.add(ev);
        return ev;
    }

    public boolean delEvolucion(Evolucion ev) {
        evoluciones.remove(ev);
        return true;
    }

    @Override
    public String toString() {
        return "Pokemon:" +
                "\n\tId: " + id +
                "\n\tNombre: " + nombre +
                "\n\tNivel: " + nivel +
                "\n\tTipo1: " + tipo1 +
                "\n\tTipo2: " + tipo2 +
                "\n\tEvoluciones: \n\t\t" + evoluciones.toString().replace("[", "").replace("]", "").replace(",","\n\t\t");
    }

    public boolean removeEvolucion(Evolucion ev) {
        return evoluciones.remove(ev);
    }

    public int getId() { return id; }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivel() { return nivel; }

    public void setNivel(int nivel) { this.nivel = nivel; }

    public String getTipo1() { return tipo1; }

    public void setTipo1(String tipo1) { this.tipo1 = tipo1; }

    public String getTipo2() { return tipo2; }

    public void setTipo2(String tipo2) { this.tipo2 = tipo2; }

    public Set<Evolucion> getEvoluciones() { return evoluciones; }

    public void setEvoluciones(Set<Evolucion> evoluciones) {
        this.evoluciones = evoluciones;
    }
}
