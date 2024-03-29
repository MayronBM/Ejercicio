package ni.com.userApi.support.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import ni.com.userApi.support.excepcion.ExceptionResponse;
import ni.com.userApi.support.util.Util;
import org.springframework.web.filter.GenericFilterBean;

public class Filter extends GenericFilterBean {

  private final String secret;

  public Filter(String secret) {
    this.secret = secret;
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
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
        response
            .getWriter()
            .write(Util.convertObjectToJson(errorFilter("Debe agregar token de acceso.")));
        return;
      }
    }
    final String token = authHeader.substring(7);
    try {
      Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
      request.setAttribute("claims", claims);
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write(Util.convertObjectToJson(errorFilter("Token invalido.")));
      return;
    }

    filterChain.doFilter(request, response);
  }

  private ExceptionResponse errorFilter(String msg) {
    return new ExceptionResponse(msg);
  }
}
