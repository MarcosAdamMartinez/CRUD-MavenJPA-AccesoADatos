package oneToManyIdNombre;

import jakarta.persistence.*;

@Entity
@Table(name = "evoluciones")
public class Evolucion {

    @Id
    private String nombre;

    private int nivel;
    private String tipo1;
    private String tipo2;

    @ManyToOne
    // Especificamos con JoinColumn el nombre de la columna que se debe crear como referencia a la tabla padre
    // Ademas le pasamos como propiedad en el referencedColumnName el nombre de la columna a la que hace referencia
    // Y por ultimo instanciamos que no ha de ser null con la propiedad nullable dandole de valor false
    @JoinColumn(name = "nombre_pok", referencedColumnName = "nombre", nullable = false)
    private Pokemon pokemon;

    public Evolucion() {}

    public Evolucion(String nombre, int nivel, String tipo1, String tipo2, Pokemon pokemon) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
        this.pokemon = pokemon;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getNivel() { return nivel; }

    public void setNivel(int nivel) { this.nivel = nivel; }

    public String getTipo1() { return tipo1; }

    public void setTipo1(String tipo1) { this.tipo1 = tipo1; }

    public String getTipo2() { return tipo2; }

    public void setTipo2(String tipo2) { this.tipo2 = tipo2; }

    public Pokemon getPokemon() { return pokemon; }

    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }
}
