package buy_01.ecommerce_platform.media.service;

import buy_01.ecommerce_platform.media.model.Media;
import buy_01.ecommerce_platform.media.repository.MediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class MediaService {

    @Autowired
    private MediaRepository mediaRepository;

    private final Path root = Paths.get("uploads");

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(String id) {
        return mediaRepository.findById(id).orElse(null);
    }

    public Media uploadMedia(MultipartFile file, String productId) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        Media media = new Media();
        media.setImagePath(this.root.resolve(file.getOriginalFilename()).toString());
        media.setProductId(productId);
        return mediaRepository.save(media);
    }

    public Media updateMedia(String id, Media media) {
        Media existingMedia = getMediaById(id);
        if (existingMedia != null) {
            // Use getters and setters instead of direct access
            existingMedia.setImagePath(media.getImagePath());
            existingMedia.setProductId(media.getProductId());
            return mediaRepository.save(existingMedia);
        }
        return null;
    }

    public void deleteMedia(String id) {
        mediaRepository.deleteById(id);
    }
}
