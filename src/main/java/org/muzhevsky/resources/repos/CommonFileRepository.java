package org.muzhevsky.resources.repos;

import lombok.SneakyThrows;
import org.muzhevsky.resources.dtos.MyFile;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service("commonFileRepository")
public class CommonFileRepository {
    private final String path = "resources\\files\\";
    private final Random random = new Random();
    @SneakyThrows
    public String save(MyFile file){
        var instant = Instant.now();
        var name = Long.toString(instant.getEpochSecond() + instant.getNano() + random.nextLong());
        var fullName = name + "." + file.getFormat();

        var outputStream = new FileOutputStream(path+fullName);
        outputStream.write(file.getContent());

        outputStream.close();
        System.out.println(fullName);
        return fullName;
    }

    @SneakyThrows
    public String zip(List<String> fileNames){

        var seconds = Long.toString(Instant.now().getEpochSecond() + random.nextLong());
        var fullName = seconds + ".zip";

        FileOutputStream fos = new FileOutputStream(path + fullName);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (var name : fileNames){
            File fileToZip = new File(path+name);
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
            zipOut.putNextEntry(zipEntry);

            byte[] bytes = new byte[1024];
            int length;
            while((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();
        }
        zipOut.close();
        fos.close();

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
