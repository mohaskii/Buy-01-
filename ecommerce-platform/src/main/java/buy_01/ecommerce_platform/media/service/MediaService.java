package buy_01.ecommerce_platform.media.service;

import buy_01.ecommerce_platform.media.model.Media;
import buy_01.ecommerce_platform.media.repository.MediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.Arrays;

@Service
public class MediaService {

    private static final Logger logger = LoggerFactory.getLogger(MediaService.class);

    @Autowired
    private MediaRepository mediaRepository;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
        "image/jpeg",
        "image/png",
        "image/gif"
    );

    @PostConstruct
    public void init() {
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory!", e);
        }
    }

    public List<Media> getAllMedia() {
        return mediaRepository.findAll();
    }

    public Media getMediaById(String id) {
        return mediaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Media not found with id: " + id));
    }

    public Media uploadMedia(MultipartFile file, String productId) throws IOException {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Failed to store empty file.");
        }

        if (!isValidImageFile(file)) {
            throw new IllegalArgumentException("File must be a valid image (JPEG, PNG, or GIF).");
        }

        String filename = generateUniqueFilename(file.getOriginalFilename());
        Path destinationFile = resolveDestinationFilePath(filename);

        try {
            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            logger.error("Failed to store file.", e);
            throw new IOException("Failed to store file.", e);
        }

        Media media = new Media();
        media.setImagePath(destinationFile.toString());
        media.setProductId(productId);
        return mediaRepository.save(media);
    }

    private boolean isValidImageFile(MultipartFile file) {
        String contentType = file.getContentType();
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        return contentType != null && ALLOWED_CONTENT_TYPES.contains(contentType) &&
               filename != null && (filename.toLowerCase().endsWith(".jpg") || 
                                    filename.toLowerCase().endsWith(".jpeg") || 
                                    filename.toLowerCase().endsWith(".png") || 
                                    filename.toLowerCase().endsWith(".gif"));
    }

    private String generateUniqueFilename(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        return UUID.randomUUID().toString() + "." + extension;
    }

    private Path resolveDestinationFilePath(String filename) throws IOException {
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        return uploadPath.resolve(filename);
    }

    public Media updateMedia(String id, Media media) {
        Media existingMedia = getMediaById(id);
        existingMedia.setImagePath(media.getImagePath());
        existingMedia.setProductId(media.getProductId());
        return mediaRepository.save(existingMedia);
    }

    public void deleteMedia(String id) {
        Media media = getMediaById(id);
        try {
            Files.deleteIfExists(Paths.get(media.getImagePath()));
        } catch (IOException e) {
            logger.error("Failed to delete file: " + media.getImagePath(), e);
        }
        mediaRepository.deleteById(id);
    }
}