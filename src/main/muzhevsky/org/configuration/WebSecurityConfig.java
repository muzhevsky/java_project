package muzhevsky.org.configuration;

import com.mysql.cj.jdbc.MysqlDataSource;
import muzhevsky.org.auth.enums.Role;
import muzhevsky.org.auth.exceptions.UnauthorizedHandler;
import muzhevsky.org.auth.services.AuthorizationService;
import muzhevsky.org.auth.services.PublicFilter;
import org.keycloak.adapters.KeycloakConfigResolver;
import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.keycloak.adapters.springsecurity.KeycloakConfiguration;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.web.filter.GenericFilterBean;

import muzhevsky.org.auth.services.RoleFilter;

import javax.sql.DataSource;
import java.util.List;

@KeycloakConfiguration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

    @Autowired
    private UnauthorizedHandler unauthorizedHandler;

    @Override
    protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
        return new NullAuthenticatedSessionStrategy();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authManagerBuilder) {
        KeycloakAuthenticationProvider keycloakAuthenticationProvider = keycloakAuthenticationProvider();
        keycloakAuthenticationProvider.setGrantedAuthoritiesMapper(new SimpleAuthorityMapper());
        authManagerBuilder.authenticationProvider(keycloakAuthenticationProvider);
    }

    @Bean
    public KeycloakConfigResolver keycloakConfigResolver() {
        return new KeycloakSpringBootConfigResolver();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http
                .authorizeRequests()
                .anyRequest().permitAll()
                .and()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    @Order(0)
    GenericFilterBean userFilter(AuthorizationService authorizationService) {
        return new RoleFilter(
                List.of("/myprojects/create"),
                List.of(Role.USER.get()),
                authorizationService
        );
    }

    @Bean
    @Order(0)
    GenericFilterBean companyFilter(AuthorizationService authorizationService) {
        return new RoleFilter(
                List.of("/acceptproject","/createestimate"),
                List.of(Role.COMPANY.get()),
                authorizationService
        );
    }

    @Bean
    @Order(0)
    GenericFilterBean companyOrUserFilter(AuthorizationService authorizationService){
        return new RoleFilter(
                List.of("/estimates"),
                List.of(Role.COMPANY.get(), Role.USER.get()),
                authorizationService
        );
    }

    @Bean
    @Order(0)
    GenericFilterBean anyRoleFilter(AuthorizationService authorizationService) {
        return new RoleFilter(
                List.of("/myprojects"),
                List.of(Role.COMPANY.get(), Role.USER.get(), Role.ADMIN.get()),
                authorizationService
        );
    }

    @Bean
    @Order(1)
    GenericFilterBean publicFilter(AuthorizationService authorizationService){
        return new PublicFilter(
                List.of("/projects","/project/","/errors"),
                authorizationService
        );
    }

    @Bean
    public DataSource getDataSource() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setDatabaseName("project");
        dataSource.setServerName("localhost");
        dataSource.setPort(3306);
        dataSource.setUser("root");
        dataSource.setPassword("MysqlPassssaP1");
        return dataSource;
    }
}