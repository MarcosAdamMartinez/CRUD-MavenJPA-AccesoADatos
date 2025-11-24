package oneToMany;

import jakarta.persistence.*;

import java.util.HashSet;
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

    @OneToMany
    Set<Evolucion> evoluciones = new HashSet<Evolucion>();

    public Pokemon() {

    }

    public Pokemon(String nombre, int nivel, String tipo1, String tipo2) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    public void addEvolucion(String nombre) {
        evoluciones.add(new Evolucion(nombre));
    }

    public boolean delEvolucion(String nombre) {
        Evolucion evolucion = new Evolucion(nombre);
        return evoluciones.remove(evolucion);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public String getTipo1() {
        return tipo1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTipo1(String tipo1) {
        this.tipo1 = tipo1;
    }

    public String getTipo2() {
        return tipo2;
    }

    public void setTipo2(String tipo2) {
        this.tipo2 = tipo2;
    }

}