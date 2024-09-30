package buy_01.ecommerce_platform.media.controller;

import buy_01.ecommerce_platform.media.model.Media;
import buy_01.ecommerce_platform.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    @Autowired
    private MediaService mediaService;

    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    @GetMapping
    public ResponseEntity<List<Media>> getAllMedia() {
        List<Media> mediaList = mediaService.getAllMedia();
        return ResponseEntity.ok(mediaList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Media> getMediaById(@PathVariable String id) {
        Media media = mediaService.getMediaById(id);
        return ResponseEntity.ok(media);
    }

    @PostMapping
    public ResponseEntity<?> uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
        try {
            // Validation des entrées
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Le fichier est vide");
            }
            if (productId == null || productId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("L'ID du produit est requis");
            }
    
            // Log des informations sur le fichier
            logger.info("Tentative d'upload du fichier : " + file.getOriginalFilename() + " pour le produit : " + productId);
    
            Media media = mediaService.uploadMedia(file, productId);
            logger.info("Media uploadé avec succès : " + media.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(media);
        } catch (IOException e) {
            logger.error("Erreur lors de l'upload du fichier", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'upload du fichier : " + e.getMessage());
        } catch (Exception e) {
            logger.error("Erreur inattendue lors de l'upload du fichier", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur inattendue s'est produite : " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Media> updateMedia(@PathVariable String id, @RequestBody Media media) {
        Media updatedMedia = mediaService.updateMedia(id, media);
        return ResponseEntity.ok(updatedMedia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedia(@PathVariable String id) {
        mediaService.deleteMedia(id);
        return ResponseEntity.noContent().build();
    }
}
