package ni.com.userApi.servicio.servicio;

import java.util.Optional;
import ni.com.userApi.persistencia.Usuario;

public interface UserService {
  Usuario guardar(Usuario usuario);

  Optional<Usuario> obtenerUsuarioPorEmailClave(String email, String clave);

  Boolean existeEmail(String email);
}
