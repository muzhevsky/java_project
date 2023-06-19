package muzhevsky.org.auth.services;

import muzhevsky.org.auth.exceptions.TokenExpiredException;
import org.keycloak.common.VerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class RoleFilter extends GenericFilterBean {
    private AuthorizationService authorizationService;
    private final List<String> securedResoursesUrls;
    private final List<String> acceptedRoles;

    public RoleFilter(List<String> securedResoursesUrls, List<String> acceptedRoles, AuthorizationService authorizationService) {
        this.acceptedRoles = acceptedRoles;
        this.securedResoursesUrls = securedResoursesUrls;
        this.authorizationService = authorizationService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        boolean authorizationNeeded = securedResoursesUrls.stream().anyMatch(url -> httpRequest.getRequestURI().contains(url));
        if (!authorizationNeeded) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String accessToken = null;
        String refreshToken = null;

        if (httpRequest.getCookies() == null){
            authorizationService.onSigninFailed(httpResponse);
            return;
        }
        for (var cookie : httpRequest.getCookies()) {
            if (cookie.getName().equals("accessToken")) accessToken = cookie.getValue();
            if (cookie.getName().equals("refreshToken")) refreshToken = cookie.getValue();
        }

        if (accessToken != null) {
            try {
                var roles = authorizationService.getRoles(accessToken);
                var acceptedRoleIsPresent = roles.stream().anyMatch(acceptedRoles::contains);
                if (!acceptedRoleIsPresent) {
                    authorizationService.onSigninFailed(httpResponse);
                    return;
                }
                httpRequest.setAttribute("accessToken", accessToken);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            } catch (UsernameNotFoundException ex) {
                authorizationService.onSigninFailed(httpResponse);
                return;
            } catch (TokenExpiredException | VerificationException ex) {
                System.out.println(ex);
                var tokenPair = authorizationService.refreshToken(refreshToken);
                httpResponse.addHeader("Set-Cookie", "accessToken=" + tokenPair.getAccessToken() + "; Max-Age=360000; Path=/");
                httpResponse.addHeader("Set-Cookie", "refreshToken=" + tokenPair.getRefreshToken() + "; Max-Age=360000; Path=/");

                httpRequest.setAttribute("accessToken", tokenPair.getAccessToken());

                try {
                    var roles = authorizationService.getRoles(tokenPair.getAccessToken());
                    var acceptedRoleIsPresent = roles.stream().anyMatch(acceptedRoles::contains);
                    if (!acceptedRoleIsPresent) {
                        authorizationService.onSigninFailed(httpResponse);
                        return;
                    }
                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } catch (Exception e) {
                    System.out.println(e);
                    authorizationService.onSigninFailed(httpResponse);
                    return;
                }
            }
        }

        authorizationService.onSigninFailed(httpResponse);
    }
}
