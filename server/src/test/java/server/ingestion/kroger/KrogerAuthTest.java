package server.ingestion.kroger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KrogerAuthTest {

    @Autowired
    private KrogerAuth krogerAuth;

    @Test
    void shouldGetAccessToken() {
        String token = krogerAuth.getAccessToken();

        System.out.println("Token received: " + token);
    }
}