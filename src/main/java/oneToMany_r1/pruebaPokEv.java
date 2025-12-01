package oneToMany_r1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.*;

public class pruebaPokEv {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon2");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int respuesta = 0;

        try {
            do {
                System.out.println("Introduce una opcion:\n\t1 - Insertar pokemon:\n\t2 - Mostrar todos los pokemon:\n\t3 - Buscar por nombre:\n\t4 - Actualizar pokemon:\n\t5 - Borrar pokemon:\n\t6 - Salir:");
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
                    System.out.print("Quieres una evolucion para el pokemon? (si | no): ");
                    String respuestaTec = teclado.next();
                    System.out.println();
                    boolean evolucion = false;
                    if (respuestaTec.equalsIgnoreCase("si")){
                        evolucion = true;
                    } else if (respuestaTec.equalsIgnoreCase("no")){
                    } else {
                        System.out.println("Te has colado, es o si o no");
                    }

                    insertarPokemon(nombre, nivel, tipo1, tipo2, evolucion);
                    System.out.println("Pokemon añadido con exito\n");

                } else if (respuesta == 2) {
                    mostrarTodosLosPokemon();
                } else if (respuesta == 3) {
                    System.out.print("Introduce el nombre del pokemon que quieres buscar: ");
                    String nombre = teclado.next();
                    System.out.println();
                    mostrarPokemonPorNombre(nombre);
                } else if (respuesta == 4) {
                    System.out.print("Introduce el id del pokemon que quieres actualizar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    actualizarPorId(id);
                } else if (respuesta == 5) {
                    System.out.print("Introduce el id del pokemon que quieres eliminar: ");
                    int id = teclado.nextInt();
                    System.out.println();
                    eliminarPorId(id);
                } else if (respuesta == 6) {
                    System.out.print("\tAdios");
                    em.close();
                    emf.close();
                } else {
                    System.out.println("Opcion introducida no valida");
                }
            } while (respuesta != 6);
        } catch (InputMismatchException e) {
            System.out.println("\tTas colao crack, maquina, fiera, mastodonte, titan, maestro");
        }


    }

    public static void insertarPokemon(String nombre, int nivel, String tipo1, String tipo2, boolean evolucion) {

        if (tipo2.equalsIgnoreCase("null")) {
            tipo2 = null;
        }

        Pokemon po = new Pokemon(nombre, nivel, tipo1, tipo2);

        if (evolucion) {
            Scanner tecladoIf = new Scanner(System.in);
            boolean continuar = true;

            while (continuar) {

                System.out.print("Introduce un nombre para el pokemon: ");
                nombre = tecladoIf.next();
                System.out.print("Introduce un nivel para el pokemon: ");
                nivel = tecladoIf.nextInt();
                System.out.print("Introduce un tipo para el pokemon: ");
                tipo1 = tecladoIf.next();
                System.out.print("Introduce otro tipo para el pokemon: ");
                tipo2 = tecladoIf.next();

                po.addEvolucion(nombre, nivel, tipo1, tipo2);
                System.out.print("Evolucion añadida con exito, desea añadir otra? (si | no): ");
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
    public static void mostrarTodosLosPokemon() {

        try {
            em.getTransaction().begin();

            List<Pokemon> pokemons = em
                    .createQuery("SELECT p FROM Pokemon p", Pokemon.class)
                    .getResultList();

            for (Pokemon p: pokemons) {
                System.out.println(p);
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
            List<Pokemon> pokedex = em.createQuery("SELECT p FROM Pokemon p WHERE p.nombre = :nombre", Pokemon.class).setParameter("nombre", nombre).getResultList();

            for (Pokemon po : pokedex) {
                System.out.println("\tID: " + po.getId()
                        + "\n\tNombre: " + po.getNombre()
                        + "\n\tNivel: " + po.getNivel()
                        + "\n\tTipo 1: " + po.getTipo1()
                        + "\n\tTipo 2: " + po.getTipo2()
                        + "\n\tEvoluciones:\n\t\t"+ po.getEvoluciones().toString()
                                                    .replace("[", "")
                                                    .replace("]", "")
                                                    .replace(",","\n\t\t") +"\n");
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        }

    }

    public static void actualizarPorId(int id) {
        Scanner teclado = new Scanner(System.in);
        String respuesta = "no";
        boolean cambioNivel;
        boolean cambioTipo1;
        boolean cambioTipo2;
        boolean cambioEvo;

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

            System.out.print("Desea cambiar alguna evolucion? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioEvo = true;
            } else {
                cambioEvo = false;
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

            if (cambioEvo == true) {

                Set<Evoluciones> evos = po.getEvoluciones();
                System.out.println("Tienes " + evos.size() + " evoluciones, cual quieres modificar?: ");
                System.out.println("\tEvoluciones:\n");
                for (Evoluciones evo: evos) {
                    System.out.println("\t\t"+evo);
                }
                System.out.print("Introduce cual de las evoluciones quieres cambiar: ");
                int numEvo = teclado.nextInt();
                int contador = 0;

                for (Evoluciones evo: evos) {
                    if (contador == (numEvo - 1)){
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

                        int nivelEvo = evo.getNivel();
                        String tipo1Evo = evo.getTipo1();
                        String tipo2Evo = evo.getTipo2();

                        if (cambioNivel == true) {

                            System.out.print("Introduce el nuevo nivel que quieres asignar: ");
                            nivel = teclado.nextInt();
                            evo.setNivel(nivel);

                        }

                        if (cambioTipo1 == true) {
                            System.out.print("Introduce el nuevo tipo principal que quieres asignar: ");
                            tipo1 = teclado.next();
                            if (tipo1.equalsIgnoreCase("null")) {
                                System.out.println("El tipo principal no puede ser null");
                            } else {
                                evo.setTipo1(tipo1);
                            }

                        }

                        if (cambioTipo2 == true) {

                            System.out.print("Introduce el nuevo tipo secundario que quieres asignar: ");
                            tipo2 = teclado.next();
                            if (tipo2.equalsIgnoreCase("null")) {
                                tipo2 = null;
                            }
                            evo.setTipo2(tipo2);

                        }

                    }
                    contador++;
                }

                po.setEvoluciones(evos);

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

    public static void eliminarPorId(int id) {

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

}
