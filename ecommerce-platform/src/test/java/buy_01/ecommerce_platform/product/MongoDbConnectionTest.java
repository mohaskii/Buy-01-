package buy_01.ecommerce_platform.product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MongoDbConnectionTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testMongoDbConnection() {
        assertNotNull(mongoTemplate);
        assertNotNull(mongoTemplate.getDb());
    }
}
