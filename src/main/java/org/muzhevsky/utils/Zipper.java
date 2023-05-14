package org.muzhevsky.utils;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Instant;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class Zipper {
    private final String path = "resources\\files\\";
    private final Random random = new Random();

    @SneakyThrows
    public String zip(int projectId, List<String> fileNames){
        var localPath = projectId + "\\" + projectId + ".zip";

        FileOutputStream fos = new FileOutputStream(this.path + localPath);
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        for (var name : fileNames){
            File fileToZip = new File(this.path +name);
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

        return projectId + ".zip";
    }
}
