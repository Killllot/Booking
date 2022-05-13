package com.newbooking.testCont;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(initializers =  {
        Postgres.Initializer.class
})
public class Test {
    @BeforeAll
    private void init() {
        Postgres.container.start();
    }
}
