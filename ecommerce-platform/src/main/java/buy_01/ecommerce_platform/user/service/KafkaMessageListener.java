package buy_01.ecommerce_platform.user.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaMessageListener {

    @KafkaListener(topics = "user_topic", groupId = "group_id") // Remplacez par le nom de votre topic et l'ID de groupe
    public void listen(String message) {
        System.out.println("Received Message: " + message);
    }
}
