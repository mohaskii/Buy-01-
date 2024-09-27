package buy_01.ecommerce_platform.media.controller;

import buy_01.ecommerce_platform.media.model.Media;
import buy_01.ecommerce_platform.media.service.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @PostMapping("/{productId}")
    public ResponseEntity<Media> uploadMedia(@RequestParam("file") MultipartFile file, @PathVariable String productId) {
        try {
            Media savedMedia = mediaService.saveMedia(file, productId);
            return ResponseEntity.ok(savedMedia);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/product/{productId}")
    public List<Media> getMediaByProductId(@PathVariable String productId) {
        return mediaService.getMediaByProductId(productId);
    }

    // Add other endpoints as needed
}