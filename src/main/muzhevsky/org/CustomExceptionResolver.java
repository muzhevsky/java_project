package muzhevsky.org;

import lombok.SneakyThrows;
import muzhevsky.org.auth.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import java.util.stream.Stream;

@Component
public class CustomExceptionResolver extends AbstractHandlerExceptionResolver {
    @Autowired
    AuthorizationService authorizationService;
    @SneakyThrows
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        var authorized = false;
        var accessToken = Stream.of(request.getCookies()).filter(cookie -> cookie.getName().equals("accessToken")).findFirst();
        if (accessToken.isPresent()){
            var roles = authorizationService.getRoles(accessToken.get().getValue());
            authorized = roles.size() > 0;
        }
        if (ex instanceof NotFoundException) {
            var modelAndView = new ModelAndView("/errors/notfound");
            modelAndView.addObject("authorized", authorized);
            return modelAndView;
        }

        var modelAndView = new ModelAndView("/errors/internal");
        modelAndView.addObject("authorized", authorized);
        return modelAndView;
    }
}