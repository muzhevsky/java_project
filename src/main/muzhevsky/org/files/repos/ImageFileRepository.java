package muzhevsky.org.files.repos;

import lombok.SneakyThrows;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;


@Repository("imageFileRepository")
public class ImageFileRepository {
    private final String imagesPath = "src/main/resources/images/";

    @SneakyThrows
    public String save(byte[] image, String fileName) {
        var name = UUID.randomUUID().toString();
        var split = fileName.split("[.]");
        var extension = split[split.length-1];

        var fullFileName = name + "." + extension;
        var filePath = imagesPath + fullFileName;
        var newImage = new File(filePath);
        newImage.createNewFile();

        var outputStream = new FileOutputStream(filePath);
        outputStream.write(image);

        outputStream.close();
        return fullFileName;
    }


    @SneakyThrows
    public byte[] getFileByName(String fileName) {
        var inputStream = new FileInputStream(imagesPath + fileName);
        var file = inputStream.readAllBytes();
        inputStream.close();
        return file;
    }
}
