package muzhevsky.org.auth.services;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LogoutFilter extends GenericFilterBean {
    @Autowired
    KeycloakAdminService keycloakAdminService;

    @Value("${keycloak.auth-server-url}")
    @Getter
    private String url;
    @Value("${keycloak.resource}")
    @Getter
    private String client;
    @Value("${keycloak.credentials.secret}")
    @Getter
    private String secret;
    @Value("${keycloak.realm}")
    @Getter
    private String realm;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if (!httpRequest.getRequestURI().contains("/logout")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        System.out.println("LOGOUT");
        httpResponse.setHeader("Set-Cookie", "accessToken=empty; Max-Age=0; Path=/");
        httpResponse.sendRedirect("/signin");
    }
}
