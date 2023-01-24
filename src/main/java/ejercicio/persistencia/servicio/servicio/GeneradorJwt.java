package ejercicio.persistencia.servicio.servicio;

import ejercicio.persistencia.entidad.Usuario;
import ejercicio.web.util.Token;

import java.util.Map;

public interface GeneradorJwt {
    Token crearToken(Usuario usuario);
}
