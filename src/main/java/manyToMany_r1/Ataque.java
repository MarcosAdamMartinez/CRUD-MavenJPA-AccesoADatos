package manyToMany_r1;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "ataques")
public class Ataque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "potencia")
    private int potencia;

    @Column(name = "precisionAt")
    private int precisionAt;

    @ManyToMany (mappedBy = "ataques", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Set<Pokemon> pokemons = new LinkedHashSet<>();

    public Ataque(){}

    public Ataque(String nombre, String tipo, int potencia, int precision) {

        this.nombre = nombre;
        this.tipo = tipo;
        this.potencia = potencia;
        this.precisionAt = precision;

    }

    public boolean addPokemon(String nombre, int nivel, String tipo1, String tipo2) {
        Pokemon po = new Pokemon(nombre, nivel, tipo1, tipo2);
        pokemons.add(po);
        return po.getAtaques().add(this);
    }

    public boolean addPokemon(Pokemon po) {
        pokemons.add(po);
        return po.getAtaques().add(this);
    }

    public void removePokemon(Pokemon po) {
        pokemons.remove(po);
        po.getAtaques().remove(this);
    }

    @Override
    public String toString() {
        return "Ataque:" +
                "\n\t\t\tId: " + id +
                "\n\t\t\tNombre: " + nombre +
                "\n\t\t\tTipo: " + tipo +
                "\n\t\t\tPotencia: " + potencia +
                "\n\t\t\tPrecision: " + precisionAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPotencia() {
        return potencia;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public int getPrecisionAt() {
        return precisionAt;
    }

    public void setPrecisionAt(int precisionAt) {
        this.precisionAt = precisionAt;
    }

    public Set<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Set<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

}
