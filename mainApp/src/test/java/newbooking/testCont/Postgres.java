package newbooking.testCont;

import lombok.experimental.UtilityClass;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;


public class Postgres {

    @Container
    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest");


}
