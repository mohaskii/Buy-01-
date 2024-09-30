package buy_01.ecommerce_platform.user.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class StorageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String storeFile(MultipartFile file) throws Exception {
        String fileName = file.getOriginalFilename();
        Path path = Paths.get(uploadPath).resolve(fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return fileName;
    }


}