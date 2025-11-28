package oneToManyIdNombre;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class pruebaPokEv {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon3");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        Pokemon po = new Pokemon("Charmander", 10, "Fuego", null);

        po.addEvolucion("Charmeleon", 20, "Fuego", null);
        po.addEvolucion("Charizard", 38, "Fuego", "Volador");

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

        em.close();
        emf.close();
    }
}
