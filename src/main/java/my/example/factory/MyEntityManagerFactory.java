package my.example.factory;

import my.example.enums.DatabaseSaveMode;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class MyEntityManagerFactory {
    private static final Logger logger = Logger.getLogger(MyEntityManagerFactory.class.getName());
    private static EntityManager entityManager;

    // MySQL Template from persistence.xml
    // <!-- <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>-->
    // <!-- <property name="javax.persistence.jdbc.url" value="jdbc:mysql://${DB_HOST}:3306/${DB_NAME}"/>-->
    // <!-- <property name="javax.persistence.jdbc.user" value="${DB_USER}"/>-->
    // <!-- <property name="javax.persistence.jdbc.password" value="${DB_PASSWORD}"/>-->
    // <!-- <property name="eclipselink.ddl-generation" value="create-tables"/>-->
    // <!-- <property name="eclipselink.ddl-generation.output-mode" value="database"/>-->
    // <!-- <property name="javax.persistence.schema-generation.database.action" value="create"/>-->
    // <!-- <property name="javax.persistence.schema-generation.scripts.action" value="create"/>-->
    private static Map<String, String> createMySQLConnectionProperties() {
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
        //auto create tables
        properties.put("eclipselink.ddl-generation", "create-tables");
        properties.put("eclipselink.ddl-generation.output-mode", "database");
        properties.put("javax.persistence.schema-generation.database.action", "create");
        properties.put("javax.persistence.schema-generation.scripts.action", "create");
        
        return properties;
    }

    // SQLite Template from persistence.xml
    // <property name="javax.persistence.jdbc.driver" value="org.sqlite.JDBC"/>
    // <property name="eclipselink.ddl-generation" value="create-tables"/>
    // <property name="eclipselink.ddl-generation.output-mode" value="database"/>
    // <!-- SQLITE SAVE TO FILE-->
    // <property name="javax.persistence.jdbc.url" value="jdbc:sqlite:./my_db.sqlite"/>
    // <!-- SQLITE IN MEMORY-->
    // <!-- <property name="javax.persistence.jdbc.url" value="jdbc:sqlite::memory:"/>-->
    private static Map<String, String> createSQLiteProperties(DatabaseSaveMode mode) {
        // Create properties map
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", "org.sqlite.JDBC");
        if (mode == DatabaseSaveMode.IN_MEMORY) {
            properties.put("javax.persistence.jdbc.url", "jdbc:sqlite::memory:");
        } else {
            properties.put("javax.persistence.jdbc.url", "jdbc:sqlite:./my_db.sqlite");
        }
        properties.put("eclipselink.ddl-generation", "create-tables");
        properties.put("eclipselink.ddl-generation.output-mode", "database");
        return properties;
    }

    // HSQLDB Template from persistence.xml
    //  <!-- <property name="javax.persistence.jdbc.driver" value="org.hsqldb.util.DatabaseManagerSwing" />-->
    //  <!-- <property name="javax.persistence.jdbc.driver" value="org.hsqldb.jdbc.JDBCDriver"/>-->
    //  <!-- <property name="eclipselink.ddl-generation" value="create-tables"/>-->
    //  <!-- <property name="eclipselink.ddl-generation.output-mode" value="database"/>-->
    //  <!-- HSQLDB IN MEMORY -->
    //  <!-- <property name="javax.persistence.jdbc.url" value="jdbc:hsqldb:mem:testdb"/>-->
    //  <!-- HSQLDB SAVE TO FILE-->
    //  <!-- <property name="javax.persistence.jdbc.url" value="jdbc:hsqldb:file:./my_db.hsqldb"/>-->
    private static Map<String, String> createHSQLDBProperties(DatabaseSaveMode mode) {
        // Create properties map
        Map<String, String> properties = new HashMap<>();
        properties.put("javax.persistence.jdbc.driver", "org.hsqldb.jdbc.JDBCDriver");
        if (mode == DatabaseSaveMode.IN_MEMORY) {
            properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:mem:testdb");
        } else {
            properties.put("javax.persistence.jdbc.url", "jdbc:hsqldb:file:./my_db.hsqldb");
        }
        properties.put("eclipselink.ddl-generation", "create-tables");
        properties.put("eclipselink.ddl-generation.output-mode", "database");
        return properties;
    }


    // Singleton EntityManager
    @Produces
    public EntityManager getEntityManagerFactory() {
        if (entityManager == null) {
            String DB_TYPE = getDatabaseType();
            logger.info("DB_TYPE: " + DB_TYPE);
            Map<String, String> properties;

            switch (DB_TYPE == null ? "" : DB_TYPE) {
                case "MYSQL":
                    properties = createMySQLConnectionProperties();
                    break;
                case "HSQLDB_IN_MEMORY":
                    properties = createHSQLDBProperties(DatabaseSaveMode.IN_MEMORY);
                    break;
                case "HSQLDB_TO_FILE":
                    properties = createHSQLDBProperties(DatabaseSaveMode.TO_FILE);
                    break;
                case "SQLITE_TO_FILE":
                    properties = createSQLiteProperties(DatabaseSaveMode.TO_FILE);
                    break;
                default:
                    properties = createSQLiteProperties(DatabaseSaveMode.IN_MEMORY);
                    break;
            }

            entityManager = Persistence.createEntityManagerFactory("jpa_local", properties).createEntityManager();
        }

        return entityManager;
    }

    public static String getDatabaseType() {
        String DB_TYPE = System.getenv("DB_TYPE");
        return DB_TYPE == null ? "SQLITE_IN_MEMORY" : DB_TYPE;
    }
}
