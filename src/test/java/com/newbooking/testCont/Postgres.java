package com.newbooking.testCont;

import lombok.experimental.UtilityClass;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

@UtilityClass
public class Postgres {


    public static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:latest")
            .withDatabaseName("some-postgres")
            .withUsername("postgres")
            .withPassword("mysecretpassword");

    public static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        @Override
        public void initialize(ConfigurableApplicationContext applicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=jdbc:postgresql://localhost:5432/some-postgres\n",
                    "spring.datasource.username=postgres\n",
                    "spring.datasource.password=mysecretpassword"
            ).applyTo(applicationContext);
        }
    }
}
