package org.muzhevsky;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

import javax.sql.DataSource;
import java.util.Base64;
import java.util.Random;

@Configuration
@EnableJdbcRepositories("org")
public class Config extends AbstractJdbcConfiguration {
    @Bean("dataSource")
    public DataSource dataSource() {
        var datasource = new MysqlDataSource();

        datasource.setDatabaseName("project");
        datasource.setServerName("localhost");
        datasource.setPort(3306);
        datasource.setUser("root");
        datasource.setPassword("MysqlPassssaP1");

        return datasource;
    }

    @Bean("encoder")
    public Base64.Encoder base64Encoder(){
        return Base64.getEncoder();
    }

    @Bean("decoder")
    public Base64.Decoder base64Decoder(){
        return Base64.getDecoder();
    }

    @Bean("gson")
    public Gson gson(){ return new Gson(); }

    @Bean("random")
    public Random random(){ return new Random(); }
}
