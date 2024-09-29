package buy_01.ecommerce_platform.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaMessageService {

    @Value("${kafka.topic.user}")
    private String userTopic;

    @Value("${kafka.topic.product}")
    private String productTopic;

    @Value("${kafka.topic.media}")
    private String mediaTopic;

    
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(KafkaMessageService.class);


    public KafkaMessageService(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    

    public void sendUserMessage(String message) {
        log.info("Envoi du message utilisateur : {}", message);
        kafkaTemplate.send(userTopic, message);
        log.info("Message utilisateur envoyé avec succès");
    }

    public void sendProductMessage(String message) {
        log.info("Envoi du message produit : {}", message);
        kafkaTemplate.send(productTopic, message);
        log.info("Message produit envoyé avec succès");
    }

    public void sendMediaMessage(String message) {
        log.info("Envoi du message média : {}", message);
        kafkaTemplate.send(mediaTopic, message);
        log.info("Message média envoyé avec succès");
    }

    @KafkaListener(topics = "${kafka.topic.user}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenUserTopic(String message) {
        log.info("Message reçu sur le topic user-topic : {}", message);
        // Traitement du message utilisateur
    }

    @KafkaListener(topics = "${kafka.topic.product}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenProductTopic(String message) {
        log.info("Message reçu sur le topic product-topic : {}", message);
        // Traitement du message produit
    }

    @KafkaListener(topics = "${kafka.topic.media}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenMediaTopic(String message) {
        log.info("Message reçu sur le topic media-topic : {}", message);
        // Traitement du message média
    }
}