package buy_01.ecommerce_platform.media.config;

import com.fasterxml.jackson.databind.ObjectMapper;

import buy_01.ecommerce_platform.media.model.Media;

import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class MediaDeserializer implements Deserializer<Media> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public Media deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, Media.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize Media", e);
        }
    }

    @Override
    public void close() {
    }
}
