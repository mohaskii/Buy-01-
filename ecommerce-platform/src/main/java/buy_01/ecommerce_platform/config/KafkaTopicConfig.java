package buy_01.ecommerce_platform.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${kafka.topic.user}")
    private String userTopic;

    @Value("${kafka.topic.product}")
    private String productTopic;

    @Value("${kafka.topic.media}")
    private String mediaTopic;

    @Bean
    public NewTopic userTopic() {
        return TopicBuilder.name(userTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic productTopic() {
        return TopicBuilder.name(productTopic).partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic mediaTopic() {
        return TopicBuilder.name(mediaTopic).partitions(1).replicas(1).build();
    }
}
