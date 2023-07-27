package ni.com.userApi.servicio.servicio;

import ni.com.userApi.dto.Token;
import ni.com.userApi.persistencia.Usuario;

public interface GeneratorJwt {
  Token crearToken(Usuario usuario);
}
