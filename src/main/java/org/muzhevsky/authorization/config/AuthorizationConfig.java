package org.muzhevsky.authorization.config;

import com.google.gson.Gson;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;

@Component("authConfig")
@Order(0)
public final class AuthorizationConfig {
    private final String fileName = "src\\main\\java\\org\\muzhevsky\\authorization\\config\\AuthorizationConfig.json";
    @Getter
    private AuthorizationConfigData model;


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
            var cfg = gson.fromJson(string, AuthorizationConfigData.class);

            model = cfg;
        }
        catch (Exception ex){
            System.out.println(ex);
            fis.close();
            model = null;
        }
    }

    private AuthorizationConfig(){

    }
}
