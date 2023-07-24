package ni.com.userApi.servicio.servicio;

import ni.com.userApi.persistencia.Usuario;
import ni.com.userApi.dto.Token;

public interface GeneratorJwt {
    Token crearToken(Usuario usuario);
}
