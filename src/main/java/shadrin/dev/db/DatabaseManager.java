package shadrin.dev.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import shadrin.dev.entity.MessageEntity;

import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public final class DatabaseManager {
    private static final EntityManagerFactory entityManagerFactory;

    static {
        try {
            Properties props = new Properties();
            InputStream configStream = DatabaseManager.class
                    .getResourceAsStream("/config/mymod.example.properties");

            if (configStream == null) {
                throw new RuntimeException("Config file not found: /config/mymod.example.properties");
            }

            props.load(configStream);
            configStream.close();

            String url = props.getProperty("db.url");
            String username = props.getProperty("db.username");
            String password = props.getProperty("db.password");


            entityManagerFactory = Persistence.createEntityManagerFactory("mc-pu",
                    Map.of(
                            "jakarta.persistence.jdbc.url", url,
                            "jakarta.persistence.jdbc.user", username,
                            "jakarta.persistence.jdbc.password", password
                    ));

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize DatabaseManager", e);
        }
    }
    public static void sendMessage(UUID playerId, String text) {
        if (entityManagerFactory == null) {
            System.out.println("[MYMOD] Database not available, skipping save: " + text);
            return;
        }

        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(new MessageEntity(playerId, text));
            em.getTransaction().commit();
            System.out.println("[MYMOD] Message saved to DB: " + text);
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            System.err.println("[MYMOD] DB Error: " + ex.getMessage());
        } finally {
            em.close();
        }
    }
}
