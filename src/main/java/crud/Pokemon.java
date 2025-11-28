package crud;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

// Al comentar las anotaciones @Table, vemos que si la tabla ya esta creada, y los nombres no coinciden, se crearia una nueva
// Al comentar las anotaciones @Column, vemos que si la tabla ya esta creada, y los nombres no coinciden, se adaptan los campos
// La tabla si no existe se crea con el nombre de la clase en mayuscula y los nombres de los campos son los de los atributos en mayusculas también

@Entity
@Table(name = "pokedex")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pokemon")
    private int id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "nivel")
    private int nivel;

    @Column(name = "tipo1")
    private String tipo1;

    @Column(name = "tipo2")
    private String tipo2;

    public Pokemon() {

    }

    public Pokemon(String nombre, int nivel, String tipo1, String tipo2) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.tipo1 = tipo1;
        this.tipo2 = tipo2;
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