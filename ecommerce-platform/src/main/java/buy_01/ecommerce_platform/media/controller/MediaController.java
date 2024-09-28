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

    @RestController
    @RequestMapping("/api/media")
    public class MediaController {

        @Autowired
        private MediaService mediaService;

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
        public ResponseEntity<Media> uploadMedia(@RequestParam("file") MultipartFile file, @RequestParam("productId") String productId) {
            try {
                Media media = mediaService.uploadMedia(file, productId);
                return ResponseEntity.status(HttpStatus.CREATED).body(media);
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(null);
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