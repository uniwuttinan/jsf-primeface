package my.example.factory;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class MyEntityManagerFactory {
    private static EntityManager entityManager;

    @Produces
    public EntityManager getEntityManagerFactory() {
        if (entityManager == null) {
            entityManager = Persistence.createEntityManagerFactory("jpa_local").createEntityManager();
        }
        return entityManager;
    }
}
