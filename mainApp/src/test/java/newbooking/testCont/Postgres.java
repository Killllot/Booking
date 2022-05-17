package newbooking.testCont;

import lombok.experimental.UtilityClass;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class Postgres {

    @Container
    public static final PostgreSQLContainer container;

    static {
        container = new PostgreSQLContainer<>("postgres:latest")
                .withDatabaseName("some-postgres")
                .withPassword("Kirill")
                .withUsername("postgres");

        container.start();
    }
}
