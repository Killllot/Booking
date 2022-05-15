package newbooking.testCont;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Test {
    @BeforeAll
    private void init() {
        Postgres.container.start();
    }
}
