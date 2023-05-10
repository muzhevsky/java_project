package org.muzhevsky.resources.repos;

import lombok.SneakyThrows;
import org.muzhevsky.resources.dtos.MyFile;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;

@Repository("imageFileRepository")
public class ImageFileRepository {

    private final String path = "resources\\pics\\";

    @SneakyThrows
    public String save(MyFile image){
        var name = Long.toString(Instant.now().getEpochSecond());
        var fullName = name + "." + image.getFormat();

        var outputStream = new FileOutputStream(path+ fullName);
        outputStream.write(image.getContent());

        outputStream.close();
        return fullName;
    }


    @SneakyThrows
    public MyFile getFileByName(String fileName){
        var inputStream = new FileInputStream(path+fileName);
        var split = fileName.split("[.]");
        var format = split[split.length - 1];
        return new MyFile(format, inputStream.readAllBytes());
    }
}
