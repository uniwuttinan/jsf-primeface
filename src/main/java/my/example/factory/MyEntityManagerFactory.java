package my.example.factory;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class MyEntityManagerFactory {
    private static EntityManager entityManager;

    @Produces
    public EntityManager getEntityManagerFactory() {
        if (entityManager == null) {
            // load environment variables and create properties map
            String dbHost = System.getenv("DB_HOST");
            String dbName = System.getenv("DB_NAME");
            String dbUser = System.getenv("DB_USER");
            String dbPassword = System.getenv("DB_PASSWORD");
            String jdbcUrl = "jdbc:mysql://" + dbHost + ":3306/" + dbName;

            // Create properties map
            Map<String, String> properties = new HashMap<>();
            properties.put("javax.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
            properties.put("javax.persistence.jdbc.url", jdbcUrl);
            properties.put("javax.persistence.jdbc.user", dbUser);
            properties.put("javax.persistence.jdbc.password", dbPassword);
            
            entityManager = Persistence.createEntityManagerFactory("jpa_local", properties).createEntityManager();

            // load directly from persistence.xml
            // entityManager = Persistence.createEntityManagerFactory("jpa_local").createEntityManager();
        }
        return entityManager;
    }
}
