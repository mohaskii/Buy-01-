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

    public Media saveMedia(MultipartFile file, String productId) throws IOException {
        Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
        
        Media media = new Media();
        media.setImagePath(this.root.resolve(file.getOriginalFilename()).toString());
        media.setProductId(productId);
        
        return mediaRepository.save(media);
    }

    public List<Media> getMediaByProductId(String productId) {
        return mediaRepository.findByProductId(productId);
    }

    // Add other methods as needed
}