package ni.com.userApi.servicio.implementacion;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import ni.com.userApi.dto.Token;
import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.servicio.servicio.GeneratorJwt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeneratorJwtImpl implements GeneratorJwt {

  @Value("${jwt.secret}")
  private String secret;

  @Value("${token.tiempo}")
  private int min;

  @Override
  public Token crearToken(Usuario usuario) {
    String jwtToken = "";
    Date expirationDate = new Date((new Date()).getTime() + min * 60 * 1000);
    jwtToken =
        Jwts.builder()
            .setSubject(usuario.getEmail())
            .setIssuedAt(new Date())
            .setExpiration(expirationDate)
            .signWith(SignatureAlgorithm.HS256, secret)
            .compact();
    return new Token(jwtToken);
  }
}
