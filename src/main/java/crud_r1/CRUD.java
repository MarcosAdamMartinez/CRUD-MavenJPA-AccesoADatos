package crud_r1;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class CRUD {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon");
    private static EntityManager em = emf.createEntityManager();

    public static void insertarPokemon(String nombre, int nivel, String tipo1, String tipo2) {

        if (tipo2.equalsIgnoreCase("null")) {
            tipo2 = null;
        }

        Pokemon po = new Pokemon(nombre, nivel, tipo1, tipo2);

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
            List<Pokemon> pokedex = em.createQuery("SELECT p FROM Pokemon p", Pokemon.class).getResultList();

            System.out.println("Todos los pokemon son:");
            for (Pokemon po : pokedex) {
                System.out.println("\tID: " + po.getId() + "\n\tNombre: " + po.getNombre() + "\n\tNivel: " + po.getNivel() + "\n\tTipo 1: " + po.getTipo1() + "\n\tTipo 2: " + po.getTipo2() + "\n");
            }

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
                System.out.println("\tID: " + po.getId() + "\n\tNombre: " + po.getNombre() + "\n\tNivel: " + po.getNivel() + "\n\tTipo 1: " + po.getTipo1() + "\n\tTipo 2: " + po.getTipo2() + "\n");
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

        try {
            em.getTransaction().begin();

            Pokemon po = em.find(Pokemon.class, id);

            System.out.println("Desea cambiar el nivel? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioNivel = true;
            } else {
                cambioNivel = false;
            }


            System.out.println("Desea cambiar el tipo principal? (si | no): ");
            respuesta = teclado.next();
            if (respuesta.equalsIgnoreCase("si")) {
                cambioTipo1 = true;
            } else {
                cambioTipo1 = false;
            }


            System.out.println("Desea cambiar el tipo secundario? (si | no): ");
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

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        int respuesta = 0;

        try {
            do {
                System.out.println("Introduce una opcion:\n\t1 - Insertar pokemon:\n\t2 - Mostrar todos los pokemon:\n\t3 - Buscar por nombre:\n\t4 - Actualizar pokemon:\n\t5 - Borrar pokemon:\n\t6 - Salir:");
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

                    insertarPokemon(nombre, nivel, tipo1, tipo2);
                    System.out.println();

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

}