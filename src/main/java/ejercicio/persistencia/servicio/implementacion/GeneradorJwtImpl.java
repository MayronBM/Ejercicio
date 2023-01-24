package ejercicio.persistencia.servicio.implementacion;

import ejercicio.persistencia.entidad.Usuario;
import ejercicio.persistencia.servicio.servicio.GeneradorJwt;
import ejercicio.web.util.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class GeneradorJwtImpl implements GeneradorJwt {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public Token crearToken(Usuario usuario) {
        String jwtToken = "";
        Date expirationDate = new Date((new Date()).getTime() + 10 * 60 * 1000);
        jwtToken = Jwts.builder()
                .setSubject(usuario.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
        return new Token(jwtToken);
    }
}
