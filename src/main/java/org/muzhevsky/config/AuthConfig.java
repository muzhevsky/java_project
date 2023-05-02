package org.muzhevsky.config;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component("authConfig")
@Order(0)
public final class AuthConfig {
    private final String fileName = "E:\\Projects\\Java\\Project\\src\\main\\java\\org\\muzhevsky\\config\\authConfig.json";
    @Getter
    private AuthConfigData model;


    @PostConstruct
    @SneakyThrows
    @Order(0)
    private void init(){
        Gson gson = new Gson();
        var fis = new FileInputStream(fileName);
        try{
            var content = new byte[256];
            fis.read(content);
            var string = new String(content).trim();
            var cfg = gson.fromJson(string, AuthConfigData.class);

            model = cfg;
        }
        catch (Exception ex){
            System.out.println(ex);
            fis.close();
            model = null;
        }
    }

    private AuthConfig(){

    }
}
