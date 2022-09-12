package game.yachu.controller.interceptor;

import game.yachu.exception.SessionMismatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.springframework.web.servlet.HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;

@Slf4j
public class SessionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        log.info("session interceptor: requestURI={}", requestURI);

        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        log.info("PathVariables: {}", pathVariables);

        Long playerId = Long.parseLong(pathVariables.get("id"));

        HttpSession session = request.getSession(false);
        if (session != null && playerId.equals(session.getAttribute("id"))) {
            return true;
        }

        log.info("Invalid Access");
        if (requestURI.startsWith("/game/")) {
            response.sendRedirect("/");
            return true;
        }

        throw new SessionMismatchException();
    }
}
