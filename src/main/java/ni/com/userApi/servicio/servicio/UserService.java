package ni.com.userApi.servicio.servicio;

import ni.com.userApi.persistencia.Usuario;

import java.util.Optional;

public interface UserService {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> obtenerUsuarioPorEmailClave(String email, String clave);

    Boolean existeEmail(String email);
}
