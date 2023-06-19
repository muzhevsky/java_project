package muzhevsky.org.files.controllers;

import muzhevsky.org.files.repos.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {
    @Autowired
    @Qualifier("imageFileRepository")
    ImageFileRepository fileService;

    @GetMapping("images/{imageFileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String imageFileName) {
        try {
            var image = fileService.getFileByName(imageFileName);
            return new ResponseEntity<>(image, HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}