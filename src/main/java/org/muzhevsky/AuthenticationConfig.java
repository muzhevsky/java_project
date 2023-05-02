package org.muzhevsky;

import com.google.gson.Gson;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.Base64;

@Configuration
@EnableJdbcRepositories("org")
public class AuthenticationConfig extends AbstractJdbcConfiguration {
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

    @Bean
    public TransactionManager transactionManager(DataSource ds) {
        return new DataSourceTransactionManager(ds);
    }

    @Bean
    public NamedParameterJdbcOperations namedParameterJdbcOperations(DataSource ds){
        return new NamedParameterJdbcTemplate(ds);
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
    public Gson gson(){return new Gson();}
}
