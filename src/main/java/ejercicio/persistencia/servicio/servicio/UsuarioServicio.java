package ejercicio.persistencia.servicio.servicio;

import ejercicio.persistencia.entidad.Usuario;

import java.util.Optional;

public interface UsuarioServicio {
    Usuario guardar(Usuario usuario);

    Optional<Usuario> obtenerUsuarioPorEmailClave(String email, String clave);

    Boolean existeEmail(String email);
}
