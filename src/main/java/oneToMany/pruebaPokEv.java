package oneToMany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class pruebaPokEv {

    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("UnidadPokemon2");
    private static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {

        Pokemon po = new Pokemon("Charmander", 11, "Fuego", null);
        po.addEvolucion("Charmeleon", 23, "Fuego", null);

        po.addEvolucion(new Evolucion("Charizard", 38, "Fuego", "Volador",po));

        try {
//            em.getTransaction().begin();
//            em.persist(po);
//            em.getTransaction().commit();

            List<Pokemon> pokemons = em
                    .createQuery("SELECT p FROM Pokemon p", Pokemon.class)
                    .getResultList();

            for (Pokemon p: pokemons) {
                System.out.println(p);
            }
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
