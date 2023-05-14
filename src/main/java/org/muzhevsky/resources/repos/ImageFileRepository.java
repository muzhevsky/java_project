package org.muzhevsky.resources.repos;

import lombok.SneakyThrows;
import org.muzhevsky.resources.dtos.MyFile;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;

@Repository("imageFileRepository")
public class ImageFileRepository {

    private final String path = "resources\\images\\";

    @SneakyThrows
    public String save(MyFile image){
        var name = Long.toString(Instant.now().getEpochSecond());
        var split = image.getName().split("[.]");
        var fullName = name + "." + split[split.length - 1];

        var outputStream = new FileOutputStream(path+ fullName);
        outputStream.write(image.getContent());

        outputStream.close();
        return fullName;
    }


    @SneakyThrows
    public MyFile getFileByName(String fileName){
        var inputStream = new FileInputStream(path+fileName);
        var file = new MyFile(fileName, inputStream.readAllBytes());
        inputStream.close();
        return file;
    }
}
