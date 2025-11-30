package manyToMany_r1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class pruebaPokAt {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon4");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args)     {

        int respuesta = 0;

        try {
            do {
                System.out.println(
                        "Introduce una opcion:\n\t" +
                        "1  -  Insertar pokemon:\n\t" +
                        "2  -  Insertar ataque:\n\t" +
                        "3  -  Mostrar todos los pokemon:\n\t" +
                        "4  -  Mostrar todos los ataques:\n\t" +
                        "5  -  Buscar pokemon por nombre:\n\t" +
                        "6  -  Buscar ataque por nombre:\n\t" +
                        "7  -  Actualizar pokemon:\n\t" +
                        "8  -  Actualizar ataque:\n\t" +
                        "9  -  Borrar pokemon:\n\t" +
                        "10 -  Borrar ataque:\n\t" +
                        "0 -  Salir:"
                );

                System.out.println("\t- - - - - - - - - - -");
                respuesta = pedirEntero("\tOPCION");
                System.out.println();

                if (respuesta == 1) {
                    String nombre = pedirString("Introduce un nombre para el pokemon");
                    int nivel = pedirEntero("Introduce un nivel para el pokemon");
                    String tipo1 = pedirString("Introduce un tipo para el pokemon");
                    String tipo2 = pedirString("Introduce otro tipo para el pokemon");
                    boolean respuestaTec = preguntarBoolean("Quieres un ataque para el pokemon?");
                    System.out.println();
                    boolean ataque = false;
                    if (respuestaTec){
                        ataque = true;
                    }

                    insertarPokemon(nombre, nivel, tipo1, tipo2, ataque);
                    System.out.println("Pokemon añadido con exito\n");
                } else if (respuesta == 2) {
                    String nombreAt = pedirString("Introduce un nombre para el ataque (no se permiten espacios)");
                    String tipoAt = pedirString("Introduce un tipo para el ataque");
                    int potenciaAt = pedirEntero("Introduce una potencia para el ataque");
                    int precisionAt = pedirEntero("Introduce una precision para el ataque");
                    boolean respuestaTec = preguntarBoolean("Quieres un pokemon para el ataque?");
                    System.out.println();
                    boolean pokemon = false;
                    if (respuestaTec){
                        pokemon = true;
                    }

                    insertarAtaque(nombreAt, tipoAt, potenciaAt, precisionAt, pokemon);
                } else if (respuesta == 3) {
                    mostrarTodosLosPokemon();
                } else if (respuesta == 4) {
                    mostrarTodosLosAtaques();
                } else if (respuesta == 5) {
                    String nombre = pedirString("Introduce el nombre del pokemon que quieres buscar");
                    System.out.println();
                    mostrarPokemonPorNombre(nombre);
                } else if (respuesta == 6) {
                    String nombre = pedirString("Introduce el nombre del ataque que quieres buscar");
                    mostrarAtaquePorNombre(nombre);
                } else if (respuesta == 7) {
                    int id = pedirEntero("Introduce el id del pokemon que quieres actualizar");
                    System.out.println();
                    actualizarPokemonPorId(id);
                } else if (respuesta == 8) {
                    int id = pedirEntero("Introduce el id del ataque que quieres actualizar");
                    System.out.println();
                    actualizarAtaquePorId(id);
                } else if (respuesta == 9) {
                    int id = pedirEntero("Introduce el id del pokemon que quieres eliminar");
                    System.out.println();
                    eliminarPokemonPorId(id);
                } else if (respuesta == 10) {
                    int id = pedirEntero("Introduce el id del ataque que quieres eliminar");
                    System.out.println();
                    eliminarAtaquePorId(id);
                } else if (respuesta == 0) {
                    System.out.print("\tAdios");
                    em.close();
                    emf.close();
                } else {
                    System.out.println("Opcion introducida no valida");
                }
            } while (respuesta != 0);
        } catch (InputMismatchException e) {
            System.out.println("\tTas colao crack, maquina, fiera, mastodonte, titan, maestro");
        }

    }

    public static boolean preguntarBoolean(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        System.out.print(mensaje + " (si/no): ");
        return teclado.next().equalsIgnoreCase("si");
    }

    public static int pedirEntero(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        System.out.print(mensaje + ": ");
        return teclado.nextInt();
    }

    public static String pedirString(String mensaje) {
        Scanner teclado = new Scanner(System.in);
        System.out.print(mensaje + ": ");
        return teclado.next();
    }

    public static void insertarPokemon(String nombre, int nivel, String tipo1, String tipo2, boolean ataque) {

        if (tipo2.equalsIgnoreCase("null")) {
            tipo2 = null;
        }

        Pokemon po = new Pokemon(nombre, nivel, tipo1, tipo2);

        if (ataque) {
            boolean continuar = true;

            while (continuar) {

                String nombreAt = pedirString("Introduce un nombre para el ataque (no se permiten espacios)");
                String tipoAt = pedirString("Introduce un tipo para el ataque");
                int potenciaAt = pedirEntero("Introduce una potencia para el ataque");
                int precisionAt = pedirEntero("Introduce una precision para el ataque");

                po.addAtaque(nombreAt, tipoAt, potenciaAt, precisionAt);
                boolean respuesta = preguntarBoolean("Ataque añadido con exito, desea añadir otro?");
                System.out.println();
                if (!respuesta){
                    continuar = false;
                }

            }

        }

        try {
            em.getTransaction().begin();
            em.persist(po);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No ha funcionado");
        }

    }

    public static void insertarAtaque(String nombreAt, String tipoAt, int potenciaAt, int precisionAt, boolean pokemon) {

        Ataque at = new Ataque(nombreAt, tipoAt, potenciaAt, precisionAt);
        Pokemon po = null;

        if (pokemon) {

            boolean continuar = true;

            while (continuar) {

                String nombrePok = pedirString("Introduce un nombre para el pokemon");
                int nivelPok = pedirEntero("Introduce un nivel para el pokemon");
                String tipo1Pok = pedirString("Introduce un tipo para el pokemon");
                String tipo2Pok = pedirString("Introduce otro tipo para el pokemon");

                po = new Pokemon(nombrePok, nivelPok, tipo1Pok, tipo2Pok);
                at.addPokemon(po);
                boolean respuesta = preguntarBoolean("Pokemon añadido con exito, desea añadir otro?");
                System.out.println();
                if (!respuesta){
                    continuar = false;
                }

            }

        }

        try {
            em.getTransaction().begin();
            em.persist(at);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No ha funcionado");
        }

    }

    public static void mostrarTodosLosPokemon() {

        try {
            em.getTransaction().begin();

            List<Pokemon> pokemons = em
                    .createQuery("SELECT p FROM Pokemon p", Pokemon.class)
                    .getResultList();

            for (Pokemon p: pokemons) {
                if (!p.getAtaques().isEmpty()) {
                    System.out.print("\tPokemon:" +
                            "\n\t\tId: " + p.getId() +
                            "\n\t\tNombre: " + p.getNombre() +
                            "\n\t\tNivel: " + p.getNivel() +
                            "\n\t\tTipo1: " + p.getTipo1() +
                            "\n\t\tTipo2: " + p.getTipo2() +
                            "\n\t\tAtaques: ");
                } else {
                    System.out.print("\tPokemon:" +
                            "\n\t\tId: " + p.getId() +
                            "\n\t\tNombre: " + p.getNombre() +
                            "\n\t\tNivel: " + p.getNivel() +
                            "\n\t\tTipo1: " + p.getTipo1() +
                            "\n\t\tTipo2: " + p.getTipo2());
                }

                for(Ataque at: p.getAtaques()) {
                    System.out.print("\n\t\t"+at.toStringPo());
                }
                System.out.println("\n");
            }

            System.out.println();

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void mostrarPokemonPorNombre(String nombre) {

        try {
            em.getTransaction().begin();
            List<Pokemon> pokemons = em.createQuery("SELECT p FROM Pokemon p WHERE p.nombre = :nombre", Pokemon.class).setParameter("nombre", nombre).getResultList();

            for (Pokemon po : pokemons) {
                if (!po.getAtaques().isEmpty()) {
                    System.out.print("\tPokemon:" +
                            "\n\t\tId: " + po.getId() +
                            "\n\t\tNombre: " + po.getNombre() +
                            "\n\t\tNivel: " + po.getNivel() +
                            "\n\t\tTipo1: " + po.getTipo1() +
                            "\n\t\tTipo2: " + po.getTipo2() +
                            "\n\t\tAtaques: ");
                } else {
                    System.out.print("\tPokemon:" +
                            "\n\t\tId: " + po.getId() +
                            "\n\t\tNombre: " + po.getNombre() +
                            "\n\t\tNivel: " + po.getNivel() +
                            "\n\t\tTipo1: " + po.getTipo1() +
                            "\n\t\tTipo2: " + po.getTipo2());
                }

                for(Ataque at: po.getAtaques()) {
                    System.out.print("\n\t\t"+at.toStringPo());
                }
                System.out.println();
            }

            System.out.println();

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void mostrarAtaquePorNombre(String nombre) {

        try {
            em.getTransaction().begin();
            List<Ataque> ataques = em.createQuery("SELECT a FROM Ataque a WHERE a.nombre = :nombre", Ataque.class)
                    .setParameter("nombre", nombre)
                    .getResultList();

            for (Ataque ataque : ataques) {
                if (ataque.getPokemons().isEmpty()) {
                    System.out.print("\tAtaque:" +
                            "\n\t\tId: " + ataque.getId() +
                            "\n\t\tNombre: " + ataque.getNombre() +
                            "\n\t\tTipo: " + ataque.getTipo() +
                            "\n\t\tPotencia: " + ataque.getPotencia() +
                            "\n\t\tPrecision: " + ataque.getPrecisionAt());
                } else {
                    System.out.print("\tAtaque:" +
                            "\n\t\tId: " + ataque.getId() +
                            "\n\t\tNombre: " + ataque.getNombre() +
                            "\n\t\tTipo: " + ataque.getTipo() +
                            "\n\t\tPotencia: " + ataque.getPotencia() +
                            "\n\t\tPrecision: " + ataque.getPrecisionAt() +
                            "\n\t\tPokemons: ");
                }
                for(Pokemon po: ataque.getPokemons()) {
                    System.out.print("\n\t"+po.toStringAtUnico());
                }
                System.out.println();
            }

            System.out.println();

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void mostrarTodosLosAtaques() {

        try {
            em.getTransaction().begin();

            List<Ataque> ataques = em
                    .createQuery("SELECT a FROM Ataque a", Ataque.class)
                    .getResultList();

            for (Ataque ataque: ataques) {
                if (ataque.getPokemons().isEmpty()) {
                    System.out.print("\tAtaque:" +
                            "\n\t\tId: " + ataque.getId() +
                            "\n\t\tNombre: " + ataque.getNombre() +
                            "\n\t\tTipo: " + ataque.getTipo() +
                            "\n\t\tPotencia: " + ataque.getPotencia() +
                            "\n\t\tPrecision: " + ataque.getPrecisionAt());
                } else {
                    System.out.print("\tAtaque:" +
                            "\n\t\tId: " + ataque.getId() +
                            "\n\t\tNombre: " + ataque.getNombre() +
                            "\n\t\tTipo: " + ataque.getTipo() +
                            "\n\t\tPotencia: " + ataque.getPotencia() +
                            "\n\t\tPrecision: " + ataque.getPrecisionAt() +
                            "\n\t\tPokemons: ");
                }

                for(Pokemon po: ataque.getPokemons()) {
                    System.out.print("\n\t\t"+po.toStringAt());
                }
                System.out.println("\n");
            }

            System.out.println();

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void actualizarPokemonPorId(int id) {
        boolean cambioNombre = preguntarBoolean("Desea cambiar el nombre?");
        boolean cambioNivel = preguntarBoolean("Desea cambiar el nivel?");
        boolean cambioTipo1 = preguntarBoolean("Desea cambiar el tipo principal?");
        boolean cambioTipo2 = preguntarBoolean("Desea cambiar el tipo secundario?");
        boolean cambioAtaque = preguntarBoolean("Desea cambiar algun ataque?");

        try {
            em.getTransaction().begin();

            Pokemon po = em.find(Pokemon.class, id);

            String nombre = po.getNombre();
            int nivel = po.getNivel();
            String tipo1 = po.getTipo1();
            String tipo2 = po.getTipo2();

            if (cambioNombre) {
                nombre = pedirString("Introduce el nuevo nombre que quieres asignar");
                po.setNombre(nombre);
            }

            if (cambioNivel) {

                nivel = pedirEntero("Introduce el nuevo nivel que quieres asignar");
                po.setNivel(nivel);

            }

            if (cambioTipo1) {
                tipo1 = pedirString("Introduce el nuevo tipo principal que quieres asignar");
                if (tipo1.equalsIgnoreCase("null")) {
                    System.out.println("El tipo principal no puede ser null");
                } else {
                    po.setTipo1(tipo1);
                }

            }

            if (cambioTipo2) {

                tipo2 = pedirString("Introduce el nuevo tipo secundario que quieres asignar");
                if (tipo2.equalsIgnoreCase("null")) {
                    tipo2 = null;
                }
                po.setTipo2(tipo2);

            }

            if (cambioAtaque) {

                Set<Ataque> ataques = po.getAtaques();
                System.out.println("Tienes " + ataques.size() + " ataques, cual quieres modificar?: ");
                System.out.println("\tAtaques: ");
                for (Ataque at: ataques) {
                    System.out.println("\t\t"+at);
                }
                int numEvo = pedirEntero("Introduce el numero del ataque que quieres cambiar");
                int contador = 0;
                boolean cambioNombreAt = preguntarBoolean("Desea cambiar el nombre?");
                boolean cambioTipoAt = preguntarBoolean("Desea cambiar el tipo del ataque?");
                boolean cambioPotencia = preguntarBoolean("Desea cambiar la potencia?");
                boolean cambioPrecision = preguntarBoolean("Desea cambiar la precision?");

                for (Ataque at: ataques) {
                    if (contador == (numEvo - 1)){

                        String nombreAt = at.getNombre();
                        String tipoAt = at.getTipo();
                        int potenciaAt = at.getPotencia();
                        int precisionAt = at.getPrecisionAt();

                        if (cambioNombreAt) {

                            nombreAt = pedirString("Introduce el nuevo nombre que quieres asignar (no se permiten espacios)");
                            at.setNombre(nombreAt);

                        }

                        if (cambioTipoAt) {

                            tipoAt = pedirString("Introduce el nuevo tipo que quieres asignar");
                            if (tipoAt.equalsIgnoreCase("null")) {
                                System.out.println("El tipo no puede ser null");
                            } else {
                                at.setTipo(tipoAt);
                            }

                        }

                        if (cambioPotencia) {

                            potenciaAt = pedirEntero("Introduce la nueva potencia que quieres asignar");
                            at.setPotencia(potenciaAt);

                        }

                        if (cambioPrecision) {

                            precisionAt = pedirEntero("Introduce la nueva precision que quieres asignar");
                            at.setPrecisionAt(precisionAt);

                        }

                    }
                    contador++;
                }

                po.setAtaques(ataques);

            }

            em.merge(po);

            em.getTransaction().commit();

            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void actualizarAtaquePorId(int id) {
        boolean cambioNombre = preguntarBoolean("Desea cambiar el nombre?");
        boolean cambioTipo = preguntarBoolean("Desea cambiar el tipo del ataque?");
        boolean cambioPrecision = preguntarBoolean("Desea cambiar la potencia?");
        boolean cambioPotencia = preguntarBoolean("Desea cambiar la precision?");
        boolean cambioPokemon = preguntarBoolean("Desea cambiar algun pokemon?");

        try {
            em.getTransaction().begin();

            Ataque at = em.find(Ataque.class, id);

            String nombre = at.getNombre();
            String tipo = at.getTipo();
            int potencia = at.getPotencia();
            int precisionAt = at.getPrecisionAt();


            if (cambioNombre) {

                nombre = pedirString("Introduce el nuevo nombre que quieres asignar (no se permiten espacios)");
                at.setNombre(nombre);

            }

            if (cambioTipo) {
                tipo = pedirString("Introduce el nuevo tipo principal que quieres asignar");
                if (tipo.equalsIgnoreCase("null")) {
                    System.out.println("El tipo no puede ser null");
                } else {
                    at.setTipo(tipo);
                }

            }

            if (cambioPotencia) {

                potencia = pedirEntero("Introduce la nueva potencia que quieres asignar");
                at.setPotencia(potencia);

            }

            if (cambioPrecision) {

                precisionAt = pedirEntero("Introduce la nueva precision que quieres asignar");
                at.setPrecisionAt(precisionAt);

            }

            if (cambioPokemon) {

                Set<Pokemon> pokemons = at.getPokemons();
                System.out.println("Tienes " + pokemons.size() + " pokemon, cual quieres modificar?: ");
                System.out.println("\tAtaques: ");
                for (Pokemon po : pokemons) {
                    System.out.println("\t" + po.toStringAtUnico());
                }

                System.out.println();

                int numPok = pedirEntero("Introduce el numero del pokemon que quieres cambiar");
                int contador = 0;
                boolean cambioNombrePok = preguntarBoolean("Desea cambiar el nombre?");
                boolean cambioNivel = preguntarBoolean("Desea cambiar el nivel?");
                boolean cambioTipo1 = preguntarBoolean("Desea cambiar el tipo principal?");
                boolean cambioTipo2 = preguntarBoolean("Desea cambiar el tipo secundario?");

                for (Pokemon po : pokemons) {
                    if (contador == (numPok - 1)) {

                        String nombrePok = po.getNombre();
                        int nivel = po.getNivel();
                        String tipo1 = po.getTipo1();
                        String tipo2 = po.getTipo2();

                        if (cambioNombrePok) {
                            nombrePok = pedirString("Introduce el nuevo nombre que quieres asignar");
                            po.setNombre(nombrePok);
                        }

                        if (cambioNivel) {

                            nivel = pedirEntero("Introduce el nuevo nivel que quieres asignar");
                            po.setNivel(nivel);

                        }

                        if (cambioTipo1) {
                            tipo1 = pedirString("Introduce el nuevo tipo principal que quieres asignar");
                            if (tipo1.equalsIgnoreCase("null")) {
                                System.out.println("El tipo principal no puede ser null");
                            } else {
                                po.setTipo1(tipo1);
                            }

                        }

                        if (cambioTipo2) {

                            tipo2 = pedirString("Introduce el nuevo tipo secundario que quieres asignar");
                            if (tipo2.equalsIgnoreCase("null")) {
                                tipo2 = null;
                            }
                            po.setTipo2(tipo2);

                        }

                        contador++;
                    }

                    at.setPokemons(pokemons);

                }
            }

            em.merge(at);

            em.getTransaction().commit();

            System.out.println();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void eliminarPokemonPorId(int id) {

        try {
            em.getTransaction().begin();

            Pokemon po = em.find(Pokemon.class, id);
            em.remove(po);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No ha funcionado");
        }

    }

    public static void eliminarAtaquePorId(int id) {

        try {
            em.getTransaction().begin();

            Ataque at = em.find(Ataque.class, id);
            em.remove(at);

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("No ha funcionado");
        }

    }

}