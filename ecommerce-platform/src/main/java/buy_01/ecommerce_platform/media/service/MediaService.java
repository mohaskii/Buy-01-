package buy_01.ecommerce_platform.media.service;

import buy_01.ecommerce_platform.media.model.Media;
import buy_01.ecommerce_platform.media.repository.MediaRepository;
import buy_01.ecommerce_platform.service.KafkaMessageService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;
    private final RestTemplate restTemplate;
    private final String productServiceUrl;
    private final Path root;
    
    private final KafkaMessageService kafkaMessageService;

    public MediaService(MediaRepository mediaRepository, 
                        RestTemplate restTemplate, 
                        @Value("${product.service.url}") String productServiceUrl,
                        @Value("${upload.path}") String uploadPath,
                        KafkaMessageService kafkaMessageService) {
        this.mediaRepository = mediaRepository;
        this.restTemplate = restTemplate;
        this.productServiceUrl = productServiceUrl;
        this.root = Paths.get(uploadPath);
        this.kafkaMessageService = kafkaMessageService;
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(String id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with id: " + id));
    }

    public Media uploadMedia(MultipartFile file, String productId) throws IOException {
        if (!productExists(productId)) {
            throw new RuntimeException("Le produit avec l'ID " + productId + " n'existe pas");
        }

        validateFile(file);

        String fileName = generateFileName(file);
        Path filePath = saveFile(file, fileName);

        Media media = new Media();
        media.setImagePath(filePath.toString());
        media.setProductId(productId);
        media = mediaRepository.save(media);

        kafkaMessageService.sendMediaMessage("Nouveau média uploadé : " + media.getId());
        return media;
    }

    public Media updateMedia(String id, Media media) {
        Media existingMedia = getMediaById(id);
        existingMedia.setImagePath(media.getImagePath());
        existingMedia.setProductId(media.getProductId());
        existingMedia = mediaRepository.save(existingMedia);
        kafkaMessageService.sendMediaMessage("Media " + media.getId() + " a été mis à jour");
        return existingMedia;
    }

    public void deleteMedia(String id) {
        mediaRepository.deleteById(id);
    }

    private boolean productExists(String productId) {
        try {
            restTemplate.getForEntity(productServiceUrl + "/api/products/" + productId, String.class);
            return true;
        } catch (HttpClientErrorException.NotFound e) {
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la vérification de l'existence du produit", e);
        }
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("Le fichier est vide");
        }
        if (file.getSize() > 2 * 1024 * 1024) {
            throw new RuntimeException("La taille du fichier dépasse la limite de 2MB");
        }
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Seuls les fichiers image sont autorisés");
        }
    }

    private String generateFileName(MultipartFile file) {
        return System.currentTimeMillis() + "_" + file.getOriginalFilename();
    }

    private Path saveFile(MultipartFile file, String fileName) throws IOException {
        Path filePath = this.root.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }
}