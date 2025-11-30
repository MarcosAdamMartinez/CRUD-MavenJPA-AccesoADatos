package manyToMany_r1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class pruebaPokEv {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon4");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
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
                        "11 -  Salir:"
                );

                System.out.println("\t- - - - - - - - - - -");
                System.out.print("\tOPCION: ");
                respuesta = teclado.nextInt();
                System.out.println();

                if (respuesta == 1) {
                    System.out.print("Introduce un nombre para el pokemon: ");
                    String nombre = teclado.next();
                    System.out.print("Introduce un nivel para el pokemon: ");
                    int nivel = teclado.nextInt();
                    System.out.print("Introduce un tipo para el pokemon: ");
                    String tipo1 = teclado.next();
                    System.out.print("Introduce otro tipo para el pokemon: ");
                    String tipo2 = teclado.next();
                    System.out.print("Quieres un ataque para el pokemon? (si | no): ");
                    String respuestaTec = teclado.next();
                    System.out.println();
                    boolean ataque = false;
                    if (respuestaTec.equalsIgnoreCase("si")){
                        ataque = true;
                    } else if (respuestaTec.equalsIgnoreCase("no")){
                    } else {
                        System.out.println("Te has colado, es o si o no");
                    }

                    insertarPokemon(nombre, nivel, tipo1, tipo2, ataque);
                    System.out.println("Pokemon añadido con exito\n");

                } else if (respuesta == 2) {
                    System.out.print("Introduce un nombre para el ataque (no se permiten espacios): ");
                    String nombreAt = teclado.next();
                    System.out.print("Introduce un tipo para el ataque: ");
                    String tipoAt = teclado.next();
                    System.out.print("Introduce una potencia para el ataque: ");
                    int potenciaAt = teclado.nextInt();
                    System.out.print("Introduce una precision para el ataque: ");
                    int precisionAt = teclado.nextInt();
                    System.out.print("Quieres un pokemon para el ataque? (si | no): ");
                    String respuestaTec = teclado.next();
                    System.out.println();
                    boolean pokemon = false;
                    if (respuestaTec.equalsIgnoreCase("si")){
                        pokemon = true;
                    } else if (respuestaTec.equalsIgnoreCase("no")){
                    } else {
                        System.out.println("Te has colado, es o si o no");
                    }

                    insertarAtaque(nombreAt, tipoAt, potenciaAt, precisionAt, pokemon);
                } else if (respuesta == 3) {
                    mostrarTodosLosPokemon();
                } else if (respuesta == 4) {
                    mostrarTodosLosAtaques();
                } else if (respuesta == 5) {
                    System.out.print("Introduce el nombre del pokemon que quieres buscar: ");
                    String nombre = teclado.next();
                    System.out.println();
                    mostrarPokemonPorNombre(nombre);
                }  else if (respuesta == 6) {
                    System.out.print("Introduce el nombre del ataque que quieres buscar: ");
                    String nombre = teclado.next();
                    System.out.println();
                    mostrarAtaquePorNombre(nombre);
                } else if (respuesta == 7) {
                    System.out.print("Introduce el id del pokemon que quieres actualizar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    actualizarPokemonPorId(id);
                } else if (respuesta == 8) {
                    System.out.print("Introduce el id del ataque que quieres actualizar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    actualizarAtaquePorId(id);
                } else if (respuesta == 9) {
                    System.out.print("Introduce el id del pokemon que quieres eliminar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    eliminarPokemonPorId(id);
                } else if (respuesta == 10) {
                    System.out.print("Introduce el id del ataque que quieres eliminar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    eliminarAtaquePorId(id);
                } else if (respuesta == 11) {
                    System.out.print("\tAdios");
                    em.close();
                    emf.close();
                } else {
                    System.out.println("Opcion introducida no valida");
                }
            } while (respuesta != 11);
        } catch (InputMismatchException e) {
            System.out.println("\tTas colao crack, maquina, fiera, mastodonte, titan, maestro");
        }

    }

    public static void insertarPokemon(String nombre, int nivel, String tipo1, String tipo2, boolean ataque) {

        if (tipo2.equalsIgnoreCase("null")) {
            tipo2 = null;
        }

        Pokemon po = new Pokemon(nombre, nivel, tipo1, tipo2);

        if (ataque) {
            Scanner tecladoIf = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {

                System.out.print("Introduce un nombre para el ataque (no se permiten espacios): ");
                String nombreAt = tecladoIf.next();
                System.out.print("Introduce un tipo para el ataque: ");
                String tipoAt = tecladoIf.next();
                System.out.print("Introduce una potencia para el ataque: ");
                int potenciaAt = tecladoIf.nextInt();
                System.out.print("Introduce una precision para el ataque: ");
                int precisionAt = tecladoIf.nextInt();

                po.addAtaque(nombreAt, tipoAt, potenciaAt, precisionAt);
                System.out.print("Ataque añadido con exito, desea añadir otro? (si | no): ");
                String respuesta = tecladoIf.next();
                System.out.println();
                if (respuesta.equalsIgnoreCase("no")){
                    continuar = false;
                } else if (respuesta.equalsIgnoreCase("si")){
                } else {
                    System.out.println("Te has colado, es o si o no");
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
            Scanner tecladoIf = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {

                System.out.print("Introduce un nombre para el pokemon: ");
                String nombrePok = tecladoIf.next();
                System.out.print("Introduce un nivel para el pokemon: ");
                int nivelPok = tecladoIf.nextInt();
                System.out.print("Introduce un tipo para el pokemon: ");
                String tipo1Pok = tecladoIf.next();
                System.out.print("Introduce otro tipo para el pokemon: ");
                String tipo2Pok = tecladoIf.next();

                po = new Pokemon(nombrePok, nivelPok, tipo1Pok, tipo2Pok);
                at.addPokemon(po);
                System.out.print("Pokemon añadido con exito, desea añadir otro? (si | no): ");
                String respuesta = tecladoIf.next();
                System.out.println();
                if (respuesta.equalsIgnoreCase("no")){
                    continuar = false;
                } else if (respuesta.equalsIgnoreCase("si")){
                } else {
                    System.out.println("Te has colado, es o si o no");
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
                System.out.print("\tPokemon:" +
                        "\n\t\tId: " + p.getId() +
                        "\n\t\tNombre: " + p.getNombre() +
                        "\n\t\tNivel: " + p.getNivel() +
                        "\n\t\tTipo1: " + p.getTipo1() +
                        "\n\t\tTipo2: " + p.getTipo2() +
                        "\n\t\tAtaques: ");

                for(Ataque at: p.getAtaques()) {
                    System.out.print("\n\t\t"+at);
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

    public static void mostrarPokemonPorNombre(String nombre) {

        try {
            em.getTransaction().begin();
            List<Pokemon> pokemons = em.createQuery("SELECT p FROM Pokemon p WHERE p.nombre = :nombre", Pokemon.class).setParameter("nombre", nombre).getResultList();

            for (Pokemon po : pokemons) {
                System.out.print("\tID: " + po.getId()
                        + "\n\tNombre: " + po.getNombre()
                        + "\n\tNivel: " + po.getNivel()
                        + "\n\tTipo 1: " + po.getTipo1()
                        + "\n\tTipo 2: " + po.getTipo2()
                        + "\n\tAtaques: ");
                for(Ataque at: po.getAtaques()) {
                    System.out.print("\n\t\t"+at);
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

            for (Ataque at : ataques) {
                System.out.print("\tID: " + at.getId()
                        + "\n\tNombre: " + at.getNombre()
                        + "\n\tTipo: " + at.getTipo()
                        + "\n\tPotencia: " + at.getPotencia()
                        + "\n\tPrecision: " + at.getPrecisionAt()
                        + "\n\tPokemons: ");
                for(Pokemon po: at.getPokemons()) {
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

    public static void actualizarPokemonPorId(int id) {
        Scanner teclado = new Scanner(System.in);
        String respuesta = "no";
        boolean cambioNivel;
        boolean cambioTipo1;
        boolean cambioTipo2;
        boolean cambioAtaque;

        try {
            em.getTransaction().begin();

            Pokemon po = em.find(Pokemon.class, id);

            System.out.print("Desea cambiar el nivel? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioNivel = true;
            } else {
                cambioNivel = false;
            }

            System.out.print("Desea cambiar el tipo principal? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioTipo1 = true;
            } else {
                cambioTipo1 = false;
            }


            System.out.print("Desea cambiar el tipo secundario? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioTipo2 = true;
            } else {
                cambioTipo2 = false;
            }

            System.out.print("Desea cambiar algun ataque? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioAtaque = true;
            } else {
                cambioAtaque = false;
            }

            int nivel = po.getNivel();
            String tipo1 = po.getTipo1();
            String tipo2 = po.getTipo2();

            if (cambioNivel == true) {

                System.out.print("Introduce el nuevo nivel que quieres asignar: ");
                nivel = teclado.nextInt();
                po.setNivel(nivel);

            }

            if (cambioTipo1 == true) {
                System.out.print("Introduce el nuevo tipo principal que quieres asignar: ");
                tipo1 = teclado.next();
                if (tipo1.equalsIgnoreCase("null")) {
                    System.out.println("El tipo principal no puede ser null");
                } else {
                    po.setTipo1(tipo1);
                }

            }

            if (cambioTipo2 == true) {

                System.out.print("Introduce el nuevo tipo secundario que quieres asignar: ");
                tipo2 = teclado.next();
                if (tipo2.equalsIgnoreCase("null")) {
                    tipo2 = null;
                }
                po.setTipo2(tipo2);

            }

            if (cambioAtaque == true) {

                Set<Ataque> ataques = po.getAtaques();
                System.out.println("Tienes " + ataques.size() + " ataques, cual quieres modificar?: ");
                System.out.println("\tAtaques: ");
                for (Ataque at: ataques) {
                    System.out.println("\t\t"+at);
                }
                System.out.print("Introduce cual de los ataques quieres cambiar: ");
                int numEvo = teclado.nextInt();
                int contador = 0;
                boolean cambioNombre;
                boolean cambioTipoAt;
                boolean cambioPrecision;
                boolean cambioPotencia;

                for (Ataque at: ataques) {
                    if (contador == (numEvo - 1)){
                        System.out.print("Desea cambiar el nombre? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioNombre = true;
                        } else {
                            cambioNombre = false;
                        }


                        System.out.print("Desea cambiar el tipo del ataque? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioTipoAt = true;
                        } else {
                            cambioTipoAt = false;
                        }


                        System.out.print("Desea cambiar la potencia? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioPotencia = true;
                        } else {
                            cambioPotencia = false;
                        }

                        System.out.print("Desea cambiar la precision? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioPrecision = true;
                        } else {
                            cambioPrecision = false;
                        }

                        String nombreAt = at.getNombre();
                        String tipoAt = at.getTipo();
                        int potenciaAt = at.getPotencia();
                        int precisionAt = at.getPrecisionAt();

                        if (cambioNombre == true) {

                            System.out.print("Introduce el nuevo nombre que quieres asignar (no se permiten espacios): ");
                            nombreAt = teclado.next();
                            at.setNombre(nombreAt);

                        }

                        if (cambioTipoAt == true) {

                            System.out.print("Introduce el nuevo tipo que quieres asignar: ");
                            tipoAt = teclado.next();
                            if (tipoAt.equalsIgnoreCase("null")) {
                                System.out.println("El tipo no puede ser null");
                            } else {
                                at.setTipo(tipoAt);
                            }

                        }

                        if (cambioPotencia == true) {

                            System.out.print("Introduce la nueva potencia que quieres asignar: ");
                            potenciaAt = teclado.nextInt();
                            at.setPotencia(potenciaAt);

                        }

                        if (cambioPrecision == true) {

                            System.out.print("Introduce la nueva precision que quieres asignar: ");
                            precisionAt = teclado.nextInt();
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
        Scanner teclado = new Scanner(System.in);
        String respuesta = "no";
        boolean cambioNombre;
        boolean cambioTipo;
        boolean cambioPotencia;
        boolean cambioPrecision;
        boolean cambioPokemon;

        try {
            em.getTransaction().begin();

            Ataque at = em.find(Ataque.class, id);

            System.out.print("Desea cambiar el nombre? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioNombre = true;
            } else {
                cambioNombre = false;
            }


            System.out.print("Desea cambiar el tipo? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioTipo = true;
            } else {
                cambioTipo = false;
            }


            System.out.print("Desea cambiar la potencia? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioPotencia = true;
            } else {
                cambioPotencia = false;
            }

            System.out.print("Desea cambiar la precision? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioPrecision = true;
            } else {
                cambioPrecision = false;
            }

            System.out.print("Desea cambiar algun pokemon? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioPokemon = true;
            } else {
                cambioPokemon = false;
            }

            String nombre = at.getNombre();
            String tipo = at.getTipo();
            int potencia = at.getPotencia();
            int precisionAt = at.getPrecisionAt();


            if (cambioNombre == true) {

                System.out.print("Introduce el nuevo nombre que quieres asignar (no se permiten espacios): ");
                nombre = teclado.next();
                at.setNombre(nombre);

            }

            if (cambioTipo == true) {
                System.out.print("Introduce el nuevo tipo principal que quieres asignar: ");
                tipo = teclado.next();
                if (tipo.equalsIgnoreCase("null")) {
                    System.out.println("El tipo no puede ser null");
                } else {
                    at.setTipo(tipo);
                }

            }

            if (cambioPotencia == true) {

                System.out.print("Introduce la nueva potencia que quieres asignar: ");
                potencia = teclado.nextInt();
                at.setPotencia(potencia);

            }

            if (cambioPrecision == true) {

                System.out.print("Introduce la nueva precision que quieres asignar: ");
                precisionAt = teclado.nextInt();
                at.setPrecisionAt(precisionAt);

            }

            if (cambioPokemon == true) {

                Set<Pokemon> pokemons = at.getPokemons();
                System.out.println("Tienes " + pokemons.size() + " evoluciones, cual quieres modificar?: ");
                System.out.println("\tAtaques: ");
                for (Pokemon po: pokemons) {
                    System.out.println("\t"+po.toStringAtUnico());
                }

                System.out.println();

                System.out.print("Introduce cual de las evoluciones quieres cambiar: ");
                int numPok = teclado.nextInt();
                int contador = 0;
                boolean cambioNivel;
                boolean cambioTipo1;
                boolean cambioTipo2;

                for (Pokemon po: pokemons) {
                    if (contador == (numPok - 1)){
                        System.out.print("Desea cambiar el nivel? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioNivel = true;
                        } else {
                            cambioNivel = false;
                        }


                        System.out.print("Desea cambiar el tipo principal? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioTipo1 = true;
                        } else {
                            cambioTipo1 = false;
                        }


                        System.out.print("Desea cambiar el tipo secundario? (si | no): ");
                        respuesta = teclado.next();
                        if (respuesta.equalsIgnoreCase("si")) {
                            cambioTipo2 = true;
                        } else {
                            cambioTipo2 = false;
                        }

                        int nivel = po.getNivel();
                        String tipo1 = po.getTipo1();
                        String tipo2 = po.getTipo2();

                        if (cambioNivel == true) {

                            System.out.print("Introduce el nuevo nivel que quieres asignar: ");
                            nivel = teclado.nextInt();
                            po.setNivel(nivel);

                        }

                        if (cambioTipo1 == true) {
                            System.out.print("Introduce el nuevo tipo principal que quieres asignar: ");
                            tipo1 = teclado.next();
                            if (tipo1.equalsIgnoreCase("null")) {
                                System.out.println("El tipo principal no puede ser null");
                            } else {
                                po.setTipo1(tipo1);
                            }

                        }

                        if (cambioTipo2 == true) {

                            System.out.print("Introduce el nuevo tipo secundario que quieres asignar: ");
                            tipo2 = teclado.next();
                            if (tipo2.equalsIgnoreCase("null")) {
                                tipo2 = null;
                            }
                            po.setTipo2(tipo2);

                        }

                    }
                    contador++;
                }

                at.setPokemons(pokemons);

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