package manyToMany_r1;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "pokemons")
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

    // Creamos el ManyToMany y le añadimos la propiedad cascade:
    // En la propiedad cascade pasamos un array con los valores PERSIST Y MERGE
    // PERSIST hace que las creaciones persistan
    // MERGE hace que los cambios se pasen de padres a hijos
    // Añadimos tambien la anotacion @JoinTable que creara la tabla intermedia que relacionara las 2 tablas:
    // Pasamos al @JoinTable por parametros el nombre, el nombre del campo id de la table padre y el nomrbe del campo id de la tabla hija
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "pokemon_ataque", joinColumns = @JoinColumn(name = "id_pokemon"), inverseJoinColumns = @JoinColumn(name = "id_ataque"))
    Set<Ataque> ataques = new LinkedHashSet<>();

    public Pokemon() {}

    public Pokemon(String nombre, int nivel, String tipo1, String tipo2) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
    }

    public boolean addAtaque(String nombre, String tipo, int potencia, int precision) {
        Ataque at = new Ataque(nombre, tipo, potencia, precision);
        ataques.add(at);
        return at.getPokemons().add(this);
    }

    public Ataque addAtaque(Ataque at) {
        ataques.add(at);
        return at;
    }

    public void removeAtaque(Ataque at) {
        ataques.remove(at);
        at.getPokemons().remove(this);
    }


    @Override
    public String toString() {
        return "Pokemon:" +
                "\n\tId: " + id +
                "\n\tNombre: " + nombre +
                "\n\tNivel: " + nivel +
                "\n\tTipo1: " + tipo1 +
                "\n\tTipo2: " + tipo2 +
                "\n\tAtaques: \n\t\t" + ataques.toString().replace("[", "").replace("]", "").replace(",","\n\t\t");
    }

    public String toStringAt() {
        return "\tPokemon:" +
                "\n\t\t\t\tId: " + id +
                "\n\t\t\t\tNombre: " + nombre +
                "\n\t\t\t\tNivel: " + nivel +
                "\n\t\t\t\tTipo1: " + tipo1 +
                "\n\t\t\t\tTipo2: " + tipo2;
    }

    public String toStringAtUnico() {

        return "\tPokemon:" +
                "\n\t\t\tId: " + id +
                "\n\t\t\tNombre: " + nombre +
                "\n\t\t\tNivel: " + nivel +
                "\n\t\t\tTipo1: " + tipo1 +
                "\n\t\t\tTipo2: " + tipo2;

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

    public Set<Ataque> getAtaques() { return ataques; }

    public void setAtaques(Set<Ataque> ataques) {
        this.ataques = ataques;
    }

}
