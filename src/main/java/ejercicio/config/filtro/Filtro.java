package ejercicio.config.filtro;

import ejercicio.excepcion.ExceptionResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static ejercicio.web.util.Util.convertObjectToJson;

public class Filtro extends GenericFilterBean {

    private String secret;

    public Filtro(String secret) {
        this.secret = secret;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        final String authHeader = request.getHeader("authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            filterChain.doFilter(request, response);
        } else {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write(convertObjectToJson(errorFilter("Debe agregar token de acceso.")));
                return;
            }
        }
        final String token = authHeader.substring(7);
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
            request.setAttribute("claims", claims);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(convertObjectToJson(errorFilter("Token invalido.")));
            return;
        }

        filterChain.doFilter(request, response);
    }

    private ExceptionResponse errorFilter(String msg) {
        return new ExceptionResponse(msg);
    }
}